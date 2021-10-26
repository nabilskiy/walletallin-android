package com.tallin.wallet.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.tallin.wallet.R
import com.tallin.wallet.ui.widgets.webView.MyWebView
import com.tallin.wallet.utils.extensions.setTransparentLightStatusBar
import com.tallin.wallet.utils.extensions.setupFullScreen
import java.lang.Exception

class MainActivity : AppCompatActivity() {

    private var isActivePin: Boolean? = null
    var showPin: Boolean = true

    var skipPin: Boolean = false
    //var currentFragment: Fragment = getActivity().getFragmentManager().findFragmentById(R.id.navHostFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        try {
            setTransparentLightStatusBar()
            setupFullScreen()
        } catch (e: Exception) {

        }

    }


    override fun onStart() {
        super.onStart()
        try {
            if (isActivePin == true) {
                Log.e("!!!", "isActive")
                Navigation.findNavController(this, R.id.navHostFragment)
                    .navigate(R.id.action_pin_code)
            }
        } catch (e: Exception) {

        }
    }


    override fun onPause() {
        super.onPause()

        try {

            val currentFragment =
                Navigation.findNavController(this, R.id.navHostFragment).currentDestination?.label
            isActivePin =
                when(currentFragment){
                    "LoginFragment",
                    "SignUpFragment",
                    "SignUpBusinessFragment",
                    "ChooseWalletFragment"-> false

                    "BottomActivityy" -> if (skipPin){ skipPin = false; false }else showPin
                    else -> showPin

                }
        } catch (e: Exception) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (requestCode != MyWebView.INPUT_FILE_REQUEST_CODE || MyWebView.mFilePathCallback == null) {
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            var results: Array<Uri>? = null
            if (resultCode == RESULT_OK) {
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
                super.onActivityResult(requestCode, resultCode, data)
                return
            }
            if (requestCode == MyWebView.FILECHOOSER_RESULTCODE) {
                if (null == MyWebView.mUploadMessage) {
                    return
                }
                var result: Uri? = null
                try {
                    result = if (resultCode != RESULT_OK) {
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

    fun safr(intent: Intent,
             requestCode: Int){
        skipPin = true
        startActivityForResult(intent, requestCode)
    }
}
