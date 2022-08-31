package com.e.setostory2.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.e.setostory2.data.UserRegReq
import com.e.setostory2.databinding.FragmentRegisterBinding

class RegisterFragment : Fragment() {

    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(RegisterViewModel::class.java)
        viewModel.getRegistResponse().observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(context, "User Creation Failed", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
            }

        }


        binding.btnRegist.setOnClickListener {
            if(binding.etPassField.text.toString().length >= 6 ) {
                registUser()
            }
            else{
                binding.btnRegist.error = "Password must have more than 6 character"
            }
        }

    }

    private fun registUser(){
        val userReg = UserRegReq(binding.etNameField.text.toString(),binding.etEmailField.text.toString(),binding.etPassField.text.toString())
        viewModel.register(userReg)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}