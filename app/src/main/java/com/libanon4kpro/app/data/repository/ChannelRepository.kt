package com.libanon4kpro.app.data.repository

import com.libanon4kpro.app.data.local.entity.ChannelEntity
import kotlinx.coroutines.flow.Flow

interface ChannelRepository {
    fun observeChannels(): Flow<List<ChannelEntity>>
    fun observeFavorites(): Flow<List<ChannelEntity>>
    fun observeGroups(): Flow<List<String>>
    fun observeByGroup(groupName: String): Flow<List<ChannelEntity>>
    suspend fun importPlaylist(playlistName: String, playlistUrl: String): Result<Unit>
    suspend fun setFavorite(channelId: Long, isFavorite: Boolean)
}

