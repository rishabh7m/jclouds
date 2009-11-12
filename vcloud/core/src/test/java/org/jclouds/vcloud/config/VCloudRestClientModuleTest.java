/**
 *
 * Copyright (C) 2009 Cloud Conscious, LLC. <info@cloudconscious.com>
 *
 * ====================================================================
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 * ====================================================================
 */
package org.jclouds.vcloud.config;

import static org.jclouds.vcloud.reference.VCloudConstants.PROPERTY_VCLOUD_ENDPOINT;
import static org.jclouds.vcloud.reference.VCloudConstants.PROPERTY_VCLOUD_KEY;
import static org.jclouds.vcloud.reference.VCloudConstants.PROPERTY_VCLOUD_SESSIONINTERVAL;
import static org.jclouds.vcloud.reference.VCloudConstants.PROPERTY_VCLOUD_USER;
import static org.testng.Assert.assertEquals;

import org.jclouds.http.functions.config.ParserModule;
import org.jclouds.util.Jsr330;
import org.jclouds.vcloud.endpoints.internal.CatalogItemRoot;
import org.testng.annotations.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;

/**
 * @author Adrian Cole
 */
@Test(groups = "unit", testName = "vcloud.VCloudRestClientModuleTest")
public class VCloudRestClientModuleTest extends VCloudDiscoveryRestClientModuleTest {

   Injector createInjector() {
      return Guice.createInjector(new VCloudRestClientModule(),
               new VCloudDiscoveryRestClientModule(), new ParserModule(), new AbstractModule() {
                  @Override
                  protected void configure() {
                     bindConstant().annotatedWith(Jsr330.named(PROPERTY_VCLOUD_USER)).to("user");
                     bindConstant().annotatedWith(Jsr330.named(PROPERTY_VCLOUD_KEY)).to("secret");
                     bindConstant().annotatedWith(Jsr330.named(PROPERTY_VCLOUD_ENDPOINT)).to(
                              "http://localhost");
                     bindConstant().annotatedWith(Jsr330.named(PROPERTY_VCLOUD_SESSIONINTERVAL))
                              .to("2");
                  }
               });
   }

   @Test
   void testCatalogItemRoot() {
      assertEquals(createInjector().getInstance(Key.get(String.class, CatalogItemRoot.class)),
               "http://localhost/catalogItem");
   }

}