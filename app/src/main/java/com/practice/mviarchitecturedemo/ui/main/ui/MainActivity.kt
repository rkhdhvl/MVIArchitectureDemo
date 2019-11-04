package com.practice.mviarchitecturedemo.ui.main.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.practice.mviarchitecturedemo.R
import com.practice.mviarchitecturedemo.ui.util.DataState
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() , DataStateListener {
    override fun onDataStateChange(dataState: DataState<*>?) {
        handleDataStateChange(dataState)
    }

    private fun handleDataStateChange(dataState: DataState<*>?) {
        dataState?.let {
           // handle loading
            showProgressBar(dataState.loading)

            // handle message
            dataState.message?.let {
                event -> event.getContentIfNotHandled()?.let {
                    message -> showToast(message)
            }
            }
        }
    }

    fun showToast(message:String){
        Toast.makeText(this,message,Toast.LENGTH_LONG).show()
    }

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        showMainFragment()
    }

    fun showMainFragment()
    {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.fragment_container,
                MainFragment(),"MainFragment")
            .commit()
    }

    fun showProgressBar(isVisible:Boolean)
    {
        if(isVisible)
        {
            progress_bar.visibility = View.VISIBLE
        }
        else
        {
            progress_bar.visibility = View.INVISIBLE
        }
    }
}
