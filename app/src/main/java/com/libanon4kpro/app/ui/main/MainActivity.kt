package com.libanon4kpro.app.ui.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.libanon4kpro.app.databinding.ActivityMainBinding
import com.libanon4kpro.app.databinding.DialogAddPlaylistBinding
import com.libanon4kpro.app.ui.livetv.LiveTvActivity
import com.libanon4kpro.app.ui.settings.SettingsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupCardActions()
        setupTopActions()
        observeState()
    }

    private fun setupCardActions() {
        binding.cardLiveTv.setOnClickListener {
            startActivity(Intent(this, LiveTvActivity::class.java))
        }

        binding.cardMovies.setOnClickListener {
            showComingSoonDialog("Movies")
        }

        binding.cardSeries.setOnClickListener {
            showComingSoonDialog("TV Series")
        }

        binding.cardCatchUp.setOnClickListener {
            showComingSoonDialog("Catch Up")
        }
    }

    private fun setupTopActions() {
        binding.btnAddPlaylist.setOnClickListener {
            showAddPlaylistDialog()
        }

        binding.btnChangeServer.setOnClickListener {
            showComingSoonDialog("Change Server")
        }

        binding.btnSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.loading.collect { loading ->
                binding.progressBar.visibility =
                    if (loading) android.view.View.VISIBLE else android.view.View.GONE
            }
        }

        lifecycleScope.launch {
            viewModel.message.collect { message ->
                if (message != null) {
                    Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                    viewModel.clearMessage()
                }
            }
        }
    }

    private fun showAddPlaylistDialog() {
        val dialogBinding = DialogAddPlaylistBinding.inflate(layoutInflater)

        AlertDialog.Builder(this)
            .setTitle("Add Playlist")
            .setView(dialogBinding.root)
            .setPositiveButton("Import") { _, _ ->
                val name = dialogBinding.etPlaylistName.text.toString().trim()
                val url = dialogBinding.etPlaylistUrl.text.toString().trim()

                if (name.isNotBlank() && url.isNotBlank()) {
                    viewModel.importPlaylist(name, url)
                } else {
                    Toast.makeText(this, "Name and URL are required", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun showComingSoonDialog(feature: String) {
        AlertDialog.Builder(this)
            .setTitle(feature)
            .setMessage("$feature will be available in a future update.")
            .setPositiveButton("OK", null)
            .show()
    }
}

