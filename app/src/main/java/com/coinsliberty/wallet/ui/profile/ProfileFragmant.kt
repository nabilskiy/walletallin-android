package com.coinsliberty.wallet.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.isVisible
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.data.EditProfileRequest
import com.coinsliberty.wallet.data.response.ProfileResponse
import com.coinsliberty.wallet.dialogs.ErrorDialog
import com.coinsliberty.wallet.dialogs.secureCode.SecureCodeDialog
import com.coinsliberty.wallet.dialogs.secureCode.UndoSecureCodeDialog
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import com.coinsliberty.moneybee.utils.stub.StubNavigator
import com.coinsliberty.wallet.utils.currency.Currency
import com.coinsliberty.wallet.utils.extensions.disable
import com.coinsliberty.wallet.utils.extensions.enable
import com.coinsliberty.wallet.utils.extensions.visibleIfOrGone
import kotlinx.android.synthetic.main.attach_component.*
import kotlinx.android.synthetic.main.bottom_sheet_make_transfer.*
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.toolbar.view.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class ProfileFragmant : BaseKotlinFragment() {
    override val layoutRes: Int = R.layout.fragment_profile

    override val viewModel: ProfileViewModel by viewModel()
    override val navigator: StubNavigator = get()
    var bufferFile : Any? = null

    var isNeed2fa = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAttachButton.setOnClickListener {
            //
        // openGallery(1);
        }

        profileToolbar.ivToolbarIconLeft.visibility = View.GONE
        profileToolbar.ivToolbarRightIcon.setBackgroundResource(R.drawable.ic_ring)
        profileToolbar.tvToolbarTitle.text = "Profile"

        btnLoginUpdate.setOnClickListener {
            if (checkNotNull()) {
                viewModel.editProfile(
                    firstName = ifcProfileFirstName.getMyText(),
                    lastName = ifcProfileLastName.getMyText(),
                    phone = ifcProfilePhone.getMyText(),
                    optEnabled = isNeed2fa,
                    file = null,
                    otp = if(ifcProfile2Fa.visibility == View.VISIBLE) ifcProfile2Fa.getMyText() else null
                )
            } else {
                getErrorDialog("Empty fields")
            }
        }

        profileSwitch.initListeners {
            isNeed2fa = it
            ifcProfile2Fa.visibleIfOrGone { it }

            if (it) {
                viewModel.getOtp()
            } else {

                UndoSecureCodeDialog.newInstance(EditProfileRequest(ifcProfileFirstName.getMyText(), ifcProfileLastName.getMyText(), ifcProfilePhone.getMyText(), false)).apply {

                    initListeners {
                        update2FA(it)
                    }
                }.show(childFragmentManager, UndoSecureCodeDialog.TAG)
            }
        }

        tvCurrencyField.setOnClickListener {
            showPopupMenu()
        }

//        tvCurrencyField.setText(
//            viewModel.saveCurrency(if(it){
//                Currency.EUR.getTitle()
//            } else {
//                Currency.USD
//            })
//        }
        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        viewModel.showError.observe(this, ::getErrorDialog)
        bindDataTo(viewModel.ldProfile, ::initProfileData)
        bindDataTo(viewModel.ldOtp, ::initOtp)
        bindDataTo(viewModel.ldCurrency, ::initCurrency)
    }

    private fun initCurrency(currency: Currency?) {
        if(currency == null) {
            return
        }

        tvCurrencyField.text = currency.getTitle()
//        when (currency) {
//            Currency.USD -> {
//                scCurrency.changeStatus(false)
//            }
//            Currency.EUR -> {
//                scCurrency.changeStatus(true)
//            }
//        }
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

    private fun initOtp(s: String?) {
        update2FA(false)

        if(s == null) return

        SecureCodeDialog.newInstance(EditProfileRequest(ifcProfileFirstName.getMyText(), ifcProfileLastName.getMyText(), ifcProfilePhone.getMyText(), true), s, ifcProfileEmail.getMyText()).apply {
            initListeners {
                update2FA(it)
            }
        }.show(childFragmentManager, SecureCodeDialog.TAG)
    }

    fun update2FA(b: Boolean) {
        profileSwitch.changeStatus(b)
    }

    override fun onStart() {
        super.onStart()
        viewModel.getProfile()
        viewModel.getCurrency()
    }

    private fun initProfileData(profileResponse: ProfileResponse?) {
        ifcProfileFirstName.setText(profileResponse?.user?.firstName ?: "")
        ifcProfileLastName.setText(profileResponse?.user?.lastName ?: "")
        ifcProfilePhone.setText(profileResponse?.user?.phone ?: "")
        ifcProfileEmail.setText(profileResponse?.user?.login ?: "")
        profileSwitch.changeStatus(profileResponse?.user?.optEnabled == 1)
        ifcProfile2Fa.visibleIfOrGone { profileResponse?.user?.optEnabled == 1 }
        isNeed2fa = profileResponse?.user?.optEnabled == 1
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
            .show(childFragmentManager, ErrorDialog.TAG)
    }


}