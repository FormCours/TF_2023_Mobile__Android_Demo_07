package be.tftic.web2023.demo07_persistence_db.database

class DbContract {
    // Information de la base de donnée
    companion object {
        const val NAME : String = "Arnaud.sqlite"
        const val VERSION : Int = 2
    }

    // Information necessaire pour la table "person"
    class PersonTable {

        companion object {
            // Le nom de la table
            const val TABLE_NAME = "person"

            // Les noms des colonnes
            const val ID = "_id"
            const val FIRST_NAME = "first_name"
            const val LAST_NAME = "last_name"
            const val BIRTH_DATE = "birth_date"
            const val EMAIL = "email"
            const val PHONE_NUMBER = "phone_number"

            // Scripts de génération
            const val SCRIPT_CREATE =
                "CREATE TABLE $TABLE_NAME (" +
                    "$ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$FIRST_NAME VARCHAR(50), " +
                    "$LAST_NAME VARCHAR(50), " +
                    "$BIRTH_DATE VARCHAR(50), " +
                    "$EMAIL VARCHAR(50), " +
                    "$PHONE_NUMBER VARCHAR(50) " +
                ");"

            const val SCRIPT_DROP =
                "DROP TABLE $TABLE_NAME;"
        }
    }

}