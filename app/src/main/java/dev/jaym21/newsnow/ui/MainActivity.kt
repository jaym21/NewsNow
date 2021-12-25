package dev.jaym21.newsnow.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import dev.jaym21.newsnow.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : AppCompatActivity(){

    private var binding: ActivityMainBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        //TODO: Notification using work manager
        //TODO: Light Dark theme switch
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}