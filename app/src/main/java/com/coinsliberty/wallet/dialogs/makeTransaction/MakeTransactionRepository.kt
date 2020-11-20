package com.coinsliberty.wallet.dialogs.makeTransaction

import com.coinsliberty.wallet.api.BtcApi
import com.coinsliberty.wallet.api.UserApi
import com.coinsliberty.wallet.data.BtcBalance
import com.coinsliberty.wallet.data.EditProfileRequest

class MakeTransactionRepository(private val api: UserApi, private val apiBtc: BtcApi) {

    suspend fun updateUser(body: EditProfileRequest) = api.editProfile(body)

    suspend fun sendBtcBalance(data: BtcBalance) = apiBtc.sendBtcRate(data)

    suspend fun getFee() = apiBtc.getFee()
}
