package com.example.frog

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
class Game(val scope: CoroutineScope, val screenW:Int, val screenH: Int) {
    var counter = 200
    val state = MutableStateFlow(200)
    val background = Background(screenW)
    var isPlaying = true


    fun Play(){
        scope.launch {
            //counter = 200
            isPlaying = true
            while (isPlaying) {
                delay(400)
                background.Roll()
                counter--
                state.emit(counter)

            }
        }
    }
    fun Restart(){
       // Virus.Reset()
        counter = 200
        isPlaying = true
        Play()
    }
}
