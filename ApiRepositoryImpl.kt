package com.app.outrighttalk.modules.preference.data.repo

import com.app.outrighttalk.modules.preference.data.local.dataSource.PreferenceLocalDataSource
import com.app.outrighttalk.modules.preference.data.local.model.SavedAccount
import com.app.outrighttalk.modules.preference.data.model.local.RoomPmPrefItemModel
import com.app.outrighttalk.modules.preference.data.remote.dataSource.PreferenceRemoteDataSource
import com.app.outrighttalk.modules.preference.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import timber.log.Timber

class ApiRepositoryImpl(
    private val remoteDataSource: PreferenceRemoteDataSource,
    private val localDataSource: PreferenceLocalDataSource,
    ): PreferenceRepository {

    override suspend fun fetchAllRoomPmPref(
    ): Flow<Resource<List<RoomPmPrefItemModel>>> = flow {
        emit(Resource.Loading())
        emit(remoteDataSource.fetchAllRoomPmPref())
    }.flowOn(Dispatchers.IO)

    override suspend fun savePreferences(preferences: Map<String, String>): Flow<Resource<String>> = flow {
        emit(Resource.Loading())
        emit(remoteDataSource.savePreferences(preferences))
    }.flowOn(Dispatchers.IO)

    override suspend fun getSavedAccounts(): Flow<Resource<SavedAccount>> = flow {
        emit(Resource.Loading())
        emit(localDataSource.getLocallySavedAccounts())
    }

    override suspend fun updateSavedAccounts(
        newSavedAccounts: HashMap<String, String>
    ): Flow<Resource<Unit>> = flow {
        emit(Resource.Loading())
        emit(localDataSource.updateSavedAccounts(newSavedAccounts))
    }.flowOn(Dispatchers.IO)

}

