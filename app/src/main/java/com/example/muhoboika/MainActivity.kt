package com.example.muhoboika

import android.graphics.drawable.Drawable
import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.TypedValue
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity(), Contract.GameView  {

    private var gameLayout: FrameLayout? = null
    private var playButton: View? = null
    private var introText: View? = null
    private var gameOverText: View? = null
    private var score: TextView? = null
    //    private var mp: MediaPlayer? = null
    private var mp: MediaPlayer? = null


    private val gameEngine: GameEngine = GameEngine()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        gameLayout = findViewById<FrameLayout>(R.id.game_layout)
        playButton = findViewById(R.id.play_button)
        introText = findViewById(R.id.intro_text)
        gameOverText = findViewById(R.id.game_over)
        score = findViewById<TextView>(R.id.score)
        var mp = MediaPlayer.create(this, R.raw.uh)
        var position = 0

        playButton!!.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                if (mp.isPlaying == false) {
                    mp.seekTo(position)
                    mp.start()
                }
                gameEngine.onPlayButtonClicked()

            }

        })

        val d = Drawable.createFromStream(assets.open("bg.jpeg"), null)
        gameLayout?.background = d


        gameEngine.onViewAttached(this)


    }

    override fun showAnt(ant: Ant) {
        val antView = ImageView(this)
        antView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_ant))
        antView.scaleType = ImageView.ScaleType.FIT_CENTER
        antView.tag = ant
        antView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                val ant = v!!.tag as Ant

                gameEngine.onAntClicked(ant)

            }
        })
        val antSize =
            TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 56f, resources.displayMetrics)

        val layoutParams = FrameLayout.LayoutParams(antSize.toInt(), antSize.toInt())
        val screenWidth = gameLayout!!.width - antSize
        val screenHeight = gameLayout!!.height - antSize
        layoutParams.leftMargin = (ant.x * screenWidth).toInt()
        layoutParams.topMargin = (ant.y * screenHeight).toInt()

        gameLayout?.addView(antView, layoutParams)
    }

    override fun hideAnt(antToHide: Ant) {
        val antViewsCount: Int? = gameLayout?.childCount
        val shleppPlayer = MediaPlayer.create(this, R.raw.shlepp1)

        antViewsCount?.let {

            for (i: Int in 0..antViewsCount) {
                val view = gameLayout?.getChildAt(i)
                val tempAnt = view?.tag
                if (antToHide == (tempAnt)) {
                    shleppPlayer.start()
                    gameLayout?.removeView(view)
                    break
                }
            }

        }
    }

    override fun setPlayButtonVisibility(visible: Boolean) {

//        mp = MediaPlayer.create(this, R.raw.utinye)
//        mp.start ()

        playButton?.visibility = if (visible) View.VISIBLE else View.GONE
        var isStart = "Stop"
        Toast.makeText(applicationContext, "clearView-Pla:" + isStart, Toast.LENGTH_LONG).show()

//        GameEngine.playSound(this, isStart = isStart)
    }

    override fun clearView() {
        gameLayout?.removeAllViews()
        introText?.visibility = View.GONE
        gameOverText?.visibility = View.GONE
//        mp = MediaPlayer.create(this, R.raw.utinye)
//
//        var position = 0
//        if (mp!!.isPlaying ()) {
//
//            mp!!.stop ()
//        }
//        Toast.makeText(applicationContext, "STOP",Toast.LENGTH_LONG).show()


    }

    override fun setGameOverVisibility(visible: Boolean) {

//        if (mp!!.isPlaying ()) {
//
//            mp!!.stop ()
//        }


//        var position = 0

//        position = mp.getCurrentPosition ()
        val mp = MediaPlayer.create(this, R.raw.sound08426)
//        val mp = MediaPlayer.create(this, R.raw.playerexplode)
        mp.start()
//        position = 0
//        mp.seekTo (0)

        gameOverText?.visibility = if (visible) View.VISIBLE else View.GONE

//        worldPlayer.isLooping
//        worldPlayer.stop()
//        var isStart = "Start"
//        Toast.makeText(applicationContext, "setGameOverVisibility-Pla:"+ isStart,Toast.LENGTH_LONG).show()
//        GameEngine.playSound(this, isStart = isStart)

    }

    override fun setIntroTextVisibility(visible: Boolean) {
        introText?.visibility = if (visible) View.VISIBLE else View.GONE
    }

    override fun showScore(score: Int) {
        this.score?.text = "Points: " + score
    }

    override fun onDestroy() {
        super.onDestroy()
        mp!!.release()
    }
}
