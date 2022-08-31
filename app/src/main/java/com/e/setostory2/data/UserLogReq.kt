package com.e.setostory2.data

import com.google.gson.annotations.SerializedName
import retrofit2.http.Path

data class UserLogReq(

    @SerializedName ("email")
    var email:String?,

    @SerializedName("password")
    var password:String?

)

