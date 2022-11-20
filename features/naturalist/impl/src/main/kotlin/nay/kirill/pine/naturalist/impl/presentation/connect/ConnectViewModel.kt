package nay.kirill.pine.naturalist.impl.presentation.connect

import android.content.Context
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import nay.kirill.bluetooth.scanner.api.BluetoothScanner
import nay.kirill.core.arch.BaseViewModel
import nay.kirill.pine.naturalist.impl.presentation.NaturalistNavigation
import nay.kirill.pine.naturalist.impl.presentation.entername.EnterNameArgs
import nay.kirill.pine.naturalist.impl.presentation.entername.UserDataStoreKey
import nay.kirill.pine.naturalist.impl.presentation.entername.dataStore

internal class ConnectViewModel(
        private val navigation: NaturalistNavigation,
        private val bluetoothScanner: BluetoothScanner,
        private val context: Context
) : BaseViewModel<ConnectState, ConnectState>(
        converter = { it },
        initialState = ConnectState.Content
) {

    private var scanningJob: Job? = null

    fun back() = navigation.back()

    fun startScanning() {
        scanningJob?.cancel()
        scanningJob = viewModelScope.launch {

            bluetoothScanner.getScannedDevicesFlow()
                    .onEach { result ->
                        result
                                .onSuccess { devices ->
                                    if (devices.isEmpty()) return@onSuccess

                                    context.dataStore.data
                                            .map { it[UserDataStoreKey.USER_NAME] }
                                            .onEach {
                                                if (it == null) {
                                                    navigation.replaceEnterName(
                                                            args = EnterNameArgs.NewName(
                                                                    deviceAddress = devices.first().bluetoothDevice.address
                                                            )
                                                    )
                                                } else {
                                                    navigation.openChat()
                                                }
                                            }
                                            .launchIn(viewModelScope)
                                }
                                .onFailure {
                                    state = ConnectState.Error
                                }
                    }
                    .launchIn(viewModelScope)

        }
    }

}
