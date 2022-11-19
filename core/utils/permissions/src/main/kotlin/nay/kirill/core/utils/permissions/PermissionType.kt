package nay.kirill.core.utils.permissions

import androidx.annotation.StringRes

enum class PermissionType(
        @StringRes val titleId: Int,
        @StringRes val subtitleId: Int
) {

    LOCATION(R.string.dialog_permission_location_title, R.string.dialog_permission_location_subtitle),

    BLUETOOTH(R.string.dialog_permission_bluetooth_title, R.string.dialog_permission_bluetooth_subtitle),

    CAMERA(R.string.dialog_permission_camera_title, R.string.dialog_permission_camera_subtitle)

}