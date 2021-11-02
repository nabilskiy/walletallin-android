package com.tallin.wallet.dialogs.makeTransaction

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.InputType
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
import com.tallin.wallet.R
import com.tallin.wallet.data.response.Rates
import com.tallin.wallet.data.response.SendMaxResponse
import com.tallin.wallet.databinding.BottomSheetMakeTransferCopyBinding
import com.tallin.wallet.dialogs.AcceptDialog
import com.tallin.wallet.dialogs.ErrorDialog
import com.tallin.wallet.dialogs.sendDialog.BARCODE_EXTRA
import com.tallin.wallet.dialogs.sendDialog.ScanQRcodeActivity
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.custom.CustomAmountEditText
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.showKeyboard
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet_make_transfer.*
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.util.*

private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"
private const val keyBundleData = "qr"
private const val keyBundleLoginData = "login"
private const val keyBundleLink = "link"
private const val keyBundleIco = "ico"
private const val keyBundleWalletColor = "color"
private const val keyBundleWalletCoefficient = "coefficient"

private const val MIN_FEE = 0
private const val MID_FEE = 1
private const val MAX_FEE = 2
private const val CUSTOM_FEE = 3


private const val REQUEST_CODE_SCAN = 101
private val REQUEST_IMAGE_CAPTURE = 1
val REQUEST_CODE = 333
val REQUEST_SCAN = 222

class SendDialog : BottomSheetDialogFragment() {

    private lateinit var binding: BottomSheetMakeTransferCopyBinding

    val layoutRes: Int = R.layout.bottom_sheet_make_transfer_copy
    val viewModel: MakeTransactionViewModel by viewModel()
    override fun getTheme(): Int = R.style.SendDialog

    var listener: ((Boolean, String) -> Unit)? = null


    private var rates: Rates = Rates()
    private lateinit var walletType: String
    private lateinit var fee: String
    private var rate: Double = 0.0
    private var selectedRateType: Int = MID_FEE
    private lateinit var currency: com.tallin.wallet.utils.currency.Currency
    private var coefficient: Double = 1.0

    private var address: String = ""
    private var amount: String = ""

