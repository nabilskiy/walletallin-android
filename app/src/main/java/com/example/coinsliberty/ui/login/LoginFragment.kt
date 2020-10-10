package com.example.coinsliberty.ui.login

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.base.BaseKotlinFragment
import com.example.coinsliberty.model.LanguageContent
import com.example.coinsliberty.ui.dialogs.ChangeLanguageDialog
import com.example.coinsliberty.ui.dialogs.ChangeLanguageViewModel
import com.example.coinsliberty.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_login.*
import kotlinx.android.synthetic.main.toolbar.view.*

class LoginFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_login
    override val viewModel: LoginViewModel = LoginViewModel()
    override val navigator: LoginNavigation = LoginNavigation()

   val viewModelLang :ChangeLanguageViewModel = ChangeLanguageViewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //subscribeLiveData()

        loginToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.logout_icon)
        loginToolbar.ivAddPhoto.visibility = View.INVISIBLE
        tvLoginSignUpButton.setOnClickListener { navigator.goToSignUp(navController) }
        ivLock.setOnClickListener {
            ChangeLanguageDialog.newInstance().show(parentFragmentManager, ChangeLanguageDialog.TAG)
        }
        btnLoginUpdate.setOnClickListener {
            navigate()
            }
        }


    private fun navigate() {
        navigator.goToContent(navController)
    }

   // private fun subscribeLiveData() { bindDataTo(viewModelLang.currentLanguagesLiveData, ::setIVLang) }

    private fun setIVLang(item : LanguageContent){

        when(item.name) {
            R.string.english -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_unitedstates)
            R.string.russian -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_russia)
            R.string.arabic -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_united_arab_emirates)
            R.string.deutch -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_germany)
            R.string.italiano -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_italy)
            R.string.portuguese -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_brazil)
            R.string.espanol -> loginToolbar.ivToolbarIconLeft.setBackgroundResource(R.drawable.ic_spain)
        }

    }
}

