package com.coinsliberty.wallet.dialogs.secureCode

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_undo_secure_code.*
import kotlinx.android.synthetic.main.dialog_undo_secure_code.btnUpdate
import kotlinx.android.synthetic.main.dialog_undo_secure_code.ivClose
import org.koin.android.viewmodel.ext.android.viewModel

private const val keyBundleData = "qr"
private const val keyBundleLoginData = "login"
private const val keyBundleLink = "link"

class UndoSecureCodeDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_undo_secure_code
    override val viewModel: SecureCodeViewModel by viewModel()

    private var data: EditProfileRequest? = null

    var listener: ((Boolean) -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        data = arguments?.getParcelable(keyBundleData)

        ivClose.setOnClickListener {
            listener?.invoke(true)
            dismiss()
        }

        btnUpdate.setOnClickListener {
            viewModel.updateProfile(data)
        }

        subscribeLiveData()
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultRecovery, ::updateProfile)
    }

    fun updateProfile(b: Boolean) {
        listener?.invoke(!b)
        if(b) {
            dismiss()
        }
    }

    companion object {
        val TAG: String = UndoSecureCodeDialog::class.java.name
        fun newInstance(data: EditProfileRequest): UndoSecureCodeDialog {
            val fragment = UndoSecureCodeDialog()
            val bundle = bundleOf(
                keyBundleData to data
            )
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGrayBG)
            return fragment
        }
    }
}