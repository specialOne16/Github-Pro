package com.jundapp.githubpro

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.jundapp.githubpro.listuser.UserListFragment
import com.jundapp.githubpro.listuser.UserListFragment.Companion.TYPE_ALL
import com.jundapp.githubpro.listuser.UserListFragment.Companion.TYPE_SEARCH
import com.jundapp.githubpro.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        showList(binding.searchBar.text.toString())
        binding.btnSearch.setOnClickListener { showList(binding.searchBar.text.toString()) }

    }

    private fun showList(keyword: String) {
        val userListFragment = UserListFragment.newInstance(
            if (keyword.isBlank()) TYPE_ALL else TYPE_SEARCH, keyword
        )
        supportFragmentManager
            .beginTransaction()
            .replace(binding.userListContainer.id, userListFragment)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.love -> {
                val uri = Uri.parse("githubpro://favorites")
                startActivity(Intent(Intent.ACTION_VIEW, uri))
                true
            }
            else -> true
        }
    }

}
