package com.singh.daman.gamezoptask.ui

import android.Manifest
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import ben.gamezop.io.Game
import com.singh.daman.gamezoptask.R
import com.singh.daman.gamezoptask.adapters.GamesAdapter
import com.singh.daman.gamezoptask.interfaces.GamesListener
import com.singh.daman.gamezoptask.interfaces.ZipDownloadListener
import com.singh.daman.gamezoptask.network.DownloadTask
import com.singh.daman.gamezoptask.network.GetGamesRunnableTask
import com.singh.daman.gamezoptask.network.GrpcTask
import com.singh.daman.gamezoptask.utils.Utils
import io.grpc.ManagedChannelBuilder
import java.io.File


class MainActivity : AppCompatActivity(), GamesListener, ZipDownloadListener {

    private var gamesList: ArrayList<Game> = ArrayList()
    private var gamesAdapter: GamesAdapter? = null
    private var pDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val channel = ManagedChannelBuilder.forAddress("Enter_host_name", 0).usePlaintext(true).build()
        GrpcTask.grpcNetwork(GetGamesRunnableTask(), channel, this, this)
    }

    private fun onGameClicked(game : Game){
        val root = android.os.Environment.getExternalStorageDirectory()
        val dir = File(root.absolutePath, this.packageName)
        if (!dir.exists()) {
            dir.mkdirs()
        }
        val file = File(dir, game.name)
        if(checkPermission()) {
            pDialog = ProgressDialog(this)
            pDialog!!.setMessage("Please wait")
            pDialog!!.setTitle("Downloading zip")
            pDialog!!.setCancelable(false)
            pDialog!!.show()
            DownloadTask.download(game.zipUrl, file, this)
        }
    }

    override fun onGamesFetch(games: List<Game>?) {
        for (game in games!!) {
            gamesList.add(game)
        }
        val recyclerView = findViewById<RecyclerView>(R.id.rvGames)
        recyclerView.layoutManager = GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false)
        gamesAdapter = GamesAdapter(gamesList, {game: Game -> onGameClicked(game)})
        recyclerView.adapter = gamesAdapter
        gamesAdapter!!.notifyDataSetChanged()
    }

    override fun onZipDownload(s: String) {
        Utils.unpackZipFile(s, this)
    }

    override fun onUnZip(file: File) {
        pDialog!!.dismiss()
        showWebView(file)
    }

    private fun showWebView(file: File) {
        val intent = Intent(this, WebviewActivity::class.java)
        intent.putExtra(WebviewActivity.intentKey, file.absoluteFile)
        startActivity(intent)
    }

    private fun checkPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            true
        } else {
            ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    1)
            false
        }
    }
}
