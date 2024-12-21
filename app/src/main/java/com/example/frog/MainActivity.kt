package com.example.frog

import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.frog.ui.theme.FrogTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults.buttonColors
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob



class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 設定畫面方向為橫向
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE

        setContent {
            FrogTheme {
                val screenW = resources.displayMetrics.widthPixels
                val screenH = resources.displayMetrics.heightPixels
                AppScreen(screenW = screenW, screenH = screenH) // 呼叫主畫面
            }
        }
    }


    @Composable
    fun AppScreen(screenW: Int, screenH: Int) {
        val scale = resources.displayMetrics.density
        var showSecond by rememberSaveable { mutableStateOf(false) } // 單一狀態控制畫面切換
        val game = remember { Game(CoroutineScope(SupervisorJob()), screenW, screenH,scale) }
        // 避免重複初始化 Game

        if (showSecond) {
            SecondScreen(
                m = Modifier,
                game = game,
                onNavigateToFirst = { showSecond = false } // 切換回第一畫面
            )
        } else {
            FirstScreen(
                onNavigateToSecond = { showSecond = true } // 切換到第二畫面
            )
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
            painter = painterResource(id = R.drawable.frogff),
            contentDescription = "呱呱",
            modifier = Modifier
                .size(400.dp)
                .offset { IntOffset( 180,250) }
        )
    }

}

@Composable
fun SecondScreen(m: Modifier, game:Game, onNavigateToFirst: () -> Unit) {
    val counter by game.state.collectAsState() // 從 Game 取得計時器狀態
    var msg by remember { mutableStateOf("遊戲開始") }

    Column(
        modifier = m.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row {

        // 無縫連接邏輯
        if (game.background.x1 <= -game.screenW) {
            game.background.x1 = game.background.x2 + game.screenW
        }
        if (game.background.x2 <= -game.screenW) {
            game.background.x2 = game.background.x1 + game.screenW
        }

        Box(modifier = m.fillMaxSize()) {
            // 背景圖1
            Image(
                painter = painterResource(id = R.drawable.gamebackground),
                contentDescription = "背景圖1",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(game.background.x1, 0) }
            )

            // 背景圖2
            Image(
                painter = painterResource(id = R.drawable.gamebackground2),
                contentDescription = "背景圖2",
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .fillMaxSize()
                    .offset { IntOffset(game.background.x2, 0) }
            )

            //繪製呱呱
            val frogImage = arrayListOf(R.drawable.frog1, R.drawable.frog2,R.drawable.frog3)

            Image(
                painter = painterResource(id = frogImage[game.frog.pictNo]),
                contentDescription = "跳跳呱",
                modifier = Modifier
                    .width(100.dp)
                    .height(220.dp)
                    .offset { IntOffset(game.frog.x, game.frog.y) }
            )


            // 其他遊戲內容
           Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
               Button(
                   onClick = {
                       if (msg=="遊戲開始"|| msg =="遊戲繼續"){
                           msg = "遊戲暫停"
                           game.Play()
                       }
                       else if (msg=="遊戲暫停"){
                           msg = "遊戲繼續"
                           game.isPlaying = false
                       }else{  //重新開始遊戲
                           msg = "遊戲暫停"
                           game.Restart()
                       }
                   },
                   modifier = m
               ) {
                   Text(text = msg)

               }

                Text(text = counter.toString())

               Button(onClick = onNavigateToFirst) {
                    Text("返回主畫面")
                }
            }
        }
    }

// 跳跳蛙
//val frogImage = arrayListOf(R.drawable.frog2, R.drawable.inshot)
        /*Image(
            painter = painterResource(id = boyImage[game.boy.pictNo]),
            contentDescription = "跳跳蛙",
            modifier = Modifier
                .width(100.dp)
                .height(220.dp)
                .offset { IntOffset(game.boy.x, game.boy.y) }
        )*/

        // 返回按鈕

    }

}}

