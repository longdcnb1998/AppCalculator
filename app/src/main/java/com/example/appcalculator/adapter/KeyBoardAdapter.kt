package com.example.appcalculator.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.appcalculator.R
import com.example.appcalculator.utils.ScreenUtil

class KeyBoardAdapter(
    private val mListCharacter: ArrayList<String>,
    private val mContext: Context?,
    private var mCallback: Callback
) : RecyclerView.Adapter<KeyBoardAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(mContext).inflate(R.layout.item_key_board, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mListCharacter.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val viewHolder: ViewHolder = holder
        val param: ConstraintLayout.LayoutParams = ConstraintLayout.LayoutParams(
            ScreenUtil.convertPixelsToDp(200, mContext),
            ScreenUtil.convertPixelsToDp(180, mContext)
        )
        viewHolder.tvCharacter.text = mListCharacter[position]
        if (mListCharacter[position] == "C"){
            if (mContext != null) {
                viewHolder.tvCharacter.setTextColor(mContext.resources.getColor(R.color.color_red_clean))
            }
        }
        if (mListCharacter[position] == "()" || mListCharacter[position] == "%" || mListCharacter[position] == "÷" ||
            mListCharacter[position] == "x" ||  mListCharacter[position] == "-" ||  mListCharacter[position] == "+"){
            if (mContext != null) {
                viewHolder.tvCharacter.setTextColor(mContext.resources.getColor(R.color.color_green))
            }
        }
        if ( position == mListCharacter.size-1){
            viewHolder.tvCharacter.setBackgroundResource(R.drawable.shape_bg_ripple_equal_radius_30dp)
        }
//        viewHolder.clItem.layoutParams = param
        viewHolder.clItem.setOnClickListener {
            mCallback.onClick(position)
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvCharacter: TextView = itemView.findViewById(R.id.tv_character)
        var clItem: ConstraintLayout = itemView.findViewById(R.id.cl_item_key_board)
    }

    interface Callback {
        fun onClick(position: Int)
    }
}