package com.example.cocktail.ui.fragments.overview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import coil.load
import com.example.cocktail.databinding.FragmentOverviewBinding
import com.example.cocktail.models.Drink


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
        binding.summeryTextView.text = myBundle?.strInstructions

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


        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}