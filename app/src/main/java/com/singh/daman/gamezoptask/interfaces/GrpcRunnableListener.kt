package com.singh.daman.gamezoptask.interfaces

import ben.gamezop.io.Game
import ben.gamezop.io.InfoServiceGrpc

/**
 * Created by Daman on 3/31/2018.
 */
interface GrpcRunnableListener {

    @Throws(Exception::class)
    fun run(blockingStub: InfoServiceGrpc.InfoServiceBlockingStub, asyncStub: InfoServiceGrpc.InfoServiceStub): List<Game>
}