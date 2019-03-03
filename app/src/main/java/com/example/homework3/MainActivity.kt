package com.example.homework3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val cvg = findViewById<CustomViewGroup>(R.id.customViewGroup)

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
            val txt = TextView(this)
            txt.text = data[i]
            if(i % 2 == 0)
                txt.visibility = View.GONE
            cvg.addView(txt)
        }
    }
}
