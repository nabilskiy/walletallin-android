package com.tallin.wallet.ui.widgets.webView

import android.annotation.SuppressLint
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.AttributeSet
import android.webkit.*
import androidx.navigation.Navigation
import com.tallin.wallet.R
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.kyc.webView.KYCWebViewFragment
import com.tallin.wallet.ui.profile.ProfileFragment
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*


class MyWebView : WebView {

    companion object {
        var mUploadMessage: ValueCallback<Uri>? = null
        var mFilePathCallback: ValueCallback<Array<Uri>>? = null
        var mCameraPhotoPath: String? = null
        var mCapturedImageURI: Uri? = null
        const val INPUT_FILE_REQUEST_CODE = 1
        const val FILECHOOSER_RESULTCODE = 1

    }
    var activity: KYCWebViewFragment? = null

    constructor(context: Context) : super(context) {
        initDefaultSetting()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initDefaultSetting()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initDefaultSetting()
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initDefaultSetting() {
        this.settings.apply {
            allowFileAccess = true
            cacheMode = WebSettings.LOAD_NO_CACHE
            mixedContentMode = 0
            javaScriptEnabled = true
            loadWithOverviewMode = true
            useWideViewPort = true
            loadWithOverviewMode = true
            domStorageEnabled = true
            setSupportZoom(false)
            builtInZoomControls = true
        }
        webChromeClient = MyWebChromeClient()
        webViewClient = MyWebViewClient()
    }

    /**
     * Load Web View with url
     */
    fun load(url: String, activity: KYCWebViewFragment) {
        this.activity = activity
        this.loadUrl(url)
    }

    var pageFinishedListener: ((url: String?) -> Unit)? = null

    internal inner class MyWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            println("shouldOverrideUrlLoading")
            view.loadUrl(request.url.toString())
            pageFinishedListener?.invoke(request.url.toString())
            return true
        }
    }
    internal inner class MyWebChromeClient : WebChromeClient() {
        override fun onShowFileChooser(
            view: WebView?,
            filePath: ValueCallback<Array<Uri>>,
            fileChooserParams: FileChooserParams?
        ): Boolean {
            if (mFilePathCallback != null) {
                mFilePathCallback!!.onReceiveValue(null)
            }
            mFilePathCallback = filePath
            var takePictureIntent: Intent? = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent!!.resolveActivity(ContextWrapper(context).packageManager) != null) {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                    takePictureIntent.putExtra("PhotoPath", mCameraPhotoPath)
                } catch (ex: IOException) {
                }
                if (photoFile != null) {
                    mCameraPhotoPath = "file:" + photoFile.absolutePath
                    takePictureIntent.putExtra(
                        MediaStore.EXTRA_OUTPUT,
                        Uri.fromFile(photoFile)
                    )
                } else {
                    takePictureIntent = null
                }
            }
            val contentSelectionIntent = Intent(Intent.ACTION_GET_CONTENT)
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE)
            contentSelectionIntent.type = "image/*"
            val intentArray: Array<Intent?>
            intentArray = takePictureIntent?.let { arrayOf(it) } ?: arrayOfNulls(0)
            val chooserIntent = Intent(Intent.ACTION_CHOOSER)
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent)
            chooserIntent.putExtra(Intent.EXTRA_TITLE, "Image Chooser")
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, intentArray)
            activity?.safrForWV(chooserIntent, INPUT_FILE_REQUEST_CODE)
            return true
        }

        @Throws(IOException::class)
        private fun createImageFile(): File? {
            val timeStamp =
                SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
            val imageFileName = "JPEG_" + timeStamp + "_"
            val storageDir = Environment.getExternalStoragePublicDirectory(
                Environment.DIRECTORY_PICTURES
            )
            return File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
            )
        }
    }
}