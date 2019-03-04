package com.yoy.foodiemap.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yoy.foodiemap.data.PlaceRepository

class PlacesViewModelFactory(private val repository: PlaceRepository) : ViewModelProvider.NewInstanceFactory(){


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T = PlacesViewModel(repository) as T
}