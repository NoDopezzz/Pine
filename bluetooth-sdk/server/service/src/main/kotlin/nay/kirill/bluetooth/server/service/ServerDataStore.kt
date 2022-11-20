package nay.kirill.bluetooth.server.service

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.serverDataStore: DataStore<Preferences> by preferencesDataStore(name = "server")

object ServerDataStoreKey {

    val CHAT_MESSAGES = stringPreferencesKey("CHAT_MESSAGES")

    val IS_SERVER_RUNNING = booleanPreferencesKey("IS_SERVER_RUNNING")

}