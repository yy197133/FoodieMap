package com.yoy.foodiemap

import android.os.Bundle
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation

class MainActivity : AppCompatActivity() {




//    private lateinit var aMap: AMap
//    private var mLocationClient: AMapLocationClient? = null
//    private var useMoveToLocationWithMapMode = true
//
//    private lateinit var currLatLng: LatLng
//    //自定义定位小蓝点的Marker
////    private var locationMarker: Marker? = null
//    //坐标和经纬度转换工具
//    private var projection: Projection? = null
//
////    private val myCancelCallback = MyCancelCallback()
//
//    private val mInfoWindowAdapter by lazy { InfoWindowAdapter(this) }
//
//    //当前选中的已收藏的marker
//    private var placeMarker: Marker? = null
//    //点击地图poi，添加的marker
//    private var poiMarker: Marker? = null
//
//    private var isMarkerClicked = false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
//        DataBindingUtil.setContentView<ActivityMainBinding>(this,R.layout.activity_main)
        setContentView(R.layout.activity_main)

        val navController = Navigation.findNavController(this,R.id.nav_fragment)



    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 0)
            super.onBackPressed()
        else
            supportFragmentManager.popBackStack()
    }


//    private fun init() {
//        if (!this::aMap.isInitialized) {
//            aMap = mapView.map
//            locate()
//        }
//        addMarkers()
//    }
//
//    private fun initListeners(){
//        aMap.setOnMyLocationChangeListener {
//            currLatLng = LatLng(it.latitude, it.longitude)
//        }
//
//        aMap.setOnMarkerClickListener{
//            if (it.position.latitude == aMap.myLocation.latitude
//                && it.position.longitude == aMap.myLocation.longitude)
//                return@setOnMarkerClickListener true
//
//            if (poiMarker == it){
//                startActivity(Intent(this,AddPlaceActivity::class.java))
//                return@setOnMarkerClickListener true
//            }
//
//            val places = viewModel.places.value
//
//            places?.forEach { place ->
//                if (it.position == place.getLatLng()){
////                    toast(place.name)
//                    mInfoWindowAdapter.place = place
//                    placeMarker = it
//                    isMarkerClicked = true
//                }
//            }
//            false
//        }
//        aMap.setOnMapClickListener {
//            if (isMarkerClicked)
//                isMarkerClicked = false
//            else {
//                placeMarker?.hideInfoWindow()
//                placeMarker = null
//            }
//            poiMarker?.remove()
//        }
//
//        aMap.setOnPOIClickListener {
//            placeMarker?.hideInfoWindow()
//            placeMarker = null
//            poiMarker?.remove()
//            poiMarker = null
//
//            val markerOptions = MarkerOptions()
//            markerOptions.position(it.coordinate)
//            val view = layoutInflater.inflate(R.layout.marker_add_place,null)
//            markerOptions.icon(BitmapDescriptorFactory.fromView(view))
//            poiMarker = aMap.addMarker(markerOptions)
//            poiMarker?.`object` = it
//        }
//
//        aMap.setOnMapLongClickListener {
//
//        }
//    }
//
//    private fun setUpMap() {
//        val myLocationStyle = MyLocationStyle()
//        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE_NO_CENTER)
//        myLocationStyle.interval(2000)
//        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.ic_navigation))
//        myLocationStyle.radiusFillColor(resources.getColor(R.color.location_circle))
//        myLocationStyle.strokeColor(resources.getColor(R.color.location_circle))
//        aMap.myLocationStyle = myLocationStyle
////        aMap.setLocationSource(this)
////        aMap.uiSettings.isMyLocationButtonEnabled = true
//        aMap.uiSettings.isZoomControlsEnabled = false
//        aMap.uiSettings.isRotateGesturesEnabled = false
//        aMap.isMyLocationEnabled = true
//        aMap.setInfoWindowAdapter(mInfoWindowAdapter)
//
//
//
//    }
//
//
//    private fun locate() {
//        if (mLocationClient == null) {
//            val locationClientOption = AMapLocationClientOption()
//            mLocationClient = AMapLocationClient(this@MainActivity)
//            //设置定位监听
//            mLocationClient?.setLocationListener(this@MainActivity)
//            locationClientOption.isOnceLocation = true
//            locationClientOption.isLocationCacheEnable = false
//            //设置为高精度定位模式
//            locationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//            //是指定位间隔
////            locationClientOption.interval = 2000
//            //设置定位参数
//            mLocationClient?.setLocationOption(locationClientOption)
//            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//            // 在定位结束后，在合适的生命周期调用onDestroy()方法
//            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//            mLocationClient?.startLocation()
//        }
//    }
//
////    private fun getPlaces(){
////        val place1 = Place("象屎咖啡", FoodType.COFFEE.ordinal, arrayOf("拿铁", "摩卡"), true, "", 31.184317, 121.306253)
////        val place2 = Place("太平洋咖啡", FoodType.COFFEE.ordinal, arrayOf("拿铁", "摩卡"), false, "", 31.188081, 121.302187)
////        val place3 = Place("帕斯库奇咖啡", FoodType.COFFEE.ordinal, arrayOf("拿铁", "摩卡"), true, "", 31.192058, 121.315157)
////        val place4 = Place("图尚咖啡", FoodType.COFFEE.ordinal, arrayOf("拿铁", "摩卡"), false, "", 31.193104, 121.313338)
////        val place5 = Place("肯德基", FoodType.FAST_FOOD.ordinal, arrayOf("拿铁", "摩卡"), true, "", 31.190428, 121.301558)
////        val place6 = Place("自助", FoodType.BUFFET.ordinal, arrayOf("拿铁", "摩卡"), false, "", 31.176786, 121.29396)
////        places.add(place1)
////        places.add(place2)
////        places.add(place3)
////        places.add(place4)
////        places.add(place5)
////        places.add(place6)
////    }
//
//
///*
//    fun activate(listener: LocationSource.OnLocationChangedListener?) {
//        if (mLocationClient == null) {
//            val locationClientOption = AMapLocationClientOption()
//            mLocationClient = AMapLocationClient(this@MainActivity)
//            //设置定位监听
//            mLocationClient?.setLocationListener(this@MainActivity)
//            locationClientOption.isOnceLocation = true
//            locationClientOption.isLocationCacheEnable = false
//            //设置为高精度定位模式
//            locationClientOption.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
//            //是指定位间隔
////            locationClientOption.interval = 2000
//            //设置定位参数
//            mLocationClient?.setLocationOption(locationClientOption)
//            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
//            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
//            // 在定位结束后，在合适的生命周期调用onDestroy()方法
//            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
//            mLocationClient?.startLocation()
//        }
//    }
//    */
//
//    private fun deactivate() {
//        mLocationClient?.let {
//            it.stopLocation()
//            it.onDestroy()
//        }
//        mLocationClient = null
//    }
//
//    /**
//     * 定位成功后回调函数
//     */
//    override fun onLocationChanged(amapLocation: AMapLocation?) {
//        if (amapLocation != null && amapLocation.errorCode == 0) {
//            currLatLng = LatLng(amapLocation.latitude, amapLocation.longitude)
//            //展示自定义定位小蓝点
////                if (locationMarker == null){
//            //首次定位
////                    locationMarker = aMap.addMarker(
////                        MarkerOptions()
////                            .position(latLng)
////                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location_marker))
////                            .anchor(0.5f,0.5f))
//            //首次定位,选择移动到地图中心点并修改级别到15级
//            aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currLatLng, 16f))
//
//            if (useMoveToLocationWithMapMode) {
//                useMoveToLocationWithMapMode = false
//                setUpMap()
//            }
////                }
////                else {
////                    if (useMoveToLocationWithMapMode)
////                        moveLocationAndMap(latLng)
////                    else
////                        changeLocation(latLng)
////                }
//        }
//    }
//
//    /*
//    private fun moveLocationAndMap(latLng: LatLng) {
//        if (projection == null)
//            projection = aMap.projection
//
//        if (locationMarker != null && projection != null) {
//            val markerPosition = locationMarker?.position
//            val screenPosition = aMap.projection.toScreenLocation(markerPosition)
//            locationMarker?.setPositionByPixels(screenPosition.x, screenPosition.y)
//        }
//
//        myCancelCallback.targetLatLng = latLng
//
//        aMap.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback)
//
//    }
//
//    private fun changeLocation(latLng: LatLng) {
//        if (locationMarker != null) {
//            val currLatLng = locationMarker?.position
//            if (currLatLng == null || currLatLng != latLng)
//                locationMarker?.position = latLng
//        }
//    }
//
//    inner class MyCancelCallback : AMap.CancelableCallback {
//
//        var targetLatLng: LatLng? = null
//        override fun onFinish() {
//            if (locationMarker != null && targetLatLng != null)
//                locationMarker?.position = targetLatLng
//        }
//
//        override fun onCancel() {
//            if (locationMarker != null && targetLatLng != null)
//                locationMarker?.position = targetLatLng
//        }
//
//    }
//
//    */
//
//    fun onLocateBtnClick(view: View) {
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(currLatLng))
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mapView.onResume()
//        useMoveToLocationWithMapMode = true
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mapView.onPause()
//        deactivate()
//
//        useMoveToLocationWithMapMode = false
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//        mapView.onDestroy()
//        mLocationClient?.onDestroy()
//    }
//
//    override fun onSaveInstanceState(outState: Bundle) {
//        super.onSaveInstanceState(outState)
//        mapView.onSaveInstanceState(outState)
//    }
}


