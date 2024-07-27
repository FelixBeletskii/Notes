package com.example.noteslesson.database

import android.provider.BaseColumns

object MyDBNameClass: BaseColumns {
    const val TABLE_NAME ="my_table"
    const val COLUMN_NAME_TITLE ="title"
    const val COLUMN_NAME_CONTENT="content"
    const val COLUMN_NAME_IMAGE = "uri"

    const val DATABASE_VERSION = 1
    const val DATABASE_NAME="MyDB.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME(" +
            "${BaseColumns._ID} INTEGER PRIMARY KEY, $COLUMN_NAME_TITLE TEXT, $COLUMN_NAME_CONTENT TEXT, $COLUMN_NAME_IMAGE TEXT)"


    const val SQL_DELETE_TABLE = "DROP TABLE IF EXISTS ${MyDBNameClass.TABLE_NAME}"
}