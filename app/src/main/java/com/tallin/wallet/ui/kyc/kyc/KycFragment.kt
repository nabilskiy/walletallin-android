package com.tallin.wallet.ui.kyc.kyc

import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseAdapter
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.ProfileResponse
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.tallin.wallet.ui.kyc.adapters.ItemHolder
import com.tallin.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_kyc.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class KycFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc
    override val viewModel: KycViewModel by viewModel()
    override val navigator: KycNavigation = get()

    private var adapter: BaseAdapter? = null

    private var flowName: String? = null

    override fun onStart() {
        super.onStart()
        viewModel.result.value = null
        adapter = BaseAdapter()
            .map(R.layout.item_kyc, ItemHolder( { type, flowName, id, docId ->
                if (!type.isNullOrBlank())
                when (type){
                    "manually" -> {
                        if (id != null && docId != null)
                            goToKycManually(id, docId)
                    }
                    "getId" -> {
                        if (!flowName.isNullOrBlank()) {
                            this.flowName = flowName
                            goToKycProcess(flowName)
                        }
                    }
                }
            }, context))
        rvItemKYC.adapter = null
        adapter!!.removeAll()
        rvItemKYC.adapter = adapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()
        viewModel.getProfile()

        ivBack.setOnClickListener { activity?.onBackPressed() }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.ldProfile, ::showResultGetProfile)
        bindDataTo(viewModel.result, ::showResultLink)
    }

    private fun showResultGetProfile(b: ProfileResponse?) {
        if (b != null) {
            setAdapter()
        }
    }

    private fun setAdapter() {
        if (viewModel.sharedPreferencesProvider.getUser()?.kycProgram?.kycDocuments != null)
            adapter!!.itemsLoaded(viewModel.sharedPreferencesProvider.getUser()!!.kycProgram!!.kycDocuments)
       // navigator.goToContent(navController)
    }

    private fun goToKycProcess(flowName: String){
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
            if (flowName.isNotBlank()) {
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
        }
    }
    private fun goToKycManually(id: Int, docId: Int){
        navigator.goToKycManually(navController, id, docId)
    }

    private fun showResultLink(s: Array<String>?) {
        if (s != null/* && flowName != null*/) {
            navigator.goToKycProcess(navController, s[0], s[1])
        }
    }

    /*override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (!flowName.isNullOrBlank()) {
            viewModel.getKycLink(flowName!!)
        }
    }*/
}