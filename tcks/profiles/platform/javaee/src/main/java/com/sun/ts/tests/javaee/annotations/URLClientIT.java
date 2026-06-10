/*
 * Copyright (c) 2024 Oracle and/or its affiliates. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v. 2.0, which is available at
 * http://www.eclipse.org/legal/epl-2.0.
 *
 * This Source Code may also be made available under the following Secondary
 * Licenses when the conditions for such availability set forth in the
 * Eclipse Public License v. 2.0 are satisfied: GNU General Public License,
 * version 2 with the GNU Classpath Exception, which is available at
 * https://www.gnu.org/software/classpath/license.html.
 *
 * SPDX-License-Identifier: EPL-2.0 OR GPL-2.0 WITH Classpath-exception-2.0
 */

/*
 * Test @Nonnull and @Nullable annotations from Jakarta Annotations API 3.0
 */

package com.sun.ts.tests.javaee.annotations;

import java.io.IOException;

import com.sun.ts.tests.javaee.common.client.AbstractUrlClient;
import com.sun.ts.tests.javaee.common.servlets.HttpTCKServlet;
import com.sun.ts.tests.javaee.common.util.ServletTestUtil;
import com.sun.ts.tests.servlet.common.util.Data;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit5.ArquillianExtension;
import org.jboss.shrinkwrap.api.Filters;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.System.Logger;

/**
 * Integration tests for @Nonnull and @Nullable annotations.
 * Tests Jakarta Annotations API 3.0 null-safety annotations enforcement
 * by the Jakarta EE container.
 */
@Tag("platform")
@Tag("javaee-module")
@Tag("tck-javatest")
@ExtendWith(ArquillianExtension.class)
public class URLClientIT extends AbstractUrlClient {

  private static final Logger logger = System.getLogger(URLClientIT.class.getName());

  @BeforeEach
  void logStartTest(TestInfo testInfo) {
      logger.log(Logger.Level.INFO, "STARTING TEST : " + testInfo.getDisplayName());
  }

  @AfterEach
  void logFinishTest(TestInfo testInfo) {
      logger.log(Logger.Level.INFO, "FINISHED TEST : " + testInfo.getDisplayName());
  }

  public URLClientIT() {
    setServletName("NonnullNullableTestServlet");
  }

  /* Run test */

  @Deployment(testable = false)
  public static EnterpriseArchive createDeployment() throws IOException {

    WebArchive webarchive = ShrinkWrap.create(WebArchive.class, "javaee_annotations_nonnull_nullable.war");
    webarchive.addPackages(true, Filters.exclude(URLClientIT.class), URLClientIT.class.getPackageName())
            .addClasses(HttpTCKServlet.class, ServletTestUtil.class, Data.class);

    EnterpriseArchive earArchive = ShrinkWrap.create(EnterpriseArchive.class, "javaee_annotations_nonnull_nullable.ear");
    earArchive.addAsModule(webarchive);

    return earArchive;
  }

