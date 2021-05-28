package com.tallin.wallet.dialogs.qrCode

import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinDialogFragment
import com.tallin.wallet.utils.extensions.bindDataTo
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.dialog_qr_code.ivClose
import kotlinx.android.synthetic.main.dialog_qr_code.ivCopy
import kotlinx.android.synthetic.main.dialog_qr_code.ivQrCode
import kotlinx.android.synthetic.main.dialog_qr_code.tvLink
import kotlinx.android.synthetic.main.dialog_qr_code.tvTittle
import org.koin.android.viewmodel.ext.android.viewModel

private const val keyBundleTitle = "title"
private const val keyBundleLink = "link"

class QrCodeDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_qr_code
    override val viewModel: QrCodeViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true

       viewModel.getAddress()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTitle)
        ivQrCode.setImageBitmap(arguments?.getString(keyBundleLink)?.let { create(it) })

        ivClose.setOnClickListener { dismiss() }
        ivCopy.setOnClickListener {
            val clipboard = context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", tvLink.text.toString())
            clipboard.setPrimaryClip(clip)
            
            Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
        }



        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultRecovery, ::create)
    }

//    private fun setAddress(addressInfo: AddressInfoResponse) {
//        if (addressInfo.result == true) {
//            tvLink.text = addressInfo.address
//            ivQrCode.setImageBitmap(addressInfo.address.toString().let { create(it) })
//        }
//    }


    fun create(text: String): Bitmap? {
        tvLink.text = text
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
        val TAG: String = QrCodeDialog::class.java.name
        fun newInstance(title: String, link: String): DialogFragment {
            val fragment = QrCodeDialog()
            val bundle = bundleOf(keyBundleTitle to title, keyBundleLink to link)
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogBlueBG)
            return fragment
        }
    }
}