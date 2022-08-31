package com.e.setostory2.login

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.edit
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.e.setostory2.R
import com.e.setostory2.data.UserLogReq
import com.e.setostory2.databinding.FragmentLoginBinding
import com.e.setostory2.main.MainActivity

class LoginFragment : Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var viewModel:LoginViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
        loadPref()

        binding.loginBtn.setOnClickListener{
            login()
        }
        binding.loginGotoregisTxt.setOnClickListener{
            findNavController().navigate(R.id.action_FragmentLogin_to_FragmentRegister)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun loadPref() {
        sharedPreferences = requireActivity().getSharedPreferences("SHARED_PREF", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)
        viewModel.getLoginResponse().observe(viewLifecycleOwner) {
            if (it == null) {
                Toast.makeText(context, "Wrong Email or Password", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(
                    context,
                    "Welcome ${it.logResult?.name}",
                    Toast.LENGTH_LONG
                ).show()
                sharedPreferences.edit{
                    putString("userid", it.logResult?.id)
                    putString("userid", it.logResult?.id)
                    putString("token", it.logResult?.token)
                }
                val intent = Intent(context, MainActivity::class.java)
                this.startActivity(intent)
                activity?.finish()
            }
        }
        if (token == null) {
            Toast.makeText(context, "Please fill the email and password", Toast.LENGTH_LONG).show()
        } else {
            val intent = Intent(context, MainActivity::class.java)
            this.startActivity(intent)
            activity?.finish()
        }
    }


    private fun login(){
        val loginReq = UserLogReq(binding.loginEmailEdttext.text.toString(),binding.loginPassEdttxt.text.toString())
        viewModel.login(loginReq)
    }
}