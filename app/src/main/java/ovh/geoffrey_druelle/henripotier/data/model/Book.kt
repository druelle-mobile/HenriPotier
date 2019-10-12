package ovh.geoffrey_druelle.henripotier.data.model

data class Book(
    val isbn: String,
    val title: String,
    val price: Int,
    val cover: String,
    val synopsis: List<String>
) {

}