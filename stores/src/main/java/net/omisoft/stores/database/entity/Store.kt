package net.omisoft.stores.database.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import net.omisoft.stores.database.entity.Store.ColumnName.ADDRESS
import net.omisoft.stores.database.entity.Store.ColumnName.CITY
import net.omisoft.stores.database.entity.Store.ColumnName.ID
import net.omisoft.stores.database.entity.Store.ColumnName.LATITUDE
import net.omisoft.stores.database.entity.Store.ColumnName.LOGO_URL
import net.omisoft.stores.database.entity.Store.ColumnName.LONGITUDE
import net.omisoft.stores.database.entity.Store.ColumnName.NAME
import net.omisoft.stores.database.entity.Store.ColumnName.PHONE
import net.omisoft.stores.database.entity.Store.ColumnName.STATE
import net.omisoft.stores.database.entity.Store.ColumnName.TABLE_NAME
import net.omisoft.stores.database.entity.Store.ColumnName.ZIP_CODE

@Entity(tableName = TABLE_NAME, indices = [Index(value = [ID], unique = true)])
data class Store(@PrimaryKey @ColumnInfo(name = ID)
                 @SerializedName("storeID") val storeId: String,
                 @ColumnInfo(name = ADDRESS)
                 @SerializedName("address") val address: String,
                 @ColumnInfo(name = CITY)
                 @SerializedName("city") val city: String,
                 @ColumnInfo(name = LATITUDE)
                 @SerializedName("latitude") val latitude: String,
                 @ColumnInfo(name = LONGITUDE)
                 @SerializedName("longitude") val longitude: String,
                 @ColumnInfo(name = NAME)
                 @SerializedName("name") val name: String,
                 @ColumnInfo(name = PHONE)
                 @SerializedName("phone") val phone: String,
                 @ColumnInfo(name = STATE)
                 @SerializedName("state") val state: String,
                 @ColumnInfo(name = LOGO_URL)
                 @SerializedName("storeLogoURL") val storeLogoURL: String,
                 @ColumnInfo(name = ZIP_CODE)
                 @SerializedName("zipcode") val zipcode: String) : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty(),
            parcel.readString().orEmpty()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(storeId)
        parcel.writeString(address)
        parcel.writeString(city)
        parcel.writeString(latitude)
        parcel.writeString(longitude)
        parcel.writeString(name)
        parcel.writeString(phone)
        parcel.writeString(state)
        parcel.writeString(storeLogoURL)
        parcel.writeString(zipcode)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Store> {
        override fun createFromParcel(parcel: Parcel): Store {
            return Store(parcel)
        }

        override fun newArray(size: Int): Array<Store?> {
            return arrayOfNulls(size)
        }
    }

    object ColumnName {
        const val TABLE_NAME = "stores"
        const val ID = "${TABLE_NAME}_id"
        const val ADDRESS = "${TABLE_NAME}_address"
        const val CITY = "${TABLE_NAME}_city"
        const val NAME = "${TABLE_NAME}_name"
        const val PHONE = "${TABLE_NAME}_phone"
        const val ZIP_CODE = "${TABLE_NAME}_zip_code"
        const val LATITUDE = "${TABLE_NAME}_latitude"
        const val LONGITUDE = "${TABLE_NAME}_longitude"
        const val LOGO_URL = "${TABLE_NAME}_logo_url"
        const val STATE = "${TABLE_NAME}_state"
    }
}