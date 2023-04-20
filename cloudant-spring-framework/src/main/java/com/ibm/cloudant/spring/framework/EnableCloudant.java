/*
 * Copyright © 2017, 2023 IBM Corp. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file
 * except in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the
 * License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language governing permissions
 * and limitations under the License.
 */

package com.ibm.cloudant.spring.framework;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.springframework.context.annotation.Import;

/**
 * <p>
 * Add this annotation to an {@code @Configuration} class to expose a
 * ClientBuilder and CloudantClient bean connected to the Cloudant
 * instance specified as Spring properties.
 * </p>
 * 
 * <h1>Usage Examples</h1>
 * <code>
 * &#064;Configuration
 * &#064;EnableCloudant
 * public class CloudantConfig{}
 * </code>
 * 
 * @author Katherine Stanley
 * @since 0.0.1
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import({CloudantConfiguration.class, CloudantConfigurationProperties.class})
public @interface EnableCloudant{}
