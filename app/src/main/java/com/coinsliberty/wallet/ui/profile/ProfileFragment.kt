package com.coinsliberty.wallet.ui.profile

import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.databinding.FragmentPinBinding
import com.coinsliberty.wallet.databinding.FragmentProfileBinding
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.makeTransaction.REQUEST_CODE
import com.coinsliberty.wallet.dialogs.makeTransaction.REQUEST_SCAN
import com.coinsliberty.wallet.ui.MainActivity
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.attach_component.*
import kotlinx.android.synthetic.main.fragment_profile.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


class ProfileFragment : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: StubNavigator = get()
    var bufferFile: Any? = null

    private lateinit var binding: FragmentProfileBinding

    var isNeed2fa = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getProfile()
        viewModel.getCurrency()



        tvAttachButton.setOnClickListener {

        }

        binding.profileToolbar.ivAddPhoto.setOnClickListener {
            requestPermissionAndCapturePhoto()
        }

        binding.profileToolbar.ivToolbarIconLeft.visibility = View.VISIBLE
        binding.profileToolbar.ivToolbarIconLeft.setImageResource(R.drawable.ic_arrow_back)
        //profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        binding.profileToolbar.tvToolbarTitle.text = "Profile"

        binding.profileToolbar.ivToolbarIconLeft.setOnClickListener {
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

    private fun updateAvatar(id: Long) {
        Glide.with(this)
            .asBitmap()
            .load(viewModel.getUserAvatarGlideUrl(id))
            .apply(RequestOptions.circleCropTransform())
            .skipMemoryCache(true)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.profileToolbar.ivToolbarLogo.setImageBitmap(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
//                    Toast.makeText(context, "Unable to download image", Toast.LENGTH_SHORT).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun subscribeLiveData() {
        viewModel.showError.observe(viewLifecycleOwner, ::getErrorDialog)
        bindDataTo(viewModel.ldProfile, ::initProfileData)
        bindDataTo(viewModel.ldCurrency, ::initCurrency)
    }


    private fun initCurrency(currency: Currency?) {
        if (currency == null) {
            return
        }

        tvCurrencyField.text = currency.getTitle()
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

        updateAvatar(profileResponse?.user?.avatar ?: -1)
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
                val body = MultipartBody.Part.createFormData(
                    "file" + SimpleDateFormat("dd.MM.yyyy'-'HHmm").format(Date()),
                    file.name,
                    requestFile
                )
                Glide.with(this)
                    .asBitmap()
                    .load(uri)
                    .apply(RequestOptions.circleCropTransform())
                    .skipMemoryCache(true)
                    .into(binding.profileToolbar.ivToolbarLogo)
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