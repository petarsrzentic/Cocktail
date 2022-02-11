package com.example.cocktail.ui.fragments.cocktail.bottomsheet

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.asLiveData
import com.example.cocktail.databinding.FragmentCocktailBottomSheetBinding
import com.example.cocktail.util.Constants.Companion.DEFAULT_COCKTAIL
import com.example.cocktail.viewmodels.CocktailViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import java.util.*

class CocktailBottomSheet : BottomSheetDialogFragment() {

    private var _binding: FragmentCocktailBottomSheetBinding? = null
    private val binding get() = _binding!!
    private lateinit var cocktailViewModel: CocktailViewModel
    private var cocktailTypeChip = DEFAULT_COCKTAIL
    private var cocktailTypeChipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cocktailViewModel = ViewModelProvider(requireActivity())[CocktailViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCocktailBottomSheetBinding.inflate(layoutInflater, container, false)

        cocktailViewModel.readCocktailType.asLiveData().observe(viewLifecycleOwner) { value ->
            cocktailTypeChip = value.selectedCocktail
            updateChip(value.selectedCocktailId, binding.chipGroup)
        }

        binding.chipGroup.setOnCheckedChangeListener { group, checkedId ->
            val chip = group.findViewById<Chip>(checkedId)
            val selectedCocktailType = chip.text.toString().lowercase(Locale.getDefault())
            cocktailTypeChip = selectedCocktailType
            cocktailTypeChipId = checkedId
        }

        binding.applyButton.setOnClickListener {
            cocktailViewModel.saveCocktailType(
                cocktailTypeChip,
                cocktailTypeChipId
            )
// 
        }

        return binding.root
    }

    private fun updateChip(chipId: Int, chipGroup: ChipGroup) {
        if (chipId != 0) {
            try {
                chipGroup.findViewById<Chip>(chipId).isChecked = true
            } catch (e: Exception) {
                Log.d("CocktailBottomSheet", e.message.toString())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}