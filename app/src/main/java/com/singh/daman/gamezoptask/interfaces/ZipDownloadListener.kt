package com.singh.daman.gamezoptask.interfaces

import java.io.File

/**
 * Created by Daman on 4/1/2018.
 */
interface ZipDownloadListener {
    fun onZipDownload(s: String)
    fun onUnZip(file: File)
}