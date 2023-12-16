package com.ophi.animeapp


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.ophi.animeapp.di.Injection
import com.ophi.animeapp.ui.ViewModelFactory
import com.ophi.animeapp.ui.navigation.NavigationItem
import com.ophi.animeapp.ui.navigation.Screen
import com.ophi.animeapp.ui.screen.detail.DetailScreen
import com.ophi.animeapp.ui.screen.detail.DetailViewModel
import com.ophi.animeapp.ui.screen.favorite.FavoriteScreen
import com.ophi.animeapp.ui.screen.favorite.FavoriteViewModel
import com.ophi.animeapp.ui.screen.home.HomeScreen
import com.ophi.animeapp.ui.screen.profile.ProfileScreen
import com.ophi.animeapp.ui.theme.AnimeAppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val repository = Injection.provideRepository()
    val detailViewModel: DetailViewModel = viewModel(
        factory = ViewModelFactory(repository)
    )

    val favoriteViewModel: FavoriteViewModel = viewModel(
        factory = ViewModelFactory(repository)
    )

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.Detail.route) {
                BottomBar(navController)
            }
        },
        floatingActionButton = {
            if (currentRoute == Screen.Detail.route) {
                detailViewModel.anime.collectAsState().value.let { it?.let { anime ->
                    FloatingActionButton(onClick = {
                        detailViewModel.setFavorite(anime.id, !anime.isFavorite)
                    }) {
                        Icon(
                            imageVector = if (anime.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            contentDescription = stringResource(R.string.add_to_favorite)
                        )
                    }
                } }
            }
        },
        modifier = modifier
    ) { innerPadding ->
        val navigateToDetail = { id: Int ->
            navController.navigate(Screen.Detail.createRoute(id))
        }

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Home.route) {
                HomeScreen(
                    navigateToDetail = navigateToDetail
                )
            }
            composable(Screen.Favorite.route) {
                favoriteViewModel.getFavorite()
                FavoriteScreen(
                    navigateToDetail = navigateToDetail,
                    viewModel = favoriteViewModel
                )
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.Detail.route,
                arguments = listOf(navArgument("animeId") { type = NavType.IntType })
            ) {
                detailViewModel.getAnimeById(it.arguments?.getInt("animeId") ?: -1 )
                DetailScreen(viewModel = detailViewModel) {}
            }
        }
    }
}

@Composable
private fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavigationBar(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Default.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        navigationItems.map { item ->
            NavigationBarItem(
                selected = currentRoute == item.screen.route,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        restoreState = true
                        launchSingleTop = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.title
                    )
                },
                label = { Text(text = item.title) }
            )
        }
    }
}

@Preview
@Composable
fun AnimeAppPreview() {
    AnimeAppTheme {
        AnimeApp()
    }
}