package com.example.shoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_upload_form.*

class UploadForm : AppCompatActivity() {


    lateinit var shoeBrand:String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_form)

        supportActionBar?.hide()

        val brand = resources.getStringArray(R.array.brands)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,brand)




        spinner3.adapter = adapter

        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                shoeBrand = brand[position].toString()
                Toast.makeText(this@UploadForm,"Selected shoe brand:" + brand[position],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //code to perform when nothing is selected

            }
        }

    }

    fun saveData(view: View){
        //capture users data

        var retailPrice = price.text.toString()
        var contactName = contact_name.text.toString()


        //instance of Database helper class
        val databaseHelper = DatabaseHelper(this)

        if(shoeBrand.trim() != "" && retailPrice.trim() != "" && contactName.trim() != ""){

            //if its not equal to blank we can write to database
            val status = databaseHelper.addSellers(SqlLiteModel(shoeBrand = shoeBrand , retailPrice = retailPrice, contactName = contactName ))

            if(status > -1){
                Toast.makeText(applicationContext, "Record saved", Toast.LENGTH_LONG).show()

                val brand = resources.getStringArray(R.array.brands)
                //clear inputs
                price.text?.clear()
                contact_name.text?.clear()
                shoeBrand = brand.first()

            }else{
                Toast.makeText(applicationContext, "Something went wrong,try again", Toast.LENGTH_LONG).show()
            }

        }else{

            Toast.makeText(applicationContext, "Fields cannot be empty", Toast.LENGTH_LONG).show()

        }


    }

    fun viewdata(view: View){
        //define instance of database helper class

        val databaseHelper = DatabaseHelper(this)

        //make reference to the view data method
        val viewData: List<SqlLiteModel> = databaseHelper.viewData()

        //define array variables to store each record detail
        var arrayBrand = Array<String>(viewData.size){"null"}
        var arrayPrice = Array<String>(viewData.size){"null"}
        var arrayContact = Array<String>(viewData.size){"null"}

        var index = 0

        for (e in viewData){

            arrayBrand[index] = e.shoeBrand
            arrayPrice[index] = e.retailPrice
            arrayContact[index] = e.contactName

         index++
        }

        //create details for my adapter and also set the adapter to the list view
        val myAdapter = SellersListAdapter(this,arrayBrand,arrayPrice,arrayContact)

        val list = findViewById<ListView>(R.id.viewList)

        list.adapter = myAdapter
    }



}