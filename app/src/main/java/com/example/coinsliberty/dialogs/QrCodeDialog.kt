package com.example.coinsliberty.dialogs

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.dialog_qr_code.*
import kotlinx.android.synthetic.main.dialog_qr_code.ivClose
import kotlinx.android.synthetic.main.dialog_qr_code.tvLink
import org.koin.android.viewmodel.ext.android.viewModel
private const val keyBundleTitle = "title"
private const val keyBundleLink = "link"
class QrCodeDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_qr_code
    override val viewModel: StubViewModel by viewModel()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTitle)
        tvLink.text = arguments?.getString(keyBundleLink)
        ivQrCode.setImageBitmap(arguments?.getString(keyBundleLink)?.let { create(it) })
        ivClose.setOnClickListener { dismiss() }

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
        val TAG: String = QrCodeDialog::class.java.name
        fun newInstance(title: String, link: String): DialogFragment {
            val fragment = QrCodeDialog()
            val bundle = bundleOf(keyBundleTitle to title, keyBundleLink to link)
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}