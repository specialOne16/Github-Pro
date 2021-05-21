package com.jundapp.githubpro.activity.detailuser

import android.os.Bundle
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jundapp.githubpro.R
import com.jundapp.githubpro.adapter.DetailPagerAdapter
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.databinding.ActivityDetailBinding

@Suppress("SameParameterValue")
class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private lateinit var binding: ActivityDetailBinding
    private var user: User? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        user = intent.getParcelableExtra(EXTRA_USER)

        binding.tvName.text = user?.username ?: resources.getString(R.string.name)
        binding.tvUName.text = resources.getString(R.string.uname)
        binding.tvCompany.text = resources.getString(R.string.company)
        binding.tvLocation.text = resources.getString(R.string.location)
        binding.tvHyphen.text = "-"

        Glide.with(this@DetailUserActivity)
            .load(user?.avatar_url)
            .placeholder(R.drawable.ic_avatar)
            .error(R.drawable.ic_avatar)
            .into(binding.ivAvatar)

        binding.fabFavorite.setOnClickListener {
            // TODO : Add to favorites
        }

        // TODO : ganti tab layout
        user?.username?.let { setUpTabLayout(it) }
    }

    private fun setUpTabLayout(username: String) {
        val detailPagerAdapter = DetailPagerAdapter(this, username)
        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

}