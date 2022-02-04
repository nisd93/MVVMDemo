package com.mvvmdemo.view.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mvvmdemo.model.request.MerchantDataRequest
import com.mvvmdemo.model.response.PreminumMerchant
import com.mvvmdemo.network.Resource
import com.mvvmdemo.repository.MerchantListRepository
import kotlinx.coroutines.launch

class MerchantListViewModel constructor(private var merchantListRepository: MerchantListRepository)  : ViewModel(){

    val merchantListViewModel = MutableLiveData<Resource<List<PreminumMerchant>>>()

    private val observer = Observer<Resource<List<PreminumMerchant>>> {
        // handles the success case when the API request gets a successful response.
        merchantListViewModel.postValue(it)
    }

    init {
        merchantListRepository.userLiveData.observeForever(observer)
    }

    fun getMerchantList(request: MerchantDataRequest) {
        viewModelScope.launch {
            // requesting asynchronously
            merchantListRepository.fetchData(request)
        }
    }

    override fun onCleared() {
        super.onCleared()
        merchantListRepository.userLiveData.removeObserver(observer)
    }

}