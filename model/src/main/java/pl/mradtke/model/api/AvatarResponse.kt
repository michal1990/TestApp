package pl.mradtke.model.api

import com.google.gson.annotations.SerializedName

/**
 * Data models for [IAvatarsApi].
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */

data class AvatarListResponse(
        @SerializedName("list") val list: List<AvatarResponseItem>)

data class AvatarResponseItem(
        @SerializedName("avatar_360_url") val avatarUrl: String,
        val username: String)
