package com.example.cocktail.ui.fragments.filter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.doOnEnd
import com.example.cocktail.R
import com.example.cocktail.databinding.FragmentFilterBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FilterFragment : Fragment() {

    private var _binding: FragmentFilterBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentFilterBinding.inflate(layoutInflater, container, false)

        binding.buttonAlcohol.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonAlcohol, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonAlcohol)
            animator.start()
            animator.doOnEnd {

            }
        }

        binding.buttonNonAlcoholic.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonNonAlcoholic, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonNonAlcoholic)
            animator.start()
            animator.doOnEnd {

            }
        }

        binding.buttonPopular.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonPopular, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonPopular)
            animator.start()
            animator.doOnEnd {

            }
        }

        binding.buttonLatest.setOnClickListener {
            val animator = ObjectAnimator.ofFloat(binding.buttonLatest, View.ROTATION_Y, -360f, 0f)
            animator.duration = 500
            animator.disableViewDuringAnimation(binding.buttonLatest)
            animator.start()
            animator.doOnEnd {

            }
        }

        return binding.root
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