package ovh.geoffrey_druelle.henripotier.data.database

import android.content.Context
import androidx.databinding.adapters.Converters
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ovh.geoffrey_druelle.henripotier.data.converters.ListStringsConverter
import ovh.geoffrey_druelle.henripotier.data.dao.CartDao
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.data.model.Cart

@Database(
    entities = [
        Cart::class,
        Book::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(ListStringsConverter::class)
abstract class HPDatabase: RoomDatabase(){
    abstract fun cartDao(): CartDao

    companion object{
        @Volatile
        private var INSTANCE: HPDatabase? = null
        private const val DB_NAME = "HenriPotier_Database"

        fun getInstance(context: Context): HPDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance =
                        Room.databaseBuilder(
                            context.applicationContext,
                            HPDatabase::class.java,
                            DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
