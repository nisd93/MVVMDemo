package com.mvvmdemo.network

/**
 * Created by Nisarg Mistry on 01/02/22.
 */
sealed class Resource<out T> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error<out T>(val throwable: Throwable) : Resource<T>()
    class Loading<out T> : Resource<T>()
}
