package com.t0p47.denimtestlistview.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import androidx.databinding.DataBindingUtil
import com.t0p47.capitals.model.Capital
import com.t0p47.denimtestlistview.R
import com.t0p47.denimtestlistview.databinding.ListItemCapitalBinding

class CapitalAdapter(
	context: Context,
	private val capitalList: ArrayList<Capital>): BaseAdapter(){

	private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

	override fun getCount(): Int{
		return capitalList.size
	}

	override fun getItem(position: Int): Capital{
		return capitalList[position]
	}

	override fun getItemId(position: Int): Long{
		return position.toLong()
	}

	override fun getView(position: Int, convertView: View?, parent: ViewGroup): View{

        val rowView = inflater.inflate(R.layout.list_item_capital, parent, false)
		val binding: ListItemCapitalBinding? = DataBindingUtil.bind(rowView)

		val capital: Capital? = capitalList[position]
		binding?.capital = capital
		/*binding?.click = object: CapitalClickHandler{
			override fun onItemClicked(v: View){

			}
		}*/

		//val rowView = inflater.inflate(R.layout.list_item_capital, parent, false)

		//return rowView
		return binding!!.root
	}

}

interface CapitalClickHandler{
	fun onItemClicked(v: View)
}