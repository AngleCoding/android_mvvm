package com.github.yuang.kt.android_mvvm.utils

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.github.yuang.kt.android_mvvm.BaseApp
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking


object DataStoreHelper {
    private val BaseApp.dataStore: DataStore<Preferences>
            by preferencesDataStore(name = BaseApp.mContext.packageName)

    private val ds = BaseApp.instance.dataStore


    //存储数据
    fun <T> putData(key: String, value: T) {
        runBlocking {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Long -> putLong(key, value)
                is Float -> putFloat(key, value)
                is Double -> putDouble(key, value)
                is Boolean -> putBoolean(key, value)
                else -> throw Exception("DataStore写入时输入了未知的数据类型")
            }


        }
    }


    //读取数据
    fun <T> getData(key: String, default: T): T {
        val resdata = when (default) {
            is String -> getString(key, default)
            is Int -> getInt(key, default)
            is Long -> getLong(key, default)
            is Float -> getFloat(key, default)
            is Double -> getDouble(key, default)
            is Boolean -> getBoolean(key, default)
            else -> throw Exception("DataStore读取时输入了未知的数据类型")
        }
        return resdata as T
    }


    //region 写入DataStore数据
    //写入String类型数据
    private suspend fun putString(key: String, value: String) = ds.edit {
        it[stringPreferencesKey(key)] = value
    }


    //写入Int类型数据
    private suspend fun putInt(key: String, value: Int) = ds.edit {
        it[intPreferencesKey(key)] = value
    }


    //写入Long类型数据
    private suspend fun putLong(key: String, value: Long) = ds.edit {
        it[longPreferencesKey(key)] = value
    }


    //写入Float类型数据
    private suspend fun putFloat(key: String, value: Float) = ds.edit {
        it[floatPreferencesKey(key)] = value
    }


    //写入Double类型数据
    private suspend fun putDouble(key: String, value: Double) = ds.edit {
        it[doublePreferencesKey(key)] = value
    }


    //写入Boolean类型数据
    private suspend fun putBoolean(key: String, value: Boolean) = ds.edit {
        it[booleanPreferencesKey(key)] = value
    }
    //endregion


    //region 读取DataStore数据
    //读取String类型数据
    private fun getString(key: String, default: String = ""): String =
        runBlocking {
            return@runBlocking ds.data.map {
                it[stringPreferencesKey(key)] ?: default
            }.first()
        }


    //读取Int类型数据
    private fun getInt(key: String, default: Int = -1): Int =
        runBlocking {
            return@runBlocking ds.data.map {
                it[intPreferencesKey(key)] ?: default
            }.first()
        }


    //读取Long类型数据
    private fun getLong(key: String, default: Long = -1): Long =
        runBlocking {
            return@runBlocking ds.data.map {
                it[longPreferencesKey(key)] ?: default
            }.first()
        }


    //读取Float类型数据
    private fun getFloat(key: String, default: Float = 0.0f): Float =
        runBlocking {
            return@runBlocking ds.data.map {
                it[floatPreferencesKey(key)] ?: default
            }.first()
        }


    //读取Double类型数据
    private fun getDouble(key: String, default: Double = 0.00): Double =
        runBlocking {
            return@runBlocking ds.data.map {
                it[doublePreferencesKey(key)] ?: default
            }.first()
        }


    //读取Boolean类型数据
    private fun getBoolean(key: String, default: Boolean = false): Boolean =
        runBlocking {
            return@runBlocking ds.data.map {
                it[booleanPreferencesKey(key)] ?: default
            }.first()
        }

}