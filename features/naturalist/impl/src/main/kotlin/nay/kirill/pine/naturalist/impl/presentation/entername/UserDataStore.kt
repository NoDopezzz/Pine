package nay.kirill.pine.naturalist.impl.presentation.entername

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user")

object UserDataStoreKey {

    val USER_NAME = stringPreferencesKey("USER_NAME")

}