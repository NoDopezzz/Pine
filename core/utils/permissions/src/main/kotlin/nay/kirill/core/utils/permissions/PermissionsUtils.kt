package nay.kirill.core.utils.permissions

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat

object PermissionsUtils {

    fun checkBluetoothAdvertisePermission(context: Context): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_ADVERTISE) == PackageManager.PERMISSION_GRANTED

    fun checkBluetoothConnectPermission(context: Context): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.S
            || ActivityCompat.checkSelfPermission(context, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED

    fun checkScanningPermissions(context: Context): Boolean = bluetoothScanningPermissions.all { permission ->
        ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkAdvertisingPermissions(context: Context): Boolean = bluetoothAdvertisingPermissions.all { permission ->
        ActivityCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
    }

    fun checkFineLocation(context: Context): Boolean =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    val bluetoothScanningPermissions: List<String>
        get() {
            val permissions = mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            return permissions
        }

    val bluetoothAdvertisingPermissions: List<String>
        get() {
            val permissions = mutableListOf(Manifest.permission.ACCESS_COARSE_LOCATION)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION)
            }
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.S) {
                permissions.add(Manifest.permission.BLUETOOTH_ADVERTISE)
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
            }

            return permissions
        }

}
