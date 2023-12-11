package com.example.individual_project_3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

// LevelSelectionActivity.kt
val buttonIds = arrayOf(
    R.id.levelOneButton,
    R.id.levelTwoButton,
    R.id.levelThreeButton,
   // R.id.levelFourButton,
   // R.id.levelFiveButton,
   // R.id.levelSixButton
    // Add more button IDs as needed
)

class LevelSelectionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_level_selection)
        for (buttonId in buttonIds) {
            val button: Button = findViewById(buttonId)
            button.setOnClickListener {
                val levelName = when (buttonId) {
                    R.id.levelOneButton -> "LevelOne"
                    R.id.levelTwoButton -> "LevelTwo"
                    R.id.levelThreeButton -> "LevelThree"
                   // R.id.levelFourButton -> "LevelFour"
                 //   R.id.levelFiveButton -> "LevelFive"
                   // R.id.levelSixButton -> "LevelSix"
                    // Add more cases as needed
                    else -> throw IllegalArgumentException("Unknown button ID: $buttonId")
                }
                startGameViewActivity(levelName)
            }
        }
    }
    private fun startGameViewActivity(selectedLevel: String) {
        val intent = Intent(this@LevelSelectionActivity, GameView::class.java)
        intent.putExtra("SELECTED_LEVEL", selectedLevel)
        startActivity(intent)
    }
}
