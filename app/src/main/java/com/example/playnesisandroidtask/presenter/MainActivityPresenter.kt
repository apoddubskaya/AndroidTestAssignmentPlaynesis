package com.example.playnesisandroidtask.presenter

import com.example.playnesisandroidtask.model.Model
import com.example.playnesisandroidtask.view.IMainActivity
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols

const val LEFT_CHAR_CODE_INITIAL_VALUE: String = "RUB"
const val RIGHT_CHAR_CODE_INITIAL_VALUE: String = "USD"

class MainActivityPresenter(private val view: IMainActivity) {
    enum class Side{
        LEFT, RIGHT
    }

    private lateinit var currentCharCode: String

    fun onEditTextChangedHandler(
            new_value: CharSequence?,
            from: CharSequence?,
            to: CharSequence?,
            side: Side
    ) {
        if (new_value == null || from == null || to == null) {
            view.error()
            return
        }
        if (new_value.isEmpty()) {
            when (side) {
                Side.LEFT -> view.setRightEditTextValue("")
                Side.RIGHT -> view.setLeftEditTextValue("")
            }
            return
        }
        if (new_value.toString().toDoubleOrNull() == null) {
            view.inputError()
            return
        }
        val convertedValue = Model.convert(
                new_value.toString().toDouble(),
                from.toString(),
                to.toString()
        )
        if (convertedValue == null) {
            view.error()
            return
        }
        val formatString = DecimalFormat(
                "#.##",
                DecimalFormatSymbols().apply { decimalSeparator = '.' }
        ).format(convertedValue)
        when (side) {
            Side.LEFT -> view.setRightEditTextValue(formatString)
            Side.RIGHT -> view.setLeftEditTextValue(formatString)
        }
    }

    fun onButtonClickHandler(curr: CharSequence?, side: Side) {
        if (curr == null) {
            view.error()
            return
        }
        currentCharCode = curr.toString()
        val checkedItem = Model.getIndexOf(currentCharCode)
        if (checkedItem == -1) {
            view.error()
            return
        }
        view.openConfirmationDialog(
                Model.currencyList.map {it.toString()}.toTypedArray(),
                checkedItem,
                side
        )
    }

    fun onItemSelectedHandler(selectedItemIndex: Int) {
         currentCharCode = Model.getCharCodeByIndex(selectedItemIndex)
    }

    fun onPositiveButtonClickHandler(side: Side) {
        when (side) {
            Side.LEFT -> view.setLeftTextViewValue(currentCharCode)
            Side.RIGHT -> view.setRightTextViewValue(currentCharCode)
        }
    }

    fun onCreateHandler(isLeftTextEmpty: Boolean, isRightTextEmpty: Boolean) {
        if (isLeftTextEmpty)
            view.setLeftTextViewValue(LEFT_CHAR_CODE_INITIAL_VALUE)
        if (isRightTextEmpty)
            view.setRightTextViewValue(RIGHT_CHAR_CODE_INITIAL_VALUE)
    }
}