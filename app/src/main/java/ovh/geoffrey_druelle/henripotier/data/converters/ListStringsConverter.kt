package ovh.geoffrey_druelle.henripotier.data.converters

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ListStringsConverter {
    @TypeConverter
    fun toListFromString(string: String): List<String> {
        val list = object : TypeToken<List<String>>(){}.type
        return Gson().fromJson(string, list)
    }

    @TypeConverter
    fun toStringFromList(stringsList: List<String>): String {
        val gson = Gson()
        return gson.toJson(stringsList)
    }
}
