package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.utils

import androidx.lifecycle.SavedStateHandle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.serialization.KSerializer
import kotlinx.serialization.serializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.jsonObject
import kotlin.properties.PropertyDelegateProvider
import kotlin.properties.ReadOnlyProperty

/**
 * Default JSON configuration optimized for state serialization in SavedStateHandle.
 * - ignoreUnknownKeys: Prevents deserialization failures when schema evolves
 * - isLenient: Allows more flexible JSON parsing
 * - encodeDefaults: Ensures all fields are included in serialization
 * - prettyPrint: Disabled for storage efficiency
 */
val defaultJson = Json {
    ignoreUnknownKeys = true
    isLenient = true
    encodeDefaults = true
    prettyPrint = false // More compact representation for storage efficiency
}

/**
 * Creates a MutableStateFlow backed by SavedStateHandle with automatic key inference and selective persistence.
 * Combines the power of Kotlin StateFlow with SavedStateHandle persistence.
 *
 * This function is especially useful for MVI architecture patterns where you have a single state object
 * but need certain properties (like loading states) to reset after process death.
 *
 * Usage:
 * ```
 * // Property name "_state" will be used as the key
 * private val _state by savedStateHandle.getMutableStateFlow(
 *     defaultValue = ProductState(),
 *     coroutineScope = viewModelScope,
 *     transientProperties = listOf("isLoading", "errorMessage")
 * )
 * val state = _state.asStateFlow()
 *
 * // Later, update it:
 * _state.update { it.copy(isLoading = true) }
 * ```
 *
 * @param key Optional key to store the serialized value in SavedStateHandle. If null, property name is used.
 * @param defaultValue The default value to use if no value is stored or deserialization fails
 * @param coroutineScope The CoroutineScope to launch flow collection in (typically viewModelScope)
 * @param transientProperties List of property names that should NOT be persisted across process death
 * @param json The Json instance to use for serialization/deserialization
 * @return A PropertyDelegateProvider that returns a MutableStateFlow
 */
inline fun <reified T : Any> SavedStateHandle.getMutableStateFlow(
    defaultValue: T,
    coroutineScope: CoroutineScope,
    key: String? = null,
    transientProperties: List<String> = emptyList(),
    json: Json = defaultJson
): PropertyDelegateProvider<Any, ReadOnlyProperty<Any, MutableStateFlow<T>>> {
    return PropertyDelegateProvider { thisRef, property ->
        val actualKey = key ?: property.name

        // Create a serializable MutableStateFlow with selective persistence
        val stateFlow = createSerializableStateFlow(
            key = actualKey,
            defaultValue = defaultValue,
            coroutineScope = coroutineScope,
            transientProperties = transientProperties,
            json = json
        )

        // Return a read-only property delegate that provides the StateFlow
        ReadOnlyProperty { _, _ -> stateFlow }
    }
}

/**
 * Internal helper method that creates a MutableStateFlow with serialization and selective persistence.
 * This is the core implementation that handles the complex logic of:
 * 1. Retrieving and deserializing state from SavedStateHandle
 * 2. Handling transient properties during restoration
 * 3. Setting up automatic persistence of state changes back to SavedStateHandle
 * 4. Filtering out transient properties during serialization
 *
 * This function should not be called directly - use getMutableStateFlow() instead.
 *
 * @param key Key to store the serialized value under in SavedStateHandle
 * @param defaultValue The default value to use if no value is stored or deserialization fails
 * @param coroutineScope The CoroutineScope to launch flow collection in
 * @param transientProperties List of property names that should NOT be persisted across process death
 * @param json The Json instance to use for serialization/deserialization
 * @return A MutableStateFlow that automatically persists changes to SavedStateHandle
 */
@PublishedApi
internal inline fun <reified T : Any> SavedStateHandle.createSerializableStateFlow(
    key: String,
    defaultValue: T,
    coroutineScope: CoroutineScope,
    transientProperties: List<String>,
    json: Json = defaultJson
): MutableStateFlow<T> {
    val serializer = serializer<T>() as KSerializer<T>

    // Get initial value from SavedStateHandle or use default
    val savedString = get<String>(key)
    val initialValue = if (savedString != null) {
        try {
            // If we have saved state, deserialize it
            val jsonElement = json.parseToJsonElement(savedString)

            if (transientProperties.isEmpty()) {
                // No transient properties, deserialize directly
                json.decodeFromJsonElement(serializer, jsonElement)
            } else {
                // Handle transient properties - deserialize, but reset transient properties
                val persistedState = json.decodeFromJsonElement(serializer, jsonElement)

                // Create defaultValue JsonElement to extract default values for transient properties
                val defaultJsonElement = json.encodeToJsonElement(serializer, defaultValue)
                val defaultJsonObject = defaultJsonElement.jsonObject

                // Create a merged state by starting with persisted state
                val mergedJsonElement = json.encodeToJsonElement(serializer, persistedState)

                // If persisted state is a JsonObject, we can selectively reset transient properties
                if (mergedJsonElement is JsonObject) {
                    // Create a new JSON object with transient properties reset to defaults
                    val mergedJsonObject = buildJsonObject {
                        // Copy all properties from the persisted state
                        mergedJsonElement.jsonObject.forEach { (propName, propValue) ->
                            put(propName, propValue)
                        }

                        // Reset transient properties to default values
                        transientProperties.forEach { propName ->
                            if (defaultJsonObject.containsKey(propName)) {
                                put(propName, defaultJsonObject[propName] ?: JsonPrimitive(null))
                            }
                        }
                    }

                    // Deserialize the merged state
                    json.decodeFromJsonElement(serializer, mergedJsonObject)
                } else {
                    // If not a JsonObject, just use the persisted state as is
                    persistedState
                }
            }
        } catch (e: Exception) {
            println("Error deserializing value for key '$key': ${e.message}")
            defaultValue
        }
    } else {
        defaultValue
    }

    // Create the StateFlow with initial value
    val stateFlow = MutableStateFlow(initialValue)

    // Observe the StateFlow and update SavedStateHandle on each change
    stateFlow.onEach {
        try {
            if (transientProperties.isEmpty()) {
                // No transient properties, serialize the entire state
                val serialized = json.encodeToString(serializer, it)
                set(key, serialized)
            } else {
                // Serialize but exclude transient properties if possible
                val jsonElement = json.encodeToJsonElement(serializer, it)

                if (jsonElement is JsonObject) {
                    // Create a filtered JSON object without transient properties
                    val filteredJsonObject = buildJsonObject {
                        jsonElement.forEach { (propName, propValue) ->
                            if (propName !in transientProperties) {
                                put(propName, propValue)
                            }
                        }
                    }

                    // Store the filtered state
                    val serialized =
                        json.encodeToString(JsonObject.serializer(), filteredJsonObject)
                    set(key, serialized)
                } else {
                    // If not a JsonObject, just serialize the entire state
                    val serialized = json.encodeToString(serializer, it)
                    set(key, serialized)
                }
            }
        } catch (e: Exception) {
            println("Error serializing value for key '$key': ${e.message}")
        }
    }.launchIn(coroutineScope)

    return stateFlow
}