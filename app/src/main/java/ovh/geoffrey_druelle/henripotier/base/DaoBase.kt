package ovh.geoffrey_druelle.henripotier.base

import androidx.room.*

@Dao
interface DaoBase<T> {
    @Delete
    fun delete(tObject: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(tObject: T): Long

    @Update
    fun update(tObject: T)
}