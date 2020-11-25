package com.coinsliberty.wallet.dialogs.sendDialog


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.core.os.bundleOf
import org.koin.android.viewmodel.ext.android.viewModel
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinDialogFragment
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.dialog_send.*
import java.io.File
import java.io.IOException

private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"


const val REQUEST_CODE_SCAN = 101
private val REQUEST_IMAGE_CAPTURE = 1
private val REQUEST_CODE = 333
private val REQUEST_SCAN =222

class SendDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_send
    override val viewModel: SendBtcViewModel by viewModel()

    var listener: ((Boolean, String) -> Unit)? = null

    private var barcode = ""
    private lateinit var cardPhotoPath: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    fun initListeners(onChoosen: (Boolean, String) -> Unit) {
        listener = onChoosen
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTittle)
        val rates = arguments?.getDouble(keyBundleRates)
        val bundle = arguments?.getDouble(keyBundleBalance)
        val result = bundle!! * rates!!

        tvAmountCripto.setText(String.format("%.8f", bundle))
        tvAmountFiat.text = String.format("%.2f", result)

        tvAmountCripto.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) = Unit

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                tvAmountFiat.text = String.format("%.2f", ((s.toString().toDoubleOrNull() ?: 0.0) * rates))
            }
        })

        ivQrCode.setOnClickListener {
            barcodeScan()
        }

        ivClose.setOnClickListener { dismiss() }

        btnSentCoin.setOnClickListener {
            viewModel.sendBtc("btc", tvAmountCripto.text.toString(), tvLink.text.toString(), etCL2FA.text)
            //listener?.invoke(tvAmountCripto.text.toString() != "" && tvAmountFiat.text.toString() != "")
        }
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::initResult)
    }

    private fun initResult(b: Boolean?) {
        listener?.invoke(b == true, tvAmountCripto.text.toString())
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
        val file = File((activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES))?.absolutePath
                + "IMG_${barcode}.jpg")
        cardPhotoPath = file.absolutePath
        return file
    }


    private fun requestPermissionAndCapturePhoto() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            cardPhotoCaptureIntent() //if granted
        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_CODE) //request
        }
    }

    private fun barcodeScan() {
        if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

            startActivityForResult(Intent(context, ScanQRcodeActivity::class.java), REQUEST_CODE_SCAN)

        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE), REQUEST_SCAN) //request
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                cardPhotoCaptureIntent()
            } else {
                requestPermissionAndCapturePhoto()
            }
        } else if(requestCode == REQUEST_SCAN) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                barcodeScan()
            } else {
                barcodeScan()

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK) {

            tvLink.setText(data?.extras?.getString(BARCODE_EXTRA) ?: "")
        }
    }

    companion object {
        val TAG: String = SendDialog::class.java.name
        fun newInstance(
            tittle: String, rates: Double, balance: Double
        ): SendDialog {
            val fragment = SendDialog()
            val bundle = bundleOf(
                keyBundleTittle to tittle,
                keyBundleRates to rates,
                keyBundleBalance to balance
            )
            fragment.arguments = bundle
            fragment.setStyle(STYLE_NO_TITLE, R.style.DialogGreenBG)
            return fragment
        }
    }
}