package com.e.setostory2.api

import com.e.setostory2.data.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @POST("register")
    fun regist(@Body reg: UserRegReq):Call<RegistResponse>

    @POST("login")
    fun login(@Body login: UserLogReq):Call<LogResponse>

    @Multipart
    @POST("stories")
    fun upload(
        @Header("Authorization") authHeader : String,
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody
    ):Call<UploadResponse>

    @GET("stories")
    suspend fun getStory(
        @Header("Authorization") authHeader :String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): StoryResponse

    @GET("stories?location=1")
    fun getStoriesLoc(
        @Header("Authorization") authHeader: String,
        @Query("size") size: Int
    ):Call<ListStories>

}