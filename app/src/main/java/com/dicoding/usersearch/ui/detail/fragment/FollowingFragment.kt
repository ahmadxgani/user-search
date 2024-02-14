package com.dicoding.usersearch.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usersearch.data.response.UserItem
import com.dicoding.usersearch.databinding.FragmentFollowingBinding
import com.dicoding.usersearch.ui.UserAdapter

class FollowingFragment(private val viewModel: FollowingViewModel) : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollowing.layoutManager = layoutManager

        viewModel.listFollowing.observe(requireActivity()) {
            setFollowing(it)
        }

        viewModel.isLoadingFollowing.observe(requireActivity()) {
            showLoadingFollowing(it)
        }
    }

    private fun showLoadingFollowing(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                pbFollowing.visibility = View.VISIBLE
                rvFollowing.visibility = View.GONE
            } else {
                pbFollowing.visibility = View.GONE
                rvFollowing.visibility = View.VISIBLE
            }
        }
    }

    private fun setFollowing(users: List<UserItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)

        binding.rvFollowing.adapter = adapter
    }
}