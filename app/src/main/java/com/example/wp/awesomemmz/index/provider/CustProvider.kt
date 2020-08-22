package com.example.wp.awesomemmz.index.provider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.text.TextUtils
import com.example.wp.resource.utils.LogUtils


/**
 * Created by wp on 2020/8/20.
 */
class CustProvider : ContentProvider() {
    private val MATCH_CODE_ITEM = 1    //匹配所有数据
    private val MATCH_CODE_ITEM_ID = 2 //根据ID匹配数据
    private val MATCH_CODE_ITEM_POS = 3//根据数据位置(position)匹配数据(pos=1不一定id=1)
    private val AUTHORITY = "com.example.wp.awesomemmz.provider" //authority
    private lateinit var mContext: Context

    private val uriMatcher: UriMatcher
    private val personProjectMap: HashMap<String, String>
    private lateinit var dbHelper: DBHelper

    init {
        //设置URI匹配规则
        uriMatcher = UriMatcher(UriMatcher.NO_MATCH)//不能匹配指定的URI时，就返回UriMatcher.NO_MATCH
                .apply {
                    addURI(AUTHORITY, "item", MATCH_CODE_ITEM)  //content://authority/item
                    addURI(AUTHORITY, "item/#", MATCH_CODE_ITEM_ID) //#表示匹配任意一个数字
                    addURI(AUTHORITY, "pos/#", MATCH_CODE_ITEM_POS) //content://authority/pos/#(后必须指定position值)
                }

        //project map
        personProjectMap = hashMapOf(
                Tables.ID to Tables.ID,
                Tables.NAME to Tables.NAME,
                Tables.SEX to Tables.SEX,
                Tables.AGE to Tables.AGE)
    }

    override fun onCreate(): Boolean {
        mContext = context!!
        dbHelper = DBHelper(mContext)

        return true
    }

    //用来返回数据的MIME类型
    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        LogUtils.d("-----insert()--uri: $uri")
        if (uriMatcher.match(uri) != MATCH_CODE_ITEM)
            throw IllegalArgumentException("Error Uri: $uri")

        val db = dbHelper.writableDatabase
        val id = db.insert(Tables.TABLE_NAME, "_id", values)
        if (id < 0) {
            throw SQLiteException("Unable to insert $values for $uri");
        }

        val newUri = ContentUris.withAppendedId(uri, id)
        mContext.contentResolver.notifyChange(newUri, null)
        LogUtils.d("-----insert()--newUri: $newUri")

        return newUri
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        LogUtils.d("-----update()--uri: $uri")
        val db = dbHelper.writableDatabase

        val count = when (uriMatcher.match(uri)) {
            MATCH_CODE_ITEM -> {
                db.update(Tables.TABLE_NAME, values, selection, selectionArgs)
            }
            MATCH_CODE_ITEM_ID -> {
                val id = uri.pathSegments[1]
                db.update(Tables.TABLE_NAME, values, Tables.ID + "=" + id
                        + if (!TextUtils.isEmpty(selection)) " and ($selection)" else "", selectionArgs)

            }
            else -> throw IllegalArgumentException("Error Uri: $uri")
        }
        mContext.contentResolver.notifyChange(uri, null)
        LogUtils.d("-----update()--count: $count")

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        LogUtils.d("-----delete()--uri: $uri")
        val db = dbHelper.writableDatabase

        val count = when (uriMatcher.match(uri)) {
            MATCH_CODE_ITEM -> {
                db.delete(Tables.TABLE_NAME, selection, selectionArgs)
            }
            MATCH_CODE_ITEM_ID -> {
                val id = uri.pathSegments[1]
                db.delete(Tables.TABLE_NAME, Tables.ID + "=" + id
                        + if (!TextUtils.isEmpty(selection)) " and ($selection)" else "", selectionArgs)

            }
            else -> throw IllegalArgumentException("Error Uri: $uri")
        }
        mContext.contentResolver.notifyChange(uri, null)
        LogUtils.d("-----delete()--count: $count")

        return count
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        LogUtils.d("-----query()--uri: $uri")
        val db = dbHelper.writableDatabase
        val sqlBuilder = SQLiteQueryBuilder() //查询辅助类
                .apply {
                    tables = Tables.TABLE_NAME
                    setProjectionMap(personProjectMap)
                }
        var limit: String? = null

        when (uriMatcher.match(uri)) {
            MATCH_CODE_ITEM -> {
            }
            MATCH_CODE_ITEM_ID -> {
                val id = uri.pathSegments[1]
                sqlBuilder.appendWhere(Tables.ID + "=" + id)
            }
            MATCH_CODE_ITEM_POS -> {
                val pos: String = uri.pathSegments[1]
                limit = "$pos, 1"
            }
            else -> throw IllegalArgumentException("Error Uri: $uri")
        }

        val cursor = sqlBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder, limit)
        cursor.setNotificationUri(mContext.contentResolver, uri)
        LogUtils.d("-----query()--count: ${cursor.count}")

        return cursor
    }

    private class DBHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
        private val DB_CREATE = "create table ${Tables.TABLE_NAME}" +
                " (${Tables.ID} integer primary key autoincrement, " +
                "${Tables.NAME} text not null, " +
                "${Tables.SEX} text not null, " +
                "${Tables.AGE} text not null);"

        companion object {
            private val DB_NAME = "person.db"
            private val DB_VERSION = 1
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(DB_CREATE)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${Tables.TABLE_NAME}")
            onCreate(db)
        }
    }

}