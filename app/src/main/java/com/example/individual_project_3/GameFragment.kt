package com.example.individual_project_3

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.individual_project_3.databinding.FragmentGameBinding




class GameFragment : Fragment(),GameAnimationListener {

    private lateinit var binding: FragmentGameBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }
   /* fun updateResult(result: String) {
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show()
    }*/


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Animation will be started when the "Play" button is pressed in the GameView activity
    }



    override fun startAnimation() {
        val personImageView = binding.Person

        val moveRightAnimator = ObjectAnimator.ofFloat(personImageView, "translationX", 1200f)
        moveRightAnimator.duration = 2000
        moveRightAnimator.interpolator = LinearInterpolator()

        val moveDownAnimator = ObjectAnimator.ofFloat(personImageView, "translationY", 1000f)
        moveDownAnimator.duration = 2000
        moveDownAnimator.interpolator = LinearInterpolator()

        val moveRightAgainAnimator = ObjectAnimator.ofFloat(personImageView, "translationX", 2700f)
        moveRightAgainAnimator.duration = 2000
        moveRightAgainAnimator.interpolator = LinearInterpolator()

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(moveRightAnimator, moveDownAnimator, moveRightAgainAnimator)

        animatorSet.start()


    }


}

