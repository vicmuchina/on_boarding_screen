package com.example.shoeapp

class ProductModel (val id: String ,
                    val shoeBrand: String, val shoePrice: String,
                    val contactName: String, val contactNumber: String,
                    val shoeImage: String){
    //blank constructor for reading process
    constructor() : this("","","","","","")

}