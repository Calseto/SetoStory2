package com.e.setostory2.data

import com.google.gson.annotations.SerializedName

data class LogResponse(

    @field:SerializedName("loginResult")
    val logResult: LogResult? = null,

    @field:SerializedName("error")
    val error: Boolean? = null,

    @field:SerializedName("message")
    val message: String? = null
)
data class RegistResponse(

    @SerializedName("error")
    val error : Boolean? = null,

    @SerializedName("message")
    val message : String? = null
    )

data class LogResult(

    @field:SerializedName("name")
     val name: String? = null,

        @field:SerializedName("userId")
        val id: String? = null,

        @field:SerializedName("token")
        val token: String? = null
    )
