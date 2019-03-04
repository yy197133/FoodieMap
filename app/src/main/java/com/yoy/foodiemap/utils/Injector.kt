package com.yoy.foodiemap.utils

import android.content.Context
import com.yoy.foodiemap.data.AppDatabase
import com.yoy.foodiemap.data.PlaceRepository
import com.yoy.foodiemap.viewmodels.PlacesViewModelFactory

object Injector {

    private fun getPlaceRepository(context: Context): PlaceRepository{
        return PlaceRepository.getInstance(AppDatabase.getInstance(context).getPlaceDao())
    }


    fun providePlaceViewModelFactory(context: Context): PlacesViewModelFactory{
        val repository = getPlaceRepository(context)
        return PlacesViewModelFactory(repository)
    }
}