package com.e.setostory2.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.setostory2.api.Api
import com.e.setostory2.api.RetrofitClient
import com.e.setostory2.data.UploadResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddStoryViewModel:ViewModel() {
    var data: MutableLiveData<UploadResponse> = MutableLiveData()

    fun addStoryObserver() : MutableLiveData<UploadResponse> {
        return data
    }

    fun postStory(header: String, x: MultipartBody.Part, y: RequestBody) {
        val retroService = RetrofitClient.getRetrofitInst().create(Api::class.java)
        val call = retroService.upload(header, x,y)
        call.enqueue(object : Callback<UploadResponse>{
            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                data.postValue(null)
            }
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                if(response.isSuccessful){
                    data.postValue(response.body())
                }else{
                    data.postValue(response.body())
                }
            }
        })
    }

}