package kr.co.shared.clocktest.db

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "MyDatabase"
        private const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // 테이블 생성 SQL 문장 실행
        db.execSQL("CREATE TABLE MyTable (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)")
        db.execSQL("CREATE TABLE MyRoutine (seq INTEGER PRIMARY KEY AUTOINCREMENT,  name varchar(100), workout int, rest int, sets int, dt_insert datetime, dt_update datetime)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // 데이터베이스 업그레이드 시 실행되는 코드
        // 필요에 따라 테이블 재생성 등의 작업을 수행할 수 있음
    }

    fun insertRoutine(name: String, workout: Int, rest: Int, sets: Int) {
        // 읽고 쓰기가 가능하게 DB 열기
        val db = writableDatabase
        // DB에 입력한 값으로 행 추가
        db.execSQL("INSERT INTO MyRoutine(name, workout, rest, sets, dt_insert) VALUES('$name', '$workout', '$rest', '$sets', now())")
        db.close()
    }

    fun updateRoutine(id: Int, name: String, workout: Int, rest: Int, sets: Int) {
        val db = writableDatabase
        // 입력한 항목과 일치하는 행의 이름 정보 수정
        db.execSQL("UPDATE MyRoutine SET name='$name', workout='$workout',  rest='$rest',  sets='$sets', dt_update = now() WHERE id='$id'")
        db.close()
    }

    fun deleteRoutine(id: Int) {
        val db = writableDatabase
        // 입력한 항목과 일치하는 행 삭제
        db.execSQL("DELETE FROM MyRoutine WHERE id='$id'")
        db.close()
    }

    @SuppressLint("Range")
    fun selectRoutine() {
        val db = writableDatabase
        var query1 = "SELECT * FROM MyRoutine;"
        var cursor2 = db.rawQuery(query1,null)
        while(cursor2.moveToNext()){

            Log.d("pjs", "name : " +cursor2.getString(cursor2.getColumnIndex("name"))
            + ", workout : " +cursor2.getString(cursor2.getColumnIndex("workout"))
                    + ", rest : " +cursor2.getString(cursor2.getColumnIndex("rest"))
                    + ", sets : " +cursor2.getString(cursor2.getColumnIndex("sets"))
            );
        }
        cursor2.close()
        db.close()
    }

//    fun testProcess() {
//        Log.d("pjs", "dbTest start" );
//
//        val context: Context = this;
//
//        val dbHelper = DatabaseHelper(context)
//        val db = dbHelper.writableDatabase // 데이터베이스 쓰기 모드
//
//        val values = ContentValues()
//        values.put("name", "John")
//        val newRowId = db.insert("MyTable", null, values)
//
//        var query = "INSERT INTO MyTable('name') values('pjongsik');"
//        db.execSQL(query)
//
//        // 데이터 조회 예시
//        val cursor: Cursor = db.query("MyTable", null, null, null, null, null, null)
//        while (cursor.moveToNext()) {
//            val id = cursor.getString(cursor.getColumnIndex("id"))
//            val name = cursor.getString(cursor.getColumnIndex("name"))
//            Log.d("pjs",  "id = "+  id + "name : " + name);
//            // 데이터 처리
//        }
//        cursor.close()
//
//        var query1 = "SELECT * FROM mytable;"
//        var cursor2 = db.rawQuery(query1,null)
//        while(cursor2.moveToNext()){
//
//            Log.d("pjs", "name : " +cursor2.getString(cursor2.getColumnIndex("name")));
//        }
//        cursor2.close()
//
//        db.close()
//    }
}