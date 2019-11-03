package com.practice.mviarchitecturedemo.ui.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.practice.mviarchitecturedemo.ui.api.MyRetrofitBuilder
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainViewState
import com.practice.mviarchitecturedemo.ui.util.*

object Repository {
    fun getBlogPosts(): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(MyRetrofitBuilder.apiService.getBlogPosts()) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState(data = MainViewState(blogPosts = apiResponse.body))
                        }
                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error(message = "Some Error")
                        }
                    }
                }
            }
        }
    }

    fun getUser(userId: String): LiveData<DataState<MainViewState>> {
        return Transformations.switchMap(MyRetrofitBuilder.apiService.GetUser(userId)) { apiResponse ->
            object : LiveData<DataState<MainViewState>>() {
                override fun onActive() {
                    super.onActive()
                    when (apiResponse) {
                        is ApiSuccessResponse -> {
                            value = DataState(data=MainViewState(
                                user = apiResponse.body
                            ))
                        }
                        is ApiErrorResponse -> {
                            value = DataState.error(message = apiResponse.errorMessage)
                        }
                        is ApiEmptyResponse -> {
                            value = DataState.error(message = "Some Error")
                        }
                    }
                }
            }
        }
    }
}