package com.theapache64.quickemoji.core

import com.google.gson.reflect.TypeToken
import com.theapache64.quickemoji.models.Emoji
import com.theapache64.quickemoji.utils.GsonUtils
import com.theapache64.quickemoji.utils.JarUtils
import java.io.File

object EmojiFinder {

    private val EMOJI_FILE = File("${JarUtils.getJarDir()}emoji.json")

    private val EMOJIS by lazy {
        val type = object : TypeToken<List<Emoji>>() {}.type!!
        GsonUtils.gson.fromJson(EMOJI_FILE.readText(), type) as List<Emoji>
    }

    fun find(_keyword: String): List<Emoji> {
        val keywords = _keyword.toLowerCase()
        val result = mutableListOf<Emoji>()
        for (keyword in keywords.split(" ")) {
            for (emoji in EMOJIS) {
                if (emoji.name.contains(keyword) ||
                    emoji.category.contains(keyword) ||
                    emoji.group.contains(keyword) ||
                    emoji.subgroup.contains(keyword)
                ) {
                    result.add(emoji)
                }
            }
        }

        return result
    }

    fun all(): List<Emoji> = EMOJIS
}