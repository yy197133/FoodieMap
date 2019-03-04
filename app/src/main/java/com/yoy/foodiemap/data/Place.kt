package com.yoy.foodiemap.data

import androidx.room.Entity
import com.amap.api.maps.model.LatLng


@Entity(tableName = "places",primaryKeys = ["lat", "lng"])
data class Place(
    var name: String,
    var type: String,
    var specialty: String,
    var hasBeenThere: Boolean,
    var evaluation: String,
    var address: String,
    var lat: Double,
    var lng: Double
) {

    fun getLatLng() = LatLng(lat,lng)

}

enum class FoodType(var type: String) {
    COFFEE("咖啡"),
    FAST_FOOD("快餐"),
    BUFFET("自助餐");

}