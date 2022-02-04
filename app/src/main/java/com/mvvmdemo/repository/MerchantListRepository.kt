package com.mvvmdemo.repository

import com.google.gson.Gson
import com.mvvmdemo.model.request.MerchantDataRequest
import com.mvvmdemo.model.response.MerchantDataResponse
import com.mvvmdemo.model.response.PreminumMerchant
import com.mvvmdemo.network.ApiService
import com.mvvmdemo.network.NetworkBoundResource
import kotlinx.coroutines.Deferred


class MerchantListRepository constructor(
    private var apiService: ApiService
) {

    var merchantDataRequest: MerchantDataRequest? = null

    private var networkBoundSource: NetworkBoundResource<List<PreminumMerchant>, MerchantDataResponse> =
        object : NetworkBoundResource<List<PreminumMerchant>, MerchantDataResponse>() {
            override suspend fun getRemoteAsync(): Deferred<MerchantDataResponse> {
                return apiService.getMerchantList(
                    request = Gson().toJson(merchantDataRequest)!!
                )
            }

            override suspend fun mapper(remoteType: MerchantDataResponse): List<PreminumMerchant> {
                return remoteType.premiumMerchant
            }

            override suspend fun status(remoteType: MerchantDataResponse): Boolean {
                if(remoteType.status==1)
                {
                    return false
                }
                return true
            }
        }

    val userLiveData = networkBoundSource.asLiveData()

    suspend fun fetchData(request: MerchantDataRequest) {
        this.merchantDataRequest = request
        networkBoundSource.fetch()
    }

}