package com.tallin.wallet.ui.transactions.transactionsDocumentUpLoad

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.data.response.TransactionDocumentsInfoResponse
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_SCAN
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.invisible
import com.tallin.wallet.utils.extensions.visible
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.fragment_transaction_document_upload.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class TransactionDocumentUpLoadFragment: BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_transaction_document_upload
    override val viewModel: TransactionDocumentUpLoadViewModel by viewModel()
    override val navigator: TransactionDocumentUpLoadNavigation = get()

    var mCameraPhotoPath: String? = null

    var imageUri: String? = null
    var imgUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val name = arguments?.getString("[transactionDocs]name")
        val transId = arguments?.getInt("[transactionDocs]transId")
        val id = arguments?.getInt("[transactionDocs]assignId")
        val docId = arguments?.getInt("[transactionDocs]docId")

        tvTitle.text = name ?: ""
        tvChoose.invisible()
        tvNext.invisible()
        tvTitleGL.gone()
        tvTextGL.gone()

        tvBack.setOnClickListener { activity?.onBackPressed() }
        tvChoose.setOnClickListener { activity?.onBackPressed() }
        tvNext.setOnClickListener { loadPhoto() }
        clCanvasImgUpload.setOnClickListener { loadPhoto() }
        tvChooseDoc.setOnClickListener { loadPhoto()  }
        tvNext.setOnClickListener {
            try {
                viewModel.loadFile(requireContext(), imgUri!!, id!!, docId!!, transId!!)
            } catch (e: Exception){
                //todo Error
            }
        }
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.loadFileLiveData, ::showResult)
    }
    private fun showResult(result: Boolean){
        if (result){
            activity?.onBackPressed()
        } else {
            //todo Error
        }
    }

    private fun loadPhoto(){
        if (activity != null) {
            val context = activity
            (activity as MainActivity).showPin = false
            TedImagePicker.with(context!!)
                .start { uri ->
                    (activity as MainActivity).showPin = true
                    ivNewImage.setImageURI(uri)
                    photoChoosed()
                    imageUri = uri.path
                    imgUri = uri
                    //showSingleImage(uri)
                }
        }
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
            openGallery(IMAGE_PICK_CODE)//if granted
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {

        if (requestCode == REQUEST_CODE) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery(IMAGE_PICK_CODE)
            } else {
                requestPermissionAndCapturePhoto()
            }
        } else if (requestCode == REQUEST_SCAN) {
            if (grantResults.size == 3 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED
                && grantResults[2] == PackageManager.PERMISSION_GRANTED
            ) {
                openGallery(IMAGE_PICK_CODE)
            } else {
                openGallery(IMAGE_PICK_CODE)

            }
        }
    }

    private fun openGallery(req_code: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        (activity as MainActivity).showPin = false
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select file to upload "
            ), req_code
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == IMAGE_PICK_CODE || requestCode == IMAGE_PICK_CODE) {
            (activity as MainActivity).showPin = true
            try {
                //val uri: Uri = data?.data ?: return
                /*val file = File(getImageFilePath(uri))

                val requestFile =
                    RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData(
                    "file" + SimpleDateFormat("dd.MM.yyyy'-'HHmm").format(Date()),
                    file.name,
                    requestFile
                )*/
                if (data?.data == null) {
                    if (mCameraPhotoPath != null) {
                        val uri: Uri? = /*mCameraPhotoPath!!*/Uri.parse(mCameraPhotoPath)
                        println("tesstt $mCameraPhotoPath")
                        Glide.with(this)
                            .asBitmap()
                            .load(uri)
                            .skipMemoryCache(false)
                            .into(ivNewImage)
                        photoChoosed()
                        imageUri = uri.toString()//getImageFilePath(uri)
                        //results = arrayOf(Uri.parse(MyWebView.mCameraPhotoPath))
                    }
                } else {
                    val uri: Uri = data.data!!
                    println("tesstt FILE $uri")
                    Glide.with(this)
                        .asBitmap()
                        .load(uri)
                        .skipMemoryCache(false)
                        .into(ivNewImage)
                    photoChoosed()
                    imageUri = getImageFilePath(uri)
                }
            } catch (e: Exception) {
                Log.e("ERROE_IMG", e.toString())
            }
        }
    }

    @SuppressLint("Range")
    fun getImageFilePath(uri: Uri?): String? {

        var path: String? = null
        var imageId: String? = null
        val firstCursor =
            uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (firstCursor != null) {
            firstCursor.moveToFirst()
            imageId = firstCursor.getString(0)
            imageId = imageId.substring(imageId.lastIndexOf(":") + 1)
            firstCursor.close()
        }
        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(imageId),
            null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            cursor.close()
        }
        return path
    }

    private fun photoChoosed(){
        clCanvasImgUpload.gone()
        tvChooseDoc.gone()
        tvTitleGL.visible()
        tvTextGL.visible()
        ivNewImage.visible()
        tvChoose.visible()
        tvNext.visible()
        tvNext.text = activity?.resources?.getText(R.string.kyc_continue)
    }

    companion object {
        private const val IMAGE_PICK_CODE = 3000
    }
}
