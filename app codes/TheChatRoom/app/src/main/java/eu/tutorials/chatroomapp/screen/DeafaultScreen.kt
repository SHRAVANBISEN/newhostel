package eu.tutorials.chatroomapp.screen

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import eu.tutorials.chatroomapp.Screen

@Composable
fun DeafaultView(navController: NavHostController) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = {
                Toast.makeText(context, "Please Log In", Toast.LENGTH_SHORT).show()
                navController.navigate(Screen.LoginScreen.route) {
                }
            },
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Login")
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                navController.navigate(Screen.SignupScreen.route)
            },
            modifier = Modifier.width(200.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text(text = "Sign Up")
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
