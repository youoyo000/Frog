package com.example.frog

import kotlin.math.abs
class Background(val screenW:Int) {
    var x1 = 0  //背景圖1_x軸
    var x2 = screenW  //背景圖2_x軸

    fun Roll(){
        x1 -=190
        if (abs(x1) > screenW){ //已經移動整張
            x1 = 0
        }
        x2 = x1 + screenW
    }
}
