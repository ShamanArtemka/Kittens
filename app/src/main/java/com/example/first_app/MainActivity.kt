package com.example.first_app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.NavigationRailItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.first_app.data_classes.alignYourBodyData
import com.example.first_app.data_classes.favoriteCollectionsData
import com.example.first_app.ui.theme.First_appTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            First_appTheme {
                val windowSizeClass = calculateWindowSizeClass(this)
                AppNavigation(windowSizeClass, viewModel)
            }
        }
    }
}

@Composable
fun FinalMainScreen(navController: NavController, windowSize: WindowSizeClass) {



    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MySootheAppPortrait(navController)
        }
        WindowWidthSizeClass.Expanded -> {
            MySootheAppLandscape(navController)
        }
    }
}

@Composable
fun MySootheAppLandscape(navController: NavController) {
    First_appTheme {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Row {
                SootheNavigationRail(navController = navController)
                HomeScreen(Modifier)
            }
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MySootheAppPortrait(navController: NavController) {
    First_appTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation(navController = navController) },
            containerColor = MaterialTheme.colorScheme.secondary
        ) { padding ->
            HomeScreen(Modifier.padding(padding))
        }
    }
}

@Composable
fun SootheNavigationRail(modifier : Modifier = Modifier, navController: NavController) {
    NavigationRail(
        modifier = modifier.padding(start = 8.dp, end = 8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        Column(
            modifier = modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.btn_vavigation_star)
                        )
                    },
                    selected = true,
                    onClick = {
                        navController.navigate(NavGraph.MainActivity)
                    }
                )
                Spacer(modifier = Modifier.height(8.dp))
                NavigationRailItem(
                    icon = {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = null
                        )
                    },
                    label = {
                        Text(
                            text = stringResource(R.string.btn_vavigation_account)
                        )
                    },
                    selected = true,
                    onClick = {
                        navController.navigate(NavGraph.HomeScreen)
                    }
                )

        }
    }
}
//Главная функция, содержащая весь контент UI элементов
@Composable
fun HomeScreen(modifier: Modifier = Modifier){
    Column(modifier.verticalScroll(rememberScrollState())){
        Spacer(Modifier.height(16.dp))
        SearchBar(Modifier.padding(horizontal = 16.dp))
        HomeSection (title = R.string.first_section) {
            AlignYourBody_row()
        }
        HomeSection (title = R.string.second_section) {
            FavoriteCollections_grid()
        }
        Spacer(Modifier.height(16.dp))

    }
}
@Composable
fun SootheBottomNavigation(modifier: Modifier = Modifier, navController: NavController){
    NavigationBar(
        modifier = modifier.height(70.dp),
        containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ) {
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.btn_vavigation_star)
                )
            },
            selected = true,
            onClick = {
                navController.navigate(NavGraph.MainActivity)
            }
        )
        NavigationBarItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text(
                    text = stringResource(R.string.btn_vavigation_account)
                )
            },
            selected = true,
            onClick = {
                navController.navigate(NavGraph.HomeScreen)
            }
        )
    }
}
//Функция добавляющая название и прорисвки секции интерфейса
@Composable
fun HomeSection(
    @StringRes title: Int = R.string.default_text,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Column(modifier) {
        Text(
            color = Color.Black,
            text = stringResource(title),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .paddingFromBaseline(top = 40.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
        )
        content()
    }
}

//Готовые части с интерактивным контентом
@Composable
fun AlignYourBody_row(
    modifier : Modifier = Modifier
){
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 16.dp),
        modifier = modifier
    ) {
        this.items(alignYourBodyData) { item ->
            AlignYourBody_element(photo = item.drawable, text = item.text)
        }
    }
}
@Composable
fun FavoriteCollections_grid(
    modifier : Modifier = Modifier
) {
    LazyHorizontalGrid(
        rows = GridCells.Fixed(2),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = modifier
            .height(168.dp)
    ) {
        this.items(favoriteCollectionsData) { item ->
            FavoriteCollections_element(photo = item.drawable,text = item.text)
        }
    }
}


//Cоставные части контента
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(modifier: Modifier = Modifier){

        TextField(
            value = "",
            onValueChange = {},
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null
                )
            },
            placeholder = {
                Text(stringResource(R.string.search))
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(
                MaterialTheme.colorScheme.primary, MaterialTheme.colorScheme.surface
            ),
            modifier = modifier
                .fillMaxWidth()
                .heightIn(min = 56.dp),
        )

}
@Composable
fun AlignYourBody_element(
    modifier: Modifier = Modifier.padding(8.dp),
    photo: Int = R.drawable.kat1,
    text: String = stringResource(R.string.default_text)
){
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 3.dp,
        color = Color(217, 241, 255)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(88.dp)
                    .clip(CircleShape)
            )
            Text(
                color = Color.Black,
                text = text,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 24.dp, bottom = 16.dp)
            )
        }
    }
}
@Composable
fun FavoriteCollections_element(
    modifier: Modifier = Modifier.padding(8.dp),
    photo : Int = R.drawable.kat5,
    text: String = stringResource(R.string.default_text)
){
    Surface(
        shape = MaterialTheme.shapes.extraSmall,
        shadowElevation = 2.dp,
        color = Color(217, 241, 255)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = modifier
                .widthIn(min = 255.dp)
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(80.dp)
            )
            Text(
                text = text,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
            )
        }
    }

}

@Preview(heightDp = 300, widthDp = 850)
@Composable
fun screen () {
    val navController = rememberNavController()
    First_appTheme {
        MySootheAppLandscape(navController)
    }
}