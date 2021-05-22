package com.jundapp.githubpro.activity.favoriteuser

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jundapp.githubpro.core.ui.UserListFragment
import com.jundapp.githubpro.core.ui.UserListFragment.Companion.TYPE_FAVORITE
import com.jundapp.githubpro.databinding.ActivityFavoriteUserBinding

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