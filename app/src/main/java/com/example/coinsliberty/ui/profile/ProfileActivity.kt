package com.example.coinsliberty.ui.profile

import android.content.Intent
import android.os.Bundle
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinActivity
import com.example.coinsliberty.dialogs.ErrorDialog
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
                getErrorDialog()
            }
        }
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

    private fun getErrorDialog() {
        ErrorDialog.newInstance("Empty fields")
            .show(supportFragmentManager, ErrorDialog.TAG)
    }


}