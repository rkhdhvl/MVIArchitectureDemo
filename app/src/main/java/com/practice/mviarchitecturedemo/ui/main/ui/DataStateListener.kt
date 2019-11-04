package com.practice.mviarchitecturedemo.ui.main.ui

import com.practice.mviarchitecturedemo.ui.util.DataState


interface DataStateListener {

    fun onDataStateChange(dataState: DataState<*>?)
}