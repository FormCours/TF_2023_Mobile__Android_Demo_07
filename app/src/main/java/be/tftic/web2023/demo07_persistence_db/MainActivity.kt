package be.tftic.web2023.demo07_persistence_db

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import be.tftic.web2023.demo07_persistence_db.database.dao.PersonDao
import be.tftic.web2023.demo07_persistence_db.models.Person
import java.time.LocalDate

class MainActivity : AppCompatActivity() {

    private val TAG = "MyDebugTag"

    private val personDao = PersonDao(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        personDao.openWritable()

        // addPersonInDB()

        Log.w(TAG, "Avant")
        getPersonInDB()

        removeOnePersonInDB()

        Log.w(TAG, "Après")
        getPersonInDB()
    }

    private fun removeOnePersonInDB() {

        val test : Boolean = personDao.delete(1)

        Log.w(TAG, if(test) "Element supprimé !" else "Aucun element supprimé")
    }

    private fun getPersonInDB() {

        val people : List<Person> =  personDao.getAll()

        for(person in people) {
            Log.d(TAG, person.toString())
        }
    }

    private fun addPersonInDB() {
        val riri : Person = Person(
            firstName = "Riri",
            lastName =  "Duck",
            birthDate = LocalDate.of(2007, 7, 7),
            email =  "riri42@demo.be",
            phone = "+324 123 45 67"
        )

        val zaza : Person = Person(
            firstName = "Zaza",
            lastName =  "Vanderquack",
            birthDate = LocalDate.of(2006, 9, 13),
            email =  "zaza.vanderquack@demo.org",
            phone = null
        )

        val della : Person = Person(
            firstName = "Della",
            lastName =  "Duck",
            birthDate = LocalDate.of(1988, 5, 3),
            email =  "della.duck@demo.be",
            phone = null
        )

        personDao.create(riri)
        personDao.create(zaza)
        personDao.create(della)
    }

    override fun onDestroy() {
        super.onDestroy()

        personDao.close()
    }
}