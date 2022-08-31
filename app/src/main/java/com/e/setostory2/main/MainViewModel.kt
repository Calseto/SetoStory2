package com.e.setostory2.main

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.e.setostory2.api.Api
import com.e.setostory2.api.RetrofitClient
import com.e.setostory2.data.StoryItemList
import kotlinx.coroutines.flow.Flow

class MainViewModel:ViewModel(){
    var retrofitServ: Api = RetrofitClient.getRetrofitInst().create(Api::class.java)

    fun getStoriesList(token :String): Flow<PagingData<StoryItemList>> {
        return Pager (config = PagingConfig(pageSize =6),
            pagingSourceFactory = { PagingHelper(retrofitServ,token) }).flow.cachedIn(viewModelScope)
    }
}