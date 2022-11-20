package nay.kirill.bluetooth.utils

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "config")

object DataStoreKey {

    val IS_SERVER_RUNNING = booleanPreferencesKey("IS_SERVER_RUNNING")

}