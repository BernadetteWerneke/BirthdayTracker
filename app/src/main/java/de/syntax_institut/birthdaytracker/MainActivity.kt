package de.syntax_institut.birthdaytracker

import android.os.Build
import android.os.Bundle
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.example.birthdaytracker.R
import com.google.android.material.textfield.TextInputEditText
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // TODO hole deinen Button aus dem Layout und speichere ihn in einer Variable
        val calculate = findViewById<Button>(R.id.buttonCalculate)

        // TODO setzte einen On-Click-Listener auf deinen Button und rufe die Methode
        //  getValuesFromInput auf wenn er geklickt wird
        calculate.setOnClickListener {
            getValuesFromInput()
        }

    }

    /**
     * Diese Funktion liest die EditText Felder aus ruft die Funktion zum Kalkulieren der
     * Tages-Differenz auf und befüllt die beiden TextViews
     **/
    @RequiresApi(Build.VERSION_CODES.O)
    private fun getValuesFromInput() {

        // TODO speichere die Texteingaben der Spinner und der InputFelder in Variablen
        val firstName = findViewById<TextView>(R.id.vorname_text).text
        val secondName = findViewById<TextView>(R.id.nachname_text).text
        val dayBirth = findViewById<Spinner>(R.id.birthdayText)
        val monthBirth = findViewById<Spinner>(R.id.monthText)

        // TODO rufe die Methode calculateBirthday mit den richtigen Variablen auf und speichere
        //  den Return-Wert in einer Variable
        val birthday = dayBirth.selectedItem.toString().toInt()
        val birthmonth = monthBirth.selectedItem.toString()
        var calculate = calculateBirthday(birthday, birthmonth)

        // TODO hole dir die TextView aus dem Layout und befülle diese mit den richtigen String
        //  Ressorucen und Variablen
        val greeting = findViewById<TextView>(R.id.greeting_text)
        val calculatesDays = findViewById<TextView>(R.id.daysUntil_text)
        greeting.text = "Hi $firstName $secondName!"
        calculatesDays.text = "There are $calculate days remaining until your birthday!"

    }

    /**
     * Diese Methode benutzt mehrere Funktionen um die Tages-Differenz zwischen 2 Daten zu vergleichen
     * Hier brauchst du nichts zu verändern!
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun calculateBirthday(day: Int, monthString: String): Int {

        // Erst brauchen wir den Monat als Zahl 1-12 anstatt des Strings
        val month = getMonthFromString(monthString)

        // Erstellen einer Calendar Instanz um mit Daten arbeiten zu können
        val cal = Calendar.getInstance()

        // Erstellen eines Date-Formats und eines Date-Formatters
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.GERMANY)
        val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

        // Speicher des heutigen Datums
        val currentDateString = dateFormat.format(cal.time)

        // Die Daten des Kalenders werden auf die angegebenen Werte des Geburtstags gesetzt
        cal.set(Calendar.MONTH, month)
        cal.set(Calendar.DAY_OF_MONTH, day)

        // Der Geburtstag wird gespeichert
        var birthdayString = dateFormat.format(cal.time)

        // Die beiden Daten werden in das LocaleDate Format geparsed
        val currentDate = LocalDate.parse(currentDateString, dateFormatter)
        var birthday = LocalDate.parse(birthdayString, dateFormatter)

        // Wenn der Geburtstag dieses Jahr schon war, muss das Jahr des Kelanders um 1 erhöht
        // werden und der Geburtstag neu abgespeichert und geparsed werden
        if (currentDate.isAfter(birthday)) {
            cal.add(Calendar.YEAR, 1)
            birthdayString = dateFormat.format(cal.time)
            birthday = LocalDate.parse(birthdayString, dateFormatter)
        }

        // Zurückgeben der Differenz der beiden Daten in Tagen
        return currentDate.until(birthday, ChronoUnit.DAYS).toInt()
    }

    /**
     * Diese Methode verwandelt den Monat als String in einen Integer welchen wir dann benutzen können
     * Hier brauchst du nichts zu verändern!
     */
    private fun getMonthFromString(month: String): Int {
        return when (month) {
            "January" -> Calendar.JANUARY
            "February" -> Calendar.FEBRUARY
            "March" -> Calendar.MARCH
            "April" -> Calendar.APRIL
            "May" -> Calendar.MAY
            "June" -> Calendar.JUNE
            "July" -> Calendar.JULY
            "August" -> Calendar.AUGUST
            "September" -> Calendar.SEPTEMBER
            "October" -> Calendar.OCTOBER
            "November" -> Calendar.NOVEMBER
            "December" -> Calendar.DECEMBER
            else -> 0
        }
    }

}
