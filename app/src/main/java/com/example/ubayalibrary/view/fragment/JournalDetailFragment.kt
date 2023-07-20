package com.example.ubayalibrary.view.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.ubayalibrary.R
import com.example.ubayalibrary.databinding.FragmentJournalDetailBinding
import com.example.ubayalibrary.viewmodel.JournalDetailViewModel

class JournalDetailFragment : Fragment() {
    private lateinit var detailViewModel : JournalDetailViewModel
    private lateinit var dataBinding : FragmentJournalDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_journal_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        detailViewModel = ViewModelProvider(this).get(JournalDetailViewModel::class.java)

        val id = JournalDetailFragmentArgs.fromBundle(requireArguments()).id
        detailViewModel.fetch(id)
    }

}