package com.mvvmdemo.model.response

import com.google.gson.annotations.SerializedName
import kotlinx.coroutines.Job
import java.io.Serializable

data class MerchantDataResponse(
        @SerializedName("PremiumMerchant")
        var premiumMerchant: List<PreminumMerchant>,
        @SerializedName("Status")
        var status: Int
) : Serializable

