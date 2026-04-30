package com.vampyreworld.w2t

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform