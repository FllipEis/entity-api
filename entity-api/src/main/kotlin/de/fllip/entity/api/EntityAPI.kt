/*
 * Copyright (c) 2021 Philipp Eistrach
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package de.fllip.entity.api

import com.google.inject.Inject
import com.google.inject.Injector
import de.fllip.entity.api.entity.Entity
import de.fllip.entity.api.entity.armorstand.ArmorStandEntity
import de.fllip.entity.api.entity.fakeplayer.FakePlayerEntity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 15:46
 *
 * Offers all functions of the api
 */
class EntityAPI @Inject constructor(
    val fakePlayerEntityFactory: FakePlayerEntity.Factory,
    val armorStandEntityFactory: ArmorStandEntity.Factory,
    private val injector: Injector
) {

    /**
     * @param entityFactoryClass the class of the factory
     * @return the instance of the factory
     */
    fun <T: Entity.Factory> getEntityFactoryByClass(entityFactoryClass: Class<T>): T {
        return injector.getInstance(entityFactoryClass)
    }

    companion object {

        @JvmStatic
        @Inject
        lateinit var instance: EntityAPI
            private set

    }


}