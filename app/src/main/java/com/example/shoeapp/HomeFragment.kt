package com.example.shoeapp

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import kotlinx.android.synthetic.main.fragment_home.view.*


class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        val button_1 = view.findViewById<ImageView>(R.id.image_1)
        val button_2 = view.findViewById<ImageView>(R.id.image_2)
        val button_3 = view.findViewById<ImageView>(R.id.image_3)
        val button_4 = view.findViewById<ImageView>(R.id.image_4)
        val button_5 = view.findViewById<ImageView>(R.id.image_5)
        val button_6 = view.findViewById<ImageView>(R.id.image_6)
        val button_7 = view.findViewById<ImageView>(R.id.image_7)
        val button_8 = view.findViewById<ImageView>(R.id.image_8)
        val button_9 = view.findViewById<ImageView>(R.id.image_9)
        val button_10 = view.findViewById<ImageView>(R.id.image_10)
        val button_11= view.findViewById<ImageView>(R.id.image_11)
        val button_12= view.findViewById<ImageView>(R.id.image_12)


        button_1.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)

            startActivity(intent)
        }
        button_2.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_3.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_4.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_5.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_6.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_7.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_8.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_9.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_10.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_11.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }
        button_12.setOnClickListener {
            val intent = Intent(context,ShoeActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}