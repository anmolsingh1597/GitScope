package com.example.gitscope.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val avatarUrl: String?
): Parcelable

@Parcelize
data class Repository(
    val name: String,
    val description: String?,
    val updatedAt: String,
    val stargazersCount: Int,
    val forksCount: Int,
): Parcelable