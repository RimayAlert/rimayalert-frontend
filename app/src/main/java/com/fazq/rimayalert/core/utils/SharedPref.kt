package com.fazq.rimayalert.core.utils

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class SharedPref(private val appContext: Application) {

    private lateinit var mPreferences: SharedPreferences

    fun save(key: String, value: String) {
        mPreferences = appContext.getSharedPreferences(key, Context.MODE_PRIVATE)
        mPreferences.edit {
            putString(key, value)
        }
    }

    fun read(key: String): String {
        mPreferences = appContext.getSharedPreferences(key, Context.MODE_PRIVATE)
        return mPreferences.getString(key, "") ?: ""
    }


    fun contains(key: String): Boolean {
        mPreferences = appContext.getSharedPreferences(key, Context.MODE_PRIVATE)
        return mPreferences.contains(key)
    }

    fun remove(key: String?) {
        mPreferences = appContext.getSharedPreferences(key, Context.MODE_PRIVATE)
        mPreferences.edit {
            clear()
        }
    }

}

class SharedPrefContext {
    companion object {
        private lateinit var mPreferences: SharedPreferences

        fun save(context: Context, key: String, value: String) {
            mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            mPreferences.edit {
                putString(key, value)
            }
        }

        fun read(context: Context, key: String?): String {
            mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            return mPreferences.getString(key, "") ?: ""
        }


        fun contains(context: Context, key: String?): Boolean {
            mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            return mPreferences.contains(key)
        }

        fun remove(context: Context, key: String?) {
            mPreferences = context.getSharedPreferences(key, Context.MODE_PRIVATE)
            mPreferences.edit {
                clear()
            }
        }
    }
}