    private lateinit var amountET: CustomAmountEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = BottomSheetMakeTransferCopyBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        subscribeLiveData()
        initData()
        viewModel.updateData(walletType.toLowerCase(Locale.ROOT))
        initListeners()
    }

    private fun initData() {
        Log.i(TAG, "initData: ${arguments?.getDouble(keyBundleBalance)}")
        amountET = CustomAmountEditText(binding.etAmount).also {
            it.underline = false
            it.walletColor = arguments?.getInt(keyBundleWalletColor) ?: 0
            it.balance = arguments?.getDouble(keyBundleBalance)
            it.amount = 0.0
            it.initListeners()
        }
        coefficient = arguments?.getDouble(keyBundleWalletCoefficient, 1.0) ?: 1.0
        currency = viewModel.getCurrency()
        walletType = arguments?.getString(keyBundleTittle, "BTC")!!
        rate = arguments?.getDouble(keyBundleRates) ?: 0.0
        binding.ivCrypto.setImageResource(arguments?.getInt(keyBundleIco) ?: 0)
        val title = "${getString(R.string.send)} $walletType"
        binding.tvTittle.text = title
        binding.tvAmountFiatName.text = currency.getTitle()
    }

    private fun initListeners() {
        binding.tvFees.setOnClickListener { showPopupMenu() }
        binding.btnQR.setOnClickListener {
            (activity as MainActivity).showPin = false
            barcodeScan()
        }
        binding.ivClose.setOnClickListener { dismiss() }
        binding.etAmount.addTextChangedListener(amountTextWatcher)
        binding.btnMax.setOnClickListener { sendMax() }
        binding.btnSend.setOnClickListener { send() }
    }

    private fun sendMax() {
        val rate = String.format(
            "%.8f",
            (binding.etNetworkFees.text.toString().replace(",", ".", true)
                .toDouble() / (arguments?.getDouble(keyBundleRates) ?: 1.0))
        )
        viewModel.sendMax(walletType.toLowerCase(Locale.ROOT), rate)
    }

    private fun send() {

        viewModel.sendBtc(
            walletType.toLowerCase(Locale.ROOT),
            amountET.amount.toString(),
            binding.etAddress.text.toString(),
            String.format(
                "%.8f",
                ((binding.etNetworkFees.text.toString().replace(",", ".")
                    .toDoubleOrNull())?.div((arguments?.getDouble(keyBundleRates) ?: 0.0)))
            ),
            binding.scReplaceable.isChecked
        )
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.feeInit, ::feeObserver)
        bindDataTo(viewModel.result, ::resultObserver)
        bindDataTo(viewModel.maxAvailable, ::sendMax)
        bindDataTo(viewModel.messageError, ::showToastError)
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), binding.tvFees)
        popupMenu.inflate(R.menu.fee_menu)

        popupMenu
            .setOnMenuItemClickListener { item ->
                binding.tvFees.text = item.title
                when (item.itemId) {
                    R.id.slow -> {
                        selectedRateType = MIN_FEE
                        binding.etNetworkFees.inputType = InputType.TYPE_NULL
                        binding.etNetworkFees.setText(
                            String.format(
                                "%.2f",
                                rates.min
                            )
                        )
                        true
                    }
                    R.id.medium -> {
                        selectedRateType = MID_FEE
                        binding.etNetworkFees.inputType = InputType.TYPE_NULL
                        binding.etNetworkFees.setText(
                            String.format(
                                "%.2f",
                                rates.mid
                            )
                        )
                        true
                    }
                    R.id.fast -> {
                        selectedRateType = MAX_FEE
                        binding.etNetworkFees.inputType = InputType.TYPE_NULL
                        binding.etNetworkFees.setText(
                            String.format(
                                "%.2f",
                                rates.max
                            )
                        )
                        true
                    }
                    R.id.custom -> {
                        selectedRateType = CUSTOM_FEE
                        binding.etNetworkFees.inputType = InputType.TYPE_NUMBER_FLAG_DECIMAL
                        binding.etNetworkFees.setSelection(binding.etAmount.text.toString().length)
                        binding.etNetworkFees.requestFocus()
                        binding.etNetworkFees.showKeyboard()
                        true
                    }
                    else -> false
                }
            }

        popupMenu.show()
    }

    private val amountTextWatcher =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun afterTextChanged(p0: Editable?) {
                p0 ?: return
                if (p0.isEmpty()) return
                val amount: Double = p0?.toString()?.toDouble() ?: 0.0
                val amountString = String.format("%.2f", amount.times(rate))
                binding.tvAmountFiat.text = amountString
            }
        }

    private fun feeObserver(rates: Rates) {

        Log.i(TAG, "feeObserver: ${String.format("%.10f", 10E9)}")
        this.rates = rates
//        Log.i(TAG, "feeObserver: $rates\n$rate")
        this.rates.min =
            (rates.min ?: 0.0) * coefficient * rate
        this.rates.mid =
            (rates.mid ?: 0.0) * coefficient * rate
        this.rates.max =
            (rates.max ?: 0.0) * coefficient * rate

//        val v = 0.00000001*240
//        Log.i(TAG, "feeObserver: $v")
//        Log.i(TAG, "feeObserver: $rate $coefficient")


        when (selectedRateType) {
            MIN_FEE -> {
                fee = String.format(
                    "%.2f",
                    this.rates.min
                )
                binding.etNetworkFees.setText(fee)
            }
            MID_FEE -> {
                fee = String.format(
                    "%.2f",
                    this.rates.mid
                )
                binding.etNetworkFees.setText(fee)
            }
            MAX_FEE -> {
                fee = String.format(
                    "%.2f",
                    this.rates.max
                )
                binding.etNetworkFees.setText(fee)
            }
        }
        viewModel.refreshFee(walletType.toLowerCase(Locale.ROOT))
    }

    private fun cardPhotoCaptureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            //perform check is there is an app that can handle this intent -> in not - just close
            takePictureIntent.resolveActivity(requireActivity().packageManager).also {
                var photoFile: File? = null
                try {
//                    photoFile = createImageFile()
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

//    private fun createImageFile(): File {
//        val file = File(
//            (activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES))?.absolutePath
//                    + "IMG_barcode.jpg"
//        )
//        cardPhotoPath = file.absolutePath
//        return file
//    }

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

    override fun onStart() {
        super.onStart()
        activity?.window?.navigationBarColor =
            ContextCompat.getColor(requireContext(), R.color.balance_header_color)
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


            tvLink.setText(data?.extras?.getString(BARCODE_EXTRA) ?: "")
        }
    }

    private fun resultObserver(b: Boolean?) {
        listener?.invoke(b == true, etAmountCripto.text.toString())
        if (b == true) {
            showAcceptDialog()
        } else if (b == false) {
            showErrorDialog(viewModel.showError.value.toString())
        }

    }


    private fun showAcceptDialog() {
        AcceptDialog.newInstance(amountET.amount.toString(),
            binding.etAddress.text.toString())
            .show(parentFragmentManager, AcceptDialog.TAG)
    }

    private fun showErrorDialog(value: String) {
        ErrorDialog.newInstance(value)
            .show(parentFragmentManager, ErrorDialog.TAG)
    }

    private fun showToastError(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    private fun sendMax(response: SendMaxResponse) {
        if (response.result == true) {
            etAmountCripto.setText(String.format("%.8f", response.balance))
            val rs: Double =
                (response.balance ?: 0.0) * (arguments?.getDouble(keyBundleRates) ?: 0.0)
            etAmountFiat.setText(String.format("%.2f", rs))
        }
    }

    companion object {
        val TAG: String = SendDialog::class.java.name
        fun getInstance(
            balance: Double,
            walletType: String,
            rate: Double,
            walletColor: Int,
            ico: Int,
            coefficient: Double
        ): SendDialog {
            val dialog = SendDialog()
            val bundle = bundleOf(
                keyBundleBalance to balance,
                keyBundleTittle to walletType,
                keyBundleRates to rate,
                keyBundleWalletColor to walletColor,
                keyBundleIco to ico,
                keyBundleWalletCoefficient to coefficient
            )
            dialog.arguments = bundle
            dialog.setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
            return dialog
        }
    }
}