package net.omisoft.stores.screens.list.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import net.omisoft.mvptemplate.R
import net.omisoft.mvptemplate.databinding.ItemStoreBinding
import net.omisoft.stores.common.data.model.Store

class StoresAdapter(
    private val listener: (Store) -> Unit,
) : PagingDataAdapter<Store, StoresAdapter.ViewHolder>(DIFF_CALLBACK) {

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Store>() {
            override fun areItemsTheSame(old: Store, new: Store) = old.storeId == new.storeId

            override fun areContentsTheSame(old: Store, new: Store) = old == new
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemStoreBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val store: Store? = getItem(position)
        store?.let { holder.bind(it) }
    }

    inner class ViewHolder(private val binding: ItemStoreBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(store: Store) = binding.run {

            Glide.with(itemView)
                .load(store.storeLogoURL)
                .into(storeLogo)

            storeTitle.text = store.name
            storeCity.text = itemView.context.getString(R.string.store_city, store.city)
            storeAddress.text = itemView.context.getString(R.string.store_address, store.address)
            storePhone.text = itemView.context.getString(R.string.store_phone, store.phone)

            itemView.setOnClickListener { listener.invoke(store) }
        }
    }
}