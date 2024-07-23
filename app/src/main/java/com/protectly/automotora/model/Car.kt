package com.protectly.automotora.model

import android.os.Parcel
import android.os.Parcelable

data class Car(
    val title: String?,
    val description: String?,
    val engineOutput: String?,
    val highestSpeed: String?,
    val price: Int?,
    val rating: Double?,
    val totalCapacity: String?,
    val picUrl: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readValue(Double::class.java.classLoader) as? Double,
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(engineOutput)
        parcel.writeString(highestSpeed)
        parcel.writeValue(price)
        parcel.writeValue(rating)
        parcel.writeString(totalCapacity)
        parcel.writeString(picUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Car> {
        override fun createFromParcel(parcel: Parcel): Car {
            return Car(parcel)
        }

        override fun newArray(size: Int): Array<Car?> {
            return arrayOfNulls(size)
        }
    }
}

