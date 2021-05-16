package com.testapp.testapplication.registration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.testapp.testapplication.R
import com.testapp.testapplication.ToastAdapter
import com.testapp.testapplication.databinding.FragmentRegistrationScreenBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RegistrationFragment : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) =
        DataBindingUtil.inflate<FragmentRegistrationScreenBinding>(inflater,
            R.layout.fragment_registration_screen, container, false).run {

            val viewModel = ViewModelProvider(this@RegistrationFragment, viewModelFactory).get(RegistrationViewModel::class.java)

            this.viewModel = viewModel
            adapter =
                ToastAdapter(requireActivity().applicationContext)

            root
        }

    companion object {
        @JvmStatic
        fun newInstance() =
            RegistrationFragment()
    }
}