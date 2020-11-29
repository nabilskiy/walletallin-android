package com.coinsliberty.wallet.dialogs.makeTransaction


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
import android.util.Log
import android.view.LayoutInflater
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
import com.coinsliberty.wallet.data.response.SendMaxResponse
import com.coinsliberty.wallet.dialogs.AcceptDialog
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.sendDialog.BARCODE_EXTRA
import com.coinsliberty.wallet.dialogs.sendDialog.ScanQRcodeActivity
import com.coinsliberty.wallet.ui.MainActivity
import com.coinsliberty.wallet.utils.extensions.*
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.qrcode.QRCodeWriter
import kotlinx.android.synthetic.main.bottom_sheet_make_transfer.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import kotlin.math.abs


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
    val layoutRes: Int = R.layout.bottom_sheet_make_transfer
    val viewModel: MakeTransactionViewModel by viewModel()

    var listener: ((Boolean, String) -> Unit)? = null

    var itemRate: Int = 0

    private lateinit var cardPhotoPath: String
    private var rates: Rates? = null
    private var isSend: Boolean = true
    private var barcode = ""
    private var qrCode: String? = null
    private var login: String? = null
    private var data: EditProfileRequest? = null
    private var isChangeEtAmountCrypto = true

    var addressForSend: String = " "
    var balanceForSend: String = " "
    var refactorUsd = false
    var ress: Boolean? = null

    override fun getTheme(): Int = R.style.SendDialog

    var cryptoValueChanged: Boolean = false
    var usdValueChanged: Boolean = false

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
        tvTittle.text = "SEND $tittle"

        viewModel.updateData()

        dialogReceive()
        dialogSend()

        clSendSpinner.setOnClickListener {
            showPopupMenu()
        }

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
                        itemRate = 0
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.min.toString())
                        true
                    }
                    R.id.medium -> {
                        itemRate = 1
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.mid.toString())
                        true
                    }
                    R.id.fast -> {
                        itemRate = 2
                        tvAmountSatPerByte.disable()
                        tvAmountSatPerByte.setText(rates?.max.toString())
                        true
                    }
                    R.id.custom -> {
                        itemRate = 3
                        tvAmountSatPerByte.enable()
                        tvAmountSatPerByte.setSelection(tvAmountSatPerByte.text.length)
                        tvAmountSatPerByte.requestFocus()
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

        tvBTCTransferResult.text = String.format("%.2f", result) + " $"
        etAmountCripto.setText(String.format("%.8f", bundle))
        etAmountFiat.setText(String.format("%.2f", result))
        balanceForSend = etAmountFiat.text.toString() + " $"

        etAmountCripto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                val amountFiat = String.format("%.2f", ((s.toString().replace(",", ".", true).toDoubleOrNull() ?: 0.0) * rates))

                if (!cryptoValueChanged) {
                    usdValueChanged = true
                    etAmountFiat.setText(amountFiat)
                } else {
                    cryptoValueChanged = false
                    usdValueChanged = false
                }
            }
        })
        etAmountFiat.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val amountCrypto = String.format(
                    "%.8f",
                    ((s.toString().replace(",", ".", true).toDoubleOrNull() ?: 0.0) / rates)
                )

                if((s.toString().replace(",", ".", true).split(".").getOrNull(1)?.length ?: 0) > 2) {
                    refactorUsd = true
                    val splitValue = s.toString().replace(",", ".", true).split(".")
                    formantText(
                        splitValue[0] + "." + splitValue.getOrNull(1)?.substring(0,2)
                    )
                    return
                } else if(refactorUsd) {
                    refactorUsd = false
                    return
                }

                if (!usdValueChanged) {
                    cryptoValueChanged = true
                    etAmountCripto.setText(amountCrypto)
                } else {
                    cryptoValueChanged = false
                    usdValueChanged = false
                }
            }
        })
        tvAmountSatPerByte.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
                Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                if(s.isNullOrEmpty()) {
                    return
                }

                if(s.toString().toLong() > 500) {
                    tvAmountSatPerByte.setText("500")
                    tvAmountSatPerByte.setSelection(3)
                }
                tvBTCTransferResult.text = String.format(
                    "%.2f",
                    (240 * (s.toString().toLong())).toDouble() / 100000000.0 * rates
                ) + " USD"
            }
        })

        ivQrCode.setOnClickListener {
            (activity as MainActivity).showPin = false
            barcodeScan()
        }

        ivClose.setOnClickListener { dismiss() }

        btnSentCoin.setOnClickListener {
            viewModel.sendBtc(
                "btc",
                etAmountCripto.text.toString(),
                tvLink.text.toString(),
                etCL2FA.text,
                tvAmountSatPerByte.text.toString()
            )

            if (ress == true) {
                getAcceptDialog(balanceForSend, addressForSend)
            } else if (ress == false) {
                getErrorDialog(viewModel.messageError.value.toString())
            }
        }

        tvSendMax.setOnClickListener {
            sendMax()
        }
    }

    fun sendMax() {
        Log.e("!!!", "send max")
        val rate: String = tvAmountSatPerByte.text.toString()
        viewModel.sendMax("btc", rate)
    }

    fun initListeners(onChoosen: (Boolean, String) -> Unit) {
        listener = onChoosen
    }

    private fun getErrorDialog(value: String) {
        ErrorDialog.newInstance(value)
            .show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun getAcceptDialog(balance: String, link: String) {
        AcceptDialog.newInstance(balance, link)
            .show(parentFragmentManager, AcceptDialog.TAG)
    }

    private fun formantText(text: String) {
        etAmountFiat.setText(text)
        etAmountFiat.setSelection(text.length)
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::initResult)
        bindDataTo(viewModel.feeInit, ::initFee)
        bindDataTo(viewModel.resultRecovery, ::setAddress)
        bindDataTo(viewModel.maxAvailable, ::sendMax)

    }

    private fun sendMax(response: SendMaxResponse) {
        if (response.result == true) {
            etAmountCripto.setText(String.format("%.8f", response.withdrawData?.available))
            val rs: Double =
                response.withdrawData?.available!! * arguments?.getDouble(keyBundleRates)!!
            etAmountFiat.setText(String.format("%.2f", rs))
        }
    }

    private fun setAddress(link: String) {
        tvLinkReceive.text = link
        ivQrCodeReceive.setImageBitmap(create(link))
        addressForSend = link
    }

    private fun initFee(rates: Rates?) {
        Log.e("!!!", rates.toString())
        this.rates = rates

        when (itemRate) {
            0 -> {
                //tvAmountSatPerByte.disable()
                tvAmountSatPerByte.setText(rates?.min.toString())
            }
            1 -> {
                //tvAmountSatPerByte.disable()
                tvAmountSatPerByte.setText(rates?.mid.toString())
            }
            2 -> {
                //tvAmountSatPerByte.disable()
                tvAmountSatPerByte.setText(rates?.max.toString())
            }
        }

        viewModel.refreshFee()
    }

    private fun initResult(b: Boolean?) {
        listener?.invoke(b == true, etAmountCripto.text.toString())
        ress = b
    }

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
            (activity as MainActivity).showPin = true
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