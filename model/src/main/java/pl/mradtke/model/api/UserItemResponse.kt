package pl.mradtke.model.api

/**
 * Data models for [IUsersApi].
 *
 * @author Michał Radtke
 * @version 27.07.2020
 */

class UserListResponse : ArrayList<UserItemResponse>()

data class UserItemResponse(val url: String)
