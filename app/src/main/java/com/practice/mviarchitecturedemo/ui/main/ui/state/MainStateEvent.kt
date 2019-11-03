package com.practice.mviarchitecturedemo.ui.main.ui.state

sealed class MainStateEvent {

    class GetBlogPostEvent : MainStateEvent()

    class GetUserEvent( val userId : String) : MainStateEvent()

    class None : MainStateEvent()

}