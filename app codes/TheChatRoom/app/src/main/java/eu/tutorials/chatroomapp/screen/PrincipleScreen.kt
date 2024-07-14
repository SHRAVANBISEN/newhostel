package eu.tutorials.chatroomapp.screen

import WishViewModel
import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import eu.tutorials.chatroomapp.R
import eu.tutorials.chatroomapp.Screen
import eu.tutorials.chatroomapp.viewmodel.AuthViewModel
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrincipleScreen(navController: NavController,
                    viewModel: WishViewModel = viewModel(),
                    authViewModel: AuthViewModel = viewModel()
){
    val navigationController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(initialValue =
    DrawerValue.Closed)
    val context = LocalContext.current.applicationContext
    val scaffoldState = rememberScaffoldState()

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            ModalDrawerSheet {
                Box(
                    modifier = Modifier
                        .background(Color.Black)
                        .fillMaxWidth()
                        .height(150.dp)
                ){
                    Text(text = "")
                }
                Divider()
                NavigationDrawerItem(label = { Text(text = "LBS" , color
                = Color.Black) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Home,
                        contentDescription = "home",tint = Color.Black) },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(""){
                            popUpTo(0)
                        }
                    }
                )
                NavigationDrawerItem(label = { Text(text = "BVB" ,
                    color = Color.Black) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Person,
                        contentDescription = "profile",tint = Color.Black) },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(""){
                            popUpTo(0)
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "AP" ,
                    color = Color.Black) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.Settings,
                        contentDescription = "settings",tint = Color.Black) },
                    onClick = {
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        navigationController.navigate(""){
                            popUpTo(0)
                        }
                    })
                NavigationDrawerItem(label = { Text(text = "GH" ,
                    color = Color.Black
                ) },
                    selected = false,
                    icon = { Icon(imageVector = Icons.Default.ExitToApp,
                        contentDescription = "logout",tint = Color.Black) },
                    onClick = {
                        navController.navigate("login")
                        coroutineScope.launch {
                            drawerState.close()
                        }
                        Toast.makeText(context,"Logout",
                            Toast.LENGTH_SHORT).show()
                    })

            }
        }) {
        androidx.compose.material.Scaffold(
            scaffoldState = scaffoldState,

            topBar = {
                val coroutineScope = rememberCoroutineScope()
                TopAppBar(title = { Text(text = "AppName") },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color.Black,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White
                    ),
                    navigationIcon = {
                        IconButton(onClick = {
                            coroutineScope.launch{
                                drawerState.open()
                            }
                        }) {
                            Icon(
                                Icons.Rounded.Menu, contentDescription =
                                "MenuButton"
                            )
                        }
                    },
                )
            }
        ) {
Text(text = "principal screen ")
            }
        }

    }
