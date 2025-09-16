package hw1;

import java.lang.Comparable;
import java.time.LocalDate;
import lombok.Getter;

/**
 * Abstract base class for all employee types in the organization.
 * Encapsulates shared attributes (name, hire date) and defines the interface for pay calculation and comparison.
 * Implements Comparable<Employee> to enable sorting by monthly pay, supporting use cases like payroll reports or compensation analysis.
 * Design rationale: Placing Comparable in the base class allows polymorphic sorting across all employee types without requiring client code to handle subtypes separately.
 * Fields are private with getter-only access (no setters), enforcing immutability post-construction. This design choice promotes data integrity, thread-safety, and easier testing/debugging.
 * Educational note for juniors: Immutability prevents unexpected state changes, reducing bugs in shared or concurrent code; always consider if mutability is truly needed.
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
}
