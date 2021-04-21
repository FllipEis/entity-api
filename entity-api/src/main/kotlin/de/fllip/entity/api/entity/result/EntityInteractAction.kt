package de.fllip.entity.api.entity.result

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 21:13
 */
enum class EntityInteractAction {

    INTERACT,
    ATTACK,
    INTERACT_AT;

    /*companion object {
        fun fromEntityUseAction(action: EnumWrappers.EntityUseAction): EntityInteractAction {
            return valueOf(action.toString())
        }
    }*/

}