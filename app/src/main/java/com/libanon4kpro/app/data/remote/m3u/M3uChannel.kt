package com.libanon4kpro.app.data.remote.m3u

data class M3uChannel(
    val name: String,
    val url: String,
    val logo: String = "",
    val group: String = "",
    val tvgId: String = ""
)
