package com.yoy.foodiemap.data

import com.yoy.foodiemap.utils.runOnIoThread

class PlaceRepository private constructor(private val placeDao: PlaceDao){

    fun getPlaces() = placeDao.getPlaces()


    fun addPlace(place: Place){

        runOnIoThread { placeDao.addPlace(place) }

    }


    companion object {
        @Volatile private var instance: PlaceRepository? = null

        fun getInstance(placeDao: PlaceDao): PlaceRepository{
            return instance ?: synchronized(this){
                instance ?: PlaceRepository(placeDao).also { instance = it }
            }
        }
    }


}