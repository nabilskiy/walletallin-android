package com.tallin.wallet.dialogs.secureCode

import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinDialogFragment
import com.tallin.wallet.data.EditProfileRequest
import com.tallin.wallet.utils.extensions.bindDataTo
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.dialog_secure_code.*
import org.koin.android.viewmodel.ext.android.viewModel

private const val keyBundleData = "qr"
private const val keyBundleLoginData = "login"
private const val keyBundleLink = "link"

class SecureCodeDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_secure_code
    override val viewModel: SecureCodeViewModel by viewModel()

    private var qrCode: String? = null
    private var login: String? = null
    private var data: EditProfileRequest? = null

    var listener: ((Boolean) -> Unit)? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        qrCode = arguments?.getString(keyBundleLink)
        login = arguments?.getString(keyBundleLoginData)
        data = arguments?.getParcelable(keyBundleData)
        tvLink.text = qrCode
        ivQrCode.setImageBitmap(
            arguments?.getString(keyBundleLink)
                ?.let { create("otpauth://totp/CoinsLiberty:$login?secret=$qrCode&period=30&digits=6&algorithm=SHA1&issuer=Testing") })

        ivClose.setOnClickListener {
            listener?.invoke(false)
            dismiss()
        }

        btnUpdate.setOnClickListener {
            viewModel.updateProfile(data)
        }
        ivCopy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", tvLink.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
        }



        subscribeLiveData()
    }

    fun initListeners(onChoosen: (Boolean) -> Unit) {
        listener = onChoosen
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultRecovery, ::updateProfile)
    }

//    private fun setAddress(addressInfo: AddressInfoResponse) {
//        if (addressInfo.result == true) {
//            tvLink.text = addressInfo.address
//            ivQrCode.setImageBitmap(addressInfo.address.toString().let { create(it) })
//        }
//    }

    fun updateProfile(b: Boolean) {
        listener?.invoke(b)
        if(b) {
            dismiss()
        }
    }


    fun create(text: String): Bitmap? {
        val writer = QRCodeWriter()
        return try {
            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
            val width = bitMatrix.width
            val height = bitMatrix.height
            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (x in 0 until width) {
                for (y in 0 until height) {
                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
                }
            }
            bmp

        } catch (e: WriterException) {
            null
        }
    }

    companion object {
        val TAG: String = SecureCodeDialog::class.java.name
        fun newInstance(data: EditProfileRequest, link: String, login: String): SecureCodeDialog {
            val fragment = SecureCodeDialog()
            val bundle = bundleOf(
                keyBundleData to data,
                keyBundleLink to link,
                keyBundleLoginData to login
            )
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogBlueBG)
            return fragment
        }
    }
}