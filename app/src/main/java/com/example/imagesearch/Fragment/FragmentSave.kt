package com.example.imagesearch.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.imagesearch.MainActivity
import com.example.imagesearch.databinding.FragmentSaveBinding
import com.example.imagesearch.Data.Search

class FragmentSave : Fragment() {
    private var _binding: FragmentSaveBinding? = null
    private val binding get() = _binding!!
    private var savedList : MutableList<Search> = mutableListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSaveBinding.inflate(inflater, container,false)
        val view = binding.root

        savedList = (activity as MainActivity).saveList

        binding.saveRecyclerview.adapter = SaveAdapter(requireContext(), savedList).apply {
            Data = savedList.toMutableList()
            Log.d("Data", "$Data")
            Log.d("SaveList", "$savedList")
        }

        binding.saveRecyclerview.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
        }
        return view
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}