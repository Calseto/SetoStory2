package com.e.setostory2.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.setostory2.api.Api
import com.e.setostory2.api.RetrofitClient
import com.e.setostory2.data.RegistResponse
import com.e.setostory2.data.UserRegReq
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){

    var status : MutableLiveData<RegistResponse> = MutableLiveData()

    fun getRegistResponse() : MutableLiveData<RegistResponse>{
        return status
    }

    fun register(x : UserRegReq){
        val instance = RetrofitClient.getRetrofitInst().create(Api::class.java)
        val call = instance.regist(x)

        call.enqueue(object : Callback<RegistResponse>{
            override fun onFailure(call: Call<RegistResponse>, t: Throwable) {
                status.postValue(null)
            }
            override fun onResponse(call: Call<RegistResponse>, response: Response<RegistResponse>) {
               if(response.isSuccessful){
                   status.postValue(response.body())
               }else{
                   status.postValue(null)
               }
            }
        })
    }


}