package ovh.geoffrey_druelle.henripotier.ui.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import ovh.geoffrey_druelle.henripotier.R
import ovh.geoffrey_druelle.henripotier.data.model.Cart
import ovh.geoffrey_druelle.henripotier.databinding.ItemCartBinding

class CartAdapter(var cart: List<Cart> = listOf(), private val cartViewModel: CartViewModel) :
    RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CartViewHolder {
        return CartViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_cart,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = cart.size

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cart[position]
        holder.binding.itemCart = cartItem
        holder.binding.datacontext = cartViewModel
    }

    inner class CartViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val binding: ItemCartBinding = DataBindingUtil.bind(view)!!
    }
}