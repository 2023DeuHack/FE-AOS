package com.example.deuHack.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.deuHack.R
import com.example.deuHack.databinding.RegisterFragmentBinding
import com.example.deuHack.databinding.SignUpBinding
import com.example.deuHack.ui.base.BindFragment

class RegisterFragment : BindFragment<RegisterFragmentBinding>(R.layout.register_fragment) {
    private val loginViewModel:LoginViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner=viewLifecycleOwner
        binding.loginViewModel=loginViewModel
        binding.loginBtn.setOnClickListener {
            loginViewModel.register()
        }

        loginViewModel.registerState.observe(viewLifecycleOwner, Observer {
            if(it){
                findNavController().navigate(R.id.action_registerFragment_to_loginFragment)
            }
        })
    }
}