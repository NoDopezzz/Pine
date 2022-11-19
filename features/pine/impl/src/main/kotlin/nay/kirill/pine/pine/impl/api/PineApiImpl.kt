package nay.kirill.pine.pine.impl.api

import androidx.fragment.app.Fragment
import nay.kirill.pine.pine.api.PineApi
import nay.kirill.pine.pine.impl.presentation.PineFragment

internal class PineApiImpl : PineApi {

    override fun getPineFragment(): Fragment = PineFragment.newInstance()

}