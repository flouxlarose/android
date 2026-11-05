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
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

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
    var dureeDepart: Long = 0L
    var premierLancement = true



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


        // find view
        playStop = findViewById(R.id.playStop)
        skip = findViewById(R.id.skip)
        back = findViewById(R.id.back)
        minus = findViewById(R.id.minus)
        plus = findViewById(R.id.plus)
        time = findViewById(R.id.time)
        seekBar = findViewById(R.id.seekBar)
        image = findViewById(R.id.imageView)
        songName = findViewById(R.id.songName)
        albumName = findViewById(R.id.albumName)
        artistName = findViewById(R.id.artistName)

        // ecouteur
        ec = Ecouteur()
        playStop.setOnClickListener(ec)
        skip.setOnClickListener(ec)
        back.setOnClickListener(ec)
        minus.setOnClickListener(ec)
        plus.setOnClickListener(ec)

        // position
        position = intent.getIntExtra("position", -1)
        // si savedInstanceState != null alors ce n'est pas le premier lancement
        premierLancement = (savedInstanceState == null) && (position != -1)
    }

    override fun onStart() {
        super.onStart()

        // lire la sauvegarde
        if (!premierLancement || position == -1) {
            try {
                openFileInput("sauv.ser").use { fis ->
                    ObjectInputStream(fis).use { ois ->
                        val etat = ois.readObject() as? EtatLecture
                        if (etat != null) {
                            position = etat.position
                            dureeDepart = etat.dureeMS
                        }
                    }
                }
            } catch (e: Exception) {
                position = 0
                dureeDepart = 0L
            }
        } else {
            // est le premier lancement donc remet a 0
            dureeDepart = 0L
            premierLancement = false
        }

        if(position < 0 || position >= Modele.array.size){
            // s'assure de la validité de position
            position = 0
        }

        // maj interface
        songName.text = Modele.array[position]["nomChanson"].toString()
        albumName.text = Modele.array[position]["album"].toString()
        artistName.text = Modele.array[position]["artiste"].toString()
        Glide.with(this).load(Modele.array[position]["image"].toString()).into(image)

        // init exoplayer
        if (player == null){
            player = ExoPlayer.Builder(this).build()
        }

        mediaItem = MediaItem.fromUri(Modele.array[position]["previewUrl"].toString())
        player?.setMediaItem(mediaItem)
        player?.prepare()

        if(dureeDepart > 0L){
            player?.seekTo(dureeDepart)
            seekBar.progress = dureeDepart.toInt()
        }
        player?.play()
        onPlay = true
        playStop.text = "Stop"
        seekBar.max = 30000

        val tempsRestant = (30000 - dureeDepart).coerceAtLeast(1000L)
        timer = Timer(tempsRestant)
        timer?.start()
    }

    inner class Ecouteur : View.OnClickListener
    {
        override fun onClick(v: View?) {
            if (v == playStop){
                val p = player ?: return    // si null quitte

                if (onPlay) { // pause
                    p.pause()
                    onPlay = false
                    playStop.text = "Play"
                    timer?.cancel()
                } else { // play
                    p.play()
                    onPlay = true
                    playStop.text = "Stop"

                    // temps restant
                    val tempsRestant = (30000 - p.currentPosition).coerceAtLeast(1000L)
                    timer?.cancel()
                    timer = Timer(tempsRestant)
                    timer?.start()
                }
            }
            if (v == skip) {
                if(position == Modele.array.size - 1){
                    position = 0
                }else{
                    position++
                }
                chargerEtJouerChanson()
            }
            if(v == back){
                if(position == 0){
                    position = Modele.array.size - 1
                }else{
                    position--
                }
                chargerEtJouerChanson()
            }
            if(v == minus){
                val p = player
                if (p != null){
                    val nouvellePos = (p.currentPosition - 5000).coerceAtLeast(0L)  //peux pas aller en dessous de 0
                    p.seekTo(nouvellePos)
                    seekBar.progress = nouvellePos.toInt()
                    time.text = (30 - (nouvellePos / 1000)).toString()
                }
            }
            if(v == plus){
                val p = player
                if (p != null){
                    val nouvellePos = (p.currentPosition + 5000).coerceAtMost(30000L)  //peux pas aller au dessus de 30 sec
                    p.seekTo(nouvellePos)
                    seekBar.progress = nouvellePos.toInt()
                    time.text = (30 - (nouvellePos / 1000)).toString()
                }
            }
        }
    }

    override fun onStop() {
        // serialistation

        try {
            val duree = player?.currentPosition?: 0L
            val etat = EtatLecture(position, duree)

            openFileOutput("sauv.ser", MODE_PRIVATE).use { fos ->
                ObjectOutputStream(fos).use { oos ->
                    oos.writeObject(etat)
                }
            }
        } catch (e: Exception){
            e.printStackTrace()
        }

        // nettoyer le l'exo player
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
    }
    inner class Timer(temps: Long): CountDownTimer(temps, 25){
        override fun onTick(mili: Long) {
            val p = player ?: return // empeche de planter si player a été détruit
            val pos = p.currentPosition
            seekBar.progress = pos.toInt()
            time.text = (30 - (pos / 1000)).toString()
        }
        override fun onFinish() {
            if(player == null) return

            seekBar.progress = 30000
            time.text = "0"
            if(position >= Modele.array.size - 1){
                position = 0
            }else{
                position++
            }
            if(!onPlay){
                onPlay = true
                playStop.text = "Stop"
            }

            chargerEtJouerChanson()
        }
    }
    private fun chargerEtJouerChanson(){
        val p = player ?: return    // quitte si player est null

        if (!onPlay){
            onPlay = true
            playStop.text = "Stop"
        }

            player?.stop()
            mediaItem = MediaItem.fromUri(Modele.array.get(position).get("previewUrl").toString())
            player?.setMediaItem(mediaItem)
            player?.prepare()
            player?.play()

            Glide.with(this@MusicPlayer).load(Modele.array.get(position).get("image").toString()).into(image)
            songName.text = Modele.array.get(position).get("nomChanson").toString()
            albumName.text = Modele.array.get(position).get("album").toString()
            artistName.text = Modele.array.get(position).get("artiste").toString()

            timer?.cancel()
            seekBar.progress = 0
            timer = Timer(30000)
            timer?.start()
            dureeDepart = 0L
    }

}