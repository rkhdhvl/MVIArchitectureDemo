package com.practice.mviarchitecturedemo.ui.main.ui.state

import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User

data class MainViewState(
    var blogPosts : List<BlogPost>? = null,
    var user:User?=null
)