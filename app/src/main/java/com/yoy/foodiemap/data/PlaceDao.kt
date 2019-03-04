package com.yoy.foodiemap.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface PlaceDao {


    @Query("SELECT * FROM places")
    fun getPlaces(): LiveData<List<Place>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addPlace(place: Place)
}