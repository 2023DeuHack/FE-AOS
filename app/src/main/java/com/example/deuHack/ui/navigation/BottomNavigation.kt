package com.example.deuHack.ui.navigation

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.deuHack.ui.compose.ui.HomeStory
import com.example.deuHack.ui.home.HomeViewModel
import com.example.deuHack.ui.home.ProfileBottomSheetDialog
import com.example.deuHack.ui.home.ReplyView
import com.example.deuHack.ui.posting.PostingView
import com.example.deuHack.ui.posting.PostingViewModel
import com.example.deuHack.ui.profile.ProfileFixView
import com.example.deuHack.ui.profile.ProfileViewModel
import com.example.deuHack.ui.search.SearchView
import com.example.deuHack.ui.search.SearchViewModel

@Composable
fun InstagramBottomNavigation(
    navController: NavHostController
) {
    val clickState = remember {
        mutableStateOf(false)
    }
    val items = listOf(
        BottomNavigationItem.Home,
        BottomNavigationItem.Search,
        BottomNavigationItem.Lils,
        BottomNavigationItem.Shop,
        BottomNavigationItem.Profile
    )

    BottomNavigation(
        backgroundColor = Color.White,
        contentColor = Color.Black
    )
    {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentDestination = navBackStackEntry?.destination
        val backStackEntry = navController.currentBackStackEntryAsState()
        items.forEach { screen ->
            val selected = backStackEntry.value?.destination?.route== screen.route
            BottomNavigationItem(
                icon = {
                    Icon(painterResource(
                        id =
                            if(!selected)
                                screen.icon
                            else
                                screen.iconClicked
                    ),
                        contentDescription = null,
                    )
                       },
                label = { Text(screen.title) },
                selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                    clickState.value = !clickState.value
                }
            )
        }
    }

}

@Composable
fun NavigationGraph(
    navController:NavHostController,
    homeViewModel: HomeViewModel,
    searchViewModel: SearchViewModel,
    navigationViewModel: NavigationViewModel,
    profileViewModel: ProfileViewModel = viewModel(),
    postingViewModel:PostingViewModel = viewModel()
){
    NavHost(navController = navController, startDestination = BottomNavigationItem.Home.route) {
        composable(BottomNavigationItem.Home.route){
            HomeStory(
                onNavigateToReply = {navController.navigate("reply")},
                homeViewModel,
                navigationViewModel,
                onNavigateToPosting = {navController.navigate("posting")}
            )
        }
        composable(BottomNavigationItem.Search.route){
            SearchView(searchViewModel,navigationViewModel)
        }
        composable(BottomNavigationItem.Lils.route){

        }
        composable(BottomNavigationItem.Shop.route){

        }
        composable(BottomNavigationItem.Profile.route){
            ProfileBottomSheetDialog(
                onNavigateToProfileFix= {navController.navigate("profileFix")},
                navigationViewModel,
                profileViewModel,
                searchViewModel
            )
        }
        composable("reply"){
            ReplyView(
                onNavigatePopBackStack = {navController.popBackStack(BottomNavigationItem.Home.route,false)},
                navigationViewModel
            )
        }
        composable("profileFix"){
            ProfileFixView(
                onNavigatePopBackStack = {navController.popBackStack(BottomNavigationItem.Profile.route,false)},
                navigationViewModel,
                profileViewModel
            )
        }
        composable("posting"){
            PostingView(
                {navController.popBackStack(BottomNavigationItem.Home.route,false)},
                navigationViewModel,
                postingViewModel
            )
        }
    }
}