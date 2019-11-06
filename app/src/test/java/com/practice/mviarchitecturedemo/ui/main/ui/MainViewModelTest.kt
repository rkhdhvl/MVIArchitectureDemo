package com.practice.mviarchitecturedemo.ui.main.ui

import com.practice.mviarchitecturedemo.ui.main.ui.state.MainStateEvent
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import com.practice.mviarchitecturedemo.ui.test
import com.practice.mviarchitecturedemo.util.InstantExecutorExtension
import com.practice.mviarchitecturedemo.util.TestUtil
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.MockitoAnnotations

@ExtendWith(InstantExecutorExtension::class)
class MainViewModelTest {

    lateinit var mainViewModel: MainViewModel

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        mainViewModel = MainViewModel()
    }

    @Test
    fun shouldReturnListOfBlogPosts() {
        // Arrange
        val testBlogList: List<BlogPost> = TestUtil.TEST_BLOG_LIST
        // Act
        val testObserver = mainViewModel.viewState.test()
        mainViewModel.setBloglist(testBlogList)
        mainViewModel.handleStates(MainStateEvent.GetBlogPostEvent())
        // Assert
        testObserver.assertValue { it.blogPosts?.equals(testBlogList) }
            .assertHistorySize(1)
            .value().apply {
                assertEquals(2, blogPosts?.size)
                assertThat(blogPosts?.get(0), `is`(testBlogList.get(0)))
            }
        println(testObserver.value().blogPosts)
    }

    @Test
    fun shouldReturnUserDetails() {
        // Arrange
        val testUser: User = TestUtil.USER
        // Act
        val testObserver = mainViewModel.viewState.test()
        mainViewModel.setUser(testUser)
        mainViewModel.handleStates(MainStateEvent.GetUserEvent("1"))
        // Assert
        testObserver.assertValue{ it.user?.equals(testUser)}
            .assertHistorySize(1)
            .value().apply {
                assertEquals(user?.username,testUser.username)
            }
        println(testObserver.value().user)
    }
}