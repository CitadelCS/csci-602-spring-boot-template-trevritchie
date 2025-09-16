package edu.citadel.hw1;

import java.lang.Comparable;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Abstract base class for all employee types in the organization.
 * Implements Comparable<Employee> to enable sorting by monthly pay, supporting use cases like payroll reports or compensation analysis.
 */
@Getter
public abstract class Employee implements Comparable<Employee> {
    /**
     * The full name of the employee.
     * Immutable after construction.
     */
    private String name;

    /**
     * The date when the employee was hired.
     * Immutable after construction.
     */
    private LocalDate hireDate;

    /**
     * Constructs a new Employee instance.
     * Intended for use by subclasses via super(name, hireDate).
     * Validates inputs implicitly via constructor params; in production, add null checks.
     *
     * @param name the employee's full name (non-null, non-empty in practice)
     * @param hireDate the hire date (non-null, should be in past or present)
     */
    public Employee(String name, LocalDate hireDate) {
        this.name = name;
        this.hireDate = hireDate;
    }

    /**
     * Computes and returns the monthly pay for this employee.
     * Abstract to force subclasses to implement type-specific logic (e.g., hourly vs. salaried).
     * Ensures consistent pay calculation interface across the hierarchy.
     *
     * @return the monthly pay in USD as a double
     */
    public abstract double getMonthlyPay();

    /**
     * Compares this employee to another for ordering purposes, based on monthly pay.
     * Returns negative if this employee's pay is less (sorts lower pay first), zero if equal, positive if greater.
     * Enables natural ordering for SortedSet, TreeMap, or Collections.sort on Employee lists.
     *
     * @param other the Employee to compare against (non-null)
     * @return the comparison result
     * @throws NullPointerException if other is null
     */
    @Override
    public int compareTo(Employee other) {
        return Double.compare(getMonthlyPay(), other.getMonthlyPay());
    }

    /**
     * Compares this Employee to another object for equality.
     * Returns true if the other is an Employee with identical name and hireDate.
     * Base implementation for subclasses to extend via super.equals().
     *
     * @param obj the object to compare (may be null)
     * @return true if equal based on name and hireDate
     */
    @Override
    public boolean equals(Object obj) {
        // Reference equality check
        if (this == obj) return true;
        // Null or wrong type check
        if (obj == null || getClass() != obj.getClass()) return false;
        Employee that = (Employee) obj;
        // Field comparisons for base class fields
        return java.util.Objects.equals(name, that.name) &&
               java.util.Objects.equals(hireDate, that.hireDate);
    }

    /**
     * Generates a hash code based on name and hireDate.
     * Base implementation for subclasses to extend.
     * Uses Objects.hash for simplicity and null-safety.
     *
     * @return the hash code for base Employee fields
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, hireDate);
    }
}
