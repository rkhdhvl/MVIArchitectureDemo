package com.practice.mviarchitecturedemo.ui


import androidx.lifecycle.LiveData
import com.practice.mviarchitecturedemo.ui.livedatatesting.TestObserver

/**
 * Credit - jraska on github
 * Copied from [https://github.com/jraska/livedata-testing]
 * Reason - The library on maven only supports AndroidX
 */
fun <T> LiveData<T>.test(): TestObserver<T> {
  return TestObserver.test(this)
}
