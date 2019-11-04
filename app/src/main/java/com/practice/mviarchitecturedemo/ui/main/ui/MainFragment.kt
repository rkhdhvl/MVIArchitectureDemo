package com.practice.mviarchitecturedemo.ui.main.ui


import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.practice.mviarchitecturedemo.R
import com.practice.mviarchitecturedemo.ui.main.ui.adapter.MainRecyclerAdapter
import com.practice.mviarchitecturedemo.ui.main.ui.state.MainStateEvent
import com.practice.mviarchitecturedemo.ui.model.BlogPost
import com.practice.mviarchitecturedemo.ui.model.User
import com.practice.mviarchitecturedemo.ui.util.TopSpacingItemDecoration
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.ClassCastException
import java.lang.Exception

/**
 * A simple [Fragment] subclass.
 */
class MainFragment : Fragment(),MainRecyclerAdapter.Interaction {

    override fun onItemSelected(position: Int, item: BlogPost) {
       println("DEBUG : CLICKED $position")
        println("DEBUG : CLICKED $item")
    }

    lateinit var viewModel: MainViewModel

    lateinit var dataStateListener: DataStateListener

    lateinit var blogListAdapter: MainRecyclerAdapter

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
        initRecyclerView()
    }

    private fun initRecyclerView()
    {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(activity)
            val topSpacingDecorator = TopSpacingItemDecoration(30)
            addItemDecoration(topSpacingDecorator)
            blogListAdapter = MainRecyclerAdapter(this@MainFragment)
            adapter = blogListAdapter
        }
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
            viewState.blogPosts?.let {
                // setting up the recyclerView here
                lisOfBlogPosts ->
                blogListAdapter.submitList(lisOfBlogPosts)
            }
            viewState.user?.let {
                // setting the user details
                user ->  setUserProperties(user)
            }
        })
    }

    private fun setUserProperties(user:User)
    {
        email.text = user.email
        username.text = user.username
        view?.let { Glide.with(it)
            .load(user.image)
            .into(image)
        }
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
