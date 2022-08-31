package com.e.setostory2.main

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.e.setostory2.api.Api
import com.e.setostory2.data.StoryItemList

class PagingHelper (private val api: Api, private val token : String): PagingSource<Int, StoryItemList>() {
    override fun getRefreshKey(state: PagingState<Int, StoryItemList>): Int? {

        return state.anchorPosition?.let { anchorPosition ->
            val anchor = state.closestPageToPosition(anchorPosition)
            anchor?.prevKey?.plus(1) ?: anchor?.nextKey?.minus(1)
        }

    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, StoryItemList> {
        return try {
            val page: Int = params.key ?: FIRST_PAGE_INDEX
            val response = api.getStory(token, page, params.loadSize)

            LoadResult.Page(
                data = response.listStory,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.listStory.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 1
    }
}