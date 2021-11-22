package com.tallin.wallet.ui.kyc.processKYC

import android.os.Bundle
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_kyc_process.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class KYCProcessFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc_process
    override val viewModel: KYCProcessViewModel by viewModel()
    override val navigator: KYCProcessNavigation = get()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //subscribeLiveData()

        tvBack.setOnClickListener { activity?.onBackPressed() }
        btnLater.setOnClickListener { activity?.onBackPressed() }
        btnContinue.setOnClickListener {

            navigator.goToKyc(navController)
            /*if (ContextCompat.checkSelfPermission(
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
                if (!flowName.isNullOrBlank()) {
                    viewModel.getKycLink(flowName)
                }
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_CODE
                )
            }*/
        }

       /* webView.webViewClient = object : WebViewClient() {
            override fun onLoadResource(view: WebView?, url: String?) {
                if (url == "https://wallet-stage.walletallin.com/getid/complete?externalId=$externalId" ||
                    url == "https://wallet-stage.walletallin.com/getid/error?error_code={errorCode}&externalId={externalId}") {
                    activity?.onBackPressed()
                }
            }
        }*/
    }

    /*private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }

    private fun showResult(s: Array<String>?) {
        if (s != null) {

           /* val starter = Intent(context, WVActivity::class.java)
            starter.putExtra("url", s)
            requireContext().startActivity(starter)*/

        /*    webView.visible()
            webView.load(s[0], activity as MainActivity)
            parent.gone()
            externalId = s[1]*/
        }
    }*/
}