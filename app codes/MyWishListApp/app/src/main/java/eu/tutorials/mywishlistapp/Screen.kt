package eu.tutorials.mywishlistapp
//HERE WE SET ALL THE ROUTES FOR THE DIFFERENT SCREEENS WE WANT TO NAVIGATE TO
sealed class Screen(val route:String) {
    object HomeScreen: Screen("home_screen")
    object AddScreen: Screen("add_screen")
}