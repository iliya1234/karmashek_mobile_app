package com.example.financeapplication.screens.login

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
import com.example.financeapplication.models.UserAuthRequest
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initUser()
        registerUser()
        errorMessageServer()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initUser() {
        binding.loginBtnSingIn.setOnClickListener {
            when {
                binding.loginEtUsername.text.isEmpty() -> {
                    Toast.makeText(context, R.string.empty_login, Toast.LENGTH_SHORT).show()
                }
                binding.loginEtPassword.text.isEmpty() -> {
                    Toast.makeText(context, R.string.empty_password, Toast.LENGTH_SHORT).show()
                }
                else -> {
                    viewModel.initUser(
                        UserAuthRequest(
                            binding.loginEtUsername.text.toString(),
                            binding.loginEtPassword.text.toString()
                        )
                    )

                }
            }
        }
        viewModel.userResponse.observe(this, { response ->
            if (response != null) {
                Toast.makeText(context, response.token, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_loginFragment_to_purchaseFragment);
            }
        })
    }

    private fun registerUser() {
        binding.loginBtnSingUp.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
        }
    }

    private fun errorMessageServer() {
        viewModel.errorMessage.observe(this, { response ->
            if (response != null) {
                Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}