package com.yoy.foodiemap

import android.Manifest
import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.Projection
import com.amap.api.maps.model.*
import com.yoy.foodiemap.adapters.InfoWindowAdapter
import com.yoy.foodiemap.utils.Injector
import com.yoy.foodiemap.utils.toast
import com.yoy.foodiemap.viewmodels.PlacesViewModel
import kotlinx.android.synthetic.main.fragment_map.view.*
import permissions.dispatcher.NeedsPermission
import permissions.dispatcher.OnNeverAskAgain
import permissions.dispatcher.OnPermissionDenied
import permissions.dispatcher.RuntimePermissions

@RuntimePermissions
class MapFragment : Fragment(), AMapLocationListener {

    companion object {
        const val TAG = "MapFragment"
    }

    private var rootView: View? = null

    private lateinit var viewModel: PlacesViewModel

    private lateinit var mMapView: MapView
    private lateinit var mLocationBtn: ImageButton
    private lateinit var aMap: AMap
    private var mLocationClient: AMapLocationClient? = null
    private var useMoveToLocationWithMapMode = true

    private lateinit var currLatLng: LatLng
    //自定义定位小蓝点的Marker
//    private var locationMarker: Marker? = null
    //坐标和经纬度转换工具
    private var projection: Projection? = null

//    private val myCancelCallback = MyCancelCallback()

    private val mInfoWindowAdapter by lazy { InfoWindowAdapter(requireContext()) }

    //当前选中的已收藏的marker
    private var placeMarker: Marker? = null
    //点击地图poi，添加的marker
    private var poiMarker: Marker? = null

    private var isMarkerClicked = false



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Log.i("123","onCreateView:$activity")
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_map,container,false)
            mMapView = rootView!!.mapView
            mLocationBtn = rootView!!.map_view_location
            mMapView.onCreate(savedInstanceState)

            viewModel = ViewModelProviders
                .of(this, Injector.providePlaceViewModelFactory(requireContext()))
                .get(PlacesViewModel::class.java)

            init()
            initListeners()
        }


        return rootView
    }

    override fun onDestroyView() {
        super.onDestroyView()

    }

    private fun init() {
        if (!this::aMap.isInitialized) {
            aMap = mMapView.map
            locateWithPermissionCheck()
        }
        addMarkers()
    }

    private fun initListeners(){

        aMap.setOnMyLocationChangeListener {
            currLatLng = LatLng(it.latitude, it.longitude)
        }

        aMap.setOnMarkerClickListener{
            //如果点击的是本身定位marker
            if (it.position.latitude == aMap.myLocation.latitude
                && it.position.longitude == aMap.myLocation.longitude)
                return@setOnMarkerClickListener true


            if (poiMarker == it){
                val poi = poiMarker?.`object` as Poi
                val args = bundleOf(
                    AddPlaceFragment.NAME to poi.name,
                    AddPlaceFragment.ADDRESS to "",
                    AddPlaceFragment.LAT to poi.coordinate.latitude,
                    AddPlaceFragment.LNG to poi.coordinate.longitude)
                findNavController().navigate(R.id.action_fragment_map_to_addPlaceFragment,args)
                it.remove()
                return@setOnMarkerClickListener true
            }


            val places = viewModel.places.value

            places?.forEach { place ->
                if (it.position == place.getLatLng()){
//                    toast(place.name)
                    mInfoWindowAdapter.place = place
                    placeMarker = it
                    isMarkerClicked = true
                }
            }
            false
        }
        aMap.setOnMapClickListener {
            if (isMarkerClicked)
                isMarkerClicked = false
            else {
                placeMarker?.hideInfoWindow()
                placeMarker = null
            }
            poiMarker?.remove()
        }

        aMap.setOnPOIClickListener {
            placeMarker?.hideInfoWindow()
            placeMarker = null
            poiMarker?.remove()
            poiMarker = null

            val markerOptions = MarkerOptions()
            markerOptions.position(it.coordinate)
            val view = layoutInflater.inflate(R.layout.marker_add_place,null)
            markerOptions.icon(BitmapDescriptorFactory.fromView(view))
            poiMarker = aMap.addMarker(markerOptions)
            poiMarker?.`object` = it
        }

        aMap.setOnMapLongClickListener {

        }

        mLocationBtn.setOnClickListener {
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(currLatLng))
        }
    }

    private fun setUpMap() {
        val myLocationStyle = MyLocationStyle()
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
        myLocationStyle.interval(2000)
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation))
        myLocationStyle.radiusFillColor(resources.getColor(R.color.location_circle))
        myLocationStyle.strokeColor(resources.getColor(R.color.location_circle))
        aMap.myLocationStyle = myLocationStyle
