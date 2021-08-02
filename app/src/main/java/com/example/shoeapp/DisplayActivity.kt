package com.example.shoeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_display.*

class DisplayActivity : AppCompatActivity() {

    private var databaseReference: DatabaseReference? = null
    lateinit var recycler: RecyclerView
    //mutable list to store our fetch from firebase
    lateinit var shoeList: MutableList<ProductModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display)

        //intialize db ref
        databaseReference = FirebaseDatabase.getInstance().getReference("shoesTable")
        //initialize our mutable list
        shoeList = mutableListOf()

        //read our data
        databaseReference!!.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot!!.exists()){
                    Log.d("shoesSnap","Snapshot looks like : " + snapshot)
                    //clear list
                    shoeList.clear()
                    //for loop to iterate of existing data\
                    for (h in snapshot.children){
                        //add to model class
                        val shoes = h.getValue(ProductModel::class.java)
                        //add details to list
                        shoeList?.add(shoes!!)
                        Log.d("shoeSnap", " snapshot is " + shoes)
                    }
                    val adapter = DisplayAdapter(this@DisplayActivity!!,shoeList)
                    //setting the adapter for the recyclerView
                    recyclerProducts?.adapter = adapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,
                    "Something went wrong, try again, Check internet connection"
                            +  error.details, Toast.LENGTH_LONG).show()
                Log.d("error","error is " + error.details)
            }

        })
        //settings recyclerview
        recyclerProducts.layoutManager = LinearLayoutManager(this)
        recyclerProducts.setHasFixedSize(true)
    }



}
