package com.test.breakingbadcharacters

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.test.breakingbadcharacters.databinding.RecyclerViewHolderBinding
import com.test.breakingbadcharacters.webApi.models.Characters


class RecyclerViewAdapter(activity: SearchResultActivity) : RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>() {
    private var mData : Characters? = null
    private var mActivity = activity

    inner class RecyclerViewHolder(val recyclerViewHolderBinding: RecyclerViewHolderBinding) :
        RecyclerView.ViewHolder(recyclerViewHolderBinding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder =
        RecyclerViewHolder(
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.recycler_view_holder,
                parent,
                false
            )
        )

    override fun getItemCount(): Int {
        return mData?.size ?: 0

    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if(mData != null) {
            holder.recyclerViewHolderBinding.character = mData!![position]
            holder.recyclerViewHolderBinding.cardView.setOnClickListener{
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(Utils.INTENT_CHARACTER_KEY, mData!![position])
                it.context.startActivity(intent)
                mActivity.overridePendingTransition(R.anim.slide_from_center_to_left_anim,R.anim.slide_from_right_to_center_anim)

            }
        }
    }

    fun addAll(characters: Characters){
        mData = characters

    }

}