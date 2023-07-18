package com.example.ubayalibrary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ubayalibrary.R
import com.example.ubayalibrary.model.User
import com.example.ubayalibrary.viewmodel.UserViewModel

class RegisterFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val txtLogin = view.findViewById<TextView>(R.id.txtLogin)
        txtLogin.setOnClickListener {
            val action = RegisterFragmentDirections.actionToLogin()
            Navigation.findNavController(it).navigate(action)
        }

        val btnRegister = view.findViewById<Button>(R.id.btnRegister)
        val txtNama = view.findViewById<TextView>(R.id.txtNama)
        val txtNRPRegister = view.findViewById<TextView>(R.id.txtNRPRegister)
        val txtPasswordRegister = view.findViewById<TextView>(R.id.txtPasswordRegister)
        val txtPhotoUrlUser = view.findViewById<TextView>(R.id.txtPhotoUrlUser)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btnRegister.setOnClickListener {
            val nama = txtNama.text.toString()
            val nrp = txtNRPRegister.text.toString()
            val password = txtPasswordRegister.text.toString()
            val photoUrl = txtPhotoUrlUser.text.toString()

            val user = User(nrp, nama, password, photoUrl)
            userViewModel.registerUser(user)
        }

        userViewModel.userLoadErrorLD.observe(viewLifecycleOwner) { userLoadErrorLD ->
            // User registration failed
            if (userLoadErrorLD) {
                Toast.makeText(requireContext(), "Registration Failed", Toast.LENGTH_SHORT).show()
            } else {
                // User registration succeeded
                Toast.makeText(requireContext(), "Registration Successful", Toast.LENGTH_SHORT).show()
                val action = RegisterFragmentDirections.actionToLogin()
                Navigation.findNavController(requireView()).navigate(action)
            }
        }

    }
}