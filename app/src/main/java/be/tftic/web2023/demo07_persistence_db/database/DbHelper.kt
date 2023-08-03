package be.tftic.web2023.demo07_persistence_db.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DbContract.NAME, null, DbContract.VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(DbContract.PersonTable.SCRIPT_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        // Migration simple (Ne pas faire en prod :D)
        // - On efface tout
        db?.execSQL(DbContract.PersonTable.SCRIPT_DROP)
        // - On regenere tout
        onCreate(db)
    }
}