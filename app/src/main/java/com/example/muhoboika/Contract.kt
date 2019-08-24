package com.example.muhoboika

interface Contract {

    interface GameView {
        fun setIntroTextVisibility(visible: Boolean)
        fun setPlayButtonVisibility(visible: Boolean)
        fun setGameOverVisibility(visible: Boolean)
        fun showAnt(ant: Ant)
        fun hideAnt(antToHide: Ant)
        fun showScore(score: Int)
        fun clearView()
    }

    interface GameEngine {

        fun onViewAttached(view: GameView)
        fun onViewDetached()
        fun onPlayButtonClicked()
        fun onAntClicked(ant: Ant)
    }
}