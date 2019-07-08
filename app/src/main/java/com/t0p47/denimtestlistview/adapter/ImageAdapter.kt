package com.t0p47.denimtestlistview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.squareup.picasso.Picasso
import com.t0p47.denimtestlistview.R
import com.t0p47.denimtestlistview.databinding.ImageItemBinding

class ImageAdapter(context: Context,
                   private val imagesList: ArrayList<String>): BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val rowView = inflater.inflate(R.layout.image_item, parent, false)
        val binding:  ImageItemBinding? = DataBindingUtil.bind(rowView)

        val image = imagesList[position]


        Picasso.get().load(image).into(binding?.imgView)

        return binding!!.root
    }

    override fun getItem(position: Int): Any {
        return imagesList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return imagesList.size
    }
}