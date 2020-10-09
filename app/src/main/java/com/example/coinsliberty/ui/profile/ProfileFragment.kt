package com.example.coinsliberty.ui.profile

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*

class ProfileFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_profile
    override val viewModel: ProfileViewModel = ProfileViewModel()
    override val navigator: ProfileNavigation = ProfileNavigation()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_arrow_back)
        profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"
    }

}