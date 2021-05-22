package com.jundapp.githubpro.favorites

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jundapp.githubpro.activity.fragment.UserListFragment
import com.jundapp.githubpro.activity.fragment.UserListFragment.Companion.TYPE_FAVORITE
import com.jundapp.githubpro.favorites.databinding.ActivityFavoriteUserBinding

class FavoriteUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteUserBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userListFragment = UserListFragment.newInstance(TYPE_FAVORITE)
        supportFragmentManager
            .beginTransaction()
            .replace(binding.userListContainer.id, userListFragment)
            .commit()
    }
}