package ovh.geoffrey_druelle.henripotier.ui.cart

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ovh.geoffrey_druelle.henripotier.data.model.Cart

@BindingAdapter(value = ["cartList","cartViewModel"], requireAll = false)
fun setRecyclerViewSource(
    recyclerView: RecyclerView,
    list: List<Cart>,
    viewModel: CartViewModel
) {
    recyclerView.adapter?.run {
        if (this is CartAdapter) {
            this.cart = list
            this.notifyDataSetChanged()
        }
    } ?: run {
        CartAdapter(list, viewModel).apply {
            recyclerView.adapter = this
        }
    }
}

@BindingAdapter("coverCart")
fun setCover(imageView: ImageView, url: String){
    Picasso.get().load(url).resize(200,300).onlyScaleDown().into(imageView)
}