package com.practice.mviarchitecturedemo.ui.main.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainStateEvent
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainViewState
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.util.DataState
import com.practice.mviarchitecturedemo.ui.util.Event
import com.practice.mviarchitecturedemo.util.InstantExecutorExtension
import com.practice.mviarchitecturedemo.util.LiveDataTestUtil
import com.practice.mviarchitecturedemo.util.TestUtil
import org.junit.Assert
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest_Util {

    lateinit var mainViewModel: MainViewModel

    @Mock
    lateinit var dataState: DataState<MainViewState>

    @Mock
    lateinit var mainViewState: MainViewState

    @Mock
    lateinit var mainStateEvent: MainStateEvent

    @Mock
    lateinit var getBlogPostEvent: MainStateEvent.GetBlogPostEvent

    @Mock
    lateinit var observer: Observer<DataState<MainViewState>>



    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel()
    }

    @Test
    fun handleStateForReturningTheListsOfBlogPost() {
        val blogList: List<BlogPost> = TestUtil.TEST_BLOG_LIST
        val wrappedData: LiveDataTestUtil<DataState<MainViewState>> = LiveDataTestUtil()
        val returnedValue = MutableLiveData<DataState<MainViewState>>()
        mainViewState.blogPosts = blogList

        dataState.data = Event.dataEvent(mainViewState)
        returnedValue.setValue(dataState)
        when(mainStateEvent)
        {
            getBlogPostEvent -> println(returnedValue.value?.data?.hasBeenHandled.toString())

        }
        // Act
        mainViewModel.setBloglist(blogList)
        mainViewModel.dataState.observeForever(observer)
        val observedData = wrappedData.getValue(mainViewModel.handleStates(getBlogPostEvent))

        // Assert
        Assert.assertEquals(dataState.data,observedData.data)
        println(dataState.data)
        println(observedData.data)

    }


}