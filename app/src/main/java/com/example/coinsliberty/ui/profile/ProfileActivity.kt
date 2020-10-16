package com.example.coinsliberty.ui.profile

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinActivity
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.utils.extensions.setTransparentLightStatusBar
import com.example.coinsliberty.utils.extensions.setupFullScreen
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileActivity : BaseKotlinActivity() {

    override val viewModel: ProfileViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_profile)

        setTransparentLightStatusBar()
        setupFullScreen()

        profileToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_arrow_back)
        profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"
    }
}