package com.thanushaw.restapi

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.thanushaw.restapi.adapters.RecyclerViewAdapter2
import com.thanushaw.restapi.api.Comment
import com.thanushaw.restapi.api.Post
import com.thanushaw.restapi.api.RetrofitAPI
import com.thanushaw.restapi.databinding.FragmentThirdBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class ThirdFragment : Fragment() {

    private var _binding: FragmentThirdBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFragmentResultListener("requestKey_2"){_,bundle->
            val postID = bundle.getInt("postid")
            (activity as AppCompatActivity).supportActionBar?.title = "Comments of post $postID"
            // getting the recyclerview by its id
            val recyclerview = binding.recyclerview

            val data = ArrayList<Comment>()

            // this creates a vertical layout Manager
            recyclerview.layoutManager = LinearLayoutManager(this.context)
            val apiInterface = RetrofitAPI.create().getComments()

            apiInterface.enqueue( object : Callback<List<Comment>> {
                override fun onResponse(call: Call<List<Comment>>?, response: Response<List<Comment>>?) {

                    if (response != null) {
                        response.body()!!.forEach {
                            if(it.postId == postID){
                                data.add(it)
                            }
                        }
                        recyclerview.adapter = RecyclerViewAdapter2(data)
                    }

                }

                override fun onFailure(call: Call<List<Comment>>?, t: Throwable?) {
                    Log.i("ThirdFrgment", "onFailure: retrieval failed")
                }
            })
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}