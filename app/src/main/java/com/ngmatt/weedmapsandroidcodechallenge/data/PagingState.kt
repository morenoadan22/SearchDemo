package com.ngmatt.weedmapsandroidcodechallenge.data

sealed class PagingState {
    object Loading: PagingState()
    object Empty: PagingState()
    object NewData: PagingState()
    data class Error(val message: String): PagingState()
}