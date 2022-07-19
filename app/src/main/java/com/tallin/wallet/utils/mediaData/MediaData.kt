/*package com.tallin.wallet.utils.mediaData

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.tallin.wallet.dialogs.makeTransaction.REQUEST_SCAN
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.ui.profile.ProfileFragment
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MediaData(private val activity: Activity) {

    var tryGetPermission = 0
    fun requestPermissionAndCapturePhoto() {
        if (tryGetPermission > 2){
            tryGetPermission = 0
        } else {
            try {
                if (ContextCompat.checkSelfPermission(
                        activity,
                        android.Manifest.permission.CAMERA
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        activity,
                        android.Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        activity,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    openGallery(IMAGE_PICK_CODE)//if granted
                } else {
                    tryGetPermission++
                    requestPermissions(activity,
                        arrayOf(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ), REQUEST_CODE
                    ) //request
                }
            } catch (e: Exception){ tryGetPermission++ }
        }
    }

    private fun openGallery(req_code: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        (activity as MainActivity).showPin = false
        activity.startActivityForResult(
            Intent.createChooser(
                intent,
                "Select file to upload "
            ), req_code
        )
        Log.e("SEND_IMG", "SENT")
    }

    fun onRequestPermissionsResult(
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

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?, listener: (Uri, MultipartBody.Part) -> Unit) {

        if (resultCode == IMAGE_PICK_CODE || requestCode == IMAGE_PICK_CODE) {
            (activity as MainActivity).showPin = true

            try {
                val uri: Uri = data?.data ?: return
                val file = File(getImageFilePath(uri))

                val requestFile =
                    RequestBody.create("image/png".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData(
                    "file" + SimpleDateFormat("dd.MM.yyyy'-'HHmm").format(Date()),
                    file.name,
                    requestFile
                )
                listener.invoke(uri, body)
                /*Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .skipMemoryCache(true)
                    .into(binding.profileToolbar.ivToolbarLogo)
                viewModel.sendFile(body)*/
            } catch (e: Exception) {
                Log.e("ERROE_IMG", e.toString())
            }
        }
    }

    private fun getImageFilePath(uri: Uri?): String? {

        var path: String? = null
        var imageId: String? = null
        val firstCursor =
            uri?.let { activity.contentResolver?.query(it, null, null, null, null) }
        if (firstCursor != null) {
            firstCursor.moveToFirst()
            imageId = firstCursor.getString(0)
            imageId = imageId.substring(imageId.lastIndexOf(":") + 1)
            firstCursor.close()
        }
        val cursor: Cursor? = activity.contentResolver?.query(
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

    companion object {
        private const val IMAGE_PICK_CODE = 1000
    }
}*/