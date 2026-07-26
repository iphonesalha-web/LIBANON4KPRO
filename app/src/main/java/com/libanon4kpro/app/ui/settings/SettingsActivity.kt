package com.libanon4kpro.app.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.libanon4kpro.app.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvSettingsInfo.text = "Basic IPTV MVP settings screen"
    }
}
