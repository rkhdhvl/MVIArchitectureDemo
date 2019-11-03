package com.practice.mviarchitecturedemo.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.practice.mviarchitecturedemo.ui.api.MyRetrofitBuilder
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainViewState
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import com.practice.mviarchitecturedemo.ui.util.*

object Repository {

    fun getBlogPosts() : LiveData<DataState<MainViewState>>{
        return object : NetworkBoundResource<List<BlogPost>,MainViewState>()
        {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<List<BlogPost>>) {
              result.value = DataState.data(
                  data = MainViewState(blogPosts = response.body)
              )
            }

            override fun createCall(): LiveData<GenericApiResponse<List<BlogPost>>> {
                 return MyRetrofitBuilder.apiService.getBlogPosts()
            }

        }.asLiveData()
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return object : NetworkBoundResource<User,MainViewState>()
        {
            override fun handleApiSuccessResponse(response: ApiSuccessResponse<User>) {
                result.value = DataState.data(
                    data = MainViewState(user = response.body)
                )
            }

            override fun createCall(): LiveData<GenericApiResponse<User>> {
                return MyRetrofitBuilder.apiService.GetUser(userId)
            }

        }.asLiveData()
    }


}