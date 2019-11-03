package com.practice.mviarchitecturedemo.ui.main.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainStateEvent
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainViewState
import com.practice.mviarchitecturedemo.ui.main.ui.util.AbsentLiveData
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import com.practice.mviarchitecturedemo.ui.repository.Repository
import com.practice.mviarchitecturedemo.ui.util.DataState

class MainViewModel : ViewModel() {
private val _stateEvent : MutableLiveData<MainStateEvent> = MutableLiveData()
private val _viewState : MutableLiveData<MainViewState> = MutableLiveData()

    val viewState: LiveData<MainViewState>
    get() = _viewState

    val dataState : LiveData<DataState<MainViewState>> = Transformations
        .switchMap(_stateEvent){
            stateEvent -> stateEvent?.let {
            handleStates(it)
        }
        }

    fun handleStates(stateEvent: MainStateEvent) : LiveData<DataState<MainViewState>>
    {
     when(stateEvent)
     {
         is MainStateEvent.GetBlogPostEvent -> {
             return Repository.getBlogPosts()
         }

         is MainStateEvent.GetUserEvent -> {
             return Repository.getUser(stateEvent.userId)
         }

         is MainStateEvent.None -> {
             return AbsentLiveData.create()
         }
     }
    }

    // updating the value of the state event
    fun getCurrentViewStateOrNew() : MainViewState {
        val value = viewState.value?.let { it }?: MainViewState()
        return value
    }

    fun setBloglist(blogs : List<BlogPost>)
    {
        val update = getCurrentViewStateOrNew()
        update.blogPosts = blogs
        _viewState.value = update
    }

    fun setUser(user: User)
    {
        val update = getCurrentViewStateOrNew()
        update.user = user
        _viewState.value = update
    }

    fun setStateEvent(event: MainStateEvent)
    {
        _stateEvent.value = event
    }

}