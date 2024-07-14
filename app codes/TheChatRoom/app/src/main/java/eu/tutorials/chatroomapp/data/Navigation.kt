package eu.tutorials.chatroomapp.data

import WishViewModel
import android.app.Activity
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import eu.tutorials.chatroomapp.Screen
import eu.tutorials.chatroomapp.screen.*
import eu.tutorials.chatroomapp.viewmodel.AuthViewModel
import eu.tutorials.mywishlistapp.AddEditDetailView
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun NavigationGraph(
    navController: NavHostController,
    authViewModel: AuthViewModel,
    wishViewModel: WishViewModel
) {
    val isUserLoggedIn: Boolean by authViewModel.isUserLoggedIn.observeAsState(initial = false)

    NavHost(
        navController = navController,
        startDestination = if (isUserLoggedIn) Screen.HomeScreen.route else Screen.DefaultScreen.route
    ) {
        composable(Screen.SignupScreen.route) {
            SignUpScreen(
                authViewModel = authViewModel,
                onNavigateToLogin = { navController.navigate(Screen.LoginScreen.route) }
            )
        }
        composable(Screen.DefaultScreen.route) {
            DeafaultView(navController = navController)
        }
        composable(Screen.LoginScreen.route) {
            LoginScreen(
                authViewModel = authViewModel,
                onNavigateToSignUp = { navController.navigate(Screen.SignupScreen.route) },
                onSignInSuccess = {
                    navController.navigate(Screen.HomeScreen.route) {
                        popUpTo(Screen.LoginScreen.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.HomeScreen.route) {
            HomeView(
                navController = navController,
                viewModel = wishViewModel,
                authViewModel = authViewModel
            )
        }
        composable("${Screen.AddScreen.route}/{wishId}") { backStackEntry ->
            val wishIdString = backStackEntry.arguments?.getString("wishId") ?: "0"
            val wishId = wishIdString.toLongOrNull() ?: 0L
            AddEditDetailView(
                id = wishId,
                viewModel = wishViewModel,
                navController = navController
            )
        }
    }

    if (isUserLoggedIn && navController.currentBackStackEntry?.destination?.route == Screen.HomeScreen.route) {
        BackHandler {
            (navController.context as? Activity)?.finish()
        }
    }
}