  /*
   * @testName: testNonnullParameterWithNull
   * 
   * @assertion: A method parameter annotated with @Nonnull must reject null values
   * and the container should enforce this constraint.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2
   * 
   * @test_Strategy: Invoke a method with a @Nonnull parameter passing null.
   * Verify that the container throws an appropriate exception (e.g., 
   * ConstraintViolationException) to enforce the @Nonnull constraint.
   */
  @Test
  public void testNonnullParameterWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullParameterWithNull");
    invoke();
  }

  /*
   * @testName: testNonnullParameterWithValue
   * 
   * @assertion: A method parameter annotated with @Nonnull must accept non-null values.
   * 
   * @assertion_ids: Annotations:SPEC:1
   * 
   * @test_Strategy: Invoke a method with a @Nonnull parameter passing a non-null value.
   * Verify that the method executes successfully and returns the expected result.
   */
  @Test
  public void testNonnullParameterWithValue() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullParameterWithValue");
    invoke();
  }

  /*
   * @testName: testNullableParameterWithNull
   * 
   * @assertion: A method parameter annotated with @Nullable must accept null values.
   * 
   * @assertion_ids: Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with a @Nullable parameter passing null.
   * Verify that the method executes successfully and handles the null value appropriately.
   */
  @Test
  public void testNullableParameterWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNullableParameterWithNull");
    invoke();
  }

  /*
   * @testName: testNullableParameterWithValue
   * 
   * @assertion: A method parameter annotated with @Nullable must accept non-null values.
   * 
   * @assertion_ids: Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with a @Nullable parameter passing a non-null value.
   * Verify that the method executes successfully and returns the expected result.
   */
  @Test
  public void testNullableParameterWithValue() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNullableParameterWithValue");
    invoke();
  }

  /*
   * @testName: testNonnullReturnWithNull
   * 
   * @assertion: A method with @Nonnull return type must not return null values
   * and the container should enforce this constraint.
   * 
   * @assertion_ids: Annotations:SPEC:4, Annotations:SPEC:5
   * 
   * @test_Strategy: Invoke a method with @Nonnull return type that attempts to return null.
   * Verify that the container throws an appropriate exception to enforce the @Nonnull constraint.
   */
  @Test
  public void testNonnullReturnWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullReturnWithNull");
    invoke();
  }

  /*
   * @testName: testNonnullReturnWithValue
   * 
   * @assertion: A method with @Nonnull return type must return non-null values.
   * 
   * @assertion_ids: Annotations:SPEC:4
   * 
   * @test_Strategy: Invoke a method with @Nonnull return type that returns a non-null value.
   * Verify that the method executes successfully and returns the expected result.
   */
  @Test
  public void testNonnullReturnWithValue() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullReturnWithValue");
    invoke();
  }

  /*
   * @testName: testNullableReturnWithNull
   * 
   * @assertion: A method with @Nullable return type must accept null return values.
   * 
   * @assertion_ids: Annotations:SPEC:6
   * 
   * @test_Strategy: Invoke a method with @Nullable return type that returns null.
   * Verify that the method executes successfully and returns null without throwing an exception.
   */
  @Test
  public void testNullableReturnWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNullableReturnWithNull");
    invoke();
  }

  /*
   * @testName: testNullableReturnWithValue
   * 
   * @assertion: A method with @Nullable return type must accept non-null return values.
   * 
   * @assertion_ids: Annotations:SPEC:6
   * 
   * @test_Strategy: Invoke a method with @Nullable return type that returns a non-null value.
   * Verify that the method executes successfully and returns the expected result.
   */
  @Test
  public void testNullableReturnWithValue() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNullableReturnWithValue");
    invoke();
  }

  /*
   * @testName: testMultipleNonnullParametersWithNull
   * 
   * @assertion: When a method has multiple @Nonnull parameters, the container
   * must enforce the constraint on all parameters.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2
   * 
   * @test_Strategy: Invoke a method with multiple @Nonnull parameters, passing null
   * to one of them. Verify that the container throws an appropriate exception.
   */
  @Test
  public void testMultipleNonnullParametersWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testMultipleNonnullParametersWithNull");
    invoke();
  }

  /*
   * @testName: testMultipleNonnullParametersWithValues
   * 
   * @assertion: When a method has multiple @Nonnull parameters, all parameters
   * must accept non-null values.
   * 
   * @assertion_ids: Annotations:SPEC:1
   * 
   * @test_Strategy: Invoke a method with multiple @Nonnull parameters, passing
   * non-null values to all. Verify that the method executes successfully.
   */
  @Test
  public void testMultipleNonnullParametersWithValues() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testMultipleNonnullParametersWithValues");
    invoke();
  }

  /*
   * @testName: testMixedParametersNonnullNull
   * 
   * @assertion: When a method has mixed @Nonnull and @Nullable parameters,
   * the container must enforce @Nonnull constraints while allowing null for @Nullable.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2, Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with mixed @Nonnull and @Nullable parameters,
   * passing null to the @Nonnull parameter. Verify that the container throws an exception.
   */
  @Test
  public void testMixedParametersNonnullNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testMixedParametersNonnullNull");
    invoke();
  }

  /*
   * @testName: testMixedParametersNullableNull
   * 
   * @assertion: When a method has mixed @Nonnull and @Nullable parameters,
   * null values must be accepted for @Nullable parameters.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with mixed @Nonnull and @Nullable parameters,
   * passing null to the @Nullable parameter and a non-null value to @Nonnull.
   * Verify that the method executes successfully.
   */
  @Test
  public void testMixedParametersNullableNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testMixedParametersNullableNull");
    invoke();
  }

  /*
   * @testName: testMixedParametersAllValues
   * 
   * @assertion: When a method has mixed @Nonnull and @Nullable parameters,
   * non-null values must be accepted for all parameters.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with mixed @Nonnull and @Nullable parameters,
   * passing non-null values to all parameters. Verify that the method executes successfully.
   */
  @Test
  public void testMixedParametersAllValues() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testMixedParametersAllValues");
    invoke();
  }

  /*
   * @testName: testNonnullParameterAndReturn
   * 
   * @assertion: A method can have both @Nonnull parameter and @Nonnull return type,
   * and the container must enforce both constraints.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:4
   * 
   * @test_Strategy: Invoke a method with both @Nonnull parameter and @Nonnull return type,
   * passing a non-null value. Verify that the method executes successfully and returns
   * a non-null result.
   */
  @Test
  public void testNonnullParameterAndReturn() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullParameterAndReturn");
    invoke();
  }

  /*
   * @testName: testNonnullObjectParameterWithNull
   * 
   * @assertion: @Nonnull annotation works with Object type parameters and must
   * reject null values.
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2
   * 
   * @test_Strategy: Invoke a method with a @Nonnull Object parameter passing null.
   * Verify that the container throws an appropriate exception.
   */
  @Test
  public void testNonnullObjectParameterWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNonnullObjectParameterWithNull");
    invoke();
  }

  /*
   * @testName: testNullableObjectParameterWithNull
   * 
   * @assertion: @Nullable annotation works with Object type parameters and must
   * accept null values.
   * 
   * @assertion_ids: Annotations:SPEC:3
   * 
   * @test_Strategy: Invoke a method with a @Nullable Object parameter passing null.
   * Verify that the method executes successfully and handles the null value appropriately.
   */
  @Test
  public void testNullableObjectParameterWithNull() throws Exception {
    TEST_PROPS.setProperty(APITEST, "testNullableObjectParameterWithNull");
    invoke();
  }
}

// Made with Bob
