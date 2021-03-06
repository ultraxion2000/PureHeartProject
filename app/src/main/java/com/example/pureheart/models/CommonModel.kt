package com.example.pureheart.models

import android.os.Message

data class CommonModel(
    val id: String = "",
    var username: String = "",
    var bio: String = "",
    var fullname: String = "",
    var state: String = "",
    var phone: String = "",
    var photoUrl: String = "empty",
    var code: String = "---",

    var text:String = "",
    var type:String ="",
    var from:String = "",
    var timeStamp:Any = "",
    var imageUrl:String = "empty",

    var lastMessage:String = "",
    var choice:Boolean = false


) {
    override fun equals(other: Any?): Boolean {
        return (other as CommonModel).id == id
    }
}