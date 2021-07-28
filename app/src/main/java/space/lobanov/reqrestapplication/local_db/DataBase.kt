package space.lobanov.reqrestapplication.local_db

import androidx.room.Database
import androidx.room.RoomDatabase
import space.lobanov.reqrestapplication.model.Images
@Database(
    entities = [Images::class],
    version = 1, exportSchema = false
)
abstract  class DataBase: RoomDatabase() {
        abstract val imageDao: ImageDao


    }
