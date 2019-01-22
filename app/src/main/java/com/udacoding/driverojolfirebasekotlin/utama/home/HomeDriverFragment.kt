package com.udacoding.driverojolfirebasekotlin.utama.home


import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.udacoding.driverojolfirebasekotlin.R
import com.udacoding.driverojolfirebasekotlin.utama.complete.CompleteFragment
import com.udacoding.driverojolfirebasekotlin.utama.handle.HandleFragment
import com.udacoding.driverojolfirebasekotlin.utama.home.model.Booking
import com.udacoding.driverojolfirebasekotlin.utama.request.RequestFragment
import kotlinx.android.synthetic.main.driver_home.*
import org.jetbrains.anko.support.v4.intentFor


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class HomeDriverFragment : Fragment() {

//    internal inner class TabAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
//
//        override fun getItem(i: Int): Fragment? {
//
//            var fragment: Fragment? = null
//
//            when (i) {
//                0 -> fragment = RequestFragment()
//                1 -> fragment = HandleFragment()
//                2 -> fragment = CompleteFragment()
//            }
//
//            return fragment
//        }
//
//        override fun getCount(): Int {
//            //banyak menu di tab
//            return 3
//        }
//
//        override fun getPageTitle(position: Int): CharSequence? {
//
//            when (position) {
//                0 -> return "Request"
//                1 -> return "Handle"
//                2 -> return "Complete"
//            }
//
//            return super.getPageTitle(position)
//        }
//    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.itemRequest -> {
                setFragment(RequestFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemHandle -> {

                setFragment(HandleFragment())
                return@OnNavigationItemSelectedListener true
            }
            R.id.itemComplete -> {
                setFragment(CompleteFragment())
                return@OnNavigationItemSelectedListener true
            }

        }
        false
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.driver_home, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val adadpter = TabAdapter(childFragmentManager)
//        pager.setAdapter(adadpter)
//        tap.setupWithViewPager(pager)

//        val fragment = arguments.getString("")

        setFragment(RequestFragment())
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    fun setFragment(fragment : Fragment){

        activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.container,fragment)?.commit()
    }
}
