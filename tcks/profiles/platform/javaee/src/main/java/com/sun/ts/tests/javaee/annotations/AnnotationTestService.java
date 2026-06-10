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

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;

/**
 * Service class to test @Nonnull and @Nullable annotations.
 * This class demonstrates the use of Jakarta Annotations API 3.0
 * for null-safety annotations on method parameters and return types.
 */
@ApplicationScoped
public class AnnotationTestService {

    /**
     * Method with @Nonnull parameter - should reject null values
     * 
     * @param value non-null string parameter
     * @return processed string
     */
    public String processNonnullParameter(@Nonnull String value) {
        return "Processed: " + value;
    }

    /**
     * Method with @Nullable parameter - should accept null values
     * 
     * @param value nullable string parameter
     * @return processed string or default message
     */
    public String processNullableParameter(@Nullable String value) {
        if (value == null) {
            return "Processed: null value";
        }
        return "Processed: " + value;
    }

    /**
     * Method with @Nonnull return type - should not return null
     * 
     * @param returnNull flag to control return value
     * @return non-null string
     */
    @Nonnull
    public String getNonnullReturn(boolean returnNull) {
        if (returnNull) {
            return null; // This should trigger a constraint violation
        }
        return "Valid non-null return";
    }

    /**
     * Method with @Nullable return type - can return null
     * 
     * @param returnNull flag to control return value
     * @return nullable string
     */
    @Nullable
    public String getNullableReturn(boolean returnNull) {
        if (returnNull) {
            return null; // This is allowed
        }
        return "Valid return";
    }

    /**
     * Method with multiple @Nonnull parameters
     * 
     * @param first non-null first parameter
     * @param second non-null second parameter
     * @return concatenated string
     */
    public String processMultipleNonnullParameters(@Nonnull String first, @Nonnull String second) {
        return first + " " + second;
    }

    /**
     * Method with mixed @Nonnull and @Nullable parameters
     * 
     * @param required non-null required parameter
     * @param optional nullable optional parameter
     * @return processed string
     */
    public String processMixedParameters(@Nonnull String required, @Nullable String optional) {
        if (optional == null) {
            return "Required: " + required + ", Optional: not provided";
        }
        return "Required: " + required + ", Optional: " + optional;
    }

    /**
     * Method with @Nonnull parameter and @Nonnull return type
     * 
     * @param input non-null input
     * @return non-null output
     */
    @Nonnull
    public String transformNonnull(@Nonnull String input) {
        return input.toUpperCase();
    }

    /**
     * Method to test @Nonnull on object parameter
     * 
     * @param obj non-null object parameter
     * @return string representation
     */
    public String processNonnullObject(@Nonnull Object obj) {
        return "Object: " + obj.toString();
    }

    /**
     * Method to test @Nullable on object parameter
     * 
     * @param obj nullable object parameter
     * @return string representation or default
     */
    public String processNullableObject(@Nullable Object obj) {
        if (obj == null) {
            return "Object: null";
        }
        return "Object: " + obj.toString();
    }
}

// Made with Bob
