package nay.kirill.pine.naturalist.impl.api

import androidx.fragment.app.Fragment
import nay.kirill.pine.naturalist.api.NaturalistApi
import nay.kirill.pine.naturalist.impl.presentation.connect.ConnectFragment

internal class NaturalistApiImpl : NaturalistApi {

    override fun getNaturalistFragment(): Fragment = ConnectFragment.newInstance()

}
