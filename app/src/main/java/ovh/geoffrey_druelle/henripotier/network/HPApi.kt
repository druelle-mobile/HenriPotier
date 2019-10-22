package ovh.geoffrey_druelle.henripotier.network

import io.reactivex.Single
import ovh.geoffrey_druelle.henripotier.data.model.Book
import ovh.geoffrey_druelle.henripotier.data.model.Offers
import retrofit2.http.GET
import retrofit2.http.Path

interface HPApi {
    @GET("books")
    fun getBooks(): Single<List<Book>>

    @GET("books/{isbn}/commercialOffers")
    fun getOffers(@Path("isbn")isbn: String): Single<Offers>
}