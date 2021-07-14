package com.example.playnesisandroidtask.presenter

import com.example.playnesisandroidtask.model.APIClient
import com.example.playnesisandroidtask.model.ValuteInfo
import com.example.playnesisandroidtask.view.ISplashActivity

class SplashActivityPresenter(private val view: ISplashActivity) {
    fun onCreateHandler() = addHandler {data: List<ValuteInfo>? -> view.trySetData(data) }
    fun onRetryHandler()  = addHandler {data: List<ValuteInfo>? -> view.retrySetData(data) }
    private fun addHandler(
            handler: (data: List<ValuteInfo>?) -> Unit
    ) {
        APIClient.addHandlerForGetData(handler)
    }
}