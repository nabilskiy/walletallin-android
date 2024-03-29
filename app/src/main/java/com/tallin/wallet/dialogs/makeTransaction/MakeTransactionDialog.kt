package com.tallin.wallet.dialogs.makeTransaction


import com.google.android.material.bottomsheet.BottomSheetDialogFragment


private const val keyBundleTittle = "tittle"
private const val keyBundleBalance = "balance"
private const val keyBundleRates = "rates"
private const val keyBundleData = "qr"
private const val keyBundleLoginData = "login"
private const val keyBundleLink = "link"
private const val keyBundleIco = "ico"
private const val keyBundleIsSend = "isSend"



class MakeTransactionDialog : BottomSheetDialogFragment() {
//    val layoutRes: Int = R.layout.bottom_sheet_make_transfer
//    val viewModel: MakeTransactionViewModel by viewModel()
//
//    var listener: ((Boolean, String) -> Unit)? = null
//
//    var itemRate: Int = 1
//
//    private lateinit var cardPhotoPath: String
//    private var rates: Rates? = null
//    private var isSend: Boolean = true
//    private var barcode = ""
//    private var qrCode: String? = null
//    private var login: String? = null
//    private var data: EditProfileRequest? = null
//    private var isChangeEtAmountCrypto = true
//    private lateinit var valueFeeToSend: String
//    private lateinit var walletType: String
//
//    var addressForSend: String = " "
//    var balanceForSend: String = " "
//    var refactorUsd = false
//    var ress: Boolean? = null
//
//    override fun getTheme(): Int = R.style.SendDialog
//
//    var cryptoValueChanged: Boolean = false
//    var usdValueChanged: Boolean = false
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        isCancelable = true
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
//    ): View? {
//        return inflater.inflate(R.layout.bottom_sheet_make_transfer, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        walletType = arguments?.getString(keyBundleTittle, "BTC")!!
//        tvTittle.text = "SEND $walletType"
//        ivDownLogo.setImageResource(arguments?.getInt(keyBundleIco)!!)
//        tvCriptoName.text = arguments?.getString(keyBundleTittle)
//
//        //tvSendMax.text = Html.fromHtml(getString(R.string.send_max))
//
//        viewModel.updateData(walletType.toLowerCase(Locale.ROOT))
//
//        dialogReceive()
//        viewModel.getCurrency()
//
//        clSendSpinner.setOnClickListener {
//            showPopupMenu()
//        }
//
//        ivHint.setOnClickListener {
//            HintDialog.newInstance("Bitcoin network (mining) fees are small amounts of bitcoin given to incentivize bitcoin miners (and their operators) to confirm bitcoin transactions. Bitcoin miners are the special pieces of hardware that confirm and secure transactions on the bitcoin network.")
//                .show(childFragmentManager, HintDialog.TAG)
//        }
//
//
//
//        subscribeLiveData()
//        isSend = arguments?.getBoolean(keyBundleIsSend, true) ?: true
//        if (isSend) {
////            switchDialog.changeStatus(false)
//            //RECEIVE
//            clDialogSend.invisible()
//            clDialogReceive.visible()
//            layoutBottomSheetMakeTransfer.setBackgroundResource(R.drawable.bg_dialog_blue)
//            tvTittle.text = "RECEIVE $walletType"
//        } else {
////            switchDialog.changeStatus(true)
//            //SEND
//            clDialogSend.visible()
//            clDialogReceive.invisible()
//            layoutBottomSheetMakeTransfer.setBackgroundResource(R.drawable.bg_dialog_green)
//            tvTittle.text = "SEND $walletType"
//        }
//    }
//
//    private fun showPopupMenu() {
//        val popupMenu = PopupMenu(requireContext(), clSendSpinner)
//        popupMenu.inflate(R.menu.fee_menu)
//
//        popupMenu
//            .setOnMenuItemClickListener { item ->
//                ivText.text = item.title
//                when (item.itemId) {
//                    R.id.slow -> {
//                        itemRate = 0
//                        tvAmountSatPerByte.disable()
//                        tvAmountSatPerByte.setText(String.format("%.2f", rates?.min))
//                        true
//                    }
//                    R.id.medium -> {
//                        itemRate = 1
//                        tvAmountSatPerByte.disable()
//                        tvAmountSatPerByte.setText(String.format("%.2f", rates?.mid))
//                        true
//                    }
//                    R.id.fast -> {
//                        itemRate = 2
//                        tvAmountSatPerByte.disable()
//                        tvAmountSatPerByte.setText(String.format("%.2f", rates?.max))
//                        true
//                    }
//                    R.id.custom -> {
//                        itemRate = 3
//                        tvAmountSatPerByte.enable()
//                        tvAmountSatPerByte.setSelection(tvAmountSatPerByte.text.length)
//                        tvAmountSatPerByte.requestFocus()
//                        true
//                    }
//                    else -> false
//                }
//            }
//
//        popupMenu.show()
//    }
//
//    private fun dialogReceive() {
//        qrCode = arguments?.getString(keyBundleLink)
//        login = arguments?.getString(keyBundleLoginData)
//        data = arguments?.getParcelable(keyBundleData)
//
//        btnUpdate.setOnClickListener {
//            // viewModel.updateProfile(data?.apply { otp = ifc2FA.getMyText() })
//        }
//        ivCopy.setOnClickListener {
//            val clipboard =
//                context?.getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
//            val clip = ClipData.newPlainText("Copied Text", tvLinkReceive.text.toString())
//            clipboard.setPrimaryClip(clip)
//
//            Toast.makeText(context, "Copy", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun dialogSend(currency: Currency) {
//        val rates = arguments?.getDouble(keyBundleRates)
//        val bundle = arguments?.getDouble(keyBundleBalance)
//        val result = bundle!! * rates!!
//
//        tvBTCTransferResult.text =
//            String.format("%.2f", result) + if (currency == Currency.USD) " $" else " €"
//        etAmountCripto.setText(String.format("%.8f", bundle))
//        etAmountFiat.setText(String.format("%.2f", result))
//        balanceForSend = etAmountFiat.text.toString() + if (currency == Currency.USD) " $" else " €"
//
//        etAmountCripto.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) = Unit
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
//                Unit
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//                val amountFiat = String.format(
//                    "%.2f",
//                    ((s.toString().replace(",", ".", true).toDoubleOrNull() ?: 0.0) * rates)
//                )
//
//
//
//                if (!cryptoValueChanged) {
//                    usdValueChanged = true
//                    if (etAmountFiat.text.toString() != amountFiat) {
//                        etAmountFiat.setText(amountFiat)
//                    }
//                } else {
//                    cryptoValueChanged = false
//                    usdValueChanged = false
//                }
//            }
//        })
//        etAmountFiat.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) = Unit
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
//                Unit
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                val amountCrypto = String.format(
//                    "%.8f",
//                    ((s.toString().replace(",", ".", true).toDoubleOrNull() ?: 0.0) / rates)
//                )
//
//                if ((s.toString().replace(",", ".", true).split(".").getOrNull(1)?.length
//                        ?: 0) > 2
//                ) {
//                    refactorUsd = true
//                    val splitValue = s.toString().replace(",", ".", true).split(".")
//                    formantText(
//                        splitValue[0] + "." + splitValue.getOrNull(1)?.substring(0, 2)
//                    )
//                    return
//                } else if (refactorUsd) {
//                    refactorUsd = false
//                    return
//                }
//
//                if (!usdValueChanged) {
//                    cryptoValueChanged = true
//                    if (etAmountCripto.text.toString() != amountCrypto) {
//                        etAmountCripto.setText(amountCrypto)
//                    }
//                } else {
//                    cryptoValueChanged = false
//                    usdValueChanged = false
//                }
//            }
//        })
//        tvAmountSatPerByte.addTextChangedListener(object : TextWatcher {
//            override fun afterTextChanged(s: Editable?) = Unit
//
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) =
//                Unit
//
//            @SuppressLint("SetTextI18n")
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//
//
//                if (s.isNullOrEmpty()) {
//                    return
//                }
//
//                if (s.toString().replace(",", ".", true).toDouble() > 100) {
//                    tvAmountSatPerByte.setText("100")
//                    tvAmountSatPerByte.setSelection(3)
//                    tvBTCTransferResult.text = String.format(
//                        "%.2f",
//                        ("100".toDouble())
//                    ) + " ${currency.getTitle()}"
//                    Log.e("!!!", "3")
//                } else {
//                    tvBTCTransferResult.text = String.format(
//                        "%.2f",
//                        ((s.toString().replace(",", ".", true).toDouble()))
//                    ) + " ${currency.getTitle()}"
//                    Log.e("!!!", "4")
//                }
//
//
//            }
//        })
//
//        ivQrCode.setOnClickListener {
//            (activity as MainActivity).showPin = false
//            barcodeScan()
//        }
//
//        ivClose.setOnClickListener { dismiss() }
//
//
//
//
//        btnSentCoin.setOnClickListener {
//            val valueAmountCryptoToSend = etAmountCripto.text.toString().replace(",", ".", true)
//
//            viewModel.sendBtc(
//                walletType.toLowerCase(Locale.ROOT),
//                valueAmountCryptoToSend,
//                tvLink.text.toString(),
//                String.format(
//                    "%.8f",
//                    ((tvAmountSatPerByte.text.toString().replace(",", ".")
//                        .toDoubleOrNull())?.div((arguments?.getDouble(keyBundleRates) ?: 0.0)))
//                ),
//                scReplaceable.isChecked
//            )
//        }
//
//        tvSendMax.setOnClickListener {
//            val rate = String.format(
//                "%.8f",
//                (tvAmountSatPerByte.text.toString().replace(",", ".", true)
//                    .toDouble() / (arguments?.getDouble(keyBundleRates) ?: 1.0))
//            )
//
//            viewModel.sendMax(walletType.toLowerCase(Locale.ROOT), rate)
//        }
//    }
//
//    private fun tst() = tvAmountSatPerByte.text.toString().replace(",", ".")
//        .toDoubleOrNull()
//
//
//    fun initListeners(onChoosen: (Boolean, String) -> Unit) {
//        listener = onChoosen
//    }
//
//    private fun getErrorDialog(value: String) {
//        ErrorDialog.newInstance(value)
//            .show(parentFragmentManager, ErrorDialog.TAG)
//    }
//
//    private fun getAcceptDialog(balance: String, link: String) {
//        AcceptDialog.newInstance(balance, link)
//            .show(parentFragmentManager, AcceptDialog.TAG)
//    }
//
//    private fun formantText(text: String) {
//        etAmountFiat.setText(text)
//        etAmountFiat.setSelection(text.length)
//    }
//
//    private fun subscribeLiveData() {
//        bindDataTo(viewModel.result, ::initResult)
//        bindDataTo(viewModel.feeInit, ::initFee)
//        bindDataTo(viewModel.resultRecovery, ::setAddress)
//        bindDataTo(viewModel.maxAvailable, ::sendMax)
//        bindDataTo(viewModel.currency, ::initCurrency)
//        bindDataTo(viewModel.messageError, ::showToastError)
//
//    }
//
//    private fun showToastError(message: String) {
//        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
//    }
//
//    private fun initCurrency(currency: Currency?) {
//        if (currency == null) return
//
//        tvFiatName.text = currency.getTitle()
//        tvsatPerByte.text = currency.getTitle()
//        dialogSend(currency)
//    }
//
//    private fun sendMax(response: SendMaxResponse) {
//        if (response.result == true) {
//            etAmountCripto.setText(String.format("%.8f", response.balance))
//            val rs: Double =
//                (response.balance ?: 0.0) * (arguments?.getDouble(keyBundleRates) ?: 0.0)
//            etAmountFiat.setText(String.format("%.2f", rs))
//        }
//    }
//
//    private fun setAddress(link: String) {
//        tvLinkReceive.text = link
//        ivQrCodeReceive.setImageBitmap(create(link))
//        addressForSend = link
//    }
//
//    private fun initFee(rates: Rates?) {
//
//        this.rates = rates
//        this.rates?.min =
//            (rates?.min ?: 0.0) * (arguments?.getDouble(keyBundleRates) ?: 0.0) * 0.00000001 * 240
//        this.rates?.mid =
//            (rates?.mid ?: 0.0) * (arguments?.getDouble(keyBundleRates) ?: 0.0) * 0.00000001 * 240
//        this.rates?.max =
//            (rates?.max ?: 0.0) * (arguments?.getDouble(keyBundleRates) ?: 0.0) * 0.00000001 * 240
//
//        when (itemRate) {
//            0 -> {
//                //tvAmountSatPerByte.disable()
//                tvAmountSatPerByte.setText(String.format("%.2f", rates?.min))
//                valueFeeToSend = String.format(
//                    "%.8f",
//                    ((rates?.min)?.div((arguments?.getDouble(keyBundleRates) ?: 0.0)))
//                )
//            }
//            1 -> {
//                //tvAmountSatPerByte.disable()
//                tvAmountSatPerByte.setText(String.format("%.2f", rates?.mid))
//                valueFeeToSend = String.format(
//                    "%.8f",
//                    ((rates?.mid)?.div((arguments?.getDouble(keyBundleRates) ?: 0.0)))
//                )
//            }
//            2 -> {
//                //tvAmountSatPerByte.disable()
//                tvAmountSatPerByte.setText(String.format("%.2f", rates?.max))
//                valueFeeToSend = String.format(
//                    "%.8f",
//                    ((rates?.max)?.div((arguments?.getDouble(keyBundleRates) ?: 0.0)))
//                )
//            }
//        }
//
//        viewModel.refreshFee()
//    }
//
//
//    private fun initResult(b: Boolean?) {
//        // Log.e("!!!initRes", "1")
//        listener?.invoke(b == true, etAmountCripto.text.toString())
//        ress = b
//
//        if (ress == true) {
//            getAcceptDialog(balanceForSend, addressForSend)
//        } else if (ress == false) {
//            getErrorDialog(viewModel.showError.value.toString())
//            //Log.e("!!!initRes", "false")
//        }
//
//
//    }
//
//    private fun cardPhotoCaptureIntent() {
//        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
//            //perform check is there is an app that can handle this intent -> in not - just close
//            takePictureIntent.resolveActivity(requireActivity().packageManager).also {
//                var photoFile: File? = null
//                try {
//                    photoFile = createImageFile()
//                } catch (e: IOException) {
//                    Toast.makeText(context, "error whle creating file", Toast.LENGTH_SHORT).show()
//                }
//                if (photoFile != null) {
//                    val photoUri: Uri = FileProvider.getUriForFile(
//                        requireContext(),
//                        "com.coinsliberty.provider",
//                        photoFile
//                    )
//                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
//                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
//                }
//            }
//        }
//    }
//
//    private fun createImageFile(): File {
//        val file = File(
//            (activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES))?.absolutePath
//                    + "IMG_${barcode}.jpg"
//        )
//        cardPhotoPath = file.absolutePath
//        return file
//    }
//
//    private fun requestPermissionAndCapturePhoto() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//            cardPhotoCaptureIntent() //if granted
//        } else {
//            requestPermissions(
//                arrayOf(
//                    android.Manifest.permission.CAMERA,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ), REQUEST_CODE
//            ) //request
//        }
//    }
//
//    private fun barcodeScan() {
//        if (ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.CAMERA
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED &&
//            ContextCompat.checkSelfPermission(
//                requireContext(),
//                android.Manifest.permission.READ_EXTERNAL_STORAGE
//            ) == PackageManager.PERMISSION_GRANTED
//        ) {
//
//            startActivityForResult(
//                Intent(context, ScanQRcodeActivity::class.java),
//                REQUEST_CODE_SCAN
//            )
//
//        } else {
//            requestPermissions(
//                arrayOf(
//                    android.Manifest.permission.CAMERA,
//                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ), REQUEST_SCAN
//            ) //request
//        }
//
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//
//        if (requestCode == REQUEST_CODE) {
//            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                && grantResults[2] == PackageManager.PERMISSION_GRANTED
//            ) {
//                cardPhotoCaptureIntent()
//            } else {
//                requestPermissionAndCapturePhoto()
//            }
//        } else if (requestCode == REQUEST_SCAN) {
//            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
//                && grantResults[2] == PackageManager.PERMISSION_GRANTED
//            ) {
//                barcodeScan()
//            } else {
//                barcodeScan()
//
//            }
//        }
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK) {
//            (activity as MainActivity).showPin = true
//
//
//            tvLink.setText(data?.extras?.getString(BARCODE_EXTRA) ?: "")
//        }
//    }
//
//    private fun create(text: String): Bitmap? {
//        val writer = QRCodeWriter()
//        return try {
//            val bitMatrix = writer.encode(text, BarcodeFormat.QR_CODE, 512, 512)
//            val width = bitMatrix.width
//            val height = bitMatrix.height
//            val bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
//            for (x in 0 until width) {
//                for (y in 0 until height) {
//                    bmp.setPixel(x, y, if (bitMatrix.get(x, y)) Color.BLACK else Color.WHITE)
//                }
//            }
//            bmp
//        } catch (e: WriterException) {
//            null
//        }
//    }
//
//    companion object {
//        const val TAG = "MakeTransaction"
//        fun newInstance(
//            tittle: String,
//            rates: Double,
//            balance: Double,
//            data: EditProfileRequest?,
//            link: String,
//            login: String,
//            ico: Int,
//            isSend: Boolean
//        ): MakeTransactionDialog {
//
//            val dialog = MakeTransactionDialog()
//            val bundle = bundleOf(
//                keyBundleTittle to tittle,
//                keyBundleRates to rates,
//                keyBundleBalance to balance,
//                keyBundleData to data,
//                keyBundleLink to link,
//                keyBundleLoginData to login,
//                keyBundleIco to ico,
//                keyBundleIsSend to isSend
//            )
//            dialog.arguments = bundle
//            dialog.setStyle(STYLE_NORMAL, R.style.BottomSheetDialogTheme)
//            return dialog
//        }
//    }

}