package net.omisoft.stores.common.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.ADDRESS
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.CITY
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.ID
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.LATITUDE
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.LOGO_URL
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.LONGITUDE
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.NAME
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.PHONE
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.STATE
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.TABLE_NAME
import net.omisoft.stores.common.data.database.entity.StoreEntity.ColumnName.ZIP_CODE
import net.omisoft.stores.common.data.model.Store

@Entity(tableName = TABLE_NAME, indices = [Index(value = [ID], unique = true)])
data class StoreEntity(
    @PrimaryKey
    @ColumnInfo(name = ID) val storeId: String,
    @ColumnInfo(name = ADDRESS) val address: String,
    @ColumnInfo(name = CITY) val city: String,
    @ColumnInfo(name = LATITUDE) val latitude: String,
    @ColumnInfo(name = LONGITUDE) val longitude: String,
    @ColumnInfo(name = NAME) val name: String,
    @ColumnInfo(name = PHONE) val phone: String,
    @ColumnInfo(name = STATE) val state: String,
    @ColumnInfo(name = LOGO_URL) val storeLogoURL: String,
    @ColumnInfo(name = ZIP_CODE) val zipcode: String,
) {
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

fun StoreEntity.toStore(): Store {
    return Store(
        storeId = storeId,
        address = address,
        city = city,
        latitude = latitude,
        longitude = longitude,
        name = name,
        phone = phone,
        state = state,
        storeLogoURL = storeLogoURL,
        zipcode = zipcode,
    )
}