/*
 * Created by Muhammad Mehroz Afzal on 2020.
 */

package com.jeeny.task.ui.map.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.jeeny.task.R
import com.jeeny.task.repository.model.PoiListItem

class VehicleSpinnerAdapter(
    context: Context,
    resourceID: Int,
    private val types: List<String>
) : ArrayAdapter<String>(
    context, resourceID, types
) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return rowView(convertView, position)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View? {
        return rowDropDownView(convertView, position)
    }

    private fun rowView(convertView: View?, position: Int): View? {
        val holder: ViewHolder
        var rowView = convertView
        if (rowView == null) {
            holder = ViewHolder()
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.item_vehicle, null, false)
            holder.txtTitle = rowView.findViewById<View>(R.id.itemVehicle_type) as TextView
            rowView.tag = holder
        } else holder = rowView.tag as ViewHolder
        types[position].let { holder.bindViews(it) }
        return rowView
    }

    private fun rowDropDownView(convertView: View?, position: Int): View? {
        val holder: ViewHolderDropDown
        var rowView = convertView
        if (rowView == null) {
            holder = ViewHolderDropDown()
            val inflater =
                getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            rowView = inflater.inflate(R.layout.item_vehicle_expanded, null, false)
            holder.txtTitle = rowView.findViewById<View>(R.id.itemVehicleExpanded_type) as TextView
            rowView.tag = holder
        } else holder = rowView.tag as ViewHolderDropDown
        types[position].let { holder.bindViews(it) }
        return rowView
    }

    private inner class ViewHolder {
        var txtTitle: TextView? = null
        fun bindViews(item: String) {
            txtTitle?.text = item
        }
    }

    private inner class ViewHolderDropDown {
        var txtTitle: TextView? = null
        fun bindViews(item: String) {
            txtTitle?.text = item
        }
    }
}