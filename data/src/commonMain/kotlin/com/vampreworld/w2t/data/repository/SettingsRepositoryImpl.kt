package com.vampreworld.w2t.data.repository

import com.russhwolf.settings.ExperimentalSettingsApi
import com.russhwolf.settings.ObservableSettings
import com.russhwolf.settings.Settings
import com.russhwolf.settings.coroutines.toFlowSettings
import com.vampyreworld.w2t.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow

@OptIn(ExperimentalSettingsApi::class)
class SettingsRepositoryImpl(private val settings: Settings) : SettingsRepository {
    private val flowSettings = (settings as ObservableSettings).toFlowSettings()

    override fun getString(key: String, defaultValue: String): String = settings.getString(key, defaultValue)
    override fun setString(key: String, value: String) = settings.putString(key, value)

    override fun getInt(key: String, defaultValue: Int): Int = settings.getInt(key, defaultValue)
    override fun setInt(key: String, value: Int) = settings.putInt(key, value)

    override fun getBoolean(key: String, defaultValue: Boolean): Boolean = settings.getBoolean(key, defaultValue)
    override fun setBoolean(key: String, value: Boolean) = settings.putBoolean(key, value)

    override fun getBooleanFlow(key: String, defaultValue: Boolean): Flow<Boolean> = 
        flowSettings.getBooleanFlow(key, defaultValue)

    override fun getStringFlow(key: String, defaultValue: String): Flow<String> = 
        flowSettings.getStringFlow(key, defaultValue)

    override fun getIntFlow(key: String, defaultValue: Int): Flow<Int> = 
        flowSettings.getIntFlow(key, defaultValue)
}
