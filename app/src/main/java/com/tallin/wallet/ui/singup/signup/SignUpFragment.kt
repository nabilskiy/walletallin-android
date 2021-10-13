package com.tallin.wallet.ui.singup.signup

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
import kotlinx.android.synthetic.main.fragment_sign_up.btnSignUp
import kotlinx.android.synthetic.main.fragment_sign_up.ifcEmail
import kotlinx.android.synthetic.main.fragment_sign_up.ifcFirstName
import kotlinx.android.synthetic.main.fragment_sign_up.ifcLastName
import kotlinx.android.synthetic.main.fragment_sign_up.ifcSignUpPassword
import kotlinx.android.synthetic.main.fragment_sign_up.ifcSignUpPasswordConfirm
import kotlinx.android.synthetic.main.fragment_sign_up.ivBack
import kotlinx.android.synthetic.main.fragment_sign_up.sign_in
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

        ivBack.setOnClickListener { activity?.onBackPressed() }
        sign_in.setOnClickListener { navigator.goToLoginFragment(navController) }
        btnSignUp.setOnClickListener {
            if (ifcSignUpPassword.getMyText() == ifcSignUpPasswordConfirm.getMyText())
                viewModel.signUp(
                    "0",
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