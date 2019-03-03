package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.contains
import com.google.android.material.chip.Chip

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cvg1 = findViewById<CustomViewGroup>(R.id.customViewGroup1)
        val cvg2 = findViewById<CustomViewGroup>(R.id.customViewGroup2)

        val data = arrayListOf(
            "hello darkness",
            "my old friend",
            "i've come",
            "to talk with you",
            "again",
            "because a vision softly creeping",
            "left its seeds while I was sleeping",
            "and the vision that was planted in my brain",
            "still remains",
            "within",
            "the sound",
            "of silence"
        )

        for(i in 0 until data.size){
            val chip = Chip(this)
            chip.text = data[i]
            chip.isCloseIconEnabled = true
            chip.setOnCloseIconClickListener {
                if(cvg1.contains(chip)){
                    cvg1.removeView(chip)
                    cvg2.addView(chip)
                } else {
                    cvg2.removeView(chip)
                    cvg1.addView(chip)
                }
            }

            if(i % 3 == 0)
                chip.visibility = View.GONE

            if(i % 2 == 0)
                cvg2.addView(chip)
            else
                cvg1.addView(chip)
        }
    }
}
