package pl.mradtke.model.ui

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * @author Michał Radtke
 * @version 27.07.2020
 */
@Parcelize
data class UserItem(
        val username: String,
        val userUrl: String,
        val avatarUrl: String) : Parcelable
