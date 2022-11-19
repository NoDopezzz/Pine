package nay.kirill.core.arch

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import kotlin.properties.ReadWriteProperty

inline fun <reified TArgs : Parcelable, reified TFragment : Fragment> TFragment.args(): ReadWriteProperty<TFragment, TArgs> {
    return InitializerDelegate { thisRef, _ ->
        thisRef.arguments?.getParcelable(TFragment::class.java.name) ?: error("No args provided")
    }
}

inline fun <reified T : Fragment> T.withArgs(args: Parcelable): T {
    arguments = Bundle().apply {
        putParcelable(T::class.java.name, args)
    }
    return this
}
