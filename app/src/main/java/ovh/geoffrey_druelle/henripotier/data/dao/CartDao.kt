package ovh.geoffrey_druelle.henripotier.data.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import io.reactivex.Single
import ovh.geoffrey_druelle.henripotier.base.DaoBase
import ovh.geoffrey_druelle.henripotier.data.model.Cart

@Dao
interface CartDao : DaoBase<Cart> {

    @Query("SELECT * FROM Cart")
    fun getAllBooksInCart(): Single<List<Cart>>

}