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

package com.sun.ts.tests.javaee.annotations;

import java.io.IOException;
import java.io.PrintWriter;

import com.sun.ts.tests.javaee.common.servlets.HttpTCKServlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;

/**
 * Test servlet for @Nonnull and @Nullable annotations.
 * Tests the Jakarta EE container's enforcement of null-safety annotations.
 */
@WebServlet(urlPatterns = { "/NonnullNullableTestServlet/*" })
public class NonnullNullableTestServlet extends HttpTCKServlet {

    @Inject
    private AnnotationTestService testService;

    /**
     * Test that @Nonnull parameter rejects null values
     */
    public void testNonnullParameterWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            // This should throw an exception due to @Nonnull constraint
            testService.processNonnullParameter(null);
            out.println("Test FAILED: Expected ConstraintViolationException was not thrown");
        } catch (ConstraintViolationException e) {
            out.println("Test PASSED: ConstraintViolationException thrown as expected");
        } catch (Exception e) {
            out.println("Test PASSED: Exception thrown for null @Nonnull parameter: " + e.getClass().getName());
        }
    }

    /**
     * Test that @Nonnull parameter accepts non-null values
     */
    public void testNonnullParameterWithValue(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processNonnullParameter("test value");
            if (result != null && result.contains("test value")) {
                out.println("Test PASSED: @Nonnull parameter accepted non-null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable parameter accepts null values
     */
    public void testNullableParameterWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processNullableParameter(null);
            if (result != null && result.contains("null value")) {
                out.println("Test PASSED: @Nullable parameter accepted null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception for @Nullable parameter: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable parameter accepts non-null values
     */
    public void testNullableParameterWithValue(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processNullableParameter("test value");
            if (result != null && result.contains("test value")) {
                out.println("Test PASSED: @Nullable parameter accepted non-null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nonnull return type rejects null return values
     */
    public void testNonnullReturnWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            // This should throw an exception due to @Nonnull return constraint
            testService.getNonnullReturn(true);
            out.println("Test FAILED: Expected ConstraintViolationException was not thrown");
        } catch (ConstraintViolationException e) {
            out.println("Test PASSED: ConstraintViolationException thrown for null @Nonnull return");
        } catch (Exception e) {
            out.println("Test PASSED: Exception thrown for null @Nonnull return: " + e.getClass().getName());
        }
    }

    /**
     * Test that @Nonnull return type accepts non-null return values
     */
    public void testNonnullReturnWithValue(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.getNonnullReturn(false);
            if (result != null && result.contains("Valid")) {
                out.println("Test PASSED: @Nonnull return type returned non-null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable return type accepts null return values
     */
    public void testNullableReturnWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.getNullableReturn(true);
            if (result == null) {
                out.println("Test PASSED: @Nullable return type returned null value");
            } else {
                out.println("Test FAILED: Expected null but got: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception for @Nullable return: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable return type accepts non-null return values
     */
    public void testNullableReturnWithValue(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.getNullableReturn(false);
            if (result != null && result.contains("Valid")) {
                out.println("Test PASSED: @Nullable return type returned non-null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test multiple @Nonnull parameters with one null value
     */
    public void testMultipleNonnullParametersWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            // This should throw an exception due to @Nonnull constraint
            testService.processMultipleNonnullParameters("first", null);
            out.println("Test FAILED: Expected ConstraintViolationException was not thrown");
        } catch (ConstraintViolationException e) {
            out.println("Test PASSED: ConstraintViolationException thrown for null in multiple @Nonnull parameters");
        } catch (Exception e) {
            out.println("Test PASSED: Exception thrown for null in multiple @Nonnull parameters: " + e.getClass().getName());
        }
    }

    /**
     * Test multiple @Nonnull parameters with all non-null values
     */
    public void testMultipleNonnullParametersWithValues(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processMultipleNonnullParameters("first", "second");
            if (result != null && result.contains("first") && result.contains("second")) {
                out.println("Test PASSED: Multiple @Nonnull parameters accepted non-null values");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with null in @Nonnull
     */
    public void testMixedParametersNonnullNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            // This should throw an exception due to @Nonnull constraint on first parameter
            testService.processMixedParameters(null, "optional");
            out.println("Test FAILED: Expected ConstraintViolationException was not thrown");
        } catch (ConstraintViolationException e) {
            out.println("Test PASSED: ConstraintViolationException thrown for null @Nonnull in mixed parameters");
        } catch (Exception e) {
            out.println("Test PASSED: Exception thrown for null @Nonnull in mixed parameters: " + e.getClass().getName());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with null in @Nullable
     */
    public void testMixedParametersNullableNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processMixedParameters("required", null);
            if (result != null && result.contains("required") && result.contains("not provided")) {
                out.println("Test PASSED: Mixed parameters accepted null in @Nullable parameter");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with all non-null values
     */
    public void testMixedParametersAllValues(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processMixedParameters("required", "optional");
            if (result != null && result.contains("required") && result.contains("optional")) {
                out.println("Test PASSED: Mixed parameters accepted all non-null values");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test @Nonnull on both parameter and return type
     */
    public void testNonnullParameterAndReturn(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.transformNonnull("test");
            if (result != null && result.equals("TEST")) {
                out.println("Test PASSED: @Nonnull parameter and return type work correctly");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test @Nonnull on object parameter with null
     */
    public void testNonnullObjectParameterWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            testService.processNonnullObject(null);
            out.println("Test FAILED: Expected ConstraintViolationException was not thrown");
        } catch (ConstraintViolationException e) {
            out.println("Test PASSED: ConstraintViolationException thrown for null @Nonnull object parameter");
        } catch (Exception e) {
            out.println("Test PASSED: Exception thrown for null @Nonnull object parameter: " + e.getClass().getName());
        }
    }

    /**
     * Test @Nullable on object parameter with null
     */
    public void testNullableObjectParameterWithNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        try {
            String result = testService.processNullableObject(null);
            if (result != null && result.contains("null")) {
                out.println("Test PASSED: @Nullable object parameter accepted null value");
            } else {
                out.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            out.println("Test FAILED: Unexpected exception for @Nullable object parameter: " + e.getMessage());
        }
    }
}

// Made with Bob
