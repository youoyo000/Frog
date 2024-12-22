package com.example.frog

import Game
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
import androidx.compose.foundation.layout.padding
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
        var currentPage by rememberSaveable { mutableStateOf(1) } // 用於追踪當前頁面
        val game = remember { Game(CoroutineScope(SupervisorJob()), screenW, screenH, scale) }
        // 避免重複初始化 Game

        when (currentPage) {
            1 -> FirstScreen(
                onNavigateToSecond = { currentPage = 2 } // 切換到第二頁
            )
            2 -> SecondScreen(
                m = Modifier,
                game = game,
                onNavigateToFirst = { currentPage = 1 }, // 切換回第一頁
                onNavigateToThird = { currentPage = 3 }  // 切換到第三頁
            )
            3 -> ThirdScreen(
                onNavigateToSecond = { currentPage = 2 } // 切換回第二頁
            )
        }
    }

    @Composable
    fun FirstScreen(onNavigateToSecond: () -> Unit) {
        //var msg by remember { mutableStateOf("加速感應器實例") }
        var msg2 by remember { mutableStateOf("") }

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
    fun SecondScreen(m: Modifier, game: Game, onNavigateToFirst: () -> Unit, onNavigateToThird: () -> Unit) {
        val counter by game.state.collectAsState() // 從 Game 取得計時器狀態
        var moveDistance = 0

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

                    // 繪製呱呱
                    val frogImage = arrayListOf(R.drawable.frog1, R.drawable.frog2)
                    Image(
                        painter = painterResource(id = frogImage[game.frog.pictNo]),
                        contentDescription = "跳跳呱",
                        modifier = Modifier
                            .width(100.dp)
                            .height(220.dp)
                            .offset { IntOffset(game.frog.x, game.frog.y) }
                    )

                    // 繪製蒼蠅 (相對於背景偏移)
                    var flyOffsetX = 650 // 蒼蠅的初始水平偏移量
                    val flyOffsetY = 200 // 蒼蠅的垂直偏移量
                    Image(
                        painter = painterResource(id = R.drawable.fly),
                        contentDescription = "蒼蠅",
                        modifier = Modifier
                            .width(50.dp)  // 寬度為 50
                            .height(30.dp) // 高度為 30
                            .offset {
                                IntOffset(
                                    game.background.x1 + flyOffsetX,
                                    game.frog.y + flyOffsetY
                                )
                            }
                    )

                    var fishOffsetX = 1250 // 魚的初始水平偏移量
                    val fishOffsetY = 185
                    Image(
                        painter = painterResource(id = R.drawable.fish),
                        contentDescription = "魚",
                        modifier = Modifier
                            .width(60.dp)  // 寬度為 60
                            .height(50.dp) // 高度為 50
                            .offset {
                                IntOffset(
                                    game.background.x1 + fishOffsetX,
                                    game.frog.y + fishOffsetY
                                )
                            }
                    )
                    var shampOffsetX = 2490 // 蝦的初始水平偏移量
                    val shampOffsetY = 185
                    Image(
                        painter = painterResource(id = R.drawable.shamp),
                        contentDescription = "蝦",
                        modifier = Modifier
                            .width(60.dp)  // 寬度為 60
                            .height(50.dp) // 高度為 50
                            .offset {
                                IntOffset(
                                    game.background.x1 + shampOffsetX,
                                    game.frog.y + shampOffsetY
                                )
                            }
                    )
                    var die1OffsetX = 460 // 魚的初始水平偏移量
                    val die1OffsetY = 185
                    Image(
                        painter = painterResource(id = R.drawable.die),
                        contentDescription = "GG1",
                        //alpha = 0.2f,
                        modifier = Modifier
                            .width(70.dp)  // 寬度為 60
                            .height(90.dp) // 高度為 50
                            .offset {
                                IntOffset(
                                    game.background.x1 + die1OffsetX,
                                    game.frog.y + die1OffsetY
                                )
                            }
                    )

                    // 其他遊戲內容
                    Column(
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = onNavigateToFirst) {
                            Text("返回主畫面")
                        }
                        Button(onClick = onNavigateToThird, modifier = Modifier.padding(top = 16.dp)) {
                           Text("跳轉到第三頁")
                        }

                        Text(text = "分數：${game.score}", modifier = Modifier.padding(16.dp)) // 顯示分數
                        Text(text = "倒數計時：$counter 秒", color = Color.Transparent, modifier = Modifier.padding(1.dp))//秒數
                    }

                    Box(
                        modifier = Modifier
                            .fillMaxSize() // 讓容器填滿螢幕
                    ) {
                        // 左下角的按鈕
                        Button(
                            onClick = {
                                game.Play() // 開始遊戲
                                game.background.x1 -= 190 // 背景圖1向左移動 190 像素
                                game.background.x2 -= 190 // 背景圖2向左移動 190 像素
                                moveDistance += 190 // 累計移動距離
                                flyOffsetX -= 190
                                game.checkCollision() // 檢測碰撞

                                // 如果累計移動距離達到或超過 190 像素，觸發青蛙跳躍
                                if (moveDistance >= 190) {
                                    game.frog.fly() // 觸發圖片切換
                                    moveDistance = 0 // 重置移動距離
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomStart) // 將按鈕對齊到左下角
                                .padding(16.dp) // 添加距離邊界的內邊距
                                .fillMaxWidth(0.2f)
                                .fillMaxHeight(0.2f)
                        ) {
                            Text("跳一格")
                        }

                        // 右下角的按鈕
                        Button(
                            onClick = {
                                game.Play()
                                game.background.x1 -= 380 // 背景圖1向左移動 380 像素
                                game.background.x2 -= 380 // 背景圖2向左移動 380 像素
                                moveDistance += 380 // 累計移動距離
                                flyOffsetX -= 380 // 蒼蠅跟著移動 380 像素
                                game.checkCollision() // 檢測碰撞

                                // 每當累計移動距離達到或超過 380，觸發青蛙跳躍
                                if (moveDistance >= 380) {
                                    game.frog.fly() // 觸發青蛙跳躍
                                    moveDistance = 0 // 重置移動距離
                                }
                            },
                            modifier = Modifier
                                .align(Alignment.BottomEnd) // 將按鈕對齊到右下角
                                .padding(16.dp) // 添加距離邊界的內邊距
                                .fillMaxWidth(0.2f)
                                .fillMaxHeight(0.2f)
                        ) {
                            Text("跳兩格")
                        }
                    }
                }
            }
        }
    }
    @Composable
    fun ThirdScreen(onNavigateToSecond: () -> Unit) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = "成績", modifier = Modifier.padding(16.dp))
            Image(
                    painter = painterResource(id = R.drawable.memo),
            contentDescription = "記分板",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
            // .fillMaxSize()
            )

            Button(
                onClick = {
                    //game.Restart() // 檢測碰撞
                },
                modifier = Modifier
                   // .align(Alignment.BottomEnd) // 將按鈕對齊到右下角
                    .padding(16.dp) // 添加距離邊界的內邊距
                    .fillMaxWidth(0.2f)
                    .fillMaxHeight(0.2f)
            ) {
                Text("跳兩格")
            }
        }
    }

}