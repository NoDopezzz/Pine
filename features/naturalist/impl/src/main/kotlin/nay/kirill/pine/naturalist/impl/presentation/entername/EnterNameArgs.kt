package nay.kirill.pine.naturalist.impl.presentation.entername

import android.bluetooth.BluetoothDevice
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
sealed interface EnterNameArgs : Parcelable {

    @Parcelize
    object Edit : EnterNameArgs

    @Parcelize
    data class NewName(val device: BluetoothDevice) : EnterNameArgs

}