package com.tallin.wallet.ui.settings

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.tallin.wallet.BottomFragment
import com.tallin.wallet.R
import com.tallin.wallet.base.BaseKotlinFragment
import com.tallin.wallet.databinding.FragmentSettingsBinding
import com.tallin.wallet.dialogs.ressPassword.ResetPassDialog
import com.tallin.wallet.ui.MainActivity
import com.tallin.wallet.utils.extensions.bindDataTo
import org.koin.android.ext.android.get
import org.koin.android.viewmodel.ext.android.viewModel

class SettingsFragment() : BaseKotlinFragment() {
    override val layoutRes = R.layout.fragment_settings
    override val viewModel: SettingsViewModel by viewModel()
    override val navigator: SettingsNavigation = get()

    private lateinit var binding: FragmentSettingsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

        subscribeLiveData()
    }

    private fun subscribeLiveData() {
        bindDataTo(viewModel.ldLogout, ::logout)
        bindDataTo(viewModel.ldAva, ::loadAva)
    }

    private fun logout(b: Boolean?) {
        if(b == true) {
            val intent = Intent(context, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context?.startActivity(intent)
            if (context is Activity) {
                (context as Activity).finish()
            }
            Runtime.getRuntime().exit(0)
        }
    }

    private fun loadAva(id: Long) {
        Glide.with(this)
            .asBitmap()
            .load(viewModel.getUserAvatarGlideUrl(id))
            .apply(RequestOptions.circleCropTransform())
            .skipMemoryCache(true)
            .into(object : CustomTarget<Bitmap>() {
                override fun onResourceReady(
                    resource: Bitmap,
                    transition: Transition<in Bitmap>?
                ) {
                    binding.settingsToolbar.ivToolbarLogo.setImageBitmap(resource)
                }

                override fun onLoadFailed(errorDrawable: Drawable?) {
                    super.onLoadFailed(errorDrawable)
//                    Toast.makeText(context, "Unable to download", Toast.LENGTH_SHORT).show()
                }

                override fun onLoadCleared(placeholder: Drawable?) {}
            })
    }

    private fun initView() {
        binding.settingsToolbar.ivAddPhoto.visibility = View.GONE

        binding.btnProfile.setOnClickListener {
            ((parentFragment as NavHostFragment).parentFragment as? BottomFragment)?.goToProfile()
        }
        binding.btnSecurity.setOnClickListener {
            ResetPassDialog.newInstance()
                .apply { initListeners {
                    //if(!it) ErrorDialog.newInstance("Пустые поля").show(childFragmentManager, ErrorDialog.TAG)
                    dismiss()

                } }
                .show(childFragmentManager, ResetPassDialog.TAG)
        }
        binding.btnLogout.setOnClickListener {
            viewModel.logout()
        }
        viewModel.loadProfile()
        binding.settingsToolbar.ivBack.setOnClickListener { activity?.onBackPressed() }

        binding.btnKYC.setOnClickListener { navigator.goToKyc(navController) }
    }

}