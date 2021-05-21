package com.jundapp.githubpro.activity.detailuser

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.jundapp.githubpro.core.ui.UserListFragment
import com.jundapp.githubpro.core.ui.UserListFragment.Companion.TYPE_FOLLOWER
import com.jundapp.githubpro.core.ui.UserListFragment.Companion.TYPE_FOLLOWING

class DetailUserPagerAdapter(activity: AppCompatActivity, private val uname: String) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = UserListFragment.newInstance(TYPE_FOLLOWER, uname)
            1 -> fragment = UserListFragment.newInstance(TYPE_FOLLOWING, uname)
        }
        return  fragment as Fragment
    }

}