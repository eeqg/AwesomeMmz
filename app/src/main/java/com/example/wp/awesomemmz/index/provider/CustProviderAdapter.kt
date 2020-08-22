package com.example.wp.awesomemmz.index.provider

import android.content.ContentUris
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import com.example.wp.resource.utils.LogUtils

/**
 * Created by wp on 2020/8/22.
 */
class CustProviderAdapter(context: Context) {
    private val mContext = context

    companion object {
        val AUTHORITY = "com.example.wp.awesomemmz.provider" //authority
        val CONTENT_URI = Uri.parse("content://$AUTHORITY/item")
        val CONTENT_POS_URI = Uri.parse("content://$AUTHORITY/pos")
    }

    //insert
    fun insertPerson(personInfo: PersonInfo): Long {
        val values = ContentValues().apply {
            this.put(Tables.NAME, personInfo.name)
            this.put(Tables.SEX, personInfo.sex)
            this.put(Tables.AGE, personInfo.age)
        }
        val uri: Uri? = mContext.contentResolver.insert(CONTENT_URI, values)
        var itemId = "-1"
        uri?.let { itemId = it.pathSegments[1] }
        LogUtils.d("-----insertPerson()--itemId = $itemId")

        return itemId.toLong()
    }

    //update
    fun updatePerson(personInfo: PersonInfo): Boolean {
        val uri = ContentUris.withAppendedId(CONTENT_URI, personInfo.id)
        LogUtils.d("-----updatePerson()--uri = $uri")
        val values = ContentValues().apply {
            this.put(Tables.NAME, personInfo.name)
            this.put(Tables.SEX, personInfo.sex)
            this.put(Tables.AGE, personInfo.age)
        }
        return mContext.contentResolver.update(uri, values, null, null) > 0
    }

    //delete
    fun deletePersonById(id: Long): Boolean {
        val uri = ContentUris.withAppendedId(CONTENT_URI, id)
        LogUtils.d("-----deletePerson()--uri = $uri")
        return mContext.contentResolver.delete(uri, null, null) > 0
    }

//    //delete
//    fun deletePersonByPos(position: Long): Boolean {
//        val uri = ContentUris.withAppendedId(CONTENT_POS_URI, position)
//        LogUtils.d("-----deletePerson()--uri = $uri")
//        return mContext.contentResolver.delete(uri, null, null) > 0
//    }

    //query
    fun getAllPersons(): List<PersonInfo> {
        val personList = mutableListOf<PersonInfo>()
        val projection = arrayOf<String>(
                Tables.ID, Tables.NAME, Tables.SEX, Tables.AGE
        )
        val cursor = mContext.contentResolver.query(CONTENT_URI, projection,
                null, null, Tables.DEFAULT_SORT_ORDER)
        cursor?.let {
            if (it.moveToFirst()) {
                do {
                    personList.add(PersonInfo(
                            it.getLong(0),
                            it.getString(1),
                            it.getString(2),
                            it.getString(3)))
                } while (it.moveToNext())
            }
            cursor.close()
        }

        return personList
    }

    fun getPersonCount(): Int {
        val cursor = mContext.contentResolver.query(CONTENT_URI, null,
                null, null, null)
        cursor?.let {
            return it.count
        }
        return 0
    }

    fun getPersonById(id: Long): PersonInfo? {
        val uri = ContentUris.withAppendedId(CONTENT_URI, id)
        val projection = arrayOf<String>(
                Tables.ID, Tables.NAME, Tables.SEX, Tables.AGE
        )
        val cursor = mContext.contentResolver.query(uri, projection,
                null, null, Tables.DEFAULT_SORT_ORDER)

        if (cursor == null || !cursor.moveToFirst()) return null

        val name = cursor.getString(1)
        val sex = cursor.getString(2)
        val age = cursor.getString(3)
        cursor.close()

        return PersonInfo(id, name, sex, age)
    }

    fun getPersonByPosition(position: Int): PersonInfo? {
        val uri = ContentUris.withAppendedId(CONTENT_POS_URI, position.toLong())
        val projection = arrayOf<String>(
                Tables.ID, Tables.NAME, Tables.SEX, Tables.AGE
        )
        val cursor = mContext.contentResolver.query(uri, projection,
                null, null, Tables.DEFAULT_SORT_ORDER)

        if (cursor == null || !cursor.moveToFirst()) return null

        val id = cursor.getLong(0)
        val name = cursor.getString(1)
        val sex = cursor.getString(2)
        val age = cursor.getString(3)
        cursor.close()

        return PersonInfo(id, name, sex, age)
    }
}