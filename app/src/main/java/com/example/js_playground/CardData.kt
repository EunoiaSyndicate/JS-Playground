package com.example.js_playground

import android.os.Parcel
import android.os.Parcelable

data class CardData(
  val title: String,
  val status: String,
  val difficultly: String
) : Parcelable {

  constructor(parcel: Parcel) : this(
    parcel.readString() ?: "",
    parcel.readString() ?: "",
    parcel.readString() ?: ""
  )

  override fun writeToParcel(parcel: Parcel, flags: Int) {
    parcel.writeString(title)
    parcel.writeString(status)
    parcel.writeString(difficultly)
  }

  override fun describeContents(): Int {
    return 0
  }

  companion object CREATOR : Parcelable.Creator<CardData> {
    override fun createFromParcel(parcel: Parcel): CardData {
      return CardData(parcel)
    }

    override fun newArray(size: Int): Array<CardData?> {
      return arrayOfNulls(size)
    }
  }
}
