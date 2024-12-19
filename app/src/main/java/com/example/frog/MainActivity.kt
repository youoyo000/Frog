package com.example.frog
/*
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.frog.ui.theme.FrogTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrogTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    FirstScreen()
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)

                    SecondScreen()

                }
            }
        }
    }
}

@Composable
fun FirstScreen() {
    var showSecond by remember { mutableStateOf(false) }


    Image(
            painter = painterResource(id = R.drawable.background),
            contentDescription = "背景圖",
            contentScale = ContentScale.FillBounds,  //縮放符合螢幕寬度
            modifier = Modifier
        )
    Row{

        Box(
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally){
                Image(
                    painter = painterResource(id = R.drawable.start),
                    contentDescription = "字幕",
                    //alpha = 0.7f,
                    modifier = Modifier.size(200.dp)
                    //.clip(CircleShape)
                    //.background(Color.Black)
                )
                var msg by remember { mutableStateOf("TAP相關手勢實例") }

                Image(
                    painter = painterResource(id = R.drawable.start),
                    contentDescription = "開始",
                    modifier = Modifier
                        .size(300.dp)
                    .pointerInput(Unit) {  //觸控病毒往上，扣一秒鐘
                    detectTapGestures(
                        onTap = { showSecond()=ture }
                    )
                }

                )
            }
        }
        Image(
        painter = painterResource(id = R.drawable.frog1),
        contentDescription = "呱呱",
        modifier = Modifier
            .size(400.dp)
            .offset { IntOffset( 500,250) }
        )

    }

    if (showSecond){
        SecondScreen()
    }


}
@Composable
fun SecondScreen() {
    Column(modifier = Modifier.fillMaxSize().background(Color.Yellow),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        Button(onClick = {  })
        {
            Text(text = "返回畫面1")
        }
    }
}



/*
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FrogTheme {
        Greeting("Android")
    }
}*/
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {

}


 */
