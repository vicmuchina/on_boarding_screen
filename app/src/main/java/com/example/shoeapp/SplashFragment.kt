package com.example.shoeapp

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.splash_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Handler().postDelayed({
            if (onBoardingFinished()){
                findNavController().navigate(R.id.action_fragmentA_to_homeActivity)

            }else{
                findNavController().navigate(R.id.action_fragmentA_to_viewPagerFragment)
            }
        }, 3000)
    }



    private fun onBoardingFinished() : Boolean {
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)

        return sharedPref.getBoolean("Finished", false)

    }
}