package com.github.yuang.kt.android_mvvm.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.github.yuang.kt.android_mvvm.BaseApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = BaseApp.mContext.packageName)

object DataStoreHelper {

    /**
     * 保存数据
     * */
    suspend fun <T : Any> putData(key: String, value: T) {
        BaseApp.mContext.dataStore.edit { setting ->
            when (value) {
                is Int -> setting[intPreferencesKey(key)] = value
                is Long -> setting[longPreferencesKey(key)] = value
                is Double -> setting[doublePreferencesKey(key)] = value
                is Float -> setting[floatPreferencesKey(key)] = value
                is Boolean -> setting[booleanPreferencesKey(key)] = value
                is String -> setting[stringPreferencesKey(key)] = value
                else -> throw IllegalArgumentException("This type can be saved into DataStore")
            }
        }
    }

    /**
     * 获取数据
     * */
    suspend inline fun <reified T : Any> getData( key: String): T {
        return when (T::class) {
            Int::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[intPreferencesKey(key)] ?: 0
                }.first() as T
            }
            Long::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[longPreferencesKey(key)] ?: 0L
                }.first() as T
            }
            Double::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[doublePreferencesKey(key)] ?: 0.0
                }.first() as T
            }
            Float::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[floatPreferencesKey(key)] ?: 0f
                }.first() as T
            }
            Boolean::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[booleanPreferencesKey(key)] ?: false
                }.first() as T
            }
            String::class -> {
                BaseApp.mContext.dataStore.data.map { setting ->
                    setting[stringPreferencesKey(key)] ?: ""
                }.first() as T
            }
            else -> {
                throw IllegalArgumentException("This type can be get into DataStore")
            }
        }
    }
}