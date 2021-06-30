package com.example.shoeapp

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListAdapter
import android.widget.TextView

class SellersListAdapter(private val context: Activity,
                         private val brands: Array<String>,
                         private val prices: Array<String>,private val contacts: Array<String>) : ArrayAdapter<String>(context,R.layout.custom_list,brands) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.custom_list,null ,true)

        //view identification
        val brand :TextView = rowView.findViewById(R.id.brand)
        val price :TextView = rowView.findViewById(R.id.retailPrice)
        val contact :TextView = rowView.findViewById(R.id.contact)

        //set the data according to the position
        brand.text ="Shoe Brand: ${brands[position]}"
        price.text = "Retail Price: ${prices[position]} "
        contact.text = "Contact Name: ${contacts}"

        return rowView
    }
}