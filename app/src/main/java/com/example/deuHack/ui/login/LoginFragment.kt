package com.example.deuHack.ui.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.deuHack.R
import com.example.deuHack.data.domain.model.LoginModel
import com.example.deuHack.databinding.LoginFragmentBinding
import com.example.deuHack.ui.base.BindFragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : BindFragment<LoginFragmentBinding>(R.layout.login_fragment){
    private val loginViewModel : LoginViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.loginViewModel=loginViewModel

        loginViewModel.loginState.observe(viewLifecycleOwner, Observer {
            if(it)
                findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
        })

        binding.loginBtn.setOnClickListener{
            loginViewModel.login()
        }

        binding.fragmentContainer.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

}