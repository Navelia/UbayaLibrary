package com.example.ubayalibrary.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.FragmentProfileBinding
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.view.ProfileInterface
import com.example.ubayalibrary.viewmodel.UserViewModel

class ProfileFragment : Fragment(), ProfileInterface {
    private lateinit var viewModel: UserViewModel
    private lateinit var dataBinding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate<FragmentProfileBinding>(inflater, R.layout.fragment_profile, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        Menghilangkan Back supaya tidak ke login
        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val btnLogout = view.findViewById<Button>(R.id.btnlogOut)
        btnLogout.setOnClickListener{
            val sharedPref = requireActivity().getSharedPreferences("Preferences", Context.MODE_PRIVATE)
            val editor = sharedPref.edit()
            editor.clear()
            editor.apply()

            val action = ProfileFragmentDirections.actionProfileToLogin()
            Navigation.findNavController(it).navigate(action)
        }

        val sharedPref = this.activity?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val nrp = sharedPref?.getString("nrp","") as String

        viewModel = ViewModelProvider(this)[UserViewModel::class.java]
        viewModel.showProfile(nrp)
        observeViewModel()
        dataBinding.listener = this
    }

    fun observeViewModel(){
        viewModel.usersLD.observe(viewLifecycleOwner, Observer{
            dataBinding.user = it
        })
    }

    override fun onProfileEditClick(v: View) {
        val sharedPref = this.activity?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val nrp = sharedPref?.getString("nrp","") as String

        val action = ProfileFragmentDirections.actionToEditProfile(nrp)
        Navigation.findNavController(v).navigate(action)
    }
}