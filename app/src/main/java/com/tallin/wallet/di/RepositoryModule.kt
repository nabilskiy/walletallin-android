package com.tallin.wallet.di

import com.tallin.wallet.dialogs.faceIdDialog.FaceIdRepository
import com.tallin.wallet.dialogs.sendDialog.BtcRepository
import com.tallin.wallet.dialogs.qrCode.QrCodeRepository
import com.tallin.wallet.dialogs.ressPassword.ResetPassRepository
import com.tallin.wallet.dialogs.forgetPassword.ForgotPassRepository
import com.tallin.wallet.dialogs.makeTransaction.MakeTransactionRepository
import com.tallin.wallet.dialogs.secureCode.SecureCodeRepository
import com.tallin.wallet.dialogs.touchIdDialog.TouchIdRepository
import com.tallin.wallet.ui.actions.buy.BuySellRepository
import com.tallin.wallet.ui.actions.orderPreview.OrderPreviewRepository
import com.tallin.wallet.ui.actions.sell.ConfirmationRepository
import com.tallin.wallet.ui.actions.verifyPurchase.VerifyPurchaseRepository
import com.tallin.wallet.ui.exchange.ExchangeRepository
import com.tallin.wallet.ui.fragmentActions.ActionsRepository
import com.tallin.wallet.ui.kyc.kyc.KycRepository
import com.tallin.wallet.ui.kyc.manuallyKYC.KYCManuallyRepository
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.ui.pin.PinRepository
import com.tallin.wallet.ui.kyc.processKYC.KYCProcessRepository
import com.tallin.wallet.ui.kyc.webView.KYCWebViewRepository
import com.tallin.wallet.ui.profile.ProfileRepository
import com.tallin.wallet.ui.settings.SettingsRepository
import com.tallin.wallet.ui.singup.chooseWallet.SingUpChooseWalletRepository
import com.tallin.wallet.ui.singup.signup.SignUpRepository
import com.tallin.wallet.ui.singup.singupBusiness.SingUpBusinessRepository
import com.tallin.wallet.ui.transactions.transactionDocuments.TransactionDocumentsRepository
import com.tallin.wallet.ui.transactions.transactionsDocumentUpLoad.TransactionDocumentUpLoadRepository
import com.tallin.wallet.ui.wallet.WalletRepository
import com.tallin.wallet.utils.manager.BiometricManager
import org.koin.dsl.module


val repositoryModule = module {
    factory { SignUpRepository(get()) }
    factory { SingUpChooseWalletRepository(get()) }
    factory { SingUpBusinessRepository(get()) }
    factory { KYCProcessRepository() }
    factory { KycRepository(get(), get()) }
    factory { KYCManuallyRepository(get()) }
    factory { KYCWebViewRepository() }
    factory { ForgotPassRepository(get()) }
    factory { WalletRepository(get()) }
    factory { LoginRepository(get(), get()) }
    factory { ResetPassRepository(get()) }
    factory { ProfileRepository(get()) }
    factory { SettingsRepository(get()) }
    factory { ActionsRepository() }
    factory { BuySellRepository(get(), get()) }
    factory { ConfirmationRepository() }
    factory { VerifyPurchaseRepository() }
    factory { OrderPreviewRepository(get()) }
    factory { QrCodeRepository(get()) }
    factory { SecureCodeRepository(get()) }
    factory { BtcRepository(get()) }
    factory { ExchangeRepository(get()) }
    factory { MakeTransactionRepository(get(),get(),get()) }
    factory { TouchIdRepository(get()) }
    factory { FaceIdRepository(get()) }
    factory { PinRepository() }
    factory { TransactionDocumentsRepository(get()) }
    factory { TransactionDocumentUpLoadRepository(get()) }

    factory { BiometricManager(get(), get()) }
}