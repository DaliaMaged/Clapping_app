package com.example.clapping

import android.media.AsyncPlayer
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {
    private  var mediaPlayer: MediaPlayer?=null
    private lateinit var seekBar: SeekBar
    private lateinit var runnable: Runnable
    private lateinit var handler: Handler
    private lateinit var played:TextView
    private lateinit var due:TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val stop=findViewById<FloatingActionButton>(R.id.stop_btn)
        val pause=findViewById<FloatingActionButton>(R.id.pause_btn)
        val play=findViewById<FloatingActionButton>(R.id.play_btn)

         seekBar=findViewById(R.id.sbClapping)

        handler= Handler(Looper.getMainLooper())

       // stop btn
        stop.setOnClickListener{
            mediaPlayer?.stop()
            mediaPlayer?.reset()
            mediaPlayer?.release()
            //3shan myakhodsh mn el memory
            mediaPlayer=null
            handler.removeCallbacks(runnable)
            seekBar.progress=0
            played.text=""
            due.text=""


        }

        // pause btn
        pause.setOnClickListener{
            mediaPlayer?.pause()

        }

        // play btn
        play.setOnClickListener{
            if (mediaPlayer==null){
                mediaPlayer= MediaPlayer.create(this,R.raw.clapping)
                initializeSeekBar()
            }
            mediaPlayer?.start()

        }

    }
    private fun initializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progess: Int, fromUser: Boolean) {
                //و الي جاي من اليوزر ترو , روح في الميديا بلاير للرقم الي بيشاور عليه البروجرس من 0 الي 100
                if (fromUser) mediaPlayer?.seekTo(progess)

            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
            }

        })
        seekBar.max=mediaPlayer!!.duration
        runnable= Runnable {
            //to link the seekbar with the mediaplayer current position
            seekBar.progress=mediaPlayer!!.currentPosition
             played=findViewById(R.id.tvPlayed)
             due=findViewById(R.id.tvDue)
            val playedTime=mediaPlayer!!.currentPosition/1000
            played.text="$playedTime sec"
            val duration=mediaPlayer!!.duration/1000
            val dueTime=duration-playedTime
            due.text="$dueTime sec"


            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)

    }
}