package com.decinfo.tp1

import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import com.bumptech.glide.Glide

class MusicPlayer : AppCompatActivity(){
    lateinit var mediaItem: MediaItem
    lateinit var image: ImageView
    lateinit var playStop: Button
    lateinit var skip: Button
    lateinit var back: Button
    lateinit var minus: Button
    lateinit var plus: Button
    lateinit var time: TextView
    lateinit var seekBar: SeekBar
    lateinit var songName: TextView
    lateinit var albumName: TextView
    lateinit var artistName: TextView
    var position = 0
    var onPlay = true
    var ec : Ecouteur? = null

    var timer : Timer? = null



    var player : ExoPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_music_player)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        playStop = findViewById(R.id.playStop)
        skip = findViewById(R.id.skip)
        back = findViewById(R.id.back)
        minus = findViewById(R.id.minus)
        plus = findViewById(R.id.plus)
        time = findViewById(R.id.time)
        seekBar = findViewById(R.id.seekBar)

        ec = Ecouteur()
        playStop.setOnClickListener(ec)
        skip.setOnClickListener(ec)
        back.setOnClickListener(ec)
        minus.setOnClickListener(ec)
        plus.setOnClickListener(ec)



        image = findViewById(R.id.imageView)
        Glide.with(this).load(Modele.array.get(position).get("image").toString()).into(image)

        songName = findViewById(R.id.songName)
        songName.text = Modele.array.get(position).get("nomChanson").toString()

        albumName = findViewById(R.id.albumName)
        albumName.text = Modele.array.get(position).get("album").toString()

        artistName = findViewById(R.id.artistName)
        artistName.text = Modele.array.get(position).get("artiste").toString()

        player = ExoPlayer.Builder(this).build()
        position = intent.getIntExtra("position", 0)
        mediaItem = MediaItem.fromUri(Modele.array.get(position).get("previewUrl").toString())
    }

    override fun onStart() {
        super.onStart()
        player?.addMediaItem(mediaItem)
        player?.prepare()
        player?.play()

        seekBar.max = 30000
        seekBar.progress = 0

        timer = Timer(30000)
        timer?.start()
    }

    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == playStop){
                if (onPlay){    // met sur pause
                    player?.pause()
                    onPlay = false
                    playStop.text = "Play"

                    timer?.cancel()
                }
                else{   // mets sur play
                    player?.play()
                    onPlay = true
                    playStop.text = "Stop"

                    timer = Timer(player?.duration!!)
                    timer?.start()
                }
            }
            if (v == skip) {
                if(position == Modele.array.size - 1){
                    position = 0
                }else{
                    position++
                }

                if(!onPlay){
                    onPlay = true
                    playStop.text = "Stop"
                }

                player?.stop()
                player?.clearMediaItems()
                mediaItem = MediaItem.fromUri(Modele.array.get(position).get("previewUrl").toString())
                player?.addMediaItem(mediaItem)
                player?.prepare()
                player?.play()

                Glide.with(this@MusicPlayer).load(Modele.array.get(position).get("image").toString()).into(image)
                onPlay = true

                timer?.cancel()
                seekBar.progress = 0
                timer = Timer(30000)
                timer?.start()

                songName.text = Modele.array.get(position).get("nomChanson").toString()
                albumName.text = Modele.array.get(position).get("album").toString()
                artistName.text = Modele.array.get(position).get("artiste").toString()
            }
            if(v == back){
                if(position == 0){
                    position = Modele.array.size - 1
                }else{
                    position--
                }

                if(!onPlay){
                    onPlay = true
                    playStop.text = "Stop"
                }

                player?.stop()
                player?.clearMediaItems()
                mediaItem = MediaItem.fromUri(Modele.array.get(position).get("previewUrl").toString())
                player?.addMediaItem(mediaItem)
                player?.prepare()
                player?.play()

                Glide.with(this@MusicPlayer).load(Modele.array.get(position).get("image").toString()).into(image)
                onPlay = true

                seekBar.progress = 0
                timer = Timer(30000)
                timer?.start()

                songName.text = Modele.array.get(position).get("nomChanson").toString()
                albumName.text = Modele.array.get(position).get("album").toString()
                artistName.text = Modele.array.get(position).get("artiste").toString()
            }
            if(v == minus){
                player!!.seekTo(player!!.currentPosition - 5000)
                seekBar.progress = player!!.currentPosition.toInt()
                time.text = (player!!.currentPosition/1000).toString()
            }
            if(v == plus){
                player!!.seekTo(player!!.currentPosition + 5000)
                seekBar.progress = player!!.currentPosition.toInt()
                time.text = (player!!.currentPosition/1000).toString()
            }
        }
    }

    override fun onStop() {
        // serialistation a faire

        super.onStop()
        player?.stop()
        player?.clearMediaItems()
        timer?.cancel()
        player?.release()
        player = null
        timer = null
    }

    override fun onDestroy() {
        super.onDestroy()
        player?.stop()
        player?.clearMediaItems()
        timer?.cancel()
        player?.release()
        player = null
        timer = null
    }
    inner class Timer(temps: Long): CountDownTimer(temps, 25){
        override fun onTick(mili: Long) {
            seekBar.progress = (player!!.currentPosition).toInt()
            time.text = (30 - player!!.currentPosition/1000).toString()
        }
        override fun onFinish() {
            seekBar.progress = 30000
            time.text = "0"
            if(position == Modele.array.size - 1){
                position = 0
            }else{
                position++
            }
            if(!onPlay){
                onPlay = true
                playStop.text = "Stop"
            }

            player?.stop()
            player?.clearMediaItems()
            mediaItem = MediaItem.fromUri(Modele.array.get(position).get("previewUrl").toString())
            player?.addMediaItem(mediaItem)
            player?.prepare()
            player?.play()

            Glide.with(this@MusicPlayer).load(Modele.array.get(position).get("image").toString()).into(image)
            onPlay = true

            seekBar.progress = 0
            timer = Timer(30000)
            timer?.start()

            songName.text = Modele.array.get(position).get("nomChanson").toString()
            albumName.text = Modele.array.get(position).get("album").toString()
            artistName.text = Modele.array.get(position).get("artiste").toString()
        }
    }

}