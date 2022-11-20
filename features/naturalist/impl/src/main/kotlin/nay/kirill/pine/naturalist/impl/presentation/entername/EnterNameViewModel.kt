package nay.kirill.pine.naturalist.impl.presentation.entername

import android.annotation.SuppressLint
import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.pine.naturalist.impl.presentation.NaturalistNavigation
import nay.kirill.pine.naturalist.impl.presentation.chat.ChatArgs
import java.util.UUID

@SuppressLint("StaticFieldLeak") // Provide Application context to EnterNameViewModel
internal class EnterNameViewModel(
        private val args: EnterNameArgs,
        private val context: Context,
        private val navigation: NaturalistNavigation
) : BaseViewModel<EnterNameState, EnterNameState>(
        initialState = EnterNameState(""),
        converter = { it }
) {

    fun enterName(name: String) {
        state = EnterNameState(name)
    }

    fun accept() {
        viewModelScope.launch {
            context.dataStore.edit { pref ->
                pref[UserDataStoreKey.USER_NAME] = state.name
            }

            if (args is EnterNameArgs.NewName) {
                context.dataStore.edit { pref ->
                    pref[UserDataStoreKey.USER_ID] = UUID.randomUUID().toString()
                }
            }

            withContext(Dispatchers.Main) {
                when (args) {
                    is EnterNameArgs.NewName -> navigation.openChat(args = ChatArgs(args.device))
                    is EnterNameArgs.Edit -> navigation.back()
                }
            }
        }
    }

    fun back() = navigation.back()

}
