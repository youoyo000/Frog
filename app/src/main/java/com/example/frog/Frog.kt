package com.example.frog


class Frog(screenH:Int, scale:Float) {
    var w = (100 * scale).toInt()  //小男孩寬度
    var h = (310 * scale).toInt()  //小男孩高度
    var x = 265  //小男孩x軸座標
    var y = screenH - h  //小男孩y軸座標
    var pictNo = 0  //切換圖片

    fun fly() {
        pictNo++
        if (pictNo > 2) {
            pictNo = 0
        }
    }
}

/*
class Frog(screenH:Int, scale:Float) {
    var w = (100 * scale).toInt()  //呱寬度
    var h = (220 * scale).toInt()  //呱高度
    var x = 250  //呱x軸座標
    var y = screenH - h  //呱y軸座標
    var pictNo = 0  //切換圖片

    fun () {
        pictNo++
        if (pictNo > 2) {
            pictNo = 0
        }
    }
}*/
