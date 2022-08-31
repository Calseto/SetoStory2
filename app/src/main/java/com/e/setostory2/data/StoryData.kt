package com.e.setostory2.data

import com.google.gson.annotations.SerializedName



data class UploadResponse(

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String

    )

data class Story(
    val name : String? = "",

    val description : String?="",

    val photoUrl : String?="",

    val lat:String?="",

    val lon:String?=""
    )

data class StoryResponse(

    val error: Boolean,

    val message: String,

    val listStory: ArrayList<StoryItemList>
    )
data class StoryItemList(

    val name: String,

    val description: String,

    val photoUrl: String

    )
data class ListStories(val listStory : List<Story>)
