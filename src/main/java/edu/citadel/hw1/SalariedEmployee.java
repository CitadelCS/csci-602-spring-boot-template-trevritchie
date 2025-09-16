package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;

/**
 * Represents a salaried employee with a fixed annual salary.
 * Extends Employee to include annual salary, from which monthly pay is derived by dividing by 12.
 *
 * @see Employee
 * @see HourlyEmployee
 */
@Getter
public class SalariedEmployee extends Employee {
    /**
     * The annual salary in USD.
     * Immutable after construction.
     */
    private double annualSalary;

    /**
     * Constructs a new SalariedEmployee.
     * Calls super for name and hireDate, then sets the annual salary.
     * Assumes valid positive value for annualSalary.
     *
     * @param name the employee's full name
     * @param hireDate the hire date
     * @param annualSalary the annual salary (USD)
     */
    public SalariedEmployee(String name, LocalDate hireDate, double annualSalary) {
        super(name, hireDate);
        this.annualSalary = annualSalary;
    }


    /**
     * Calculates the monthly pay by dividing annual salary by 12.
     * Uses 12.0 (double) for precise division; integer division would truncate.
     *
     * @return the monthly pay in USD
     */
    @Override
    public double getMonthlyPay() {
        return annualSalary / 12.0;
    }

    /**
     * Returns a string representation of this SalariedEmployee.
     * Includes name, hire date, and annual salary for debugging or reporting.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "SalariedEmployee[name=" + getName() + ", hireDate=" + getHireDate() + ", annualSalary=" + annualSalary + "]";
    }

    /**
     * Compares this SalariedEmployee to another object for equality.
     * Returns true if the other is a SalariedEmployee with identical name, hireDate, and annualSalary.
     * Uses Objects.equals for strings/dates (null-safe) and Double.compare == 0 for exact salary match.
     *
     * @param obj the object to compare (may be null)
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        // First check base class equality (name and hireDate)
        if (!super.equals(obj)) return false;
        SalariedEmployee that = (SalariedEmployee) obj;
        return Double.compare(that.annualSalary, annualSalary) == 0;
    }

    /**
     * Generates a hash code based on all fields.
     * Consistent with equals: uses Objects.hash for simplicity and null-safety.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), annualSalary);
    }
}