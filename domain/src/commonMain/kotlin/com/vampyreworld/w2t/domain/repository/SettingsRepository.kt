package com.vampyreworld.w2t.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getString(key: String, defaultValue: String): String
    fun setString(key: String, value: String)
    fun getInt(key: String, defaultValue: Int): Int
    fun setInt(key: String, value: Int)
    fun getBoolean(key: String, defaultValue: Boolean): Boolean
    fun setBoolean(key: String, value: Boolean)
    fun getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean>
    fun getStringFlow(key: String, defaultValue: String): Flow<String>
    fun getIntFlow(key: String, defaultValue: Int): Flow<Int>
}
