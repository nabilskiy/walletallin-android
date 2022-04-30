package com.tallin.wallet.ui.kyc.manuallyKYC

import android.annotation.SuppressLint
import android.content.ContextWrapper
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_SCAN
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.widgets.webView.MyWebView
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import gun0912.tedimagepicker.builder.TedImagePicker
import kotlinx.android.synthetic.main.fragment_kyc_manually_button.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class KYCManuallyFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_kyc_manually_button
    override val viewModel: KYCManuallyViewModel by viewModel()
    override val navigator: KYCManuallyNavigation = get()

    var mCameraPhotoPath: String? = null

    var imageUri: String? = null
    var imgUri: Uri? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivNewImage.gone()
        wtDoc.visible()

        imageUri = null
        val id = arguments?.getInt("[KYC]id")
        val docId = arguments?.getInt("[KYC]docId")

        subscribeLiveData()

        btnCancel.setOnClickListener { activity?.onBackPressed()}
        btnNext.setOnClickListener {
            try {
                viewModel.sendFile(requireContext(), imgUri!!, id!!, docId!!)
            } catch (e: Exception){}
        }

        btnTakePhoto.setOnClickListener {
            if (activity != null) {
                val context = activity
                (activity as MainActivity).showPin = false
                TedImagePicker.with(context!!)
                    .start { uri ->
                        (activity as MainActivity).showPin = true
                        ivNewImage.setImageURI(uri)
                        ivNewImage.visible()
                        wtDoc.gone()
                        imageUri = uri.path
                        imgUri = uri
                        //showSingleImage(uri)
                    }
            }
        /*requestPermissionAndCapturePhoto(true)*/
        }
    }

    private fun requestPermissionAndCapturePhoto(camera: Boolean = false) {
        if (camera){
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    android.Manifest.permission.CAMERA
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                openCamera(IMAGE_PICK_CODE)
            } else {
                requestPermissions(
                    arrayOf(
                        android.Manifest.permission.CAMERA
                    ), REQUEST_CODE
                ) //request
            }
        } else {
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
                openGallery(IMAGE_PICK_CODE)
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

         //   openGallery(ProfileFragment.IMAGE_PICK_CODE)//if granted
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

    private fun openCamera(req_code: Int) {
        val intent = Intent()
        intent.action = MediaStore.ACTION_IMAGE_CAPTURE
        (activity as MainActivity).showPin = false
        if (intent.resolveActivity(ContextWrapper(context).packageManager) != null) {
            var photoFile: File? = null
            try {
                photoFile = createImageFile()
                intent.putExtra("PhotoPath", mCameraPhotoPath)
            } catch (ex: IOException) {
            }
            if (photoFile != null) {
                println("file error camera ))) ${photoFile.absolutePath}")
                mCameraPhotoPath = /*"file:" + */photoFile.absolutePath
                intent.putExtra(
                    MediaStore.EXTRA_OUTPUT,
                    /*Uri.fromFile(*/photoFile//)
                )
            } else {
                println("file error camera (")
                //takePictureIntent = null
            }
        }
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select file to upload "
            ), req_code
        )

    }

    @Throws(IOException::class)
    private fun createImageFile(): File? {
        val timeStamp =
            SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val imageFileName = "JPEG_" + timeStamp + "_"
        val storageDir = Environment.getExternalStoragePublicDirectory(
            Environment.DIRECTORY_PICTURES
        )
        return File.createTempFile(
            imageFileName,
            ".jpg",
            storageDir
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
                        wtDoc.gone()
                        ivNewImage.visible()
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
                    wtDoc.gone()
                    ivNewImage.visible()
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

    private fun subscribeLiveData() {
        bindDataTo(viewModel.resultSendFile, ::showResult)
    }
    private fun showResult(s: Boolean?) {
        if (s == true) {
            navigator.exitToSetting(navController)
            //activity?.onBackPressed()
        }else{

        }
    }

    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 2000

        //Permission code
        private val PERMISSION_CODE = 2001
    }
}