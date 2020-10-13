package com.ngmatt.weedmapsandroidcodechallenge.data

import com.ngmatt.weedmapsandroidcodechallenge.data.model.Business

data class BusinessResponse (
    val total: Int,
    var businesses: List<Business>
)