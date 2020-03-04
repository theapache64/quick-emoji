package com.theapache64.quickemoji.models

import com.google.gson.annotations.SerializedName

data class Emoji(
    @SerializedName("category")
    val category: String, // Smileys & Emotion (face-affection)
    @SerializedName("char")
    val char: String, // 😙
    @SerializedName("codes")
    val codes: String, // 1F619
    @SerializedName("group")
    val group: String, // Smileys & Emotion
    @SerializedName("name")
    val name: String, // kissing face with smiling eyes
    @SerializedName("subgroup")
    val subgroup: String // face-affection
)