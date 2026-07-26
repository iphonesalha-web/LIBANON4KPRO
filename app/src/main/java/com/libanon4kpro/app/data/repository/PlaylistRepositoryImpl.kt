package com.libanon4kpro.app.data.repository

import com.libanon4kpro.app.data.local.dao.PlaylistDao
import com.libanon4kpro.app.data.local.entity.PlaylistEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    private val playlistDao: PlaylistDao
) : PlaylistRepository {

    override fun observePlaylists(): Flow<List<PlaylistEntity>> =
        playlistDao.observeAll()

    override suspend fun insertPlaylist(name: String, url: String): Long {
        return playlistDao.insert(
            PlaylistEntity(
                name = name,
                url = url
            )
        )
    }

    override suspend fun deletePlaylist(id: Long) {
        playlistDao.deleteById(id)
    }
}
