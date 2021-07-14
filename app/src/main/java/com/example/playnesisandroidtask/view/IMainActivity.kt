package com.example.playnesisandroidtask.view

import com.example.playnesisandroidtask.presenter.MainActivityPresenter

interface IMainActivity {
    fun error()
    fun inputError()
    fun setLeftEditTextValue(text: String)
    fun setRightEditTextValue(text: String)
    fun setLeftTextViewValue(text: String)
    fun setRightTextViewValue(text: String)
    fun openConfirmationDialog(
            currencyStringArray: Array<String>,
            checkedItem: Int,
            side: MainActivityPresenter.Side
    )
}