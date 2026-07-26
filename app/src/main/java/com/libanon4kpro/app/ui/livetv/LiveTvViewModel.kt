package com.libanon4kpro.app.ui.livetv

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.libanon4kpro.app.data.local.entity.ChannelEntity
import com.libanon4kpro.app.data.repository.ChannelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveTvViewModel @Inject constructor(
    private val channelRepository: ChannelRepository
) : ViewModel() {

    companion object {
        const val GROUP_ALL = "All Channels"
    }

    private val groups = channelRepository.observeGroups()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val allChannels = channelRepository.observeChannels()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _selectedGroup = MutableStateFlow(GROUP_ALL)
    val selectedGroup: StateFlow<String> = _selectedGroup.asStateFlow()

    val groupItems: StateFlow<List<GroupItem>> = combine(
        allChannels, groups, _selectedGroup
    ) { channels, groupList, selected ->
        buildList {
            add(GroupItem(GROUP_ALL, channels.size, selected == GROUP_ALL))
            groupList.forEach { group ->
                add(GroupItem(group, channels.count { it.groupName == group }, selected == group))
            }
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    @OptIn(ExperimentalCoroutinesApi::class)
    val channelsForGroup: StateFlow<List<ChannelEntity>> = _selectedGroup
        .flatMapLatest { group ->
            if (group == GROUP_ALL) {
                channelRepository.observeChannels()
            } else {
                channelRepository.observeByGroup(group)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun selectGroup(group: String) {
        _selectedGroup.value = group
    }

    fun toggleFavorite(channel: ChannelEntity) {
        viewModelScope.launch {
            channelRepository.setFavorite(channel.id, !channel.isFavorite)
        }
    }
}

