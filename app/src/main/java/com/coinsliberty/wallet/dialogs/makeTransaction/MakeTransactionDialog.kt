package com.coinsliberty.wallet.dialogs.makeTransaction


import android.annotation.SuppressLint
import android.app.Activity
import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.Rates
import com.coinsliberty.wallet.dialogs.sendDialog.BARCODE_EXTRA
import com.coinsliberty.wallet.dialogs.sendDialog.ScanQRcodeActivity
import com.coinsliberty.wallet.utils.extensions.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.bottom_sheet_make_transfer.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException


private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"
private const val keyBundleData = "qr"
private const val keyBundleLoginData = "login"
private const val keyBundleLink = "link"


const val REQUEST_CODE_SCAN = 101
private val REQUEST_IMAGE_CAPTURE = 1
private val REQUEST_CODE = 333
private val REQUEST_SCAN = 222


class MakeTransactionDialog : BottomSheetDialogFragment() {
    //private var mListener: ItemClickListener? = null
    val layoutRes: Int = R.layout.bottom_sheet_make_transfer
    val viewModel: MakeTransactionViewModel by viewModel()

    var listener: ((Boolean, String) -> Unit)? = null

    private var rates: Rates? = null

    private var isSend: Boolean = true
    private var barcode = ""
    private lateinit var cardPhotoPath: String

    private var qrCode: String? = null
    private var login: String? = null
    private var data: EditProfileRequest? = null

    override fun getTheme(): Int = R.style.SendDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet_make_transfer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tittle = arguments?.getString(keyBundleTittle)

        dialogReceive()
        dialogSend()

        clSendSpinner.setOnClickListener {
            showPopupMenu()
        }

        viewModel.updateData()


        switchDialog.changeStatus(isSend)
        switchDialog.setOnClickListener {
            if (isSend) {
                switchDialog.changeStatus(false)
                //RECEIVE
                isSend = false
                clDialogSend.invisible()
                clDialogReceive.visible()
                layoutBottomSheetMakeTransfer.setBackgroundResource(R.drawable.bg_dialog_blue)
                tvTittle.text = "RECEIVE $tittle"
            } else {
                switchDialog.changeStatus(true)
                //SEND
                isSend = true
                clDialogSend.visible()
                clDialogReceive.invisible()
                layoutBottomSheetMakeTransfer.setBackgroundResource(R.drawable.bg_dialog_green)
                tvTittle.text = "SEND $tittle"
            }
        }
        subscribeLiveData()
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), clSendSpinner)
        popupMenu.inflate(R.menu.fee_menu)

        popupMenu
            .setOnMenuItemClickListener { item ->
                ivText.text = item.title
                when (item.itemId) {
                    R.id.slow -> {
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.min.toString())
                        true
                    }
                    R.id.medium -> {
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.mid.toString())
                        true
                    }
                    R.id.fast -> {
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.max.toString())
                        true
                    }
                    R.id.custom -> {
                        tvAmountSatPerByte.enable()
                        true
                    }
                    else -> false
                }
            }

        popupMenu.show()
    }

    private fun dialogReceive() {
        qrCode = arguments?.getString(keyBundleLink)
        login = arguments?.getString(keyBundleLoginData)
        data = arguments?.getParcelable(keyBundleData)
        tvLinkReceive.text = qrCode
        ivQrCodeReceive.setImageBitmap(
            arguments?.getString(keyBundleLink)
                ?.let { create("otpauth://totp/CoinsLiberty:$login?secret=$qrCode&period=30&digits=6&algorithm=SHA1&issuer=Testing") })

        btnUpdate.setOnClickListener {
           // viewModel.updateProfile(data?.apply { otp = ifc2FA.getMyText() })
        }
        ivCopy.setOnClickListener {
            val clipboard =
                context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
            val clip = ClipData.newPlainText("Copied Text", tvLinkReceive.text.toString())
            clipboard.setPrimaryClip(clip)

            Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
        }

    }


    private fun dialogSend() {
        val rates = arguments?.getDouble(keyBundleRates)
        val bundle = arguments?.getDouble(keyBundleBalance)
        val result = bundle!! * rates!!

        tvAmountCripto.setText(String.format("%.8f", bundle))
        tvAmountFiat.text = String.format("%.2f", result)


        tvAmountCripto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvAmountFiat.text =
                    String.format("%.2f", ((s.toString().toDoubleOrNull() ?: 0.0) * rates))
            }
        })

        ivQrCode.setOnClickListener { barcodeScan() }

        ivClose.setOnClickListener { dismiss() }

        btnSentCoin.setOnClickListener {
            viewModel.sendBtc(
                "btc",
                tvAmountCripto.text.toString(),
                tvLink.text.toString(),
                etCL2FA.text
            )
            //listener?.invoke(tvAmountCripto.text.toString() != "" && tvAmountFiat.text.toString() != "")
        }
    }

    fun initListeners(onChoosen: (Boolean, String) -> Unit) {
        listener = onChoosen
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::initResult)
        bindDataTo(viewModel.feeInit, ::initFee)
    }

    private fun initFee(rates: Rates?) {
        this.rates = rates
        tvAmountSatPerByte.disable()
        tvAmountSatPerByte.setText(rates?.min.toString())
    }

    private fun initResult(b: Boolean?) {
        listener?.invoke(b == true, tvAmountCripto.text.toString())
    }

    @SuppressLint("QueryPermissionsNeeded")
    private fun cardPhotoCaptureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            //perform check is there is an app that can handle this intent -> in not - just close
            takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                var photoFile: File? = null
                try {
                    photoFile = createImageFile()
                } catch (e: IOException) {
                    Toast.makeText(context, "error whle creating file", Toast.LENGTH_SHORT).show()
                }
                if (photoFile != null) {
                    val photoUri: Uri = FileProvider.getUriForFile(
                        requireContext(),
                        "com.coinsliberty.provider",
                        photoFile
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
                }
            }
        }
    }

    private fun createImageFile(): File {
        val file = File(
            (activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES))?.absolutePath
                    + "IMG_${barcode}.jpg"
        )
        cardPhotoPath = file.absolutePath
        return file
    }


    private fun requestPermissionAndCapturePhoto() {
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
            cardPhotoCaptureIntent() //if granted
        } else {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQUEST_CODE
            ) //request
        }
    }

    private fun barcodeScan() {
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

            startActivityForResult(
                Intent(context, ScanQRcodeActivity::class.java),
                REQUEST_CODE_SCAN
            )

        } else {
            requestPermissions(
                arrayOf(
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQUEST_SCAN
            ) //request
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                cardPhotoCaptureIntent()
            } else {
                requestPermissionAndCapturePhoto()
            }
        } else if (requestCode == REQUEST_SCAN) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                barcodeScan()
            } else {
                barcodeScan()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {

            tvLinkReceive.text = data?.extras?.getString(BARCODE_EXTRA) ?: ""
        }
    }

    private fun create(text: String): Bitmap? {
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
        const val TAG = "MakeTransaction"
        fun newInstance(
            tittle: String,
            rates: Double,
            balance: Double,
            data: EditProfileRequest?,
            link: String,
            login: String
        ): MakeTransactionDialog {

            val dialog = MakeTransactionDialog()
            val bundle = bundleOf(
                keyBundleTittle to tittle,
                keyBundleRates to rates,
                keyBundleBalance to balance,
                keyBundleData to data,
                keyBundleLink to link,
                keyBundleLoginData to login
            )
            dialog.arguments = bundle
             dialog.setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
            return dialog
        }
    }

}