package com.example.coinsliberty.ui.login

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.dialogs.ErrorDialog
import com.example.coinsliberty.dialogs.ForgotPassDialog
import com.example.coinsliberty.ui.config.NavigationConfig
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Error
import java.util.*
import kotlin.collections.ArrayList

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel by viewModel()
    override val navigator: LoginNavigation = get()

    var icon = R.drawable.ic_germany

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        loginToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.logout_icon)
        loginToolbar.ivToolbarIconLeft.setOnClickListener {
               ChangeLanguageDialog.newInstance(icon)
                   .apply {
                       initListeners {
                           icon = it.ico
                           dismiss()
                           changeLanguage()
                       }
                   }.show(childFragmentManager, ChangeLanguageDialog.TAG)

        }
        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE

        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }

        btnLoginUpdate.setOnClickListener {
            navigator.goToContent(navController)
        }

        tvForgotPassword.setOnClickListener {
            ForgotPassDialog.newInstance()
                .apply { initListeners {
                    //if(!it) ErrorDialog.newInstance("Пустые поля").show(childFragmentManager, ErrorDialog.TAG)
                    dismiss()

                } }
                .show(childFragmentManager, ForgotPassDialog.TAG)
        }
    }

    private fun changeLanguage() {
        loginToolbar.ivToolbarIconLeft.setImageResource(icon)
    }

//    private fun navigate() {
//        navigator.goToContent(navController)
//    }

}

