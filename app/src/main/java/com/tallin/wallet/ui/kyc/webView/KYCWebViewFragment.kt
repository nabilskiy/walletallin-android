package com.tallin.wallet.ui.kyc.webView

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.widgets.webView.MyWebView
import kotlinx.android.synthetic.main.fragment_kyc_webview.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception

class KYCWebViewFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc_webview
    override val viewModel: KYCWebViewViewModel by viewModel()
    override val navigator: KYCWebViewNavigation = get()

    private var externalId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val link = arguments?.getString("[KYC]link")
        val externalId = arguments?.getString("[KYC]externalId")
        if (!link.isNullOrBlank() && !externalId.isNullOrBlank() && !(activity as MainActivity).wvStarted){
            webView.load(link, /*activity as MainActivity*/this)
            this.externalId = externalId
            (activity as MainActivity).wvStarted = true
        } else {
            navigator.exitToSetting(navController)
        }
        /*webView.load(s[0], activity as MainActivity)
        externalId = s[1]*/

        webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {

                if (url == "https://wallet-stage.walletallin.com/getid/complete?externalId=$externalId" ||
                    url == "https://wallet-stage.walletallin.com/getid/error?error_code={errorCode}&externalId={externalId}") {
                    //activity?.onBackPressed()
                    navigator.exitToSetting(navController)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != MyWebView.INPUT_FILE_REQUEST_CODE || MyWebView.mFilePathCallback == null) {
                (activity as MainActivity).showPin = true
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null
            if (resultCode == AppCompatActivity.RESULT_OK) {
                if (data == null) {
                    if (MyWebView.mCameraPhotoPath != null) {
                        results = arrayOf(Uri.parse(MyWebView.mCameraPhotoPath))
                    }
                } else {
                    val dataString = data.dataString
                    if (dataString != null) {
                        results = arrayOf(Uri.parse(dataString))
                    }
                }
            }
            if (MyWebView.mFilePathCallback != null) {
                MyWebView.mFilePathCallback!!.onReceiveValue(results)
            }
            MyWebView.mFilePathCallback = null
        } else if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.KITKAT) {
            if (requestCode != MyWebView.FILECHOOSER_RESULTCODE || MyWebView.mUploadMessage == null) {
                (activity as MainActivity).showPin = true
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            if (requestCode == MyWebView.FILECHOOSER_RESULTCODE) {
                if (null == MyWebView.mUploadMessage) {
                    return
                }
                var result: Uri? = null
                try {
                    result = if (resultCode != AppCompatActivity.RESULT_OK) {
                        null
                    } else {
                        if (data == null) MyWebView.mCapturedImageURI else data.data
                    }
                } catch (e: Exception) {
                    /*  Toast.makeText(
                          applicationContext, "activity :$e",
                          Toast.LENGTH_LONG
                      ).show()*/
                }
                if (MyWebView.mUploadMessage != null) {
                    MyWebView.mUploadMessage!!.onReceiveValue(result)
                }
                MyWebView.mUploadMessage = null
            }
        }
        return
    }

    fun safrForWV(intent: Intent,
                  requestCode: Int){
        (activity as MainActivity).showPin = false
        //skipPin = true
        startActivityForResult(intent, requestCode)
    }

}