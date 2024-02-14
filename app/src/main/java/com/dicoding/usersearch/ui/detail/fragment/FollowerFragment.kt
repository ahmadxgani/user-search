package com.dicoding.usersearch.ui.detail.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.usersearch.data.response.FollowResponseItem
import com.dicoding.usersearch.databinding.FragmentFollowerBinding
import com.dicoding.usersearch.ui.detail.FollowAdapter

class FollowerFragment(private val viewModel: FollowerViewModel) : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireActivity())
        binding.rvFollower.layoutManager = layoutManager

        viewModel.isLoadingFollower.observe(requireActivity()) {
            showLoadingFollower(it)
        }

        viewModel.listFollower.observe(requireActivity()) {
            setFollower(it)
        }
    }

    private fun showLoadingFollower(isLoading: Boolean) {

        if (isLoading) {
            binding.pbFollower.visibility = View.VISIBLE
            binding.rvFollower.visibility = View.GONE
        } else {
            binding.pbFollower.visibility = View.GONE
            binding.rvFollower.visibility = View.VISIBLE
        }
    }

    private fun setFollower(users: List<FollowResponseItem>) {
        val adapter = FollowAdapter(requireActivity())
        adapter.submitList(users)

        binding.rvFollower.adapter = adapter
    }
}