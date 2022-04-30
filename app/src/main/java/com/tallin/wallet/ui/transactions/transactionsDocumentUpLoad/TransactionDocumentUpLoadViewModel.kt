package com.tallin.wallet.ui.transactions.transactionsDocumentUpLoad

import android.app.Application
import android.content.Context
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.tallin.wallet.base.BaseViewModel
import com.tallin.wallet.data.response.KycManuallyResponse
import com.tallin.wallet.data.response.TransactionDocumentsInfoResponse
import com.tallin.wallet.model.SharedPreferencesProvider
import com.tallin.wallet.ui.login.LoginRepository
import com.tallin.wallet.utils.extensions.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class TransactionDocumentUpLoadViewModel(
    app: Application,
    private val repository: TransactionDocumentUpLoadRepository,
    val sharedPreferencesProvider: SharedPreferencesProvider,
    private val loginRepository: LoginRepository
): BaseViewModel(app, sharedPreferencesProvider, loginRepository) {

    val loadFileLiveData: MutableLiveData<Boolean> = MutableLiveData()

    private var loadFileJob: Job? = null

    fun loadFile(ctx: Context, uri: Uri, id: Int, docId: Int, transId: Int){
        if (loadFileJob == null) {
            loadFileJob = launch(::onErrorHandler) {
                withContext(Dispatchers.Main) { onStartProgress.value = Unit }
                val file: File = FileUtils.getFile(ctx, uri)
                val fileType = file
                val document = MultipartBody.Part.createFormData(
                    "file" + SimpleDateFormat("dd.MM.yyyy'-'HHmm").format(Date()),
                    file.name,
                    file.asRequestBody("image/png".toMediaTypeOrNull())
                )

                val assignId = MultipartBody.Part.createFormData("assign_id", id.toString())
                val documentId = MultipartBody.Part.createFormData("document_id", docId.toString())
                val trId = MultipartBody.Part.createFormData("trans_id", transId.toString())
                handleResponse(
                    repository.loadFile(assignId, documentId, document, trId)
                )
                withContext(Dispatchers.Main) { onEndProgress.value = Unit }
            }
        }
    }

    fun handleResponse(response: KycManuallyResponse){
        if (
            (response.result == false && response.error?.code == 1002)
        ) {
            launch(::onErrorHandler) {
                sharedPreferencesProvider.setToken("")

                delay(200)

                baseLogout.postValue(true)
            }
            return
        } else {
            if (response.error == null) {
                loadFileLiveData.postValue(response.result)
            } else loadFileLiveData.postValue(false)
        }
    }

    override fun stopRequest() {
        loadFileJob?.cancel()
    }
    override fun onErrorHandler(throwable: Throwable) {
        loadFileLiveData.postValue(false)
    }
}