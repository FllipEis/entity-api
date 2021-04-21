package de.fllip.entity.api.event

import de.fllip.entity.api.entity.Entity
import org.bukkit.event.Event
import org.bukkit.event.HandlerList

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:24
 *
 * An event which can at an list of [Entity.Factory]
 */
class GuiceInitializeEvent(
    val factoryInformationList: MutableList<FactoryInformation<*>>
) : Event() {

    companion object {
        @JvmStatic
        val handlerList = HandlerList()
    }

    override fun getHandlers(): HandlerList {
        return handlerList
    }

    fun <T: Entity> addFactoryInformation(factoryInformation: FactoryInformation<T>): GuiceInitializeEvent {
        factoryInformationList.add(factoryInformation)
        return this
    }

}