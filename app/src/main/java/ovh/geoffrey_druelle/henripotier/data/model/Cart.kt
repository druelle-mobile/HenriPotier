package ovh.geoffrey_druelle.henripotier.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cart")
data class Cart(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "isbn")
    val isbn: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "price")
    val price: Int,
    @ColumnInfo(name = "cover")
    val cover: String
) {
//    : Parcelable {
//    constructor(parcel: Parcel) : this(
//        parcel.readLong(),
//        parcel.readString()!!,
//        parcel.readString()!!,
//        parcel.readInt(),
//        parcel.readString()!!
//    )
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    override fun writeToParcel(p0: Parcel, p1: Int) {
//        p0.writeLong(id)
//        p0.writeString(isbn)
//        p0.writeString(title)
//        p0.writeInt(price)
//        p0.writeString(cover)
//    }

//    companion object CREATOR : Parcelable.Creator<Cart> {
    companion object {
//        override fun createFromParcel(parcel: Parcel): Cart {
//            return Cart(parcel)
//        }
//
//        override fun newArray(size: Int): Array<Cart?> {
//            return arrayOfNulls(size)
//        }

        fun convertToCartObject(book: Book): Cart{
            return Cart(
                isbn = book.isbn,
                title = book.title,
                price = book.price,
                cover = book.cover
            )
        }
    }
}