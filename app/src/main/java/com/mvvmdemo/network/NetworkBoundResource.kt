package com.mvvmdemo.network

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.Deferred

abstract class NetworkBoundResource<ResultType, RemoteType> {

    private val mutableLiveData = MutableLiveData<Resource<ResultType>>()

    abstract suspend fun getRemoteAsync(): Deferred<RemoteType>

    abstract suspend fun mapper(remoteType: RemoteType): ResultType

    abstract suspend fun status(remoteType: RemoteType): Boolean

    suspend fun fetch() {
        try {
            mutableLiveData.postValue(Resource.Loading())
            val remoteData = getRemoteAsync().await()
            mutableLiveData.postValue(Resource.Success(mapper(remoteData)))
        } catch (exception: Exception) {
            mutableLiveData.postValue(Resource.Error(exception))
        }
    }

    fun asLiveData(): MutableLiveData<Resource<ResultType>> {
        return mutableLiveData
    }

}