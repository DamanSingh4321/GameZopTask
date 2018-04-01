package com.singh.daman.gamezoptask.network

import ben.gamezop.io.Game
import ben.gamezop.io.GamesRequest
import ben.gamezop.io.GamesResponse
import ben.gamezop.io.InfoServiceGrpc
import com.singh.daman.gamezoptask.interfaces.GrpcRunnableListener
import io.grpc.StatusRuntimeException

/**
 * Created by Daman on 3/31/2018.
 */
class GetGamesRunnableTask : GrpcRunnableListener {
    @Throws(Exception::class)
    override fun run(blockingStub: InfoServiceGrpc.InfoServiceBlockingStub, asyncStub: InfoServiceGrpc.InfoServiceStub): List<Game> {
        return getGamesList(blockingStub)
    }

    @Throws(StatusRuntimeException::class)
    private fun getGamesList(blockingStub: InfoServiceGrpc.InfoServiceBlockingStub): List<Game> {
        val request = GamesRequest.newBuilder().build()

        val feature: GamesResponse
        feature = blockingStub.getGames(request)
        return feature.gamesList
    }
}