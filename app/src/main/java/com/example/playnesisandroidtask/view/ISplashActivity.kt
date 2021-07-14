package com.example.playnesisandroidtask.view

import com.example.playnesisandroidtask.model.ValuteInfo

interface ISplashActivity {
    fun trySetData(data: List<ValuteInfo>?)
    fun retrySetData(data: List<ValuteInfo>?)
}