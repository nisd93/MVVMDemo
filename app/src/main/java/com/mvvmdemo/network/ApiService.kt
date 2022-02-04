package com.mvvmdemo.network

import com.mvvmdemo.model.request.MerchantDataRequest
import com.mvvmdemo.model.response.MerchantDataResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.*


interface ApiService {

    /**
     * get data from api
     */
    @FormUrlEncoded
    @POST("apitokenv8/list/model/merchant")
    fun getMerchantList(
        @Header("issecuritydisable") headerName2: Int = 0,
        @Field ("params")request: String
    ): Deferred<MerchantDataResponse>

}