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
 * Dummy component class to test @Nonnull and @Nullable annotations
 * from Jakarta Annotations API 3.0
 */
@ApplicationScoped
public class NonnullNullableComponent {

    /**
     * Method with @Nonnull parameter - should reject null values
     * 
     * @param value non-null string parameter
     * @return processed string
     */
    @Nonnull
    public String processNonnullParameter(@Nonnull String value) {
        return "Processed: " + value;
    }

    /**
     * Method with @Nullable parameter - should accept null values
     * 
     * @param value nullable string parameter
     * @return processed string or default message
     */
    @Nonnull
    public String processNullableParameter(@Nullable String value) {
        if (value == null) {
            return "Processed: null value";
        }
        return "Processed: " + value;
    }

    /**
     * Method with @Nonnull return type - should not return null
     * 
     * @param input input string
     * @return non-null result
     */
    @Nonnull
    public String getNonnullResult(String input) {
        return "Result: " + input;
    }

    /**
     * Method with @Nullable return type - may return null
     * 
     * @param returnNull flag to control null return
     * @return nullable result
     */
    @Nullable
    public String getNullableResult(boolean returnNull) {
        if (returnNull) {
            return null;
        }
        return "Result: not null";
    }

    /**
     * Method that attempts to return null despite @Nonnull annotation
     * This should trigger container enforcement
     * 
     * @return should not return null
     */
    @Nonnull
    public String violateNonnullReturn() {
        return null;
    }

    /**
     * Method with multiple @Nonnull parameters
     * 
     * @param first first non-null parameter
     * @param second second non-null parameter
     * @return concatenated result
     */
    @Nonnull
    public String processMultipleNonnullParameters(@Nonnull String first, @Nonnull String second) {
        return first + " " + second;
    }

    /**
     * Method with mixed @Nonnull and @Nullable parameters
     * 
     * @param required non-null parameter
     * @param optional nullable parameter
     * @return processed result
     */
    @Nonnull
    public String processMixedParameters(@Nonnull String required, @Nullable String optional) {
        if (optional == null) {
            return "Required: " + required + ", Optional: none";
        }
        return "Required: " + required + ", Optional: " + optional;
    }
}

// Made with Bob
