package com.example.cocktail.ui.fragments.filter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.animation.doOnEnd
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.adapters.CocktailAdapter
import com.example.cocktail.databinding.FragmentFilterBinding
import com.example.cocktail.util.NetworkListener
import com.example.cocktail.util.NetworkResult
import com.example.cocktail.viewmodels.CocktailViewModel
import com.example.cocktail.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    private lateinit var networkListener: NetworkListener

    private lateinit var mainViewModel: MainViewModel
    private lateinit var cocktailViewModel: CocktailViewModel
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
        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)


        binding.lifecycleOwner = this
        binding.filterMainViewModel = mainViewModel

        cocktailViewModel.readBackOnline.observe(viewLifecycleOwner) {
            cocktailViewModel.backOnline = it
        }

        // using launchWhenStarted for fixing bug when navigate from one fragment to another and switch internet connection app crashes.
        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetworkAvailability(requireContext())
                .collect { status ->
                    cocktailViewModel.networkStatus = status
                    cocktailViewModel.showNetworkStatus()
//                    readDatabase()
                }
        }

        binding.buttonAlcohol.setOnClickListener {
            Log.i("FilterFragment", "buttonAlcohol is called!")
            val animator = ObjectAnimator.ofFloat(binding.buttonAlcohol, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonAlcohol)
            animator.start()
            animator.doOnEnd {
                showAndHideView()
                setupRecycleView()
                requestAlcoholData("alcoholic")
            }
        }

        binding.buttonNonAlcoholic.setOnClickListener {
            val animator =
                ObjectAnimator.ofFloat(binding.buttonNonAlcoholic, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonNonAlcoholic)
            animator.start()
            animator.doOnEnd {
                showAndHideView()
                setupRecycleView()
                requestAlcoholData("non_alcoholic")
            }
        }

        binding.buttonPopular.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonPopular, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonPopular)
            animator.start()
            animator.doOnEnd {
                showAndHideView()
                setupRecycleView()
                requestPopularCocktails()
            }
        }

        binding.buttonLatest.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonLatest, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonLatest)
            animator.start()
            animator.doOnEnd {
                showAndHideView()
                setupRecycleView()
                requestLatestCocktails()
            }
        }
        return binding.root
    }

    private fun requestAlcoholData(filterQuery: String) {
        showShimmerEffect()
        mainViewModel.filterCocktailByAlcohol(cocktailViewModel.filterByAlcQueries(filterQuery))
        mainViewModel.searchCocktailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val searchResponse = response.data
                    searchResponse?.let { mAdapter.setData(it) }
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

        }
    }

    private fun requestPopularCocktails() {
        showShimmerEffect()
        mainViewModel.getPopularCocktail(cocktailViewModel.applyPopularCocktails())
        mainViewModel.searchCocktailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val searchResponse = response.data
                    searchResponse?.let { mAdapter.setData(it) }
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
        }
    }

    private fun requestLatestCocktails() {
        showShimmerEffect()
        mainViewModel.getLatestCocktail(cocktailViewModel.applyLatestCocktails())
        mainViewModel.searchCocktailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmerEffect()
                    val searchResponse = response.data
                    searchResponse?.let { mAdapter.setData(it) }
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
        }
    }

    private fun loadDataFromCash() {
        lifecycleScope.launch {
            mainViewModel.readCocktails.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].cocktails)
                }
            }
        }
    }

    private fun showAndHideView() {
        Log.i("FilterFragment", "animator doOnEnd is called!")
        binding.buttonAlcohol.visibility = View.GONE
        binding.buttonNonAlcoholic.visibility = View.GONE
        binding.buttonLatest.visibility = View.GONE
        binding.buttonPopular.visibility = View.GONE
        binding.filterRecycleView.visibility = View.VISIBLE
        binding.shimmerFrameLayout.visibility = View.GONE
    }

    private fun setupRecycleView() {
        Log.i("FilterFragment", "recyclerView is called!")
        binding.filterRecycleView.adapter = mAdapter
        binding.filterRecycleView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.filterRecycleView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.filterRecycleView.visibility = View.VISIBLE
    }


    private fun ObjectAnimator.disableViewDuringAnimation(view: View) {

        // This extension method listens for start/end events on an animation and disables
        // the given view for the entirety of that animation.

        addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                view.isEnabled = false
            }

            override fun onAnimationEnd(animation: Animator?) {
                view.isEnabled = true
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}