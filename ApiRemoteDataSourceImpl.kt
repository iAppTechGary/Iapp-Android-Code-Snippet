package com.app.outrighttalk.modules.preference.data.remote.dataSource

import com.app.outrighttalk.modules.preference.data.model.local.RoomPmPrefItemModel
import com.app.outrighttalk.modules.preference.data.model.mapper.fromRawFieldToPrefItemModelList
import com.app.outrighttalk.modules.preference.data.remote.api.PreferenceService
import com.app.outrighttalk.modules.preference.util.Resource
import com.app.outrighttalk.modules.preference.util.safeApiCall
import com.app.outrighttalk.utils.SessionManager


class ApiRemoteDataSourceImpl(
    private val service: PreferenceService,
    private val sessionManager: SessionManager
): PreferenceRemoteDataSource {

    override suspend fun fetchAllRoomPmPref(
    ): Resource<List<RoomPmPrefItemModel>> {
        return safeApiCall(
            call = { service.fetchAllRoomPmPreferences(sessionManager.token) },
            result = { it.data?.list?.fromRawFieldToPrefItemModelList() }
        )
    }

    override suspend fun savePreferences(
        preferences: Map<String, String>
    ): Resource<String> {
        return safeApiCall(
            call = { service.savePreferences(sessionManager.token, preferences) },
            result = { it.message }
        )
    }
}

