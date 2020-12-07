package com.test.breakingbadcharacters

import android.R
import android.animation.Animator
import android.animation.AnimatorInflater
import android.content.Context
import android.view.animation.BounceInterpolator
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.recyclerview.widget.SimpleItemAnimator


class CustomItemAnimator(mContext: Context) : SimpleItemAnimator() {

    var context = mContext
    override fun animateRemove(holder: ViewHolder): Boolean {
        return false
    }

    override fun animateAdd(holder: ViewHolder): Boolean {
        val set: Animator = AnimatorInflater.loadAnimator(
            context,
            R.animator.fade_out
        )
        set.interpolator = BounceInterpolator()
        set.duration = 1600
        set.setTarget(holder.itemView)
        set.start()
        return true
    }

    override fun animateMove(
        holder: ViewHolder,
        fromX: Int,
        fromY: Int,
        toX: Int,
        toY: Int
    ): Boolean {
        val set: Animator = AnimatorInflater.loadAnimator(
            context,
            R.animator.fade_out
        )
        set.interpolator = BounceInterpolator()
        set.duration = 1600
        set.setTarget(holder.itemView)
        set.start()
        return true
    }

    override fun animateChange(
        oldHolder: ViewHolder,
        newHolder: ViewHolder,
        fromLeft: Int,
        fromTop: Int,
        toLeft: Int,
        toTop: Int
    ): Boolean {
       return false

    }

    override fun runPendingAnimations() {}
    override fun endAnimation(item: ViewHolder) {}
    override fun endAnimations() {}
    override fun isRunning(): Boolean {
        return false
    }


}