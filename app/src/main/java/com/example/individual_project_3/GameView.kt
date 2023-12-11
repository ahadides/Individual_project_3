package com.example.individual_project_3

import android.content.ClipData
import android.content.ClipDescription
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.DragEvent
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.individual_project_3.databinding.ActivityGameViewBinding


class GameView : AppCompatActivity() {
    lateinit var binding: ActivityGameViewBinding
    //lateinit var Fragment: Fragment
    private var expectedSequence = listOf<String>()
    private val userSequence = mutableListOf<String>()
    private var animationStarted = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val selectedLevel = intent.getStringExtra("SELECTED_LEVEL")
        val fragment: Fragment = when (selectedLevel) {
            "LevelOne" -> GameFragment()
            "LevelTwo" -> Game2Fragment()
            "LevelThree" -> Game3Fragment()
            // Add more cases for other levels
            else -> GameFragment() // Default to a generic GameFragment
        }

        expectedSequence = when (selectedLevel) {
            "LevelOne" -> listOf("FORWARD", "DOWN", "FORWARD")
            "LevelTwo" -> listOf("FORWARD")
            "LevelThree" -> listOf("FORWARD", "DOWN", "FORWARD", "UP", "FORWARD")
            else -> emptyList() // Set a default value or leave it as an empty list
        }

        //Toast.makeText(this, expectedSequence.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this, selectedLevel, Toast.LENGTH_SHORT).show()
        supportFragmentManager.beginTransaction()
            .replace(binding.fragmentContainerView.id, fragment)
            .commit()

        setBorderColor(binding.holder01, Color.RED)
        setBorderColor(binding.holder02, Color.RED)
        setBorderColor(binding.holder03, Color.RED)
        setBorderColor(binding.holder04, Color.RED)
        setBorderColor(binding.holder05, Color.RED)

        binding.holder01.setOnDragListener(arrowDragListener)
        binding.holder02.setOnDragListener(arrowDragListener)
        binding.holder03.setOnDragListener(arrowDragListener)
        binding.holder04.setOnDragListener(arrowDragListener)
        binding.holder05.setOnDragListener(arrowDragListener)

        binding.upMoveBtn.setOnLongClickListener(onLongClickListener)
        binding.downMoveBtn.setOnLongClickListener(onLongClickListener)
        binding.forwardMoveBtn.setOnLongClickListener(onLongClickListener)
        binding.backMoveBtn.setOnLongClickListener(onLongClickListener)

        val playButton: Button = findViewById(R.id.play)
        playButton.setOnClickListener {
            //gameFragment.updateResult("Button Pressed!")
            //Toast.makeText(this, userSequence.toString(), Toast.LENGTH_SHORT).show()
           if (checkCorrectSequence()) {
              // if (!checkCorrectSequence()) {
                startAnimation()
            } else {
               userSequence.clear()
               binding.holder01.setImageResource(android.R.color.transparent)
               binding.holder02.setImageResource(android.R.color.transparent)
               binding.holder03.setImageResource(android.R.color.transparent)
               binding.holder04.setImageResource(android.R.color.transparent)
               binding.holder05.setImageResource(android.R.color.transparent)
                Toast.makeText(this, "Incorrect sequence,Try Again", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private val onLongClickListener = View.OnLongClickListener { view: View ->
        (view as? Button)?.let {
            val item = ClipData.Item(it.tag as? CharSequence)
            val dragData = ClipData(it.tag as? CharSequence, arrayOf(ClipDescription.MIMETYPE_TEXT_PLAIN), item)
            val myShadow = ArrowDragShadowBuilder(it)

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                it.startDragAndDrop(dragData, myShadow, null, 0)
            } else {
                it.startDrag(dragData, myShadow, null, 0)
            }

            true
        }
        false
    }

    private val arrowDragListener = View.OnDragListener { view, dragEvent ->
        (view as? ImageView)?.let {
            when (dragEvent.action) {
                DragEvent.ACTION_DRAG_STARTED -> {
                    return@OnDragListener true
                }
                DragEvent.ACTION_DRAG_ENTERED -> {
                    setBorderColor(view, Color.YELLOW)
                    return@OnDragListener true
                }
                DragEvent.ACTION_DRAG_EXITED -> {
                    setBorderColor(view, Color.RED)
                    return@OnDragListener true
                }
                DragEvent.ACTION_DRAG_LOCATION -> {
                    return@OnDragListener true
                }
                DragEvent.ACTION_DROP -> {
                    val item: ClipData.Item = dragEvent.clipData.getItemAt(0)
                    val lbl = item.text.toString()

                    when (lbl) {
                        "UP", "DOWN", "FORWARD", "BACK" -> {
                            userSequence.add(lbl)
                            setPlaceholderImage(view, lbl)
                        }
                    }

                    setBorderColor(view, Color.RED)
                    return@OnDragListener true
                }
                DragEvent.ACTION_DRAG_ENDED -> {
                    setBorderColor(view, Color.RED)
                    return@OnDragListener true
                }
                else -> return@OnDragListener false
            }
        }
        false
    }

    private fun setPlaceholderImage(imageView: ImageView, label: String) {
        when (label) {
            "UP" -> imageView.setImageResource(R.drawable.ic_baseline_arrow_upward_24)
            "DOWN" -> imageView.setImageResource(R.drawable.ic_baseline_arrow_downward_24)
            "FORWARD" -> imageView.setImageResource(R.drawable.ic_baseline_arrow_forward_24)
            "BACK" -> imageView.setImageResource(R.drawable.ic_baseline_arrow_back_24)
        }
    }

    private fun checkCorrectSequence(): Boolean {
        Toast.makeText(this, userSequence.size.toString(), Toast.LENGTH_SHORT).show()
        Toast.makeText(this,expectedSequence.size.toString(), Toast.LENGTH_SHORT).show()
        if (userSequence.size == expectedSequence.size) {

            for (i in userSequence.indices) {
                if (userSequence[i] != expectedSequence[i]) {
                    return false
                }
            }
            return true
        }

        return false
    }

    private fun startAnimation() {
        Log.d("GameView", "startAnimation called")

        // Retrieve the current fragment in the fragment container
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView)

        // Call the startAnimation method if the fragment implements GameAnimationListener
        if (currentFragment is GameAnimationListener) {
            currentFragment.startAnimation()
        }
    }


    private fun setBorderColor(imageView: ImageView, color: Int) {
        val border = GradientDrawable()
        border.setColor(Color.TRANSPARENT)
        border.setStroke(2, color)
        imageView.background = border
    }

    private class ArrowDragShadowBuilder(view: View) : View.DragShadowBuilder(view) {
        private val shadow = view.background
        override fun onProvideShadowMetrics(size: Point, touch: Point) {
            val width: Int = view.width
            val height: Int = view.height
            shadow?.setBounds(0, 0, width, height)
            size.set(width, height)
            touch.set(width / 2, height / 2)
        }

        override fun onDrawShadow(canvas: Canvas) {
            shadow?.draw(canvas)
        }
    }

/* fun onButtonClicked() {
        Toast.makeText(this, "Button Clicked in Fragment!", Toast.LENGTH_SHORT).show()
    }*/

}
