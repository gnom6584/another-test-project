package com.testapp.testapplication.authorization

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.testapp.testapplication.R
import com.testapp.testapplication.ToastAdapter
import com.testapp.testapplication.databinding.FragmentAuthorizationScreenBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class AuthorizationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        DataBindingUtil.inflate<FragmentAuthorizationScreenBinding>(inflater,
            R.layout.fragment_authorization_screen, container, false).run {

            val viewModel = ViewModelProvider(this@AuthorizationFragment, viewModelFactory).get(AuthorizationViewModel::class.java)

            this.viewModel = viewModel

            adapter =
                ToastAdapter(requireActivity().applicationContext)
            root
        }

    companion object {
        @JvmStatic
        fun newInstance() =
            AuthorizationFragment()
    }
}