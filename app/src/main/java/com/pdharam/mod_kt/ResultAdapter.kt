package com.pdharam.mod_kt


import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_vehicle.view.*


/**
 * Created by Dell on 26-Jan-19.
 */

class ResultAdapter(private val context: Context, private val resultList: List<Result>) :
    RecyclerView.Adapter<ResultAdapter.ResultViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResultViewHolder {
        return ResultViewHolder(LayoutInflater.from(context).inflate(R.layout.row_vehicle, parent, false))
    }

    override fun onBindViewHolder(holder: ResultViewHolder, position: Int) {
        holder.bind(resultList[position])
    }

    override fun getItemCount(): Int {
        return resultList.size
    }

    inner class ResultViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(result: Result) {
            itemView.tv_text.text = result.text
            val drawableRes = if (result.isNearest!!) R.drawable.border_active else R.drawable.border
            itemView.tv_text.background = getDrawaleRes(drawableRes)
        }

        private fun getDrawaleRes(drawableRes: Int): Drawable =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                context.resources.getDrawable(drawableRes, context.theme)
            } else {
                getDrawaleRes(drawableRes)
            }


    }

}