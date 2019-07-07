package com.t0p47.denimtestlistview.model

import com.google.gson.annotations.SerializedName
import com.t0p47.denimtestlistview.model.Capital

class CapitalResponse {

    @SerializedName("response")
    var capitals: ArrayList<Capital>? = null
}