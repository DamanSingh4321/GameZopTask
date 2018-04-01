package com.singh.daman.gamezoptask.interfaces

import ben.gamezop.io.Game

/**
 * Created by Daman on 3/31/2018.
 */
interface GamesListener {

    fun onGamesFetch(games: List<Game>?)
}