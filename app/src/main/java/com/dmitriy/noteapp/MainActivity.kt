package com.dmitriy.noteapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.dmitriy.noteapp.databinding.ActivityMainBinding
import com.dmitriy.noteapp.fragments.HomeFragment
import com.dmitriy.noteapp.utils.FragmentCallbacks

class MainActivity : AppCompatActivity(), FragmentCallbacks {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

//        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
//        if (currentFragment == null) {
//            val fragment = MainFragment()
//            supportFragmentManager.beginTransaction()
//                .add(R.id.fragment_container, fragment)
//                .commit()
//        }
        onFragmentReplacement(HomeFragment.newInstance(), false, false)
    }

    override fun onFragmentReplacement(fragment: Fragment, onBackPressed: Boolean, addToBackStack: Boolean) {
        Log.d("myLogs", "onFragmentReplacement()")

        when (onBackPressed) {
            true -> {
                supportFragmentManager.popBackStack()
            }
            false -> {
                val fManager = supportFragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)

                if (addToBackStack) {
                    fManager.addToBackStack(null)
                }

                fManager.commit()
            }
        }
    }
}