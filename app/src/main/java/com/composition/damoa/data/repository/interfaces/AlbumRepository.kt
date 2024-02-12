package com.composition.damoa.data.repository.interfaces

import com.composition.damoa.data.common.retrofit.callAdapter.ApiResponse
import com.composition.damoa.data.model.Album

interface AlbumRepository {
    suspend fun getAlbums(): ApiResponse<List<Album>>
    suspend fun getAlbum(id: Long): ApiResponse<Album>
}