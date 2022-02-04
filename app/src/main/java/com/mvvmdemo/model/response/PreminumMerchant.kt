package com.mvvmdemo.model.response

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class PreminumMerchant(
    @SerializedName("SubCategoryName")
    var subCategoryName: String?,
    @SerializedName("MerchantLogo")
    var merchantLogo: String?,
    @SerializedName("TowerNumber")
    var towerNumber: String?,
    @SerializedName("UnitNumber")
    var unitNumber: String?,
) : Serializable