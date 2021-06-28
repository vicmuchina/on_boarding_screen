package com.example.shoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_upload_form.*

class UploadForm : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_form)

        supportActionBar?.hide()

        val brands = resources.getStringArray(R.array.brands)
        val adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,brands)


        spinner3.adapter = adapter

        spinner3.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(this@UploadForm,"Selected shoe brand:" + brands[position],Toast.LENGTH_LONG).show()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                //code to perform when nothing is selected

            }
        }




    }
}