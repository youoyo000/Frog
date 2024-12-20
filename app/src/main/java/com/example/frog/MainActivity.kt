package com.example.frog

import android.content.Context.SENSOR_SERVICE
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorManager
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp




class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FrogTheme {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE)
                val screenW = resources.displayMetrics.widthPixels
                val screenH = resources.displayMetrics.heightPixels
                AppScreen()
            }
        }
    }
}
@Composable
fun AppScreen() {
    var showSecond by remember { mutableStateOf(false) } // 單一狀態控制畫面切換

    if (showSecond) {
        SecondScreen { showSecond = false } // 顯示第二畫面並提供返回功能
    } else {
        FirstScreen { showSecond = true } // 顯示第一畫面並提供跳轉功能
    }
}

@Composable
fun FirstScreen(onNavigateToSecond: () -> Unit) {
    //var msg by remember { mutableStateOf("加速感應器實例") }
    var msg2 by remember { mutableStateOf("") }
    var xTilt by remember { mutableStateOf(0f) }
    var yTilt by remember { mutableStateOf(0f) }
    var zTilt by remember { mutableStateOf(0f) }

    val context = LocalContext.current
    val sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
    val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)



    Column(
        modifier = Modifier
            .fillMaxSize()
        //horizontalAlignment = Alignment.CenterHorizontally
    ) {}
    Image(
        painter = painterResource(id = R.drawable.background),
        contentDescription = "背景圖",
        contentScale = ContentScale.FillBounds,  //縮放符合螢幕寬度
        modifier = Modifier,
    )
    Row{
        Column(verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .offset { IntOffset(0,0)}){

            Image(
                painter = painterResource(id = R.drawable.title),
                contentDescription = "標題",
                //alpha = 0.7f,
                modifier = Modifier
                    .size(500.dp)
                    .offset { IntOffset(0,0)}
            )
            Box{
                Image(
                    painter = painterResource(id = R.drawable.start),
                    contentDescription = "開始",
                    modifier = Modifier
                        .size(300.dp)
                        .offset { IntOffset(0,0)}
                )
                Button(onClick = onNavigateToSecond, Modifier
                    .fillMaxWidth(0.25f)
                    .fillMaxHeight(0.8f)
                    .offset { IntOffset(0,0)},
                    colors = buttonColors(Color.Transparent)
                ) { // 點擊跳轉到第二畫面
                    Text("跳轉畫面2")
                    Text(msg2)
                }
            }
        }
        Image(
            painter = painterResource(id = R.drawable.frog1),
            contentDescription = "呱呱",
            modifier = Modifier
                .size(400.dp)
                .offset { IntOffset( 180,250) }
        )
    }

}

@Composable
fun SecondScreen(onNavigateToFirst: () -> Unit) {
    // val counter by game.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize(),
        //verticalArrangement = Arrangement.Center, //上下置中
        //horizontalAlignment = Alignment.CenterHorizontally//左右置中
    ) {}
    //Text("這是第二個畫面")

    Image(
        painter = painterResource(id = R.drawable.gamebackground),
        contentDescription = "背景圖",
        contentScale = ContentScale.FillBounds,  //縮放符合螢幕寬度
        modifier = Modifier
        //.offset { IntOffset(+screenW, 0) }
    )
    Button(onClick = onNavigateToFirst) { // 點擊返回第一畫面
        Text("返回畫面1")
    }

}