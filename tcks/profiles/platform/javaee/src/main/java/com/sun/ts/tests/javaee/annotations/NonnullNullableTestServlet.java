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

/**
 * Test servlet for @Nonnull and @Nullable annotations
 */
@WebServlet(urlPatterns = {
    "/testNonnullParameterValid",
    "/testNonnullParameterNull",
    "/testNullableParameterValid",
    "/testNullableParameterNull",
    "/testNonnullReturnValid",
    "/testNonnullReturnViolation",
    "/testNullableReturnValid",
    "/testNullableReturnNull",
    "/testMultipleNonnullParametersValid",
    "/testMultipleNonnullParametersFirstNull",
    "/testMultipleNonnullParametersSecondNull",
    "/testMixedParametersAllValid",
    "/testMixedParametersNullableNull",
    "/testMixedParametersNonnullNull"
})
public class NonnullNullableTestServlet extends HttpTCKServlet {

    @Inject
    private NonnullNullableComponent component;

    /**
     * Test that @Nonnull parameter accepts valid non-null values
     */
    public void testNonnullParameterValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processNonnullParameter("test value");
            if (result != null && result.contains("test value")) {
                pw.println("Test PASSED: @Nonnull parameter accepted valid value");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nonnull parameter rejects null values
     * Expected: Container should throw an exception (e.g., ConstraintViolationException)
     */
    public void testNonnullParameterNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processNonnullParameter(null);
            pw.println("Test FAILED: @Nonnull parameter accepted null value, result: " + result);
        } catch (Exception e) {
            // Expected behavior - container should enforce @Nonnull
            pw.println("Test PASSED: @Nonnull parameter rejected null value with exception: " 
                    + e.getClass().getSimpleName());
        }
    }

    /**
     * Test that @Nullable parameter accepts null values
     */
    public void testNullableParameterNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processNullableParameter(null);
            if (result != null && result.contains("null value")) {
                pw.println("Test PASSED: @Nullable parameter accepted null value");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: @Nullable parameter should accept null: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable parameter accepts non-null values
     */
    public void testNullableParameterValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processNullableParameter("test value");
            if (result != null && result.contains("test value")) {
                pw.println("Test PASSED: @Nullable parameter accepted valid value");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nonnull return type returns non-null values
     */
    public void testNonnullReturnValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.getNonnullResult("input");
            if (result != null) {
                pw.println("Test PASSED: @Nonnull return type returned non-null value");
            } else {
                pw.println("Test FAILED: @Nonnull return type returned null");
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test that @Nonnull return type enforcement catches null returns
     * Expected: Container should throw an exception when method tries to return null
     */
    public void testNonnullReturnViolation(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.violateNonnullReturn();
            pw.println("Test FAILED: @Nonnull return violation not caught, result: " + result);
        } catch (Exception e) {
            // Expected behavior - container should enforce @Nonnull on return
            pw.println("Test PASSED: @Nonnull return violation caught with exception: " 
                    + e.getClass().getSimpleName());
        }
    }

    /**
     * Test that @Nullable return type can return null
     */
    public void testNullableReturnNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.getNullableResult(true);
            if (result == null) {
                pw.println("Test PASSED: @Nullable return type returned null");
            } else {
                pw.println("Test FAILED: Expected null but got: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: @Nullable return should allow null: " + e.getMessage());
        }
    }

    /**
     * Test that @Nullable return type can return non-null
     */
    public void testNullableReturnValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.getNullableResult(false);
            if (result != null && result.contains("not null")) {
                pw.println("Test PASSED: @Nullable return type returned non-null value");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test multiple @Nonnull parameters with valid values
     */
    public void testMultipleNonnullParametersValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMultipleNonnullParameters("first", "second");
            if (result != null && result.contains("first") && result.contains("second")) {
                pw.println("Test PASSED: Multiple @Nonnull parameters accepted valid values");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test multiple @Nonnull parameters with first parameter null
     */
    public void testMultipleNonnullParametersFirstNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMultipleNonnullParameters(null, "second");
            pw.println("Test FAILED: First @Nonnull parameter accepted null, result: " + result);
        } catch (Exception e) {
            pw.println("Test PASSED: First @Nonnull parameter rejected null with exception: " 
                    + e.getClass().getSimpleName());
        }
    }

    /**
     * Test multiple @Nonnull parameters with second parameter null
     */
    public void testMultipleNonnullParametersSecondNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMultipleNonnullParameters("first", null);
            pw.println("Test FAILED: Second @Nonnull parameter accepted null, result: " + result);
        } catch (Exception e) {
            pw.println("Test PASSED: Second @Nonnull parameter rejected null with exception: " 
                    + e.getClass().getSimpleName());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with valid values
     */
    public void testMixedParametersAllValid(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMixedParameters("required", "optional");
            if (result != null && result.contains("required") && result.contains("optional")) {
                pw.println("Test PASSED: Mixed parameters accepted all valid values");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: Unexpected exception: " + e.getMessage());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with @Nullable parameter null
     */
    public void testMixedParametersNullableNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMixedParameters("required", null);
            if (result != null && result.contains("required") && result.contains("none")) {
                pw.println("Test PASSED: Mixed parameters accepted null for @Nullable");
            } else {
                pw.println("Test FAILED: Unexpected result: " + result);
            }
        } catch (Exception e) {
            pw.println("Test FAILED: @Nullable parameter should accept null: " + e.getMessage());
        }
    }

    /**
     * Test mixed @Nonnull and @Nullable parameters with @Nonnull parameter null
     */
    public void testMixedParametersNonnullNull(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            String result = component.processMixedParameters(null, "optional");
            pw.println("Test FAILED: @Nonnull parameter accepted null, result: " + result);
        } catch (Exception e) {
            pw.println("Test PASSED: @Nonnull parameter rejected null with exception: " 
                    + e.getClass().getSimpleName());
        }
    }
}

// Made with Bob
