package com.libanon4kpro.app.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.libanon4kpro.app.data.local.dao.ChannelDao
import com.libanon4kpro.app.data.local.dao.PlaylistDao
import com.libanon4kpro.app.data.local.entity.ChannelEntity
import com.libanon4kpro.app.data.local.entity.PlaylistEntity

@Database(
    entities = [PlaylistEntity::class, ChannelEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun playlistDao(): PlaylistDao
    abstract fun channelDao(): ChannelDao
}
