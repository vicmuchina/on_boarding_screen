package com.example.shoeapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import kotlinx.android.synthetic.main.fragment_b.view.*
import kotlinx.android.synthetic.main.fragment_c.view.*
import kotlinx.android.synthetic.main.fragment_d.view.*


class FragmentD : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_d, container, false)

        val viewPager = activity?.findViewById<ViewPager2>(R.id.page_view)

        view.button_previous_d.setOnClickListener {

            viewPager?.currentItem = 1
        }

        view.button_skip_d.setOnClickListener {

            findNavController().navigate(R.id.action_viewPagerFragment_to_homeActivity)
            onBoardingFinished()

        }
        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding" , Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)

        editor.apply()
    }


}