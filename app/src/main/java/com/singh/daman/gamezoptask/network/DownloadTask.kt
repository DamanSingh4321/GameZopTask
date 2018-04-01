package com.singh.daman.gamezoptask.network

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import android.widget.Toast
import com.singh.daman.gamezoptask.interfaces.ZipDownloadListener
import com.singh.daman.gamezoptask.ui.MainActivity
import com.singh.daman.gamezoptask.utils.Utils
import java.io.*
import java.net.URL
import android.app.ProgressDialog



/**
 * Created by Daman on 3/31/2018.
 */
class DownloadTask {


    companion object {
        fun download(result: String, file: File, listener: ZipDownloadListener) {
            DownloadTaskAsync(result, file, listener).execute()
        }
    }

    private class DownloadTaskAsync(internal var url: String, internal var name: File,
                                    internal var listener: ZipDownloadListener) : AsyncTask<Void, Void, String>() {

        override fun doInBackground(vararg voids: Void): String {
            if(name.exists() && name.length() > 0)
                return "Already Downloaded"
            else {
                try {
                    val u = URL(url)
                    val conn = u.openConnection()
                    val contentLength = conn.contentLength

                    val stream = DataInputStream(u.openStream())

                    val buffer = ByteArray(contentLength)
                    stream.readFully(buffer)
                    stream.close()

                    Log.d("File", name.absolutePath)
                    val fos = DataOutputStream(FileOutputStream(name))
                    fos.write(buffer)
                    fos.flush()
                    fos.close()
                } catch (e: FileNotFoundException) {
                    e.printStackTrace()
                    return e.toString() // swallow a 404
                } catch (e: IOException) {
                    e.printStackTrace()
                    return e.toString() // swallow a 404
                }
            }

            return "File downloaded"
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            listener.onZipDownload(name.absolutePath)
        }
    }
}