package com.example.cocktail.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.cocktail.databinding.FragmentOverviewBinding
import com.example.cocktail.models.Drink
import java.util.*
import kotlin.collections.ArrayList


class OverviewFragment : Fragment() {

    private var _binding: FragmentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOverviewBinding.inflate(layoutInflater, container, false)

        // this variable gets data from Bundle
        val args = arguments
        val myBundle: Drink? = args?.getParcelable("drinkBundle")

        binding.mainImageView.load(myBundle?.strDrinkThumb)
        binding.titleTextview.text = myBundle?.strDrink
        // change language EN and DE
        binding.summeryTextView.text = if (Locale.getDefault().language.equals("de")) {
            myBundle?.strInstructionsDE
        } else {
            myBundle?.strInstructions
        }



        val listOfIngredients = ArrayList<String?>()
        listOfIngredients.add(myBundle?.strIngredient1)
        listOfIngredients.add(myBundle?.strIngredient2)
        listOfIngredients.add(myBundle?.strIngredient3)
        listOfIngredients.add(myBundle?.strIngredient4)
        listOfIngredients.add(myBundle?.strIngredient5)
        listOfIngredients.add(myBundle?.strIngredient6)


        binding.Ingredient1.text = listOfIngredients[0] ?: ""
        binding.Ingredient2.text = listOfIngredients[1] ?: ""
        binding.Ingredient3.text = listOfIngredients[2] ?: ""
        binding.Ingredient4.text = listOfIngredients[3] ?: ""
        binding.Ingredient5.text = listOfIngredients[4] ?: ""
        binding.Ingredient6.text = listOfIngredients[5] ?: ""

        val listOfMeasure = ArrayList<String?>()
        listOfMeasure.add(myBundle?.strMeasure1)
        listOfMeasure.add(myBundle?.strMeasure2)
        listOfMeasure.add(myBundle?.strMeasure3)
        listOfMeasure.add(myBundle?.strMeasure4)
        listOfMeasure.add(myBundle?.strMeasure5)
        listOfMeasure.add(myBundle?.strMeasure6)

        binding.textViewMeasure1.text = listOfMeasure[0] ?: ""
        binding.textViewMeasure2.text = listOfMeasure[1] ?: ""
        binding.textViewMeasure3.text = listOfMeasure[2] ?: ""
        binding.textViewMeasure4.text = listOfMeasure[3] ?: ""
        binding.textViewMeasure5.text = listOfMeasure[4] ?: ""
        binding.textViewMeasure6.text = listOfMeasure[5] ?: ""

        if (!binding.Ingredient1.text.isNullOrEmpty()){
            binding.textViewColon1.visibility = View.VISIBLE
        }
        if (!binding.Ingredient2.text.isNullOrEmpty()){
            binding.textViewColon2.visibility = View.VISIBLE
        }
        if (!binding.Ingredient3.text.isNullOrEmpty()){
            binding.textViewColon3.visibility = View.VISIBLE
        }
        if (!binding.Ingredient4.text.isNullOrEmpty()){
            binding.textViewColon4.visibility = View.VISIBLE
        }
        if (!binding.Ingredient5.text.isNullOrEmpty()){
            binding.textViewColon5.visibility = View.VISIBLE
        }
        if (!binding.Ingredient6.text.isNullOrEmpty()){
            binding.textViewColon6.visibility = View.VISIBLE
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}