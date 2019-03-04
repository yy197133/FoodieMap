package com.yoy.foodiemap.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.yoy.foodiemap.data.Place
import com.yoy.foodiemap.data.PlaceRepository

class PlacesViewModel(private val placeRepository: PlaceRepository) : ViewModel() {

    val places: LiveData<List<Place>> = placeRepository.getPlaces()


    fun addPlace(place: Place) = placeRepository.addPlace(place)






}