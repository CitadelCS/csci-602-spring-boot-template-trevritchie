package edu.citadel.hw1;

import java.time.LocalDate;

import lombok.Getter;

/**
 * Represents an hourly-paid employee.
 * Extends Employee to include wage rate and hours worked per month.
 * Monthly pay is calculated as wageRate * hoursWorked, assuming hoursWorked represents the standard monthly hours (e.g., 160 for full-time).
 *
 * @see Employee
 */
@Getter
public class HourlyEmployee extends Employee {
    /**
     * The hourly wage rate in USD.
     * Immutable after construction.
     */
    private double wageRate;

    /**
     * The number of hours worked in the current month.
     * Immutable after construction; in production, this might be recalculated periodically.
     */
    private double hoursWorked;

    /**
     * Constructs a new HourlyEmployee.
     * Calls super for name and hireDate, then sets wage and hours.
     * Assumes valid positive values for wageRate and hoursWorked.
     *
     * @param name the employee's full name
     * @param hireDate the hire date
     * @param wageRate the hourly wage rate (USD)
     * @param hoursWorked the monthly hours worked
     */
    public HourlyEmployee(String name, LocalDate hireDate, double wageRate, double hoursWorked) {
        super(name, hireDate);
        this.wageRate = wageRate;
        this.hoursWorked = hoursWorked;
    }



    /**
     * Calculates the monthly pay by multiplying wage rate by hours worked.
     * Simple linear calculation; does not account for overtime (e.g., >40 hours/week) for this basic model.
     *
     * @return the monthly pay in USD
     */
    @Override
    public double getMonthlyPay() {
        return wageRate * hoursWorked;
    }

    /**
     * Returns a string representation of this HourlyEmployee.
     * Includes name, hire date, wage rate, and hours for debugging or logging.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return "HourlyEmployee[name=" + getName() + ", hireDate=" + getHireDate() + ", wageRate=" + wageRate + ", hoursWorked=" + hoursWorked + "]";
    }

    /**
     * Compares this HourlyEmployee to another object for equality.
     * Returns true if the other is a HourlyEmployee with identical name, hireDate, wageRate, and hoursWorked.
     *
     * @param obj the object to compare (may be null)
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        // Reference equality check
        if (this == obj) return true;
        // Null or wrong type check
        if (obj == null || getClass() != obj.getClass()) return false;
        // First check base class equality (name and hireDate)
        if (!super.equals(obj)) return false;
        HourlyEmployee that = (HourlyEmployee) obj;
        // Field comparisons for subclass-specific fields: doubles use Double.compare for exact match
        return Double.compare(that.wageRate, wageRate) == 0 &&
               Double.compare(that.hoursWorked, hoursWorked) == 0;
    }

    /**
     * Generates a hash code based on all fields.
     * Consistent with equals: same fields yield same hash.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result = super.hashCode(); // Start with base class hash (name and hireDate)
        long temp;
        // Handle wageRate: convert to long bits for consistent hashing
        result = 31 * result + Double.hashCode(wageRate);
        // Handle hoursWorked similarly
        temp = Double.doubleToLongBits(hoursWorked);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}