package com.example.shoeapp

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_upload_form.*
import java.io.IOException


class DisplayAdapter (private val context: Context,
                      private val productModel: List<ProductModel> )
    : RecyclerView.Adapter<DisplayAdapter.ProductViewHolder>() {

    private val PICK_IMAGE_REQUEST = 71


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.display_item, parent, false)
        return ProductViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {

        //current position of each item
        val currentShoe  = productModel[position]


        //bind
        Glide.with(context)
            .load(currentShoe.shoeImage)
            .into(holder.shoeImage)


        holder.retailPrice.text ="Price: "+ currentShoe.shoePrice
        holder.brand.text = "Shoe Brand: "+ currentShoe.shoeBrand
        holder.contactName.text ="Contact Name: " + currentShoe.contactName
        holder.phoneNumber.text ="Phone Number: " + currentShoe.contactNumber


        //variables to store the items data
        val id  = currentShoe.id
        val brand = currentShoe.shoeBrand
        val price = currentShoe.shoePrice
        val contactNumber = currentShoe.contactNumber
        val contactName = currentShoe.contactName
        val imagePath = currentShoe.shoeImage


        holder.cardClick.setOnClickListener {

            updateAndDeleteDialog(id,brand,price,contactName,contactNumber,imagePath)

        }


    }

    private fun updateAndDeleteDialog(id: String,brand: String,price: String,contactName: String,ContactNumber: String,imagePath: String) {

        //raising a dialog
        val dialogBuilder = AlertDialog.Builder(context)
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)
                as LayoutInflater
        //attaching the dialog interface
        val dialogView = inflater.inflate(R.layout.update_delete_dialog, null)
        dialogBuilder.setView(dialogView)

        //view identification

        var updateContactNumber = dialogView.findViewById<EditText>(R.id.editTextContactNumber)
        var updateContactName = dialogView.findViewById<EditText>(R.id.editTextContactName)
        var updateBrand = dialogView.findViewById<EditText>(R.id.editTextBrand)
        var updatePrice = dialogView.findViewById<EditText>(R.id.editTextPrice)
        var btnDelete = dialogView.findViewById<Button>(R.id.btnDelete)
        var btnUpdate = dialogView.findViewById<Button>(R.id.btnUpdate)


        //customize the dialog box
        dialogBuilder.setTitle("Update or Delete a Product")
        dialogBuilder.setIcon(R.drawable.ic_baseline_scatter_plot_24)
        //create and show
        val dialog = dialogBuilder.create()
        dialog.show()


        btnUpdate.setOnClickListener {
            var priceEntered = updatePrice.text.toString()
            var brandEntered = updateBrand.text.toString()
            var contactNameEntered = updateContactName.text.toString()
            var contactNumberEntered = updateContactNumber.text.toString()

            if (priceEntered.trim() != null){
                updateProductToFirebase(id,brandEntered,priceEntered,contactNameEntered,contactNumberEntered,imagePath)
            } else {
                Toast.makeText(context,"Fill the price first",Toast.LENGTH_LONG).show()

            }
        }

        btnDelete.setOnClickListener {
            deleteProductFromFirebase(id)
            dialog.dismiss()
            Toast.makeText(context,"Delete success", Toast.LENGTH_LONG).show()

        }

    }

    private fun updateProductToFirebase(id: String, brandEntered: String, priceEntered: String, contactNameEntered: String, contactNumberEntered: String,imagePath: String) {

        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("shoesTable").child(id)
        //take the data to model
        val shoeUpload = ProductModel(id,brandEntered,priceEntered,contactNameEntered,contactNumberEntered,imagePath)
        databaseReference.setValue(shoeUpload)
        Toast.makeText(context,"Update Successful",Toast.LENGTH_LONG).show()

    }


    private fun deleteProductFromFirebase(id: String) {

        //create ref to the db
        val databaseReference = FirebaseDatabase.getInstance()
            .getReference("shoesTable").child(id)
        databaseReference.removeValue()
        Toast.makeText(context,"Shoe deleted Successfully",Toast.LENGTH_LONG).show()

    }

    override fun getItemCount(): Int = productModel.size


    class ProductViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {

        val shoeImage = itemView.findViewById<ImageView>(R.id.displayShoeImage)
        val retailPrice = itemView.findViewById<TextView>(R.id.displayRetailPrice)
        val brand = itemView.findViewById<TextView>(R.id.displayBrand)
        val contactName = itemView.findViewById<TextView>(R.id.displayContactName)
        val phoneNumber = itemView.findViewById<TextView>(R.id.displayPhoneNumber)
        val cardClick = itemView.findViewById<CardView>(R.id.cardView)

    }
}