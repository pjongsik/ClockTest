package kr.co.shared.clocktest.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDatabase"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 SQL 문장 실행
        db.execSQL("CREATE TABLE MyTable (id INTEGER PRIMARY KEY, name TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 업그레이드 시 실행되는 코드
        // 필요에 따라 테이블 재생성 등의 작업을 수행할 수 있음
    }
}