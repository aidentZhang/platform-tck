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
   * @testName: testNonnullParameterValid
   * 
   * @assertion: A method parameter annotated with @Nonnull accepts valid non-null values
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2
   * 
   * @test_Strategy: Invoke a method with @Nonnull parameter using a valid non-null value
   * and verify it is processed successfully.
   */
  @Test
  public void testNonnullParameterValid() throws Exception {
    String testName = "testNonnullParameterValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNonnullParameterNull
   * 
   * @assertion: A method parameter annotated with @Nonnull rejects null values
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3
   * 
   * @test_Strategy: Attempt to invoke a method with @Nonnull parameter using null
   * and verify that the container throws an appropriate exception.
   */
  @Test
  public void testNonnullParameterNull() throws Exception {
    String testName = "testNonnullParameterNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNullableParameterNull
   * 
   * @assertion: A method parameter annotated with @Nullable accepts null values
   * 
   * @assertion_ids: Annotations:SPEC:4, Annotations:SPEC:5
   * 
   * @test_Strategy: Invoke a method with @Nullable parameter using null
   * and verify it is processed successfully.
   */
  @Test
  public void testNullableParameterNull() throws Exception {
    String testName = "testNullableParameterNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNullableParameterValid
   * 
   * @assertion: A method parameter annotated with @Nullable accepts non-null values
   * 
   * @assertion_ids: Annotations:SPEC:4, Annotations:SPEC:5
   * 
   * @test_Strategy: Invoke a method with @Nullable parameter using a valid non-null value
   * and verify it is processed successfully.
   */
  @Test
  public void testNullableParameterValid() throws Exception {
    String testName = "testNullableParameterValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNonnullReturnValid
   * 
   * @assertion: A method with @Nonnull return type returns non-null values
   * 
   * @assertion_ids: Annotations:SPEC:6, Annotations:SPEC:7
   * 
   * @test_Strategy: Invoke a method with @Nonnull return type that returns a valid value
   * and verify the result is non-null.
   */
  @Test
  public void testNonnullReturnValid() throws Exception {
    String testName = "testNonnullReturnValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNonnullReturnViolation
   * 
   * @assertion: A method with @Nonnull return type that attempts to return null
   * triggers container enforcement
   * 
   * @assertion_ids: Annotations:SPEC:6, Annotations:SPEC:8
   * 
   * @test_Strategy: Invoke a method with @Nonnull return type that attempts to return null
   * and verify that the container throws an appropriate exception.
   */
  @Test
  public void testNonnullReturnViolation() throws Exception {
    String testName = "testNonnullReturnViolation";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNullableReturnNull
   * 
   * @assertion: A method with @Nullable return type can return null
   * 
   * @assertion_ids: Annotations:SPEC:9, Annotations:SPEC:10
   * 
   * @test_Strategy: Invoke a method with @Nullable return type that returns null
   * and verify it is accepted.
   */
  @Test
  public void testNullableReturnNull() throws Exception {
    String testName = "testNullableReturnNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testNullableReturnValid
   * 
   * @assertion: A method with @Nullable return type can return non-null values
   * 
   * @assertion_ids: Annotations:SPEC:9, Annotations:SPEC:10
   * 
   * @test_Strategy: Invoke a method with @Nullable return type that returns a valid value
   * and verify it is accepted.
   */
  @Test
  public void testNullableReturnValid() throws Exception {
    String testName = "testNullableReturnValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMultipleNonnullParametersValid
   * 
   * @assertion: A method with multiple @Nonnull parameters accepts all valid non-null values
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:2
   * 
   * @test_Strategy: Invoke a method with multiple @Nonnull parameters using valid values
   * and verify they are processed successfully.
   */
  @Test
  public void testMultipleNonnullParametersValid() throws Exception {
    String testName = "testMultipleNonnullParametersValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMultipleNonnullParametersFirstNull
   * 
   * @assertion: A method with multiple @Nonnull parameters rejects null for the first parameter
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3
   * 
   * @test_Strategy: Attempt to invoke a method with multiple @Nonnull parameters
   * passing null for the first parameter and verify container enforcement.
   */
  @Test
  public void testMultipleNonnullParametersFirstNull() throws Exception {
    String testName = "testMultipleNonnullParametersFirstNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMultipleNonnullParametersSecondNull
   * 
   * @assertion: A method with multiple @Nonnull parameters rejects null for the second parameter
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3
   * 
   * @test_Strategy: Attempt to invoke a method with multiple @Nonnull parameters
   * passing null for the second parameter and verify container enforcement.
   */
  @Test
  public void testMultipleNonnullParametersSecondNull() throws Exception {
    String testName = "testMultipleNonnullParametersSecondNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMixedParametersAllValid
   * 
   * @assertion: A method with mixed @Nonnull and @Nullable parameters accepts all valid values
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:4
   * 
   * @test_Strategy: Invoke a method with mixed @Nonnull and @Nullable parameters
   * using valid values for both and verify they are processed successfully.
   */
  @Test
  public void testMixedParametersAllValid() throws Exception {
    String testName = "testMixedParametersAllValid";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMixedParametersNullableNull
   * 
   * @assertion: A method with mixed @Nonnull and @Nullable parameters accepts null
   * for the @Nullable parameter
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:4, Annotations:SPEC:5
   * 
   * @test_Strategy: Invoke a method with mixed @Nonnull and @Nullable parameters
   * passing null for the @Nullable parameter and verify it is accepted.
   */
  @Test
  public void testMixedParametersNullableNull() throws Exception {
    String testName = "testMixedParametersNullableNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }

  /*
   * @testName: testMixedParametersNonnullNull
   * 
   * @assertion: A method with mixed @Nonnull and @Nullable parameters rejects null
   * for the @Nonnull parameter
   * 
   * @assertion_ids: Annotations:SPEC:1, Annotations:SPEC:3, Annotations:SPEC:4
   * 
   * @test_Strategy: Attempt to invoke a method with mixed @Nonnull and @Nullable parameters
   * passing null for the @Nonnull parameter and verify container enforcement.
   */
  @Test
  public void testMixedParametersNonnullNull() throws Exception {
    String testName = "testMixedParametersNonnullNull";
    TEST_PROPS.setProperty(TEST_NAME, testName);
    TEST_PROPS.setProperty(REQUEST,
        "GET " + getContextRoot() + "/" + testName + " HTTP/1.1");
    TEST_PROPS.setProperty(SEARCH_STRING, "Test PASSED");
    invoke();
  }
}

// Made with Bob
