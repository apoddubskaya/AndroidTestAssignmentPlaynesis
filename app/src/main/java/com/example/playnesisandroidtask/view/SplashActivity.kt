package com.example.playnesisandroidtask.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.playnesisandroidtask.databinding.ActivitySplashBinding
import com.example.playnesisandroidtask.model.Model
import com.example.playnesisandroidtask.model.ValuteInfo
import com.example.playnesisandroidtask.presenter.SplashActivityPresenter

class SplashActivity : AppCompatActivity(), ISplashActivity {
    private lateinit var binding: ActivitySplashBinding
    private val presenter = SplashActivityPresenter(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.retryBtn.setOnClickListener {
            presenter.onRetryHandler()
        }
        presenter.onCreateHandler()
    }

    private fun openMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun setData(data: List<ValuteInfo>) {
        Model.setData(data)
        openMainActivity()
    }

    override fun trySetData(data: List<ValuteInfo>?) {
        if (data == null) {
            binding.progressBar.visibility = View.GONE
            binding.retryBtn.visibility = View.VISIBLE
            binding.errorText.visibility = View.VISIBLE
        }
        else setData(data)
    }

    override fun retrySetData(data: List<ValuteInfo>?) {
        if (data != null)
            setData(data)
    }
}