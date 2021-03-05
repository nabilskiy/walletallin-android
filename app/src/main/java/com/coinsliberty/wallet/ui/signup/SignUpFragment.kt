package com.coinsliberty.wallet.ui.signup

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.View
import com.coinsliberty.wallet.R
import com.coinsliberty.wallet.base.BaseKotlinFragment
import com.coinsliberty.wallet.utils.extensions.bindDataTo
import kotlinx.android.synthetic.main.fragment_sign_up.*
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel


class SignUpFragment : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModel()
    override val navigator: SignUpNavigation = get()

    private val textColorError = Color.RED
    private val textColorNormal = Color.parseColor("#8FACB6")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sign_in.setOnClickListener { activity?.onBackPressed() }
        btnSignUp.setOnClickListener {
            if (ifcSignUpPassword == ifcSignUpPasswordConfirm)
                viewModel.signUp(
                    ifcEmail.getMyText(),
                    ifcSignUpPassword.getMyText(),
                    ifcFirstName.getMyText(),
                    ifcLastName.getMyText()
                )
            else
                viewModel.showError(getString(R.string.wrong_pass))
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
        val t1 = "${getString(R.string.login_from_sing_up_0)} "
        val t2 = getString(R.string.login_from_sing_up_1)
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