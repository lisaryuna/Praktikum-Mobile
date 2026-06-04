package com.example.modul5compose

import java.io.Serializable

data class Song(
    val id: Int,
    val title: String,
    val albumName: String,
    val year: String,
    val imageResId: Int,
    val externalLink: String,
    val descriptionResId: Int
) : Serializable