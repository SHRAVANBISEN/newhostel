package eu.tutorials.mywishlistapp

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.ui.platform.LocalContext

import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.mywishlistapp.data.Wish
import kotlinx.coroutines.launch
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp


@Composable
fun AddEditDetailView(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController
){ val backgroundImage: Painter = painterResource(id = R.drawable.bg3
)

    val snackMessage = remember{
        mutableStateOf("")
    }
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    if (id != 0L){
        val wish = viewModel.getAWishById(id).collectAsState(initial = Wish(0L, "", ""))
        viewModel.wishTitleState = wish.value.title
        viewModel.wishDescriptionState = wish.value.description
    }else{
        viewModel.wishTitleState = ""
        viewModel.wishDescriptionState = ""
    }

    Scaffold(
        topBar = {AppBarView(title =
    if(id != 0L) stringResource(id = R.string.update_wish)
    else stringResource(id = R.string.add_wish)
    ) {navController.navigateUp()}
    },
        scaffoldState = scaffoldState
        ) {

        Box(modifier = Modifier.fillMaxSize())
        {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )
            Column(
                modifier = Modifier
                    .padding(it)
                    .wrapContentSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(modifier = Modifier.height(20.dp))

                WishTextField(label = "Title",
                    value = viewModel.wishTitleState,
                    onValueChanged = {
                        viewModel.onWishTitleChanged(it)
                    })

                Spacer(modifier = Modifier.height(20.dp))

                WishTextField(label = "Description",
                    value = viewModel.wishDescriptionState,
                    onValueChanged = {
                        viewModel.onWishDescriptionChanged(it)
                    })

                Spacer(modifier = Modifier.height(15.dp))
                ElevatedColoredButton(
                    id = id,
                    viewModel = viewModel,
                    navController = navController,
                    snackMessage = snackMessage
                )


            }
        }
    }
}

//THIS CODE HERE IS WRITTEN FOR THE DESIGNING OF OUTLINED TEXT FIELD
@Composable
fun WishTextField(
    label: String,
    value: String,
    onValueChanged: (String) -> Unit

){

    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        label = { Text(text = label, color = Color.Black ,
            fontSize = 22.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom =22.dp , start = 5.dp , )) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 20.dp)
            .background(Color.Transparent),
        //HERE THIS TRANSPARENT THING DONE THE JOB OF REMOVING  THE BACKGROUND COLOR OF THE TEXT FIELD
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            // using predefined Color
            textColor = Color.Black,
            // using our own colors in Res.Values.Color
            backgroundColor = colorResource(id = R.color.lgrey),
            focusedBorderColor = colorResource(id = R.color.black),
            unfocusedBorderColor = colorResource(id = R.color.black),
            cursorColor = colorResource(id = R.color.black),

        ) ,
        shape = RoundedCornerShape(20.dp)  ,
        textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 16.sp)


    )
}

// THIS CODE HERE IS WRITTEN FOR THE DESIGNING OF THE BUTTON
@Composable
fun ElevatedColoredButton(
    id: Long,
    viewModel: WishViewModel,
    navController: NavController,
    snackMessage: MutableState<String>
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    Button(
        onClick = {
            if (viewModel.wishTitleState.isNotEmpty() && viewModel.wishDescriptionState.isNotEmpty()) {
                if (id != 0L) {
                    viewModel.updateWish(
                        Wish(
                            id = id,
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                } else {
                    //HERE WE ARE ADDING THE DATA(WISH) TO OUR DATABASE
                    viewModel.addWish(
                        Wish(
                            title = viewModel.wishTitleState.trim(),
                            description = viewModel.wishDescriptionState.trim()
                        )
                    )
                    snackMessage.value = "Wish has been created"
                }
            } else {
                snackMessage.value = "Enter fields to create a wish"
            }
            scope.launch {
                navController.navigateUp()
                Toast.makeText(context, "SWIPE LEFT TO DELETE WISH", Toast.LENGTH_SHORT).show()
            }
        },
        colors = androidx.compose.material3.ButtonDefaults.buttonColors(
            containerColor = colorResource(id = R.color.black), // Background color
            contentColor = colorResource(id = R.color.lgrey) // Text color
        ),
        elevation = androidx.compose.material3.ButtonDefaults.buttonElevation(
            defaultElevation = 8.dp,
            pressedElevation = 12.dp
        ),
        modifier = Modifier.padding(16.dp)
    )
    //WHAT THIS CODE IS DOING EXACTLY HERE
    {
        Text(
            text = if (id != 0L) stringResource(id = R.string.update_wish)
            else stringResource(id = R.string.add_wish),
            style = TextStyle(
                fontSize = 18.sp
            )
        )
    }
}

@Preview
@Composable
fun WishTestFieldPrev(){
    WishTextField(label = "text", value = "text", onValueChanged = {})
}

