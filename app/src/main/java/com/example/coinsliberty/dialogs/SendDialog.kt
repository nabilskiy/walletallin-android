package com.example.coinsliberty.dialogs


import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import org.koin.android.viewmodel.ext.android.viewModel
import com.example.coinsliberty.R
import com.example.coinsliberty.base.BaseKotlinDialogFragment
import com.example.coinsliberty.utils.stub.StubViewModel
import kotlinx.android.synthetic.main.dialog_send.*

private const val keyBundleTittle = "tittle"
private const val keyBundleLink = "link"
private const val keyBundleNameCripto = "nameCripto"
private const val keyBundleNameFiat = "nameFiat"
private const val keyBundleAmountCripto = "nmountCripto"
private const val keyBundleAmountFiat = "amountFiat"

class SendDialog : BaseKotlinDialogFragment() {
    override val layoutRes: Int = R.layout.dialog_send
    override val viewModel: StubViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCancelable = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvTittle.text = arguments?.getString(keyBundleTittle)
        tvLink.text = arguments?.getString(keyBundleLink)
        tvAmountCripto.text = arguments?.getString(keyBundleAmountCripto)
        tvAmountFiat.text = arguments?.getString(keyBundleAmountFiat)
        tvCriptoName.text = arguments?.getString(keyBundleNameCripto)
        tvFiatName.text = arguments?.getString(keyBundleNameFiat)

        ivClose.setOnClickListener { dismiss() }
    }

    companion object {
        val TAG: String = SendDialog::class.java.name
        fun newInstance(
            tittle: String, link: String, amountCripto: String, amountFiat: String,
            nameCripto: String, nameFiat: String
        ): DialogFragment {
            val fragment = SendDialog()
            val bundle = bundleOf(
                keyBundleTittle to tittle,
                keyBundleLink to link,
                keyBundleNameCripto to nameCripto,
                keyBundleNameFiat to nameFiat,
                keyBundleAmountCripto to amountCripto,
                keyBundleAmountFiat to amountFiat
            )
            fragment.arguments = bundle
            //fragment.setStyle(STYLE_NO_FRAME, 0)
            return fragment
        }
    }
}