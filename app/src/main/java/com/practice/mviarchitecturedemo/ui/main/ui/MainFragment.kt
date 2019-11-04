package com.practice.mviarchitecturedemo.ui.main.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.practice.mviarchitecturedemo.R
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainStateEvent
import java.lang.ClassCastException
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment() {

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try
        {
          dataStateListener = context as DataStateListener
        }
        catch(e: ClassCastException)
        {
          println("DEBUG $context must implement DataStateListener")
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        viewModel = activity?.run { ViewModelProvider(this).get(MainViewModel::class.java) }?:throw(Exception("Invalid Activity"))
        subscribeObservers()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main_menu,menu)
    }

    fun subscribeObservers(){
        // setting the data to the view models
        viewModel.dataState.observe(viewLifecycleOwner, Observer {
                dataState ->
            Log.d("TAG","${dataState}")

            // handle progress dialog loading and message
            dataStateListener.onDataStateChange(dataState)

            // handle the data
            dataState.data?.let { event ->
                event.getContentIfNotHandled()?.let {
                    mainViewState ->

                    mainViewState.blogPosts?.let { listOfBlogs ->
                        viewModel.setBloglist(listOfBlogs)
                        Log.d("TAG", "${listOfBlogs}")
                    }

                    mainViewState.user?.let { userInfo ->
                        viewModel.setUser(userInfo)
                        Log.d("TAG", "${userInfo}")
                    }
                }
            }
            // handle the error
            dataState.message?.let {  }
            // handle loading
            dataState.loading?.let {  }

        })

        // setting data to the widgets
        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            viewState ->
            viewState.blogPosts?.let { println("Setting up the recyclerview")}
            viewState.user?.let { println("Setting up the user details")
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.action_get_blogs -> triggerGetBlogEvent()

            R.id.action_get_user -> triggerGetUserEvent()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun triggerGetUserEvent() {
    viewModel.setStateEvent(MainStateEvent.GetUserEvent("1"))
    }

    private fun triggerGetBlogEvent() {
    viewModel.setStateEvent(MainStateEvent.GetBlogPostEvent())
    }

}
