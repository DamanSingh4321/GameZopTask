package com.singh.daman.gamezoptask.network

import android.os.AsyncTask
import ben.gamezop.io.Game
import ben.gamezop.io.InfoServiceGrpc
import com.singh.daman.gamezoptask.interfaces.GamesListener
import com.singh.daman.gamezoptask.interfaces.GrpcRunnableListener
import com.singh.daman.gamezoptask.ui.MainActivity
import io.grpc.ManagedChannel

/**
 * Created by Daman on 3/31/2018.
 */
class GrpcTask {

    companion object {
        fun grpcNetwork(grpcRunnable: GrpcRunnableListener, channel: ManagedChannel, activity: MainActivity, gamesListener: GamesListener) {
            GrpcTaskAsync(grpcRunnable, channel, activity, gamesListener).execute()
        }
    }

    private class GrpcTaskAsync internal constructor(private val grpcRunnable: GrpcRunnableListener,
                                                     private val channel: ManagedChannel, activity: MainActivity,
                                                     private val gamesListener: GamesListener) : AsyncTask<Void, Void, List<Game>>() {

        override fun doInBackground(vararg nothing: Void): List<Game>? {
            return try {
                grpcRunnable.run(InfoServiceGrpc.newBlockingStub(channel), InfoServiceGrpc.newStub(channel))
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }

        }

        override fun onPostExecute(result: List<Game>) {
            gamesListener.onGamesFetch(result)
        }

    }

}