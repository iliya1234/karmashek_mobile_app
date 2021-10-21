package com.example.financeapplication.screens.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.financeapplication.R
import com.example.financeapplication.databinding.FragmentLoginBinding
import com.example.financeapplication.databinding.FragmentRegistrationBinding
import com.example.financeapplication.models.UserRegistration
import com.example.financeapplication.screens.login.LoginViewModel
import com.example.financeapplication.utils.Constants
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RegistrationFragment: Fragment(R.layout.fragment_registration) {

    private var _binding: FragmentRegistrationBinding? = null
    private val binding get() = _binding!!
    private val viewModel: RegistrationViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistrationBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        singUp()
        singIn()
        errorServerMessage()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun singUp(){
        binding.registrationBtnSingUp.setOnClickListener {
            when{
                binding.registrationEtLogin.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_login,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtFirstName.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_fist_name,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtLastName.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_last_name,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtEmail.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_email,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtPassword.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_password,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtRepeatPassword.text.isEmpty() -> {
                    Toast.makeText(context,R.string.empty_repeat_password,Toast.LENGTH_SHORT).show()
                }
                binding.registrationEtPassword.text.toString()
                        != binding.registrationEtRepeatPassword.text.toString() ->{
                    Toast.makeText(context,R.string.password_mismatch,Toast.LENGTH_SHORT).show()
                }
                else -> {
                    val user = UserRegistration(
                        binding.registrationEtLogin.text.toString(),
                        binding.registrationEtFirstName.text.toString(),
                        binding.registrationEtLastName.text.toString(),
                        binding.registrationEtEmail.text.toString(),
                        binding.registrationEtRepeatPassword.text.toString()
                    )
                    viewModel.registerUser(user)
                    viewModel.userResponse.observe(this,{ response ->
                        if(response != null) {
                            Constants.TOKEN = response.token
                            Toast.makeText(context,Constants.TOKEN,Toast.LENGTH_SHORT).show()
                            findNavController().navigate(R.id.action_registrationFragment_to_purchaseFragment)
                        }
                    })
                }
            }
        }
    }

    private fun singIn(){
        binding.registrationBtnSingIn.setOnClickListener {
//            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }
    }
    private fun errorServerMessage(){
        viewModel.errorMessage.observe(this,{ response->
            if(response != null) {
                Toast.makeText(context,response.message,Toast.LENGTH_SHORT).show()
            }
        })
    }

}