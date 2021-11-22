package com.tallin.wallet.ui.kyc.manuallyKYC

import android.app.Application
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class KYCManuallyViewModel(
    app: Application,
    private val repository: KYCManuallyRepository,
    private val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
) : BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val resultSendFile = MutableLiveData<Boolean>()

    private var sendFileJob: Job? = null

    fun sendFile(uri: String, id: Int, docId: Int) {
        sendFileJob = launch(::onErrorHandler) {
            withContext(Dispatchers.Main){onStartProgress.value = Unit}
            val file = File(uri)
            val document = MultipartBody.Part.createFormData(
                "file" + SimpleDateFormat("dd.MM.yyyy'-'HHmm").format(Date()),
                file.name,
                file.asRequestBody("image/jpg".toMediaTypeOrNull()))

            val assignId = MultipartBody.Part.createFormData("assign_id", id.toString())
            val documentId = MultipartBody.Part.createFormData("document_id", docId.toString())

            val sendDoc = repository.sendDoc(assignId, documentId, document)
            withContext(Dispatchers.Main){onEndProgress.value = Unit}
            if(sendDoc.result == true) {
                resultSendFile.postValue(true)
            }

        }
    }

    override fun stopRequest() {
        sendFileJob?.cancel()
    }

    override fun onErrorHandler(throwable: Throwable) {
        resultSendFile.postValue(false)
    }
}