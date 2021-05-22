package com.jundapp.githubpro.core.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.jundapp.githubpro.activity.detailuser.DetailUserActivity
import com.jundapp.githubpro.core.data.Resource
import com.jundapp.githubpro.core.domain.model.User
import com.jundapp.githubpro.databinding.FragmentUserListBinding
import org.koin.android.viewmodel.ext.android.viewModel

const val ARG_URL = "param_url"
const val ARG_UNAME = "param_username"

class UserListFragment : Fragment() {

    private var type: String? = null
    private var username: String? = null

    private val userListViewModel: UserListViewModel by viewModel()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!

    private lateinit var userListAdapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserList()
    }

    private fun getUserList() {
        if (activity != null) {
            userListAdapter = UserListAdapter(requireActivity())
            binding.listUser.adapter = userListAdapter
            binding.listUser.layoutManager = LinearLayoutManager(context)

            userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: User) {
                    val i = Intent(activity, DetailUserActivity::class.java)
                    i.putExtra(DetailUserActivity.EXTRA_USER, data)
                    context?.startActivity(i)
                }
            })

            observeLiveData()
        }
    }

    private fun observeLiveData() {
        username?.let {
            when (type) {
                TYPE_FOLLOWER -> userListViewModel.getFollower(it)
                    .observe(viewLifecycleOwner, { users -> updateUi(users) })
                TYPE_FOLLOWING -> userListViewModel.getFollowing(it)
                    .observe(viewLifecycleOwner, { users -> updateUi(users) })
                TYPE_SEARCH -> userListViewModel.searchUser(it)
                    .observe(viewLifecycleOwner, { users -> updateUi(users) })
                else -> userListViewModel.users.observe(
                    viewLifecycleOwner,
                    { users -> updateUi(users) })
            }
        } ?:
        when (type) {
            TYPE_FAVORITE -> userListViewModel.getFavorites()
                .observe(viewLifecycleOwner, { users -> updateUi(users) })
            else -> userListViewModel.users.observe(
                viewLifecycleOwner,
                { users -> updateUi(users) })
        }
    }

    private fun updateUi(users: Resource<List<User>>) {
        when (users) {
            is Resource.Loading -> binding.progressCircular.visibility = View.VISIBLE
            is Resource.Success -> {
                binding.progressCircular.visibility = View.GONE
                binding.noData.visibility = if (users.data!!.isEmpty()) View.VISIBLE else View.INVISIBLE
                userListAdapter.data = users.data
                userListAdapter.notifyDataSetChanged()
            }
            is Resource.Error -> {
                binding.progressCircular.visibility = View.GONE
                binding.noData.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val TYPE_ALL = "type_all_user"
        const val TYPE_FAVORITE = "type_all_user"
        const val TYPE_SEARCH = "type_search_user"
        const val TYPE_FOLLOWING = "type_following"
        const val TYPE_FOLLOWER = "type_follower"

        @JvmStatic
        fun newInstance(url: String = TYPE_ALL, username: String? = null) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                    putString(ARG_UNAME, username)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            type = it.getString(ARG_URL)
            username = it.getString(ARG_UNAME)
        }
    }

}