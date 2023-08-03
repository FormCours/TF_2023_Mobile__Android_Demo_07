package be.tftic.web2023.demo07_persistence_db.database.dao

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import androidx.core.database.getStringOrNull
import be.tftic.web2023.demo07_persistence_db.database.DbContract
import be.tftic.web2023.demo07_persistence_db.database.DbHelper
import be.tftic.web2023.demo07_persistence_db.models.Person
import java.time.LocalDate

class PersonDao(val context: Context) {

    var dbHelper : DbHelper? = null
    var db : SQLiteDatabase? = null

    // region Méthodes de connexion à la DB
    fun openWritable() : Unit {
        dbHelper = DbHelper(context)
        db = dbHelper?.writableDatabase
    }

    fun openReadable() : Unit {
        dbHelper = DbHelper(context)
        db = dbHelper?.readableDatabase
    }

    fun close() : Unit {
        db?.close()
        dbHelper?.close()
    }
    // endregion

    // region Méthodes du « CRUD »
    private fun getContentValues(person: Person) : ContentValues {
        val contentValues = ContentValues().apply {
            put(DbContract.PersonTable.FIRST_NAME, person.firstName)
            put(DbContract.PersonTable.LAST_NAME, person.lastName)
            put(DbContract.PersonTable.BIRTH_DATE, person.birthDate.toString())
            put(DbContract.PersonTable.EMAIL, person.email)
            put(DbContract.PersonTable.PHONE_NUMBER, person.phone)
        }
        return contentValues
    }

    private fun cursorToPerson(cursor: Cursor) : Person {
        val person = Person(
            cursor.getLong(cursor.getColumnIndexOrThrow(DbContract.PersonTable.ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.PersonTable.FIRST_NAME)),
            cursor.getString(cursor.getColumnIndexOrThrow(DbContract.PersonTable.LAST_NAME)),
            LocalDate.parse(cursor.getString(cursor.getColumnIndexOrThrow(DbContract.PersonTable.BIRTH_DATE))),
            cursor.getStringOrNull(cursor.getColumnIndexOrThrow(DbContract.PersonTable.EMAIL)),
            cursor.getStringOrNull(cursor.getColumnIndexOrThrow(DbContract.PersonTable.PHONE_NUMBER)),
        )
        return person
    }


    fun getAll() : List<Person> {

        // Execution de la requete SQL
        val cursor = db!!.query(
            DbContract.PersonTable.TABLE_NAME,
            null, // Liste des colonnes à obtenir (null -> SELECT * ...)
            null,  null, // Clause "Where"
            null, // Clause GroupeBy
            null, // Clause Having
            "${DbContract.PersonTable.LAST_NAME} ASC, ${DbContract.PersonTable.FIRST_NAME} ASC"
        )
        // Return : A Cursor object, which is positioned before the first entry.

        // Gestion d'un resultat "vide"
        if(cursor.count == 0) {
            return listOf()
        }

        // Gestion d'un resultat "non vide"
        val result = mutableListOf<Person>()

        // - Positionner le cursor sur un element
        cursor.moveToFirst()

        while(!cursor.isAfterLast) {
            // - Récuperation des données
            val person: Person = cursorToPerson(cursor)

            // - Ajout du resultat dans la liste
            result.add(person)

            // - On passe a la ligne suivante
            cursor.moveToNext()
        }

        // - On ferme le curseur
        cursor.close()

        return result.toList()
    }

    fun getById(personId: Long) : Person? {

        // Execution de la requête
        val cursor = db!!.query(
            DbContract.PersonTable.TABLE_NAME,
            null,
            DbContract.PersonTable.ID + " = ? ",
            arrayOf(personId.toString()),
            null,
            null,
            null
        )

        // Gestion du cas d'un élément inexistant
        if(cursor.count == 0) {
            return null
        }

        // Gestion de l'élément existant
        cursor.moveToFirst()
        val result : Person = cursorToPerson(cursor)

        // Fermeture du cursor
        cursor.close()

        return result
    }

    fun create(person: Person) : Long {
        val id = db!!.insert(
            DbContract.PersonTable.TABLE_NAME,
            null,
            getContentValues(person)
        )

        return id
    }

    fun update(person: Person) : Boolean {
        val nbRow = db!!.update(
            DbContract.PersonTable.TABLE_NAME,
            getContentValues(person),
            DbContract.PersonTable.ID + " = ?",
            arrayOf(person.id.toString())
        )

        return nbRow == 1
    }

    fun delete(personId : Long) : Boolean {
        val nbRow = db!!.delete(
            DbContract.PersonTable.TABLE_NAME,
            DbContract.PersonTable.ID + " = ?" ,
            arrayOf(personId.toString())
        )

        return nbRow == 1
    }
    // endregion
}