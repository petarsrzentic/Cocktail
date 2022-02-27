package com.example.cocktail.ui.fragments.cocktail

import android.os.Bundle
import android.view.*
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.cocktail.R
import com.example.cocktail.adapters.CocktailAdapter
import com.example.cocktail.databinding.FragmentCocktailBinding
import com.example.cocktail.util.NetworkListener
import com.example.cocktail.util.NetworkResult
import com.example.cocktail.util.observeOnce
import com.example.cocktail.viewmodels.CocktailViewModel
import com.example.cocktail.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CocktailFragment : Fragment(), SearchView.OnQueryTextListener {

    private var _binding: FragmentCocktailBinding? = null
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
        _binding = FragmentCocktailBinding.inflate(layoutInflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setupRecycleView()

        setHasOptionsMenu(true)

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
                    readDatabase()
                }
        }

        return binding.root
    }

    private fun setupRecycleView() {
        binding.recyclerView.adapter = mAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.search_menu, menu)

        val search = menu.findItem(R.id.search_menu)
        val searchView = search.actionView as? SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
    }

    override fun onQueryTextSubmit(p0: String?): Boolean {
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    private fun readDatabase() {
        lifecycleScope.launch {
            // calling observeOnce from MyExtensionFunction
            mainViewModel.readCocktails.observeOnce(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].cocktails)
                    hideShimmerEffect()
                } else {
                    requestApiData()
                }
            }
        }
    }

    private fun requestApiData() {
        mainViewModel.getCocktails(cocktailViewModel.applyQueries())
        mainViewModel.cocktailResponse.observe(viewLifecycleOwner) { response ->
            when (response) {
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

    private fun showShimmerEffect() {
        binding.shimmerFrameLayout.startShimmer()
        binding.recyclerView.visibility = View.GONE
    }

    private fun hideShimmerEffect() {
        binding.shimmerFrameLayout.stopShimmer()
        binding.shimmerFrameLayout.visibility = View.GONE
        binding.recyclerView.visibility = View.VISIBLE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}