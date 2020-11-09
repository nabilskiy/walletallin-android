package com.example.coinsliberty.ui.profile

import android.content.Intent
import android.os.Bundle
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinActivity
import com.example.coinsliberty.data.EditProfileRequest
import com.example.coinsliberty.data.ProfileResponse
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.secureCode.SecureCodeDialog
import com.example.coinsliberty.dialogs.secureCode.UndoSecureCodeDialog
import com.example.coinsliberty.utils.extensions.bindDataTo
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen
import kotlinx.android.synthetic.main.attach_component.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileActivity : BaseKotlinActivity() {

    override val viewModel: ProfileViewModel by viewModel()
    var bufferFile : Any? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        setTransparentLightStatusBar()
        setupFullScreen()

        tvAttachButton.setOnClickListener {
            openGallery(1);
        }

        profileToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_arrow_back)
        profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"

        btnLoginUpdate.setOnClickListener {
            if (checkNotNull()) {
                viewModel.editProfile(
                    firstName = ifcProfileFirstName.getMyText(),
                    lastName = ifcProfileLastName.getMyText(),
                    phone = ifcProfilePhone.getMyText(),
                    optEnabled = false,
                    file = null
                )
            } else {
                getErrorDialog("Empty fields")
            }
        }

        profileSwitch.initListeners {
            if (it) {
                viewModel.getOtp()
            } else {

                UndoSecureCodeDialog.newInstance(EditProfileRequest(ifcProfileFirstName.getMyText(), ifcProfileLastName.getMyText(), ifcProfilePhone.getMyText(), false)).apply {

                    initListeners {
                        update2FA(it)
                    }
                }.show(supportFragmentManager, UndoSecureCodeDialog.TAG)
            }
        }
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.showError, ::getErrorDialog)
        bindDataTo(viewModel.ldProfile, ::initProfileData)
        bindDataTo(viewModel.ldOtp, ::initOtp)
    }

    private fun initOtp(s: String?) {
        update2FA(false)

        if(s == null) return

        SecureCodeDialog.newInstance(EditProfileRequest(ifcProfileFirstName.getMyText(), ifcProfileLastName.getMyText(), ifcProfilePhone.getMyText(), true), s, ifcProfileEmail.getMyText()).apply {
            initListeners {
                update2FA(it)
            }
        }.show(supportFragmentManager, SecureCodeDialog.TAG)
    }

    fun update2FA(b: Boolean) {
        profileSwitch.changeStatus(b)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getProfile()
    }

    private fun initProfileData(profileResponse: ProfileResponse?) {
        ifcProfileFirstName.setText(profileResponse?.user?.firstName ?: "")
        ifcProfileLastName.setText(profileResponse?.user?.lastName ?: "")
        ifcProfilePhone.setText(profileResponse?.user?.phone ?: "")
        ifcProfileEmail.setText(profileResponse?.user?.login ?: "")
        profileSwitch.changeStatus(profileResponse?.user?.optEnabled == 1)
    }

    fun openGallery(req_code: Int) {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(
            Intent.createChooser(
                intent,
                "Select file to upload "
            ), req_code
        )
    }

    private fun checkNotNull(): Boolean {
        return (ifcProfileFirstName.getMyText().isNotEmpty()
                || ifcProfileLastName.getMyText().isNotEmpty()
                || ifcProfilePhone.getMyText().isNotEmpty())
    }

    private fun getErrorDialog(message: String) {
        ErrorDialog.newInstance(message)
            .show(supportFragmentManager, ErrorDialog.TAG)
    }


}