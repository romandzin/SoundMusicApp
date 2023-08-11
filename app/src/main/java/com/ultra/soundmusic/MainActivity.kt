package com.ultra.soundmusic

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.ultra.soundmusic.ui.theme.SoundMusicTheme

class MainActivity : ComponentActivity() {

    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            this.window.statusBarColor = ContextCompat.getColor(this, R.color.black)
            val songsList = mutableListOf(Song("Cold heart", "Elton John", R.drawable.cold_heart), Song("Happier", "Billie Eilish", R.drawable.billie_eilish))

            SoundMusicTheme {
                val systemUiController = rememberSystemUiController()
                    systemUiController.setStatusBarColor(
                        color = Color(resources.getColor(R.color.blue))
                    )
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    colorResource(R.color.blue),
                                    colorResource(R.color.purple)
                                )
                            )
                        ),
                ) {
                    val value = remember { mutableStateOf(TextFieldValue("")) }
                    remember { mutableStateOf("") }
                    Column {
                        TopPanel()
                        SearchView(state = value)
                        LazyRow(Modifier.padding(top = 20.dp)) {
                            items(count = songsList.size) { index ->
                                Box(Modifier.size(160.dp, 160.dp).padding(end = 20.dp)) {
                                    Image(painter = painterResource(id = songsList[index].image), contentDescription = "", Modifier.fillMaxSize()
                                        .graphicsLayer {
                                            alpha = 0.70f
                                            rotationY = 180f
                                        })
                                    Column(Modifier.padding(top = 10.dp, start = 10.dp)) {
                                        Text(text = songsList[index].author, color = colorResource(id = R.color.white))
                                        Text(text = songsList[index].title)
                                    }
                                }
                            }
                        }
                        NewAlbums()
                        TodaysHits()
                        Spacer(modifier = Modifier.weight(1f))
                        BottomNavigationBar()
                    }
                }
            }
        }
    }
}

@Composable
fun TopPanel() {
    Row(modifier = Modifier.padding(top = 20.dp)) {
        Text(text = "Discover", color = Color.White, fontSize = 24.sp, modifier = Modifier.padding(start = 20.dp))
        Spacer(Modifier.weight(1f))
        Image(painter = painterResource(id = R.drawable.settings_icn), contentDescription = "settings", modifier = Modifier.padding(end = 20.dp))
        Image(painter = painterResource(id = R.drawable.notification_icn), contentDescription = "notification", modifier = Modifier.padding(end = 20.dp))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchView(state: MutableState<TextFieldValue>) {
    TextField(value = state.value,
        onValueChange = { value ->
            state.value = value
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 18.sp),
        leadingIcon = {
            Icon(Icons.Default.Search,
                contentDescription = "",
                modifier = Modifier
                    .padding(15.dp)
                    .size(24.dp),
                tint = Color.White
            )
        },
        trailingIcon = {
            if (state.value != TextFieldValue("")) {
                IconButton(onClick = {
                    state.value = TextFieldValue("")
                }
                ) {
                    Icon(
                        Icons.Default.Close,
                        contentDescription = "",
                        modifier = Modifier
                            .padding(15.dp)
                            .size(24.dp),
                        tint = Color.White
                    )
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.textFieldColors(
            focusedTextColor = Color.White,
            cursorColor = Color.White,
            containerColor = Color.White.copy(alpha = 0.5f),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Composable
fun NewAlbums() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row {
            Text(text = "New albums", modifier = Modifier.padding(start = 20.dp), color = colorResource(id = R.color.white))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.icn_arrow), contentDescription = "", modifier = Modifier
                .padding(end = 20.dp)
                .size(24.dp, 24.dp))
        }
    }
}

@Composable
fun TodaysHits() {
    Column(modifier = Modifier.padding(top = 20.dp)) {
        Row {
            Text(text = "Todays hits", modifier = Modifier.padding(start = 20.dp), color = colorResource(id = R.color.white))
            Spacer(modifier = Modifier.weight(1f))
            Image(painter = painterResource(id = R.drawable.icn_arrow), contentDescription = "", modifier = Modifier
                .padding(end = 20.dp)
                .size(24.dp, 24.dp))
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val items = listOf(
        NavigationItem.Music,
        NavigationItem.Home,
        NavigationItem.Profile
    )
    NavigationBar(
        containerColor = colorResource(id = R.color.dark_purple),
        contentColor = Color.White
    ) {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(painterResource(id = item.icon), contentDescription = item.title, tint = colorResource(
                    id = R.color.light_purple
                )) },
                alwaysShowLabel = true,
                selected = false,
                onClick = {
                    /* Add code later */
                }
            )
        }
    }
}
