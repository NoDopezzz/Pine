package nay.kirill.pine.naturalist.impl.presentation.chat

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ChatArgs(
        val device: BluetoothDevice
) : Parcelable