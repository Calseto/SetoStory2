package com.e.setostory2.map


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.setostory2.api.Api
import com.e.setostory2.api.RetrofitClient
import com.e.setostory2.data.ListStories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MapViewModel:ViewModel() {

    val mapData : MutableLiveData<ListStories> = MutableLiveData()

    fun observe(): MutableLiveData<ListStories>{
        return mapData
    }

    fun getMarker(authHeader : String){
        val client = RetrofitClient
            .getRetrofitInst()
            .create(Api::class.java)
            .getStoriesLoc(authHeader,100)

        client.enqueue(object : Callback<ListStories>{
            override fun onFailure(call: Call<ListStories>, t: Throwable) {
                mapData.postValue(null)
            }

            override fun onResponse(call: Call<ListStories>, response: Response<ListStories>) {
                if(response.isSuccessful){
                    mapData.postValue(response.body())
                }else{
                    mapData.postValue(null)

                }
            }
        })
    }
}