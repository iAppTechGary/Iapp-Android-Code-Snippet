package com.app.outrighttalk.modules.preference.data.remote.api

import com.app.outrighttalk.modules.preference.data.remote.model.response.RawRoomPmPreferenceResponse
import com.app.outrighttalk.modules.room.roomwindow.data.source.remote.model.base.RemoteApiResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    @GET("preferences/get-all/room")
    suspend fun fetchAllRoomPmPreferences(
        @Header("Authorization") token: String
    ): Response<RemoteApiResponse<RawRoomPmPreferenceResponse>>

    @POST("preferences/save")
    @FormUrlEncoded
    suspend fun savePreferences(
        @Header("Authorization") token: String,
        @FieldMap preferencesMap: Map<String, String>
    ): Response<RemoteApiResponse<*>>


}