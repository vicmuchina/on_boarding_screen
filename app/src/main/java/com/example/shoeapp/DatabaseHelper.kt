package com.example.shoeapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import java.util.ArrayList

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null,
    DATABASE_VERSION) {

    //define our companion variables
    companion object{
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "SellersDatabase"
        private val TABLE_SELLERS = "SellersTable"
        private val KEY_ID ="id"
        private val KEY_BRAND = "brand"
        private val KEY_PRICE = "price"
        private val KEY_NAME = "contact_name"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        //define our query
        val CREATE_SELLERS_TABLE = ("CREATE TABLE IF NOT EXISTS " + TABLE_SELLERS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_BRAND + " TEXT," + KEY_PRICE + " TEXT," + KEY_NAME + " TEXT" + ")")

        //execute the query
        db?.execSQL(CREATE_SELLERS_TABLE)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS" + TABLE_SELLERS)
        onCreate(db)

    }

    //save data
    fun addSellers(SqlLiteModel: SqlLiteModel) : Long{

        //telling the database what to do
        val db = this.writableDatabase

        //define and place content
        val contentValues = ContentValues()
        //put data to the respective fields
        contentValues.put(KEY_BRAND,SqlLiteModel.shoeBrand)
        contentValues.put(KEY_PRICE,SqlLiteModel.retailPrice)
        contentValues.put(KEY_NAME,SqlLiteModel.contactName)

        //query to insert to table users

        val success = db.insert(TABLE_SELLERS,null,contentValues)
        //close the db connection
        db.close()
        //return to the output of the method
        return success

    }

    //function to view data

    fun viewData() : List<SqlLiteModel>{

        //get a resizable array
        val sellerArray : ArrayList<SqlLiteModel> = ArrayList()

        //define our fetch query
        val selectQuery = "SELECT * FROM $TABLE_SELLERS"

        //define what db should do

        val db = this.writableDatabase

        //reading our data
        var cursor: Cursor? = null

        //declare a try and catch in case the data is not there
        //and when the database undergoes an upgrade we need to prevent a crash

        try {
            cursor = db.rawQuery(selectQuery,null)
        }
        catch (e : SQLiteException){

            db.execSQL(selectQuery)

            return ArrayList()
        }
        //iterate over the db and store them in our model class

        var userid: String
        var brand: String
        var price: String
        var contact: String

        //using cursor to pick records
        if(cursor.moveToFirst()){

            //create a do loop
           do{
            brand = cursor.getString(cursor.getColumnIndex("brand"))
            price = cursor.getString(cursor.getColumnIndex("price"))
            contact = cursor.getString(cursor.getColumnIndex("contact_name"))

            //taking the data to the model class

            val sellers = SqlLiteModel(shoeBrand = brand ,retailPrice = price , contactName = contact)

            sellerArray.add(sellers)

           }while (cursor.moveToNext())

        }

        cursor.close()

        return sellerArray
    }
}