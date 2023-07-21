package com.example.ubayalibrary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.FragmentProfileBinding
import com.example.ubayalibrary.databinding.FragmentProfileEditBinding
import com.example.ubayalibrary.model.User
import com.example.ubayalibrary.view.ProfileEditInterface
import com.example.ubayalibrary.viewmodel.UserViewModel

class ProfileEditFragment : Fragment(),  ProfileEditInterface{
    private lateinit var profileViewModel: UserViewModel
    private lateinit var dataBinding:FragmentProfileEditBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dataBinding = DataBindingUtil.inflate<FragmentProfileEditBinding>(inflater, R.layout.fragment_profile_edit, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        val nrp = ProfileEditFragmentArgs.fromBundle(requireArguments()).nrp
        profileViewModel.showProfile(nrp)

        val btnSubmit= view.findViewById<Button>(R.id.btnSubmit)
        btnSubmit.setOnClickListener{
            val txtNama = view.findViewById<TextView>(R.id.txtNamaEdit)
            val txtPass = view.findViewById<TextView>(R.id.txtPasswordEdit)
            val txtUrl = view.findViewById<TextView>(R.id.txtPhotoUrlUserEdit)
            profileViewModel.update(txtNama.text.toString(), txtPass.text.toString(), txtUrl.text.toString(), nrp )

            Toast.makeText(view.context, "Succesfully update your profile", Toast.LENGTH_SHORT).show()
            Navigation.findNavController(it).popBackStack()
        }

        observeViewModel()
    }

    fun observeViewModel() {
        profileViewModel.usersLD.observe(viewLifecycleOwner, Observer {
            dataBinding.user = it
        })
    }

    override fun onProfileSaveClick(v: View, user: User) {
        val nrp = user.nrp ?: ""
        val nama = user.nama ?: ""
        val photoUrl = user.photoUrl ?: ""
        val password = user.password ?: ""
        profileViewModel.update(nama, password, photoUrl, nrp)

        Toast.makeText(view?.context, "Succesfully update your profile", Toast.LENGTH_SHORT).show()
        Navigation.findNavController(v).popBackStack()
    }
}