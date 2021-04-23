package de.fllip.entity.api.entity.fakeplayer

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 22.04.2021
 * Time: 11:51
 */
data class SkinData(
    val value: String,
    val signature: String
) {

    fun isEmpty(): Boolean {
        return value == "" && signature == ""
    }

}
