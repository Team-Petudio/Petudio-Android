package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.Album
import kotlinx.collections.immutable.PersistentList

interface AlbumRepository {
    suspend fun getAlbums(): ApiResponse<PersistentList<Album>>

    suspend fun getAlbum(id: Long): ApiResponse<Album>
}