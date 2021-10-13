package com.tallin.wallet.ui.singup.singupBusiness

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.utils.extensions.bindDataTo
import com.tallin.wallet.utils.extensions.gone
import com.tallin.wallet.utils.extensions.visible
import kotlinx.android.synthetic.main.fragment_sing_up_business.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SingUpBusinessFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_sing_up_business
    override val viewModel: SignUpBusinessViewModel by viewModel()
    override val navigator: SingUpBusinessNavigation = get()

    private val textColorError = Color.RED
    private val textColorNormal = Color.parseColor("#8FACB6")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ivBack.setOnClickListener { activity?.onBackPressed() }
        sign_in.setOnClickListener { navigator.goToLoginFragment(navController) }
        btnSignUp.setOnClickListener {
            if (ifcSignUpPassword.getMyText() == ifcSignUpPasswordConfirm.getMyText())
                viewModel.signUp(
                    "1",
                    ifcCompanyName.getMyText(),
                    ifcNum.getMyText(),
                    ifcNumPhone.getMyText(),
                    ifcWebsite.getMyText(),
                    ifcFirstName.getMyText(),
                    ifcLastName.getMyText(),
                    ifcCity.getMyText(),
                    ifcStreet.getMyText(),
                    ifcPostCode.getMyText(),
                    ifcCountry.getMyText(),
                    ifcDescription.getMyText()
                )
            else
                viewModel.showError(getString(R.string.wrong_pass))
        }

        btnAddressInfo.setOnClickListener {
            if (llAddressInfo.visibility == View.VISIBLE){
                triangle.rotation = -90F
                llAddressInfo.gone()
            } else{
                triangle.rotation = 0F
                llAddressInfo.visible()
            }
        }


        sign_in.text = getFormattedText()
        ifcSignUpPasswordConfirm.addTextWatcher(repeatPasswordTextWatcher)

        subscribeLiveData()
    }

    private val repeatPasswordTextWatcher =
        object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() == ifcSignUpPassword.getMyText())
                    ifcSignUpPasswordConfirm.setTextColor(textColorNormal)
                else ifcSignUpPasswordConfirm.setTextColor(textColorError)
            }
        }

    private fun getFormattedText(): SpannableStringBuilder {
        val t1 = "${getString(R.string.login_from_sing_up_business_0)} "
        val t2 = getString(R.string.login_from_sing_up_business_1)
        return SpannableStringBuilder(t1 + t2).apply {
            setSpan(
                ForegroundColorSpan(Color.WHITE),
                t1.length,
                t1.length + t2.length,
                Spannable.SPAN_EXCLUSIVE_INCLUSIVE
            )
        }
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.result, ::showResult)
    }
    private fun showResult(b: Boolean?) {
        if (b == true) {
            activity?.onBackPressed()
        }
    }
}