//        aMap.setLocationSource(this)
//        aMap.uiSettings.isMyLocationButtonEnabled = true
        aMap.uiSettings.isZoomControlsEnabled = false
        aMap.uiSettings.isRotateGesturesEnabled = false
        aMap.isMyLocationEnabled = true
        aMap.setInfoWindowAdapter(mInfoWindowAdapter)



    }


    @NeedsPermission(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun locate() {
        Log.i(TAG, "loacte")
        if (mLocationClient == null) {
            val locationClientOption = AMapLocationClientOption()
            mLocationClient = AMapLocationClient(context)
            //设置定位监听
            mLocationClient?.setLocationListener(this)
            locationClientOption.isOnceLocation = true
            locationClientOption.isLocationCacheEnable = false
            //设置为高精度定位模式
            locationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
            //是指定位间隔
//            locationClientOption.interval = 2000
            //设置定位参数
            mLocationClient?.setLocationOption(locationClientOption)
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient?.startLocation()
        }
    }

    @OnPermissionDenied(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationDenied(){
        AlertDialog.Builder(requireContext())
            .setTitle("")
            .setMessage("定位权限被禁止，将无法提供位置信息...")
            .setPositiveButton("确定"){dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    @OnNeverAskAgain(Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION)
    fun onLocationNeverAskAgain(){
        toast("请到设置中开启定位权限~")
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        onRequestPermissionsResult(requestCode, grantResults)
    }

    private fun addMarkers() {
        viewModel.places.observe(this, Observer {
            it?.forEach { place ->
                aMap.addMarker(
                    MarkerOptions()
                        .position(LatLng(place.lat, place.lng))
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker))
                        .snippet(place.name)
                        .anchor(0.5f, 0.5f)
                )
            }
        })
    }

    private fun deactivate() {
        mLocationClient?.let {
            it.stopLocation()
            it.onDestroy()
        }
        mLocationClient = null
    }

    /**
     * 定位成功后回调函数
     */
    override fun onLocationChanged(amapLocation: AMapLocation?) {
        if (amapLocation != null && amapLocation.errorCode == 0) {
            currLatLng = LatLng(amapLocation.latitude, amapLocation.longitude)
            //展示自定义定位小蓝点
//                if (locationMarker == null){
            //首次定位
//                    locationMarker = aMap.addMarker(
//                        MarkerOptions()
//                            .position(latLng)
//                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker))
//                            .anchor(0.5f,0.5f))
            //首次定位,选择移动到地图中心点并修改级别到15级
            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLatLng, 16f))

            if (useMoveToLocationWithMapMode) {
                useMoveToLocationWithMapMode = false
                setUpMap()
            }
//                }
//                else {
//                    if (useMoveToLocationWithMapMode)
//                        moveLocationAndMap(latLng)
//                    else
//                        changeLocation(latLng)
//                }
        }
    }


    override fun onResume() {
        super.onResume()
        mMapView.onResume()
        useMoveToLocationWithMapMode = true
    }

    override fun onPause() {
        super.onPause()
        mMapView.onPause()
        deactivate()

        useMoveToLocationWithMapMode = false
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView.onDestroy()
        mLocationClient?.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView.onSaveInstanceState(outState)
    }
}