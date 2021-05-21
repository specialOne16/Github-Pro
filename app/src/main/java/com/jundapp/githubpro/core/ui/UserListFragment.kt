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

class UserListFragment : Fragment() {
    // TODO : Handle different URL
    private var url: String? = null

    private val userListViewModel: UserListViewModel by viewModel()
    private var _binding: FragmentUserListBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        url?.let { getUserList(it) }
    }

    private fun getUserList(url: String) {
        if (activity != null) {
            val userListAdapter = UserListAdapter(activity!!)
            binding.listUser.adapter = userListAdapter
            binding.listUser.layoutManager = LinearLayoutManager(context)

            userListAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback{
                override fun onItemClicked(data: User) {
                    val i = Intent(activity, DetailUserActivity::class.java)
                    i.putExtra(DetailUserActivity.EXTRA_USER, data)
                    context?.startActivity(i)
                }
            })

            userListViewModel.users.observe(viewLifecycleOwner, { users ->
                if (users != null) {
                    when(users){
                        is Resource.Loading -> binding.progressCircular.visibility = View.VISIBLE
                        is Resource.Success -> {
                            binding.progressCircular.visibility = View.GONE
                            userListAdapter.data = users.data!!
                            userListAdapter.notifyDataSetChanged()
                        }
                        is Resource.Error -> {
                            binding.progressCircular.visibility = View.GONE
                            // TODO : Show Error
                        }
                    }
                }
            })
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(url: String) =
            UserListFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            url = it.getString(ARG_URL)
        }
    }

}