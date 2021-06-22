package com.example.shoeapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import kotlinx.android.synthetic.main.fragment_view_pager.view.*

class ViewPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
    val view = inflater.inflate(R.layout.fragment_view_pager, container, false)

        //fragment list
        val fragmentList = arrayListOf<Fragment>(
            FragmentB(),
            FragmentC(),
            FragmentD()
        )


        val Adapter = PagerAdapter(fragmentList,requireActivity().supportFragmentManager,lifecycle)

     view.page_view.adapter = Adapter

      return view
    }


  }