package com.libanon4kpro.app.data.repository

import com.libanon4kpro.app.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow

interface PlaylistRepository {
    fun observePlaylists(): Flow<List<PlaylistEntity>>
    suspend fun insertPlaylist(name: String, url: String): Long
    suspend fun deletePlaylist(id: Long)
}
