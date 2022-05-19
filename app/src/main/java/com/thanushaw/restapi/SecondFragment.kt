package com.thanushaw.restapi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.thanushaw.restapi.api.Post
import com.thanushaw.restapi.databinding.FragmentSecondBinding

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey"){_,bundle->
            val postID = bundle.getInt("postId")
            val userID = bundle.getInt("userId")
            val title = bundle.getString("title")
            val body = bundle.getString("body")

            (activity as AppCompatActivity).supportActionBar?.title = "Post ${postID}"

            binding.idContent.text = postID.toString()
            binding.userIdContent.text = userID.toString()
            binding.titleContent.text = title
            binding.bodyContent.text = body
            setFragmentResult("requestKey_2", bundleOf("postid" to postID))
        }



        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_ThirdFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}