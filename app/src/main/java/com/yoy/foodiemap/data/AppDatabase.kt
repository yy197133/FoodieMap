package com.yoy.foodiemap.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase


@Database(entities = [Place::class],version = 2,exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getPlaceDao(): PlaceDao


    companion object {

        @Volatile private var instance: AppDatabase? = null

        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE places ADD COLUMN pub_year INTEGER")
            }
        }

        fun getInstance(context: Context): AppDatabase{
            return instance ?: synchronized(this){
                instance ?: buildDatabase(context).also { instance = it }
            }
        }


        private fun buildDatabase(context: Context): AppDatabase{
            return Room.databaseBuilder(context.applicationContext,AppDatabase::class.java,"place_database").build()
        }
    }
}