package com.example.deuHack.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment

abstract class BindFragment<T:ViewDataBinding>(@LayoutRes private val layoutRes: Int) : Fragment() {
    var _binding : T ?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding= DataBindingUtil.inflate(inflater,layoutRes,container,false)
        return binding.root
    }

    override fun onDestroyView() {
        _binding=null
        super.onDestroyView()
    }
}