package com.example.shoeapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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
    fun addSellers(sqlLiteModel: sqlLiteModel) : Long{

        //telling the database what to do
        val db = this.writableDatabase

        //define and place content
        val contentValues = ContentValues()
        //put data to the respective fields
        contentValues.put(KEY_BRAND,sqlLiteModel.shoeBrand)
        contentValues.put(KEY_PRICE,sqlLiteModel.retailPrice)
        contentValues.put(KEY_NAME,sqlLiteModel.contactName)

        //query to insert to table users

        val success = db.insert(TABLE_SELLERS,null,contentValues)
        //close the db connection
        db.close()
        //return to the output of the method
        return success

    }

    //view data
}