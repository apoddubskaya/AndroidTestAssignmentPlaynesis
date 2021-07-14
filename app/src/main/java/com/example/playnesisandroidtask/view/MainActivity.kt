package com.example.playnesisandroidtask.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.example.playnesisandroidtask.R
import com.example.playnesisandroidtask.databinding.ActivityMainBinding
import com.example.playnesisandroidtask.presenter.MainActivityPresenter
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity : AppCompatActivity(), IMainActivity {
    private lateinit var binding: ActivityMainBinding
    private lateinit var presenter: MainActivityPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = MainActivityPresenter(this)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.leftButton.setOnClickListener {
            presenter.onButtonClickHandler(
                    binding.leftTextView.text,
                    MainActivityPresenter.Side.LEFT
            )
        }
        binding.rightButton.setOnClickListener {
            presenter.onButtonClickHandler(
                    binding.rightTextView.text,
                    MainActivityPresenter.Side.RIGHT
            )
        }
        binding.leftEditText.doOnTextChanged { text, _, _, _ ->
            if (binding.leftEditText.hasFocus())
                presenter.onEditTextChangedHandler(
                    new_value = text,
                    from = binding.leftTextView.text,
                    to = binding.rightTextView.text,
                    side = MainActivityPresenter.Side.LEFT
                )
        }
        binding.rightEditText.doOnTextChanged { text, _, _, _ ->
            if (binding.rightEditText.hasFocus())
                presenter.onEditTextChangedHandler(
                        new_value = text,
                        from = binding.rightTextView.text,
                        to = binding.leftTextView.text,
                        side = MainActivityPresenter.Side.RIGHT
                )
        }
    }

    override fun error() {
        Toast.makeText(
                applicationContext,
                R.string.main_activity_error_message,
                Toast.LENGTH_LONG
        ).show()
    }

    override fun inputError() {
        Toast.makeText(
                applicationContext,
                R.string.main_activity_input_error_message,
                Toast.LENGTH_LONG
        ).show()
    }

    override fun setLeftEditTextValue(text: String) {
        binding.leftEditText.setText(text)
    }

    override fun setRightEditTextValue(text: String) {
        binding.rightEditText.setText(text)
    }

    override fun setLeftTextViewValue(text: String) {
        binding.leftTextView.text = text
        presenter.onEditTextChangedHandler(
                new_value = binding.rightEditText.text?.toString(),
                from = binding.rightTextView.text,
                to = text,
                side = MainActivityPresenter.Side.RIGHT
        )
    }

    override fun setRightTextViewValue(text: String) {
        binding.rightTextView.text = text
        presenter.onEditTextChangedHandler(
                new_value = binding.leftEditText.text?.toString(),
                from = binding.leftTextView.text,
                to = text,
                side = MainActivityPresenter.Side.LEFT
        )
    }

    override fun openConfirmationDialog(
            currencyStringArray: Array<String>,
            checkedItem: Int,
            side: MainActivityPresenter.Side
    ) {
        MaterialAlertDialogBuilder(this)
                .setTitle(R.string.dialog_title)
                .setNeutralButton(R.string.neutral_button_text) { dialog, _ ->
                    dialog.dismiss()
                }
                .setPositiveButton(R.string.positive_button_text) { _, _ ->
                    presenter.onPositiveButtonClickHandler(side)
                }
                .setSingleChoiceItems(currencyStringArray, checkedItem) { _, which ->
                    presenter.onItemSelectedHandler(which)
                }
                .show()
    }
}