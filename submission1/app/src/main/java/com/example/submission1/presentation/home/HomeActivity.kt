package com.example.submission1.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.submission1.R
import com.example.submission1.abstraction.BaseActivity
import com.example.submission1.abstraction.HasToolbar
import com.example.submission1.databinding.ActivityHomeBinding
import com.example.submission1.presentation.home.movies.MoviesFragment
import com.example.submission1.presentation.home.tvshows.TvShowFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home)

        setupViewPager()
    }


    private fun setupViewPager() {
        binding.tabLayout.setupWithViewPager(binding.viewPager)
        val adapter = ViewPagerAdapter(supportFragmentManager)

        adapter.addFragment(MoviesFragment(), "MOVIES")
        adapter.addFragment(TvShowFragment(), "TV SHOWS")
        binding.viewPager.adapter = adapter
    }

    class ViewPagerAdapter(fragmentManager: FragmentManager) :
        FragmentPagerAdapter(fragmentManager) {
        private val fragmentList = mutableListOf<Fragment>()
        private val titleList = mutableListOf<String>()

        override fun getCount(): Int = fragmentList.size

        override fun getItem(position: Int): Fragment = fragmentList[position]

        fun addFragment(fragment: Fragment, title: String) {
            fragmentList.add(fragment)
            titleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence = titleList[position]
    }
}