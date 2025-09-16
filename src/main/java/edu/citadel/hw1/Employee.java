package edu.citadel.hw1;

import java.lang.Comparable;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Abstract base class for all employee types in the organization.
 * Implements Comparable<Employee> to enable sorting by monthly pay, supporting use cases like payroll reports or compensation analysis.
 */
@Getter
public class Employee implements Comparable<Employee> {
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
     * Base implementation returns 0.0; subclasses should override with type-specific logic (e.g., hourly vs. salaried).
     * Ensures consistent pay calculation interface across the hierarchy.
     * Design rationale: Concrete base implementation allows for polymorphic usage while enabling subclass customization.
     * Educational note: Default implementation provides fallback behavior; subclasses inherit and can call super.getMonthlyPay() if needed.
     *
     * @return the monthly pay in USD as a double (0.0 for base Employee)
     */
    public double getMonthlyPay() {
        return 0.0;
    }

    /**
     * Compares this employee to another for ordering purposes, based on monthly pay.
     * Returns negative if this employee's pay is less (sorts lower pay first), zero if equal, positive if greater.
     * Enables natural ordering for SortedSet, TreeMap, or Collections.sort on Employee lists.
     * Design rationale: Pay-based sorting centralizes business logic in base class; subclasses inherit without reimplementation.
     * Uses Double.compare instead of subtraction (e.g., pay1 - pay2) to prevent integer overflow on large doubles and handle NaN/-0.0 correctly.
     * Educational note: Floating-point comparisons require care; Double.compare follows IEEE 754 total order, treating -0.0 < +0.0 and NaN > all.
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
     * Uses Objects.equals for null-safe string and date comparison.
     * Design rationale: Base class equality focuses on common fields; subclasses add their specific fields.
     * Educational note: Using getClass() ensures strict type checking; subclasses should call super.equals() first.
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
     * Design rationale: Base hash includes common fields; subclasses should incorporate super.hashCode().
     * Educational note: Consistent with equals - same base fields yield same base hash.
     *
     * @return the hash code for base Employee fields
     */
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, hireDate);
    }
}
