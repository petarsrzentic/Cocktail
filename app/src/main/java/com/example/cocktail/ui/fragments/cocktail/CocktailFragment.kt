package com.example.cocktail.ui.fragments.cocktail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.R
import com.example.cocktail.viewmodels.MainViewModel
import com.example.cocktail.adapters.CocktailAdapter
import com.example.cocktail.databinding.FragmentCocktailBinding
import com.example.cocktail.util.NetworkResult
import com.example.cocktail.util.observeOnce
import com.example.cocktail.viewmodels.CocktailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailFragment : Fragment() {

    private val args by navArgs<CocktailFragmentArgs>()

    private var _binding : FragmentCocktailBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel : MainViewModel
    private lateinit var cocktailViewModel : CocktailViewModel
    private val mAdapter by lazy { CocktailAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity())[MainViewModel::class.java]
        cocktailViewModel = ViewModelProvider(requireActivity())[CocktailViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCocktailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecycleView()
        readDatabase()

        binding.cocktailFab.setOnClickListener {
            findNavController().navigate(R.id.action_cocktailFragment_to_cocktailBottomSheet)
        }

        return binding.root
    }

    private fun setupRecycleView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            // calling observeOnce from MyExtensionFunction
            mainViewModel.readCocktails.observeOnce(viewLifecycleOwner, {database ->
                if (database.isNotEmpty() && !args.backFromBottomSheet) {
                    mAdapter.setData(database[0].cocktails)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            })
        }
    }

    private fun requestApiData() {
        mainViewModel.getCocktails(cocktailViewModel.applyQueries())
        mainViewModel.cocktailResponse.observe(viewLifecycleOwner, {response ->
            when(response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmerEffect()
                    loadDataFromCash()
                    Toast.makeText(
                        requireContext(),
                        response.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }

        })
    }

    private fun loadDataFromCash() {
        lifecycleScope.launch {
            mainViewModel.readCocktails.observe(viewLifecycleOwner, {database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].cocktails)
                }
            })
        }
    }

    private fun showShimmerEffect() {
        binding.recyclerView.showShimmer()
    }

    private fun hideShimmerEffect() {
        binding.recyclerView.hideShimmer()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}