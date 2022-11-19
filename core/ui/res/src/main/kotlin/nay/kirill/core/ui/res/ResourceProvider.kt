package nay.kirill.core.ui.res

import android.content.Context

class ResourceProvider(
        private val context: Context
) {

    fun getString(id: Int) = context.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) = context.getString(id, *formatArgs)

}