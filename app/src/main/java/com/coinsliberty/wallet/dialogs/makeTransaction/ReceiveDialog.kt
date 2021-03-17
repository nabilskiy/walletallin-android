package com.coinsliberty.wallet.dialogs.makeTransaction

import android.content.ClipData
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.bottom_sheet_transaction_receive.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*


private const val keyBundleTittle = "tittle"

class ReceiveDialog : BottomSheetDialogFragment() {
    val layoutRes: Int = R.layout.bottom_sheet_transaction_receive
    val viewModel: MakeTransactionViewModel by viewModel()

    override fun getTheme(): Int = R.style.SendDialog


    private lateinit var walletType: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_transaction_receive, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        walletType = arguments?.getString(keyBundleTittle, "BTC")!!
        val title = "${getString(R.string.receive)} $walletType"
        tvTittle.text = title

        viewModel.updateData(walletType.toLowerCase(Locale.ROOT))
        subscribeLiveData()
        initListeners()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultRecovery, ::setAddress)
    }

    private fun initListeners() {
        ivClose.setOnClickListener { dismiss() }
        btnClose.setOnClickListener { dismiss() }
        tvLinkReceive.setOnClickListener {
            if (tvLinkReceive.text.isNotEmpty())
                copyLinkToClipboard(tvLinkReceive.text.toString())
        }
    }

    private fun setAddress(link: String) {
        tvLinkReceive.text = link
        ivQR.setImageBitmap(createQR(link))
        copyLinkToClipboard(link)
    }

    private fun copyLinkToClipboard(link: String) {
        val clipboard =
            context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
        val clip = ClipData.newPlainText("Copied Text", link)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
    }

    private fun createQR(text: String): Bitmap? {
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

    override fun onStart() {
        super.onStart()
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.balance_header_color)
    }

    companion object {
        val TAG = ReceiveDialog::class.java.name
        fun newInstance(
            title: String
        ): ReceiveDialog {
            val dialog = ReceiveDialog()
            val bundel = bundleOf(
                keyBundleTittle to title
            )
            dialog.arguments = bundel
            dialog.setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
            return dialog
        }
    }


}