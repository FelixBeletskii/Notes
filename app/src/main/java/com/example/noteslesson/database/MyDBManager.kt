package com.example.noteslesson.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.provider.BaseColumns
import com.example.noteslesson.recycler.Item

class MyDBManager(context: Context) {
    val model: DataModel = DataModel()
    val myDBHelper = MyDBHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = myDBHelper.writableDatabase
    }

    fun insertDB(title: String, content: String, image: String?, ) {
        val values = ContentValues().apply {
            put(MyDBNameClass.COLUMN_NAME_TITLE, title)
            put(MyDBNameClass.COLUMN_NAME_CONTENT, content)
            put(MyDBNameClass.COLUMN_NAME_IMAGE, image)
        }
        db?.insert(MyDBNameClass.TABLE_NAME, null, values)
        val id = BaseColumns._ID
        model.createdItem.value = Item(title, content,image, id)

    }

    fun removeItemFromDB(id: String) { // в функцию удаления нужно передать айди, чтобы было понятно, что базе нужно удалить
      val selection = BaseColumns._ID + "=$id" // идентификатор берем из интерфейса бэйс колумнс + наш идентификатор
       db?.delete(MyDBNameClass.TABLE_NAME, selection,null) // передаем селекшн во второй аргумент, в первый название таблицы и последний налл


    }

    fun readDB(): ArrayList<Item> {
        val dataList = ArrayList<Item>()
        val cursor = db?.query(MyDBNameClass.TABLE_NAME, null, null, null, null, null, null,)
        with(cursor) {
            while (cursor?.moveToNext()!!) {
                val dataTitle =
                    cursor.getString(cursor.getColumnIndex(MyDBNameClass.COLUMN_NAME_TITLE))
                val dataContent =
                    cursor.getString(cursor.getColumnIndex(MyDBNameClass.COLUMN_NAME_CONTENT))
                val dataImage =
                    cursor.getString(cursor.getColumnIndex(MyDBNameClass.COLUMN_NAME_IMAGE))
                val dataId =
                    cursor.getString(cursor.getColumnIndex(BaseColumns._ID))
                val item = Item(dataTitle, dataContent, dataImage, dataId) // добавляем айди в записи класса, чтобы потом по
                // этому айди можно было удалить элемент
                dataList.add(item)
            }
            return dataList
        }
    }
    fun closeDB(){
        db?.close()
    }
}