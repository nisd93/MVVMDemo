package com.mvvmdemo.model.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class MerchantDataRequest {

    @SerializedName("CompanyID")
    var companyID: Int = 0
    @SerializedName("MenuID")
    var menuID: Int = 0
    @SerializedName("SubCategoryID")
    var subCategoryID: Int = 0
    @SerializedName("ImageSize")
    var imageSize: String = ""
    @SerializedName("SearchMerchant")
    var searchMerchant: String = ""

}