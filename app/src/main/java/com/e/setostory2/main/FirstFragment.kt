package com.e.setostory2.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.e.setostory2.R
import com.e.setostory2.databinding.FragmentFirstBinding
import com.e.setostory2.login.LoginActivity
import kotlinx.coroutines.flow.collectLatest

class FirstFragment : Fragment() {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var rvAdapter : MainAdapter
    private lateinit var viewModel : MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        binding.mainRecyclerView.setHasFixedSize(true)
        recyclerViewInit()
        viewModinit("Bearer $token")
    }




    private fun viewModinit(userToken:String){
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        lifecycleScope.launchWhenCreated {
            viewModel.getStoriesList(userToken).collectLatest {
                rvAdapter.submitData(it)
            }
        }
    }


    private fun recyclerViewInit(){
        binding.mainRecyclerView.apply {
            layoutManager = GridLayoutManager(context,2)
            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            addItemDecoration(decoration)
            rvAdapter = MainAdapter(this@FirstFragment)
            adapter = rvAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}