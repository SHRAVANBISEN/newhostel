package eu.tutorials.mywishlistapp

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.rememberDismissState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.material.*
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import eu.tutorials.mywishlistapp.data.DummyWish
import eu.tutorials.mywishlistapp.data.Wish

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun HomeView(
    navController: NavController,
    viewModel: WishViewModel
){
    val backgroundImage: Painter = painterResource(id = R.drawable.bg3)
    val context = LocalContext.current
    val scaffoldState = rememberScaffoldState()
    Scaffold(

        scaffoldState = scaffoldState,
        topBar = {AppBarView(title= "WishList")},
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.padding(all = 20.dp),
                contentColor = Color.White,
                backgroundColor = Color.Black,
                onClick = {


                    navController.navigate(Screen.AddScreen.route + "/0L")

                }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }

    )
    {
        
        Box(modifier = Modifier.fillMaxSize() )
        {
            Image(
                painter = backgroundImage,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize()
            )

        //HERE WITH THE .. LINE WE CAN FETCH ALL THE DATA FROM OUR DATABASE AND THEN WE CAN DISPLAY IT INSIDE LAZY COLUMN
        val wishlist = viewModel.getAllWishes.collectAsState(initial = listOf()) //..
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(it)){
            items(wishlist.value, key={wish-> wish.id} ){
                wish ->
                val dismissState = rememberDismissState(
                    confirmStateChange = {
                        if(it == DismissValue.DismissedToEnd || it== DismissValue.DismissedToStart){
                            viewModel.deleteWish(wish)
                        }
                        true
                    }
                )

                SwipeToDismiss(
                    state = dismissState,
                    background = {
                        val color by animateColorAsState(
                            if(dismissState.dismissDirection
                                == DismissDirection.EndToStart) Color.Black else Color.Transparent
                            ,label = ""
                        )
                        val alignment = Alignment.CenterEnd
                        Box(
                            Modifier
                                .fillMaxSize()
                                .background(color)
                                .padding(horizontal = 20.dp),
                            contentAlignment = alignment
                        ){
                            Icon(Icons.Default.Delete,
                                contentDescription = "Delete Icon",
                                tint = Color.White)
                        }

                    },
                    directions = setOf(DismissDirection.EndToStart),
                    dismissThresholds = {FractionalThreshold(1f)},
                    dismissContent = {
                        WishItem(wish = wish) {
                            val id = wish.id
                            navController.navigate(Screen.AddScreen.route + "/$id")
                        }
                    }
                )
            }
        }
    }}

}

// HERE WE HAVE WRITTEN CODE FOR THE VIEW OF EVERY WISH
@Composable
fun WishItem(wish: Wish, onClick: () -> Unit){
    Card(shape = RoundedCornerShape(12.dp),
        backgroundColor = colorResource(id = R.color.lgrey),
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth().clickable(onClick=onClick)
            .border(
                3.dp,
                colorResource(id = R.color.black),
                shape = RoundedCornerShape(12.dp)


            ),
        elevation = 80.dp
    ) {
        Column(modifier = Modifier.padding(16.dp)){

            Text(
                buildAnnotatedString {

                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline ,
                        fontSize = 19.sp, fontWeight = FontWeight.ExtraBold)) {
                        append(wish
                            .title.uppercase())
                    }

                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
           // Text(text = wish.title, fontWeight = FontWeight.ExtraBold , textAlign = )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                buildAnnotatedString {

                    withStyle(style = SpanStyle(
                        fontSize = 17.sp)) {
                        append(wish
                            .description)
                    }

                },
                style = MaterialTheme.typography.body1,
                modifier = Modifier.fillMaxWidth()

            )
            //
        }
    }
}
