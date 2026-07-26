package com.libanon4kpro.app.ui.livetv

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.libanon4kpro.app.databinding.ActivityLiveTvBinding
import com.libanon4kpro.app.ui.player.PlayerActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LiveTvActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLiveTvBinding
    private val viewModel: LiveTvViewModel by viewModels()

    private lateinit var groupAdapter: GroupAdapter
    private lateinit var channelAdapter: LiveTvChannelAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLiveTvBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupGroupList()
        setupChannelList()
        observeState()
    }

    private fun setupGroupList() {
        groupAdapter = GroupAdapter { groupName ->
            viewModel.selectGroup(groupName)
        }
        binding.recyclerGroups.layoutManager = LinearLayoutManager(this)
        binding.recyclerGroups.adapter = groupAdapter
    }

    private fun setupChannelList() {
        channelAdapter = LiveTvChannelAdapter(
            onClick = { channel ->
                startActivity(
                    Intent(this, PlayerActivity::class.java)
                        .putExtra(PlayerActivity.EXTRA_URL, channel.streamUrl)
                        .putExtra(PlayerActivity.EXTRA_NAME, channel.name)
                )
            },
            onFavoriteClick = { channel ->
                viewModel.toggleFavorite(channel)
            }
        )
        binding.recyclerChannels.layoutManager = LinearLayoutManager(this)
        binding.recyclerChannels.adapter = channelAdapter
    }

    private fun observeState() {
        lifecycleScope.launch {
            viewModel.selectedGroup.collect { selectedGroup ->
                binding.tvGroupName.text = selectedGroup
            }
        }

        lifecycleScope.launch {
            viewModel.groupItems.collect { items ->
                groupAdapter.submitList(items)
            }
        }

        lifecycleScope.launch {
            viewModel.channelsForGroup.collect { channels ->
                channelAdapter.submitList(channels)
                binding.tvNoChannels.visibility =
                    if (channels.isEmpty()) View.VISIBLE else View.GONE
                binding.recyclerChannels.visibility =
                    if (channels.isEmpty()) View.GONE else View.VISIBLE
            }
        }
    }
}

