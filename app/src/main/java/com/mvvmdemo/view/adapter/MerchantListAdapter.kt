package com.mvvmdemo.view.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mvvmdemo.R
import com.mvvmdemo.model.response.PreminumMerchant
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.row_merchant_list.view.*

class MerchantListAdapter(context: Context, data: MutableList<PreminumMerchant>?) :
    RecyclerView.Adapter<MerchantListAdapter.MerchantViewHolder>() {

    private var items: MutableList<PreminumMerchant>? = data
    private var inflater: LayoutInflater = LayoutInflater.from(context)
    private var mContext: Context = context

    override
    fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MerchantViewHolder {
        val view = inflater.inflate(R.layout.row_merchant_list, parent, false)
        return MerchantViewHolder(view)
    }

    override
    fun onBindViewHolder(holder: MerchantViewHolder, position: Int) {
        try {
            holder.bindData(items?.get(position)!!)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override
    fun getItemCount(): Int {
        return items?.size ?: 0
    }

    inner class MerchantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindData(data:PreminumMerchant)
        {
            try {
                itemView.tvNumber.text = data.towerNumber+", "+data.unitNumber
                itemView.tvCategoryName.text = data.subCategoryName

                Glide.with(mContext)
                    .load(data.merchantLogo!!).placeholder(R.drawable.ic_no_data)
                .into(itemView.ivMerchantLogo)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}