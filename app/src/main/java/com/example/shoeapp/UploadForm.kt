package com.example.shoeapp

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_upload_form.*
import java.io.IOException
import java.util.*

class UploadForm : AppCompatActivity() {


    lateinit var shoeBrand:String

    private lateinit var auth: FirebaseAuth
    private val PICK_IMAGE_REQUEST = 71
    lateinit var productImage: Uri
    var productName: String = ""
    var productPrice: String = ""
    var productContactName: String = ""
    var productContactPhone : String = ""

    // tags for db and storage ref
    private var firebaseStorage: FirebaseStorage? = null
    private var storageRef: StorageReference? = null
    private var databaseRef: DatabaseReference? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_form)

        auth = FirebaseAuth.getInstance()

        //initializing storage and db ref

        firebaseStorage = FirebaseStorage.getInstance()
        storageRef = FirebaseStorage.getInstance().reference
        databaseRef = FirebaseDatabase.getInstance().getReference("shoesTable")

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

        signOut.setOnClickListener {

            auth.signOut()
            val intent = Intent(applicationContext,HomeActivity::class.java)
            startActivity(intent)
        }


        textViewImage.setOnClickListener {

            val intent  = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(
                    Intent.createChooser(intent, "Select Picture"),
                    PICK_IMAGE_REQUEST
            )
        }

        displayButton.setOnClickListener {

            val intent= Intent(applicationContext,DisplayActivity::class.java)

            startActivity(intent)

        }

    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK){
            //check if image was picked
            if (data == null || data.data == null){
                return
            }
            productImage = data.data!!
            //showing user their selection
            try {
                val bitmap  = MediaStore.Images.Media.getBitmap(contentResolver,
                        productImage)
                //display image to image view
                shoeImage.setImageBitmap(bitmap)
            }catch (e: IOException){
                e.printStackTrace()
            }
        }
    }



    fun saveData(view: View){
        //capture users data

        var retailPrice = price.text.toString()
        var contactName = contact_name.text.toString()
        val contactNumber = contact_number.text.toString()
        val productImage = productImage


        //instance of Database helper class
        val databaseHelper = DatabaseHelper(this)

        if(shoeBrand.trim() != "" && retailPrice.trim() != "" && contactName.trim() != "" && contactName.trim() != "" ){

            submitToFireBase(shoeBrand,retailPrice,contactName,contactNumber,productImage)

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

    private fun submitToFireBase(shoeBrand: String, retailPrice: String, contactName: String, contactNumber: String, productImage: Uri) {

        //process to take data to firebase
        //UUID : refers to image name
        val ref = storageRef?.child("shoesImages/"
                + UUID.randomUUID().toString())

        //put the image to storage bucket
        val uploadTask = ref?.putFile(productImage!!)

        //monitoring the process
        val urlTask = uploadTask?.continueWithTask(
                Continuation<UploadTask.TaskSnapshot, Task<Uri>>
                {
                    //checking if it failed
                    if (!it.isSuccessful){
                        it.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                })?.addOnCompleteListener {
            if (it.isSuccessful) {
                //checking if upload process is complete : if complete we save the
                //download url for image
                val downloadUri = it.result
                Log.d("image","downlaod url " + downloadUri.toString())
                //end of storage process
                //beginning the process of taking data to realtime database
                //generate unique id for records
                val shoeId = databaseRef?.push()?.key
                //hold this data in our model
                val shoeUpload = shoeId.let {
                    ProductModel(shoeId.toString(),shoeBrand,retailPrice
                            ,contactName,contactNumber,downloadUri.toString())
                }
                //send values to realtime databse
                if (shoeId != null){
                    databaseRef?.child(shoeId)?.setValue(shoeUpload)
                            ?.addOnCompleteListener {
                                Toast.makeText(
                                        applicationContext,
                                        "Shoe added successfully",
                                        Toast.LENGTH_LONG
                                ).show()
                            }?.addOnFailureListener{
                                Toast.makeText(
                                        applicationContext,
                                        "Error, check internet settings",
                                        Toast.LENGTH_LONG
                                ).show()
                            }
                }  //end of sending text data to real time database
            } else {
                // Handle failures //e.g //
                Toast.makeText(
                        applicationContext,
                        " Error occurred , check internet connection",
                        Toast.LENGTH_LONG
                ).show()
            }
        }?.addOnFailureListener {
            //here u can get actual error from firebase
            val messageError = it.message
            Toast.makeText(applicationContext,
                    " Error is " + messageError, Toast.LENGTH_LONG)
                    .show()
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