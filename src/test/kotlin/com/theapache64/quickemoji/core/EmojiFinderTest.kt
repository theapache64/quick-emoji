package com.theapache64.quickemoji.core

import com.winterbe.expekt.should
import org.junit.Test

class EmojiFinderTest {
    @Test
    fun testEmojiSearchSuccess() {
        EmojiFinder.find("grinning").size.should.above(1)
    }
}