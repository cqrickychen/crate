/*
 * Licensed to CRATE Technology GmbH ("Crate") under one or more contributor
 * license agreements.  See the NOTICE file distributed with this work for
 * additional information regarding copyright ownership.  Crate licenses
 * this file to you under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.  You may
 * obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * However, if you have executed another commercial license agreement
 * with Crate these terms will supersede the license and you may use the
 * software solely pursuant to the terms of the relevant commercial agreement.
 */

package io.crate;

import org.elasticsearch.common.component.LifecycleComponent;
import org.elasticsearch.common.inject.Module;
import org.elasticsearch.common.settings.Settings;

import java.io.Closeable;
import java.util.Collection;

/**
 * An extension point allowing to plug in custom functionality.
 * <p>
 * A plugin may implement a constructor with a {@link Settings} argument, which will be called
 * preferred to an empty one.
 * </p>
 */
public interface Plugin {

    /**
     * The name of the plugin.
     */
    String name();

    /**
     * The description of the plugin.
     */
    String description();

    /**
     * Node level modules.
     */
    Collection<Module> nodeModules();

    /**
     * Node level services that will be automatically started/stopped/closed.
     */
    Collection<Class<? extends LifecycleComponent>> nodeServices();

    /**
     * Per index modules.
     */
    Collection<? extends Module> indexModules(Settings settings);

    /**
     * Per index services that will be automatically closed.
     */
    Collection<Class<? extends Closeable>> indexServices();

    /**
     * Per index shard module.
     */
    Collection<? extends Module> shardModules(Settings settings);

    /**
     * Per index shard service that will be automatically closed.
     */
    Collection<Class<? extends Closeable>> shardServices();

    /**
     * Process a specific module. Note, its simpler to implement a custom <tt>onModule(AnyModule module)</tt>
     * method, which will be automatically be called by the relevant type.
     */
    void processModule(Module module);

    /**
     * Additional node settings loaded by the plugin
     */
    Settings additionalSettings();
}
