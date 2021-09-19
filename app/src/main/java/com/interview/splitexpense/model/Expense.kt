package com.interview.splitexpense.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class Expense (@PrimaryKey(autoGenerate = true) val uid: Int = 0,
               @ColumnInfo val title: String?,
               @ColumnInfo val description: String?,
               @ColumnInfo val date: String?,
               @ColumnInfo val amount: String?,
               @ColumnInfo val paidBy: String?,
               @ColumnInfo val sharedBy: String? = "Everyone equally",
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(uid)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(date)
        parcel.writeString(amount)
        parcel.writeString(paidBy)
        parcel.writeString(sharedBy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Expense> {
        override fun createFromParcel(parcel: Parcel): Expense {
            return Expense(parcel)
        }

        override fun newArray(size: Int): Array<Expense?> {
            return arrayOfNulls(size)
        }
    }
}