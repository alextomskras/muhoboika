package com.example.muhoboika

import android.os.Handler
import java.util.*

class GameEngine: Contract.GameEngine {

    private var gameView: Contract.GameView? = null

    private val ants: MutableCollection<Ant> = ArrayList()
    private val random: Random = Random()
    private var score = 0
    private val handler: Handler = Handler()

    private val showNewAntTask: Runnable = object: Runnable {
        override fun run() {
            val newAnt = createNewAnt()
            val isGameOver = checkIfGameIsOver()
            if (!isGameOver) {
                gameView?.showAnt(newAnt)
                ants.add(newAnt)
                handler.postDelayed(this, 1500)
            } else {
                handler.removeCallbacks(this)
                gameView?.clearView()
                gameView?.setGameOverVisibility(true)
                gameView?.setPlayButtonVisibility(true)
            }
        }
    }

    private fun checkIfGameIsOver(): Boolean {
        return ants.isNotEmpty()
    }

    override fun onViewAttached(view: Contract.GameView) {
        gameView = view
        gameView?.clearView()
        gameView?.setIntroTextVisibility(true)
    }

    override fun onViewDetached() {
        handler.removeCallbacks(showNewAntTask)
        gameView = null
    }

    override fun onPlayButtonClicked() {
        gameView?.setPlayButtonVisibility(false)
        gameView?.clearView()
        // start new game
        ants.clear()
        showNewAntTask.run()
        score = 0
    }

    override fun onAntClicked(ant: Ant) {
        ants.remove(ant)
        gameView?.hideAnt(ant)
        score++
        gameView?.showScore(score)
    }

    private fun createNewAnt(): Ant {
        val x = getAntXposition()
        val y = getAntYposition()
        val id = ants.size + 1
        return Ant(id, x, y)
    }

    /**
     * returns Ant's vertical position as a screen height ratio, in range 0.0-1.0
     */
    private fun getAntYposition(): Double {
        return random.nextDouble()
    }

    /**
     * returns Ant's horizontal position as a screen width ratio, in range 0.0-1.0
     */
    private fun getAntXposition(): Double {
        return random.nextDouble()
    }

}



















