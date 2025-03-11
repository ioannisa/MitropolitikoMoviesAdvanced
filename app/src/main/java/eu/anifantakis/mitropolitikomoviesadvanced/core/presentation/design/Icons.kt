package eu.anifantakis.mitropolitikomoviesadvanced.core.presentation.design

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import eu.anifantakis.mitropolitikomoviesadvanced.R

object Icons {
    // Icons from resources
    val launcher: ImageVector
        @Composable
        get() = ImageVector.vectorResource(id = R.drawable.ic_launcher_foreground)

    // Icons from material design
    val favoriteOutlined: ImageVector
        @Composable
        get() = Icons.Filled.FavoriteBorder

    val favoriteFilled: ImageVector
        @Composable
        get() = Icons.Filled.Favorite
}