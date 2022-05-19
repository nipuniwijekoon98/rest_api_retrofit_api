package com.thanushaw.restapi

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanushaw.restapi.adapters.RecyclerViewAdapter
import com.thanushaw.restapi.api.Post
import com.thanushaw.restapi.api.RetrofitAPI
import com.thanushaw.restapi.databinding.FragmentFirstBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
// getting the recyclerview by its id
        val recyclerview = binding.recyclerview

        // this creates a vertical layout Manager
        recyclerview.layoutManager = LinearLayoutManager(this.context)

        val apiInterface = RetrofitAPI.create().getPosts()

        //apiInterface.enqueue( Callback<List<Movie>>())
        apiInterface.enqueue( object : Callback<List<Post>> {
            @SuppressLint("CheckResult")
            override fun onResponse(call: Call<List<Post>>?, response: Response<List<Post>>?) {

                if(response?.body() != null){
                    recyclerview.adapter = RecyclerViewAdapter(response.body()!!)

                    (recyclerview.adapter as RecyclerViewAdapter).clickEvent.subscribe {
                        val clickedPostId = it.toInt()
                        var clickedPost:Post? = null
                        response.body()!!.forEach {
                            if(clickedPostId == it.id){
                                setFragmentResult(
                                    "requestKey",
                                    bundleOf(
                                        "postId" to it.id,
                                        "userId" to it.userId,
                                        "title" to it.title,
                                        "body" to it.body
                                    ))
                                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
                            }
                        }
                    }
                }

            }

            override fun onFailure(call: Call<List<Post>>?, t: Throwable?) {
                Log.i("FirstFrgment", "onFailure: retrieval failed")
            }
        })



    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}