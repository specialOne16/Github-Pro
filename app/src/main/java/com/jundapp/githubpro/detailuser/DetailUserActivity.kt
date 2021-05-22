package com.jundapp.githubpro.detailuser

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.jundapp.githubpro.R
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.databinding.ActivityDetailBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DetailUserActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_USER = "extra_user"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.follower,
            R.string.following
        )
    }

    private val detailUserViewModel: DetailUserViewModel by viewModel()
    private lateinit var binding: ActivityDetailBinding
    private var favorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.title = ""

        val user = intent.getParcelableExtra<User>(EXTRA_USER)

        user?.username?.let {
            binding.fabFavorite.setOnClickListener {
                detailUserViewModel.setFavorite(user, !favorite)
            }

            setUpTabLayout(it)

            detailUserViewModel.getIsFavorite(user).observe(this, { isFavorite ->
                if (isFavorite != null) {
                    favorite = isFavorite
                    binding.fabFavorite.setImageDrawable(
                        ContextCompat.getDrawable(
                            this@DetailUserActivity,
                            if (isFavorite) R.drawable.ic_un_favorite else R.drawable.ic_favorite
                        )
                    )
                }
            })

            detailUserViewModel.getUserDetail(it).observe(this, { user ->
                if (user != null) {
                    when (user) {
                        is Resource.Loading -> binding.progressCircular.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressCircular.visibility = View.GONE

                            binding.tvName.text =
                                user.data?.name ?: resources.getString(R.string.name)
                            binding.tvUName.text =
                                user.data?.username ?: resources.getString(R.string.uname)
                            binding.tvCompany.text =
                                user.data?.company ?: resources.getString(R.string.company)
                            binding.tvLocation.text =
                                user.data?.location ?: resources.getString(R.string.location)
                            binding.tvHyphen.text = "-"

                            Glide.with(this@DetailUserActivity)
                                .load(user.data?.avatar_url)
                                .placeholder(R.drawable.ic_avatar)
                                .error(R.drawable.ic_avatar)
                                .into(binding.ivAvatar)
                        }
                        is Resource.Error -> {
                            binding.progressCircular.visibility = View.GONE
                            Toast.makeText(this, resources.getString(R.string.network_error), Toast.LENGTH_SHORT).show()
                            finish()
                        }
                    }
                }
            })
        }

    }

    private fun setUpTabLayout(username: String) {
        val detailPagerAdapter = DetailUserPagerAdapter(this, username)
        binding.viewPager.adapter = detailPagerAdapter
        TabLayoutMediator(binding.tabs, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f
    }

}