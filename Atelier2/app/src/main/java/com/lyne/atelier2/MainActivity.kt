package com.lyne.atelier2

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView


class MainActivity : AppCompatActivity() {
    lateinit var vue: PlayerView
    var player: ExoPlayer?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        vue = findViewById(R.id.vue)

        player = ExoPlayer.Builder(this).build()


    }

    override fun onStart() {
        super.onStart()
        vue.player = player
        val mp3url = "https://ericlabonte.com/Goéland - Marie-Annick Lepine.mp3"
        val mediaItem = MediaItem.fromUri(mp3url)
        player?.addMediaItem(mediaItem)
        player?.prepare()
        player?.play()
    }
}