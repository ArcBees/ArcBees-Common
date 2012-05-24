package com.arcbees.concurrentrichtext.shared.diffsync;

import java.io.Serializable;

/**
 * Class representing one diff operation.
 */
public class Diff implements Serializable {

    private static final long serialVersionUID = 3921708107643743462L;

    /**
     * The data structure representing a diff is a Linked list of Diff objects:
     * {Diff(Operation.DELETE, "Hello"), Diff(Operation.INSERT, "Goodbye"),
     * Diff(Operation.EQUAL, " world.")} which means: delete "Hello", add
     * "Goodbye" and keep " world."
     */
    public enum Operation {
        DELETE, INSERT, EQUAL
    }

    /**
     * One of: INSERT, DELETE or EQUAL.
     */
    public Operation operation;
    /**
     * The text associated with this diff operation.
     */
    public String text;

    @SuppressWarnings("unused")
    private Diff() {
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
