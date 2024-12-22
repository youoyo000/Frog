import android.graphics.Rect
import com.example.frog.Background
import com.example.frog.Frog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game(
    val scope: CoroutineScope,
    val screenW: Int,
    val screenH: Int,
    // 修正为一个函数类型
    scale: Float
) {
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

    // 添加触碰标志
    var hasCollidedWithFly = false
    var hasCollidedWithFish = false
    var hasCollidedWithShrimp = false
    var hasCollidedWithObstacle = false

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
            frog.y + 185,
            background.x1 + 1250 + fishWidth,
            frog.y + 185 + fishHeight
        )

        val shrimpRect = Rect(
            background.x1 + 2490,
            frog.y + 185,
            background.x1 + 2490 + shrimpWidth,
            frog.y + 185 + shrimpHeight
        )

        val dieRects = listOf(
            Rect(background.x1 + 460, frog.y + 185, background.x1 + 460 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 850, frog.y + 185, background.x1 + 850 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 1650, frog.y + 185, background.x1 + 1650 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 2050, frog.y + 185, background.x1 + 2050 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 2750, frog.y + 185, background.x1 + 2750 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 3150, frog.y + 185, background.x1 + 3150 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 4100, frog.y + 185, background.x1 + 4100 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 4400, frog.y + 185, background.x1 + 4400 + obstacleWidth, frog.y + 185 + obstacleHeight),
            Rect(background.x1 + 5000, frog.y + 185, background.x1 + 5000 + obstacleWidth, frog.y + 185 + obstacleHeight)
        )

        // 判断与物体的碰撞，并处理分数和状态
        if (frogRect.intersect(flyRect) && !hasCollidedWithFly) {
            score++ // 与苍蝇碰撞
            hasCollidedWithFly = true // 标记已经碰撞
        } else if (frogRect.intersect(fishRect) && !hasCollidedWithFish) {
            score += 2 // 与鱼碰撞，得 2 分
            hasCollidedWithFish = true // 标记已经碰撞
        } else if (frogRect.intersect(shrimpRect) && !hasCollidedWithShrimp) {
            score++ // 与虾碰撞，得 1 分
            hasCollidedWithShrimp = true // 标记已经碰撞
        } else {
            // 检查是否与障碍物碰撞
            for (dieRect in dieRects) {
                if (frogRect.intersect(dieRect) && !hasCollidedWithObstacle) {
                    score -= 1 // 与障碍物碰撞，扣 1 分
                    isPlaying = false // 停止游戏
                    hasCollidedWithObstacle = true // 标记已经碰撞
                  //  onNavigateToThird() // 遊戲結束，轉到結算畫面
                    break // 避免重複扣分
                }
            }
        }
    }

    // 游戏主逻辑
    fun Play() {
        scope.launch {
            isPlaying = true
            while (isPlaying) {
                delay(500)
                counter-- // 每 500 毫秒計時器減一
                state.emit(counter) // 更新倒計時
                checkCollision() // 每次更新检查碰撞
            }
        }
    }

    // 重新开始游戏
    fun Restart() {
        isPlaying = true // 确保游戏状态设置为进行中
        counter = 30 // 重置计时器
        score = 0 // 重置分数

        // 重置碰撞标志
        hasCollidedWithFly = false
        hasCollidedWithFish = false
        hasCollidedWithShrimp = false
        hasCollidedWithObstacle = false

        Play() // 重新启动游戏逻辑
    }

    // 停止游戏
    fun StopGame() {
        isPlaying = false // 设置游戏状态为停止
       // onNavigateToThird() // 停止游戏时切换到第三画面
    }
}
