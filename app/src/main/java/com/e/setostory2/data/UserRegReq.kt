package com.e.setostory2.data

import com.google.gson.annotations.SerializedName

data class UserRegReq(

    @SerializedName("name")
    var name :String?,

    @SerializedName("email")
    var email:String?,

    @SerializedName("password")
    var password : String?
)
