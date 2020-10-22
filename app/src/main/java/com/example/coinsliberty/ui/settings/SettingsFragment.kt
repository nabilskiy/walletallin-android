package com.example.coinsliberty.ui.settings

import android.os.Bundle
import android.view.View
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.dialogs.ResetPassDialog
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog
import com.example.coinsliberty.utils.extensions.invisible
import kotlinx.android.synthetic.main.fragment_settings.*
import kotlinx.android.synthetic.main.item_settings.view.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment() : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()
    override val navigator: SettingsNavigation = get()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView() {

        settingsToolbar.ivToolbarIconLeft.invisible()
        settingsToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.selector_notification)
        settingsToolbar.ivToolbarRightIcon.setColorFilter(resources.getColor(R.color.white))
        settingsToolbar.tvToolbarTitle.setText(R.string.settings)

        itemChangeLanguage.tvTittle.setText(R.string.change_language)

        itemEditProfile.ivLeft.setImageResource(R.drawable.ic_profile_1)
        itemEditProfile.tvTittle.setText(R.string.edit_profile)

        itemEditProfile.setOnClickListener { navigator.goToProfile(navController) }

        itemResetPassword.ivLeft.setImageResource(R.drawable.ic_password)
        itemResetPassword.tvTittle.setText(R.string.reset_password)

        itemResetPassword.setOnClickListener {
            ResetPassDialog.newInstance()
                .apply { initListeners {
                    //if(!it) ErrorDialog.newInstance("Пустые поля").show(childFragmentManager, ErrorDialog.TAG)
                    dismiss()

                } }
                .show(childFragmentManager, ResetPassDialog.TAG)
        }

        itemChangeLanguage.setOnClickListener {
            ChangeLanguageDialog.newInstance(R.drawable.ic_germany)
                .apply {
                    initListeners {
                        dismiss()
                    }
                }.show(childFragmentManager, ChangeLanguageDialog.TAG)
        }


        itemOtherSettings.ivLeft.setImageResource(R.drawable.ic_settings_1)
        itemOtherSettings.tvTittle.setText(R.string.other_settings)

        itemLogOut.ivLeft.setImageResource(R.drawable.ic_logout)
        itemLogOut.tvTittle.setText(R.string.log_out)
        itemLogOut.imNext.invisible()
    }

}