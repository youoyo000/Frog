package com.example.frog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class Game(val scope: CoroutineScope, val screenW: Int, val screenH: Int, scale: Float) {
    var counter = 20 // 倒數計時器的初始值
    val state = MutableStateFlow(20) // 使用 MutableStateFlow 來追蹤計時器的狀態
    val background = Background(screenW) // 背景物件，負責處理背景移動邏輯
    val frog = Frog(screenH, scale) // 青蛙物件，負責處理青蛙跳躍的邏輯
    var isPlaying = true // 控制遊戲是否正在進行的布林值


    // 啟動遊戲邏輯
    fun Play() {
        scope.launch {
            isPlaying = true // 遊戲開始進行
            if (isPlaying) { // 如果遊戲正在進行
                delay(1000) // 每 500 毫秒執行一次
                counter-- // 計時器減 1
                state.emit(counter) // 更新計時器的狀態
            }else if(counter<0){
                isPlaying = false
            }
        }

    }

    // 重新開始遊戲
    fun Restart() {
        counter = 20 // 將倒數計時器重置為初始值
        isPlaying = true // 確保遊戲狀態設為進行中
        Play() // 呼叫 Play() 方法重新啟動遊戲邏輯
    }
}