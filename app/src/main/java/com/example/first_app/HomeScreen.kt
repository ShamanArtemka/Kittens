package com.example.first_app

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.first_app.data_classes.Photos
import com.example.first_app.ui.theme.First_appTheme



class HomeScreen : ComponentActivity() {

    private val viewModel: MyViewModel by viewModels()

    @OptIn(ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass = calculateWindowSizeClass(this)
            AppNavigation(windowSizeClass, viewModel)
        }
    }
}
@Composable
fun AppNavigation(windowSize: WindowSizeClass, viewModel: MyViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = NavGraph.HomeScreen) {
        composable(NavGraph.HomeScreen) {
            HomeScreen(navController = navController, viewModel = viewModel, windowSize = windowSize)
        }
        composable(NavGraph.MainActivity) {
            MainActivityScreen(navController = navController, windowSize = windowSize)
        }
    }
}
@Composable
fun HomeScreen(navController: NavController, viewModel: MyViewModel, windowSize: WindowSizeClass) {
    FinalAccountScreen(navController, windowSize, viewModel)
}

@Composable
fun MainActivityScreen(navController: NavController,windowSize: WindowSizeClass) {
    FinalMainScreen(navController , windowSize)
}


@Composable
fun FinalAccountScreen(navController: NavController, windowSize: WindowSizeClass, viewModel: MyViewModel){
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            MyAccountAppPortrait(viewModel, navController)
        }
        WindowWidthSizeClass.Expanded -> {
            MyAccountAppLandscape(viewModel, navController)
        }
    }
}


@Composable
fun MyAccountAppLandscape(viewModel: MyViewModel, navController: NavController) {
    First_appTheme {
        Surface(color = MaterialTheme.colorScheme.secondary) {
            Row {
                SootheNavigationRail(navController = navController)
                ContentPart_landscape(viewModel = viewModel)
            }
        }
    }
}
@Composable
fun MyAccountAppPortrait(viewModel: MyViewModel, navController: NavController) {
    First_appTheme {
        Scaffold(
            bottomBar = { SootheBottomNavigation(navController = navController) },
            containerColor = MaterialTheme.colorScheme.secondary
        ) { padding ->
            ContentPart_portrait(Modifier.padding(padding),viewModel = viewModel)
        }
    }
}

@Composable
fun ContentPart_landscape(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    var  index_of_photoAvatar by rememberSaveable { mutableStateOf(R.drawable.kat4) }

    Surface (
        Modifier.fillMaxSize()
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Spacer(Modifier.height(20.dp))
                accountPhotoBox(viewModel)
                Spacer(Modifier.height(16.dp))

            }

            ListOfPictures(
                Photos = Photos,
                photoAvatar = index_of_photoAvatar,
                onPhotoAvatarChanged = { newIndex -> viewModel.updateSelectedPhotoIndex(newIndex) }
            )
            Spacer(Modifier.height(16.dp))

        }
    }




}

@Composable
fun ContentPart_portrait(modifier: Modifier = Modifier, viewModel: MyViewModel) {

    var  index_of_photoAvatar by rememberSaveable { mutableStateOf(R.drawable.kat4) }
    Surface (
        Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(40.dp))
            accountPhotoBox(viewModel)
            Spacer(Modifier.height(16.dp))
            ListOfPictures(
                Photos = Photos,
                photoAvatar = index_of_photoAvatar,
                onPhotoAvatarChanged = { newIndex -> viewModel.updateSelectedPhotoIndex(newIndex) }
            )
        }
        Spacer(Modifier.height(16.dp))
    }
}


//Лист выбора аватарки
@Composable
fun ListOfPictures (
    modifier: Modifier = Modifier,
    Photos: List<Int>,
    photoAvatar: Int,
    onPhotoAvatarChanged: (Int) -> Unit
) {
    val isClicked = remember { mutableStateOf(false) }
    var selectedPhotoIndex by rememberSaveable { mutableStateOf(-1) }

    Column (
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { isClicked.value = !isClicked.value; selectedPhotoIndex = -1 }
        ) {
            Text(
                text = stringResource(R.string.Home_screen_changePhoto)
            )
        }
        Spacer(Modifier.height(16.dp))
        AnimatedVisibility(
            visible = isClicked.value,
            enter = slideInVertically(initialOffsetY = { it }),
            exit = slideOutVertically(targetOffsetY = { it })
        ) {

            //Список, который появляется с анимацией
            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                contentPadding = PaddingValues(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 60.dp)
            ) {
                items(Photos) { item ->
                    PhotoForSelect(
                        photo = item,
                        wasSelected = selectedPhotoIndex == Photos.indexOf(item),
                        onClick = {
                            selectedPhotoIndex = if (selectedPhotoIndex == Photos.indexOf(item)) {
                                -1
                            } else {
                                Photos.indexOf(item)

                            }
                            onPhotoAvatarChanged(item)    //onPhotoAvatarChanged(Photos.indexOf(item))

                        }
                    )
                }
            }
        }
    }
}
//иконка фотки
@Composable
fun PhotoForSelect(
    photo: Int = R.drawable.kat4,
    wasSelected: Boolean,
    onClick:()-> Unit
) {
    var bgColor: Color?
    if(wasSelected){bgColor = MaterialTheme.colorScheme.primary}
    else {bgColor = Color.White}

        Surface(
            color = bgColor,
            modifier = Modifier
                .size(88.dp),
            shape = MaterialTheme.shapes.large,
            shadowElevation = 5.dp,
            onClick = onClick,
        ) {
            Image(
                painter = painterResource(photo),
                contentDescription = null,
                modifier = Modifier.padding(8.dp)
            )
        }

}


//Сообщение

//Фото аккаунта
@Composable
fun accountPhotoBox (
    viewModel: MyViewModel,
   // photo: Int ,
    name: String = "Artem",
    modifier: Modifier = Modifier
) {
    Surface (
        color = Color(217, 241, 255),
        shape = MaterialTheme.shapes.large,
        shadowElevation = 5.dp
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Surface (
                shape = MaterialTheme.shapes.large,
                shadowElevation = 5.dp,
                modifier = Modifier
                    .padding(24.dp)
                    .size(150.dp)
            ) {
                Image(
                    painter = painterResource(viewModel.selectedPhotoIndex.value),
                    contentDescription = null,

                )
            }
            Text(
                text = name,
                color = Color.Black,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 16.dp)

            )
        }
    }

}
