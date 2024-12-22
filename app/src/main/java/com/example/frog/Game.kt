import android.graphics.Rect
import com.example.frog.Background
import com.example.frog.Frog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game(val scope: CoroutineScope, val screenW: Int, val screenH: Int, scale: Float) {
    var counter = 30 // 倒數計時器的初始值
    val state = MutableStateFlow(30) // 使用 MutableStateFlow 來追蹤計時器的狀態
    val background = Background(screenW) // 背景物件，負責處理背景移動邏輯
    val frog = Frog(screenH, scale) // 青蛙物件，負責處理青蛙跳躍的邏輯
    var isPlaying = true // 控制遊戲是否正在進行的布林值
    var score = 0 // 分數變數

    // 各种物体的尺寸和位置
    val flyWidth = 50
    val flyHeight = 30
    val fishWidth = 60
    val fishHeight = 50
    val shrimpWidth = 60
    val shrimpHeight = 50
    val obstacleWidth = 70
    val obstacleHeight = 90

    // 碰撞检测逻辑
    fun checkCollision() {
        val frogRect = Rect(frog.x, frog.y, frog.x + 100, frog.y + 220)

        // 定义各物体的位置矩形
        val flyRect = Rect(
            background.x1 + 650,
            frog.y + 200,
            background.x1 + 650 + flyWidth,
            frog.y + 200 + flyHeight
        )

        val fishRect = Rect(
            background.x1 + 1250,
            185,
            background.x1 + 1250 + fishWidth,
            185 + fishHeight
        )

        val shrimpRect = Rect(
            background.x1 + 2490,
            185,
            background.x1 + 2490 + shrimpWidth,
            185 + shrimpHeight
        )

        val die1Rect = Rect(
            background.x1 + 460,
            185,
            background.x1 + 460 + obstacleWidth,
            185 + obstacleHeight
        )

        // 判断与每个物体是否碰撞，并加分
        if (frogRect.intersect(flyRect)) {
            score++ // 与苍蝇碰撞
        }
        else if (frogRect.intersect(fishRect)) {
            score += 2 // 与鱼碰撞，得 2 分
        }
        else if (frogRect.intersect(shrimpRect)) {
            score++ // 与虾碰撞，得 1 分
        }
        else if (frogRect.intersect(die1Rect)) {
            score -= 1 // 与障碍物碰撞，扣 1 分
            StopGame() // 停止游戏
        }
    }

    // 游戏主逻辑
    fun Play() {
        scope.launch {
            isPlaying = true
            while (isPlaying) {
                delay(500)
                counter--
                state.emit(counter)
                checkCollision() // 每次更新检查碰撞
            }
        }
    }

    // 重新开始游戏
    fun Restart() {
        isPlaying = true // 确保游戏状态设置为进行中
        counter = 30 // 重置计时器
        score = 0 // 重置分数
        Play() // 重新启动游戏逻辑
    }

    // 停止游戏
    fun StopGame() {
        isPlaying = false // 设置游戏状态为停止
    }
}
