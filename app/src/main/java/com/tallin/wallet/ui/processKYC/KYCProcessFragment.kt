package com.tallin.wallet.ui.processKYC

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_kyc_process.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class KYCProcessFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc_process
    override val viewModel: KYCProcessViewModel by viewModel()
    override val navigator: KYCProcessNavigation = get()

    private var externalId = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()

        tvBack.setOnClickListener { activity?.onBackPressed() }
        btnLater.setOnClickListener { activity?.onBackPressed() }
        btnContinue.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                viewModel.getKycLink()
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_CODE
                )
            }
        }

        webView.setWebViewClient(object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                if (url == "https://wallet-stage.walletallin.com/getid/complete?externalId=$externalId" ||
                    url == "https://wallet-stage.walletallin.com/getid/error?error_code={errorCode}&externalId={externalId}") {
                    activity?.onBackPressed()
                }
            }
        })
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }

    private fun showResult(s: Array<String>?) {
        if (s != null) {
           /* val starter = Intent(context, WVActivity::class.java)
            starter.putExtra("url", s)
            requireContext().startActivity(starter)*/

            webView.visible()
            webView.load(s[0], activity as MainActivity)
            parent.gone()
            externalId = s[1]
        }
    }
}