package space.lobanov.reqrestapplication.local_db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import space.lobanov.reqrestapplication.model.Images

@Dao
interface ImageDao {
    @Query("SELECT * FROM image_items")
    fun getAll(): List<Images>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(image: List<Images>)


}