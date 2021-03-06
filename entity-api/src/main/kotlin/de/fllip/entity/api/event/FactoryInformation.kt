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

package de.fllip.entity.api.event

import com.google.inject.Module
import com.google.inject.assistedinject.FactoryModuleBuilder
import de.fllip.entity.api.entity.Entity

/**
 * Created by IntelliJ IDEA.
 * User: Philipp.Eistrach
 * Date: 21.04.2021
 * Time: 20:27
 *
 * Class for storing information to create a new factory
 *
 * @param factoryClass the class of the factory
 * @param entityInterfaceClass the interface class of the entity
 * @param entityClass the implementation class of the entity
 */
data class FactoryInformation <T: Entity> (
    val factoryClass: Class<out Entity.Factory>,
    val entityInterfaceClass: Class<T>,
    val entityClass: Class<out T>
) {

    /**
     * Builds a guice module for this factory with this information
     *
     * @return the guice module
     */
    fun buildModule(): Module {
        return FactoryModuleBuilder()
            .implement(entityInterfaceClass, entityClass)
            .build(factoryClass)
    }

}