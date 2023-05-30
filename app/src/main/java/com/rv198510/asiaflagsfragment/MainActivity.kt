package com.rv198510.asiaflagsfragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.widget.Button
import android.widget.Toast
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() , TextToSpeech.OnInitListener{
    private var tts: TextToSpeech? = null
    private var btnSpeak: Button? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btnSpeak = findViewById<Button>(R.id.btnSpeak)

        val flags = JsonHelper.getFlagsFromJson("asia_flag.json", this)
        var flagsIndex = 0
        replaceFragment(flags, flagsIndex)


        val prevButton = findViewById<Button>(R.id.prevButton)
        val nextButton = findViewById<Button>(R.id.nextButton)


        prevButton.isEnabled = false
        tts = TextToSpeech(this, this)

        btnSpeak!!.isEnabled = false

        btnSpeak!!.setOnClickListener{
           // btnSpeak!!.isEnabled = false
            speakOut(flags[flagsIndex].name)
        }
        prevButton.setOnClickListener {
            if (flagsIndex > 0) {
                flagsIndex--

                replaceFragment(flags, flagsIndex)
//                Toast.makeText(this,flags[flagsIndex].name, Toast.LENGTH_SHORT).show()
//                if (btnSpeak?.isEnabled == false){
//                    btnSpeak!!.isEnabled = true
//                }
                if (flagsIndex == 0) {
                    prevButton.isEnabled = false
                }
                if (flagsIndex == flags.size - 2) {
                    nextButton.isEnabled = true
                }
            }
        }

        nextButton.setOnClickListener {
            if (flagsIndex < flags.size - 1) {
                flagsIndex++

                replaceFragment(flags, flagsIndex)
//                Toast.makeText(this,flags[flagsIndex].name, Toast.LENGTH_SHORT).show()
//                if (btnSpeak?.isEnabled == false){
//                    btnSpeak!!.isEnabled = true
//                }
                if (flagsIndex == flags.size - 1) {
                    nextButton.isEnabled = false
                }
                if (flagsIndex == 1) {
                    prevButton.isEnabled = true
                }
            }
        }
    }

    private fun replaceFragment(flags: ArrayList<Flags>, flagsIndex: Int) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, FlagsFragment.newInstance(flags[flagsIndex]))
            .commit()
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts!!.setLanguage(Locale.US)

            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS","The Language not supported!")

            } else {
                if (btnSpeak?.isEnabled == false){
                    btnSpeak!!.isEnabled = true
                }

            }

        }
    }


    private fun speakOut(nameSpeak: String) {
        val text = nameSpeak!!.toString()
        tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null,"")
    }

    public override fun onDestroy() {
        // Shutdown TTS when
        // activity is destroyed
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()

            }
        super.onDestroy()
    }
}