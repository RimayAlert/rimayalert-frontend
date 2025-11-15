package com.fazq.rimayalert.core.preferences

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.fazq.rimayalert.features.auth.domain.model.UserModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_session")

class UserPreferencesManager(private val context: Context) {

    companion object {
        private val USER_ID = intPreferencesKey("user_id")
        private val USERNAME = stringPreferencesKey("username")
        private val EMAIL = stringPreferencesKey("email")
        private val PHONE = stringPreferencesKey("phone")
        private val DNI = stringPreferencesKey("dni")
        private val FIRST_NAME = stringPreferencesKey("first_name")
        private val LAST_NAME = stringPreferencesKey("last_name")
        private val FULL_NAME = stringPreferencesKey("full_name")
        private val ALIAS_NAME = stringPreferencesKey("alias_name")
        private val IS_ACTIVE = booleanPreferencesKey("is_active")
        private val HAS_COMMUNITY =  booleanPreferencesKey("has_community")
    }

    suspend fun saveUser(user: UserModel) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID] = user.id
            prefs[USERNAME] = user.username ?: ""
            prefs[EMAIL] = user.email
            prefs[PHONE] = user.phone
            prefs[DNI] = user.dni ?: ""
            prefs[FIRST_NAME] = user.firstName ?: ""
            prefs[LAST_NAME] = user.lastName ?: ""
            prefs[FULL_NAME] = user.fullName ?: ""
            prefs[ALIAS_NAME] = user.aliasName ?: ""
            prefs[IS_ACTIVE] = user.active
        }
    }

    val user: Flow<UserModel?> = context.dataStore.data.map { prefs ->
        val userId = prefs[USER_ID]
        if (userId != null && userId > 0) {
            UserModel(
                id = userId,
                username = prefs[USERNAME],
                email = prefs[EMAIL] ?: "",
                phone = prefs[PHONE] ?: "",
                dni = prefs[DNI],
                firstName = prefs[FIRST_NAME],
                lastName = prefs[LAST_NAME],
                fullName = prefs[FULL_NAME],
                aliasName = prefs[ALIAS_NAME],
                token = null,
                active = prefs[IS_ACTIVE] ?: false
            )
        } else null
    }

    suspend fun saveHasCommunity(hasCommunity: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[HAS_COMMUNITY] = hasCommunity
        }
    }

    val hasCommunity: Flow<Boolean> = context.dataStore.data.map { prefs ->
        prefs[HAS_COMMUNITY] ?: false
    }

    val isLoggedIn: Flow<Boolean> = context.dataStore.data.map { prefs ->
        val userId = prefs[USER_ID] ?: 0
        userId > 0
    }

    suspend fun clearUser() {
        context.dataStore.edit { it.clear() }
    }
}