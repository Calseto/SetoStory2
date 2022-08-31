package com.e.setostory2.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.e.setostory2.api.Api
import com.e.setostory2.api.RetrofitClient
import com.e.setostory2.data.LogResponse
import com.e.setostory2.data.UserLogReq
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginViewModel : ViewModel() {

    var Status : MutableLiveData<LogResponse> = MutableLiveData()

    fun getLoginResponse() : MutableLiveData<LogResponse>{
        return Status
    }

    fun login(x : UserLogReq){
        val retroService = RetrofitClient.getRetrofitInst().create(Api::class.java)
        val call = retroService.login(x)

        call.enqueue(object : Callback<LogResponse>{
            override fun onFailure(call: Call<LogResponse>, t: Throwable) {
                Status.postValue(null)
            }

            override fun onResponse(call: Call<LogResponse>, response: Response<LogResponse>) {
                if(response.isSuccessful){
                    Status.postValue(response.body())
                }else{
                    Status.postValue(null)
                }
            }
        })
    }
}