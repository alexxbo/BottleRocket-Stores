package net.omisoft.stores.screens.list.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_store.view.*
import net.omisoft.mvptemplate.R
import net.omisoft.stores.database.entity.Store

class StoresAdapter(private val listener: ItemClickListener) :
        PagedListAdapter<Store, StoresAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(old: Store, new: Store) = old.storeId == new.storeId

            override fun areContentsTheSame(old: Store, new: Store) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store: Store? = getItem(position)
        store?.let { holder.bind(it) }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val storeLogo = itemView.storeLogo as ImageView
        private val storeTitle = itemView.storeTitle as TextView
        private val storeCity = itemView.storeCity as TextView
        private val storeAddress = itemView.storeAddress as TextView
        private val storePhone = itemView.storePhone as TextView

        fun bind(store: Store) {

            Glide.with(itemView)
                    .load(store.storeLogoURL)
                    .into(storeLogo)

            storeTitle.text = store.name
            storeCity.text = itemView.context.getString(R.string.store_city, store.city)
            storeAddress.text = itemView.context.getString(R.string.store_address, store.address)
            storePhone.text = itemView.context.getString(R.string.store_phone, store.phone)

            itemView.setOnClickListener { listener.onItemClick(store) }
        }
    }

    interface ItemClickListener {
        fun onItemClick(store: Store)
    }
}