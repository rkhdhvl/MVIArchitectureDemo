package com.practice.mviarchitecturedemo.ui.api

import androidx.lifecycle.LiveData
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import com.practice.mviarchitecturedemo.ui.util.GenericApiResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("placeholder/blogs")
    fun getBlogPosts(): LiveData<GenericApiResponse<List<BlogPost>>>

    @GET("placeholder/user/{userId}")
    fun GetUser( @Path("userId") userId : String) : LiveData<GenericApiResponse<User>>
}