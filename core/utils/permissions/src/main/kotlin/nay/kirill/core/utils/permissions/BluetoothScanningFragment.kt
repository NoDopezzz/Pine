package nay.kirill.core.utils.permissions

import android.Manifest
import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.RequiresApi
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.OnShowRationale
import permissions.dispatcher.PermissionRequest
import androidx.fragment.app.Fragment
import permissions.dispatcher.RuntimePermissions
import kotlin.jvm.functions.Function0

/**
 * Abstract Fragment for requesting permission for scanning bluetooth devices. In case your functionality requires
 * Bluetooth scanning be sure to inherit [BluetoothScanningFragment]
 *
 * Make sure to use only [launchWithScanningPermissionCheck], [launchWithAdvertisePermissionCheck],
 * [launchWithFineLocationPermissionCheck] functions.
 * Other functions are declared as public for annotation processor purposes.
 */

@RuntimePermissions
abstract class BluetoothScanningFragment : Fragment() {

    /**
     * If your functionality requires [Manifest.permission.BLUETOOTH_SCAN] you should
     * call [launchWithScanningPermissionCheck] with passing lambda is [block] param
     */
    protected fun launchWithScanningPermissionCheck(block: () -> Unit) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> onCheckBluetoothScanWithPermissionCheck(object : Function0<Unit> {
                override fun invoke() = block()
            })
            else -> block()
        }
    }

    /**
     * If your functionality requires [Manifest.permission.BLUETOOTH_ADVERTISE] you should
     * call [launchWithAdvertisePermissionCheck] with passing lambda is [block] param
     */
    protected fun launchWithAdvertisePermissionCheck(block: () -> Unit) {
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> onCheckBluetoothAdvertiseWithPermissionCheck(object : Function0<Unit> {
                override fun invoke() = block()
            })
            else -> block()
        }
    }

    /**
     * If your functionality requires [Manifest.permission.ACCESS_FINE_LOCATION] you should
     * call [launchWithFineLocationPermissionCheck] with passing lambda is [block] param
     */
    protected fun launchWithFineLocationPermissionCheck(block: () -> Unit) {
        onCheckFineLocationWithPermissionCheck(object : Function0<Unit> {
            override fun invoke() = block()
        })
    }

    /**
     * If your functionality requires [Manifest.permission.CAMERA] you should
     * call [launchWithCameraPermissionCheck] with passing lambda is [block] param
     */
    protected fun launchWithCameraPermissionCheck(block: () -> Unit) {
        onCheckCameraWithPermissionCheck(object : Function0<Unit> {
            override fun invoke() = block()
        })
    }

    @NeedsPermission(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onCheckFineLocation(block: Function0<Unit>) {
        block.invoke()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NeedsPermission(Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT)
    fun onCheckBluetoothAdvertise(block: Function0<Unit>) {
        block.invoke()
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @NeedsPermission(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun onCheckBluetoothScan(block: Function0<Unit>) {
        block.invoke()
    }

    @NeedsPermission(Manifest.permission.CAMERA)
    fun onCheckCamera(block: Function0<Unit>) {
        block.invoke()
    }

    @OnShowRationale(Manifest.permission.ACCESS_FINE_LOCATION)
    fun showRationaleForFineLocation(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.LOCATION, request = request)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnShowRationale(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun showRationaleForBluetooth(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.BLUETOOTH, request = request)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnShowRationale(Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT)
    fun showRationaleForAdvertisingBluetooth(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.BLUETOOTH, request = request)
    }

    @OnShowRationale(Manifest.permission.CAMERA)
    fun showRationaleForCamera(request: PermissionRequest) {
        showPermissionAlert(type = PermissionType.CAMERA, request = request)
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_FINE_LOCATION)
    @OnNeverAskAgain(Manifest.permission.ACCESS_FINE_LOCATION)
    fun onFineLocationPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.LOCATION,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnPermissionDenied(Manifest.permission.BLUETOOTH_SCAN, Manifest.permission.BLUETOOTH_CONNECT)
    fun onBluetoothPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.BLUETOOTH,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    @RequiresApi(Build.VERSION_CODES.S)
    @OnPermissionDenied(Manifest.permission.BLUETOOTH_ADVERTISE, Manifest.permission.BLUETOOTH_CONNECT)
    fun onBluetoothAdvertisingPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.BLUETOOTH,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    @OnNeverAskAgain(Manifest.permission.CAMERA)
    fun onCameraPermissionNeverAskAgain() {
        showPermissionAlert(
                type = PermissionType.CAMERA,
                okAction = {
                    openPermissionSettings()
                },
                cancelAction = { /* Do nothing */ }
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun showPermissionAlert(type: PermissionType, request: PermissionRequest) {
        showPermissionAlert(type, okAction = request::proceed, cancelAction = request::cancel)
    }

    private fun showPermissionAlert(
            type: PermissionType,
            okAction: () -> Unit,
            cancelAction: () -> Unit
    ) {
        AlertDialog.Builder(requireContext())
                .setTitle(type.titleId)
                .setMessage(type.subtitleId)
                .setPositiveButton(R.string.positive_button_text) { _, _ -> okAction() }
                .setNegativeButton(R.string.negative_button_text) { _, _ -> cancelAction() }
                .show()
    }

    private fun openPermissionSettings() {
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = Uri.fromParts("package", activity?.packageName, null)
        }
        requireContext().startActivity(intent)
    }

}