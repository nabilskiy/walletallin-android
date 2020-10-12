package com.example.coinsliberty.ui.profile

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class ProfileFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_profile
    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: ProfileNavigation = get()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_arrow_back)
        profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"

        btnLoginUpdate.setOnClickListener {
            navigator.goToTestFragment(navController)
        }
    }

}