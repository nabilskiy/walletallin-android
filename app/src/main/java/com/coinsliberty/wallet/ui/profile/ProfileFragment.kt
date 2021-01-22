package com.coinsliberty.wallet.ui.profile

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.ui.MainActivity
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.attach_component.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.util.jar.Manifest


class ProfileFragment : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: StubNavigator = get()
    var bufferFile: Any? = null

    var isNeed2fa = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
            && context?.let { ContextCompat.checkSelfPermission(it, android.Manifest.permission.READ_EXTERNAL_STORAGE) } != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                context as Activity, Array<String>(2) {android.Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile()
        viewModel.getCurrency()

            Glide.with(this)
                .asBitmap()
                .load(viewModel.getUserAvatarGlideUrl())
                .apply(RequestOptions.circleCropTransform())
                .skipMemoryCache(true)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        profileToolbar.ivToolbarLogo.setImageBitmap(resource)
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        super.onLoadFailed(errorDrawable)
                        Toast.makeText(context, "Unable to download", Toast.LENGTH_SHORT).show()
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {}
                })

        tvAttachButton.setOnClickListener {

        }

        profileToolbar.ivAddPhoto.setOnClickListener {
            openGallery(IMAGE_PICK_CODE)
        }

        profileToolbar.ivToolbarIconLeft.visibility = View.VISIBLE
        profileToolbar.ivToolbarIconLeft.setImageResource(R.drawable.ic_arrow_back)
        //profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"

        profileToolbar.ivToolbarIconLeft.setOnClickListener {
            navigator.back()
        }

        btnLoginUpdate.setOnClickListener {
            if (checkNotNull()) {
                viewModel.editProfile(
                    firstName = ifcProfileFirstName.getMyText(),
                    lastName = ifcProfileLastName.getMyText(),
                    phone = ifcProfilePhone.getMyText(),
                    avatar = viewModel.ldCurrentUserAvatarId.value
                )
            } else {
                getErrorDialog("Empty fields")
            }
        }

        tvCurrencyField.setOnClickListener {
            showPopupMenu()
        }

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.showError.observe(this, ::getErrorDialog)
        bindDataTo(viewModel.ldProfile, ::initProfileData)
        bindDataTo(viewModel.ldCurrency, ::initCurrency)
    }


    private fun initCurrency(currency: Currency?) {
        if (currency == null) {
            return
        }

        tvCurrencyField.text = currency.getTitle()
    }

    private fun showPopupMenu() {
        val popupMenu = PopupMenu(requireContext(), tvCurrencyField)
        popupMenu.inflate(R.menu.currency_menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.usd -> {
                    viewModel.saveCurrency(Currency.USD)
                    true
                }
                R.id.eur -> {
                    viewModel.saveCurrency(Currency.EUR)
                    true
                }
                else -> false
            }
        }

        popupMenu.show()
    }

    private fun initProfileData(profileResponse: ProfileResponse?) {
        ifcProfileFirstName.setText(profileResponse?.user?.firstName ?: "")
        ifcProfileLastName.setText(profileResponse?.user?.lastName ?: "")
        ifcProfilePhone.setText(profileResponse?.user?.phone ?: "")
        ifcProfileEmail.setText(profileResponse?.user?.login ?: "")
        isNeed2fa = profileResponse?.user?.optEnabled == 1
    }

    fun openGallery(req_code: Int) {
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
        Log.e("SEND_IMG", "SENT")

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == IMAGE_PICK_CODE || requestCode == IMAGE_PICK_CODE) {
            (activity as MainActivity).showPin = true

            try {
                val uri: Uri = data?.data ?: return
                val file = File(getImageFilePath(uri))

                val requestFile =
                    RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
                viewModel.sendFile(body)
            } catch (e: Exception) {
                Log.e("ERROE_IMG", e.toString())
            }
        }
    }

    fun getImageFilePath(uri: Uri?): String? {

        var path: String? = null
        var image_id: String? = null
        var first_cursor =
            uri?.let { context?.contentResolver?.query(it, null, null, null, null) }
        if (first_cursor != null) {
            first_cursor.moveToFirst()
            image_id = first_cursor.getString(0)
            image_id = image_id.substring(image_id.lastIndexOf(":") + 1)
            first_cursor.close()
        }
        val cursor: Cursor? = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            null,
            MediaStore.Images.Media._ID + " = ? ",
            arrayOf(image_id),
            null
        )
        if (cursor != null) {
            cursor.moveToFirst()
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA))
            cursor.close()
        }
        return path
    }

    fun getRealPath(uri: Uri?): String {
        var filePath = ""
        val wholeID = DocumentsContract.getDocumentId(uri)
        val id = wholeID.split(":".toRegex()).toTypedArray()[1]
        val column = arrayOf(MediaStore.Images.Media.DATA)
        // where id is equal to
        val sel = MediaStore.Images.Media._ID + "=?"
        val cursor: Cursor = context?.contentResolver?.query(
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            column, sel, arrayOf(id), null
        )!!
        val columnIndex = cursor.getColumnIndex(column[0])
        if (cursor.moveToFirst()) {
            filePath = cursor.getString(columnIndex)
        }
        cursor.close()
        return filePath
    }

    private fun checkNotNull(): Boolean {
        return (ifcProfileFirstName.getMyText().isNotEmpty()
                || ifcProfileLastName.getMyText().isNotEmpty()
                || ifcProfilePhone.getMyText().isNotEmpty())
    }

    private fun getErrorDialog(message: String) {
        ErrorDialog.newInstance(message)
            .show(childFragmentManager, ErrorDialog.TAG)
    }


    companion object {
        //image pick code
        private val IMAGE_PICK_CODE = 1000;

        //Permission code
        private val PERMISSION_CODE = 1001;
    }
}