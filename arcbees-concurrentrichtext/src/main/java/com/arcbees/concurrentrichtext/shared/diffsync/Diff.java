/*
 * Copyright 2011 ArcBees Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.concurrentrichtext.shared.diffsync;

import java.io.Serializable;

/**
 * Class representing one diff operation.
 */
public class Diff implements Serializable {
    private static final long serialVersionUID = 3921708107643743462L;

    public enum Operation {
        DELETE, INSERT, EQUAL
    }

    public Operation operation;
    public String text;

    @SuppressWarnings("unused")
    Diff() {
    }

    /**
     * Constructor. Initializes the diff with the provided values.
     *
     * @param operation One of INSERT, DELETE or EQUAL.
     * @param text      The text being applied.
     */
    public Diff(Operation operation, String text) {
        // Construct a diff with the specified operation and text.
        this.operation = operation;
        this.text = text;
    }

    /**
     * Is this Diff equivalent to another Diff?
     *
     * @param d Another Diff to compare against.
     * @return true or false.
     */
    public boolean equals(Object d) {
        try {
            return (((Diff) d).operation == this.operation)
                   && (((Diff) d).text.equals(this.text));
        } catch (ClassCastException e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return operation.toString() + ": " + text;
    }
}
