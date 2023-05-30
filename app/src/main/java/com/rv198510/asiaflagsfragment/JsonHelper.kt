package com.rv198510.asiaflagsfragment

import android.content.Context
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException

object JsonHelper {

    val KEY_NAME = "name"
    val KEY_POPULATION = "population"
    val KEY_GDP = "gdp"
    val KEY_GDP_CAPITA = "gdp_capita"
    val KEY_CAPITAL_CITY = "capital_city"
    val KEY_ISO_ALPHA_2 = "iso_alpha_2"
    val KEY_ISO_ALPHA_3 = "iso_alpha_3"
    val KEY_DIALING_CODE = "dialing_code"

    fun getFlagsFromJson(fileName: String, context: Context): ArrayList<Flags> {

        val flags = ArrayList<Flags>()

        try {
            val jsonString = loadJsonFromFile(fileName, context)
            val json = JSONObject(jsonString)
            val jsonMovies = json.getJSONArray("Flags")


            for (index in 0 until jsonMovies.length()) {
                val flagName = jsonMovies.getJSONObject(index).getString(KEY_NAME)
                val flagPopulation = jsonMovies.getJSONObject(index).getString(KEY_POPULATION)
                val flagGdpP = jsonMovies.getJSONObject(index).getString(KEY_GDP)
                val flagGdpCapita = jsonMovies.getJSONObject(index).getString(KEY_GDP_CAPITA)
                val flagCapitalCity = jsonMovies.getJSONObject(index).getString(KEY_CAPITAL_CITY)
                val flagIsoAlpha2 = jsonMovies.getJSONObject(index).getString(KEY_ISO_ALPHA_2)
                val flagIsoAlpha3 = jsonMovies.getJSONObject(index).getString(KEY_ISO_ALPHA_3)
                val flagDialingCode = jsonMovies.getJSONObject(index).getString(KEY_DIALING_CODE)
                flags.add(Flags(flagName,flagPopulation,flagGdpP,flagGdpCapita,flagCapitalCity,flagIsoAlpha2,flagIsoAlpha3,flagDialingCode))
            }
        } catch (e: JSONException) {
            return flags
        }

        return flags
    }

    private fun loadJsonFromFile(filename: String, context: Context): String {
        var json = ""

        try {
            val input = context.assets.open(filename)
            val size = input.available()
            val buffer = ByteArray(size)
            input.read(buffer)
            input.close()
            json = buffer.toString(Charsets.UTF_8)
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return json
    }
}