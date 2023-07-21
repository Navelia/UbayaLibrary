package com.example.ubayalibrary.view.fragment

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.example.ubayalibrary.R
import com.example.ubayalibrary.view.MainActivity
import com.example.ubayalibrary.viewmodel.UserViewModel


class LoginFragment : Fragment() {
    private lateinit var userViewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)

        val sharedPref = this.activity?.getSharedPreferences("Preferences", Context.MODE_PRIVATE)
        val nrp = sharedPref?.getString("nrp","")
        if (nrp != ""){
            val action = LoginFragmentDirections.actionToBookList()
            Navigation.findNavController(view).navigate(action)
        }

        val txtRegister = view.findViewById<TextView>(R.id.txtRegister)
        txtRegister.setOnClickListener {
            val action = LoginFragmentDirections.actionToRegister()
            Navigation.findNavController(it).navigate(action)
        }

        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val txtNrpLogin = view.findViewById<TextView>(R.id.txtNRPLogin)
        val txtPassLogin = view.findViewById<TextView>(R.id.txtPassLogin)

        userViewModel = ViewModelProvider(this)[UserViewModel::class.java]

        btnLogin.setOnClickListener {
            val nrp = txtNrpLogin.text.toString()
            val password = txtPassLogin.text.toString()

            userViewModel.selectUser(nrp, password)

            // Observe the LiveData to get the user data
            userViewModel.usersLD.observe(viewLifecycleOwner) { user ->
                if (user != null) {
                    // Retrieve the necessary data from the user object
                    val userId = user.uuid.toInt()
                    val nrp = user.nrp ?: ""
                    val nama = user.nama ?: ""
                    val photoUrl = user.photoUrl ?: ""

                    // Use the onLoginSuccess function in the MainActivity
                    val mainActivity = requireActivity() as MainActivity
                    mainActivity.onLoginSuccess(userId, nrp, nama, photoUrl)

//                    Supaya memunculkan draawer navigation
                    (requireActivity() as MainActivity).setDrawerLockMode(true)

                    val action = LoginFragmentDirections.actionToBookList()
                    Navigation.findNavController(it).navigate(action)
                } else {
                    // Handle invalid login credentials
                    Toast.makeText(requireContext(), "Invalid login credentials", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}