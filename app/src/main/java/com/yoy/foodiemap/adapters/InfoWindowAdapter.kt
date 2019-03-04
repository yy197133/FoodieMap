package com.yoy.foodiemap.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.databinding.DataBindingUtil
import com.amap.api.maps.AMap
import com.amap.api.maps.model.Marker
import com.yoy.foodiemap.R
import com.yoy.foodiemap.data.Place
import com.yoy.foodiemap.databinding.LayoutInfoWindowBinding
import com.yoy.foodiemap.utils.toast

class InfoWindowAdapter(private val mContext: Context) : AMap.InfoWindowAdapter, View.OnClickListener {

    private var mBinding: LayoutInfoWindowBinding? = null

    var place: Place? = null

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.button) {
            mContext.toast("click")
        }
    }

    override fun getInfoWindow(marker: Marker): View? {
        return initView()
    }

    override fun getInfoContents(marker: Marker): View? {
        return null
    }

    private fun initView(): View {
        if (mBinding == null) {
            mBinding = DataBindingUtil.inflate(
                LayoutInflater.from(mContext),
                R.layout.layout_info_window,
                null,
                false
            )
            mBinding?.button?.setOnClickListener(this)
        }
        mBinding?.place = place

        return mBinding!!.root
    }




}
