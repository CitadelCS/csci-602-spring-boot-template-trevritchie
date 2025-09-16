package edu.citadel.hw1;

import java.time.LocalDate;

import lombok.Getter;

/**
 * Represents an hourly-paid employee.
 * Extends Employee to include wage rate and hours worked per month.
 * Monthly pay is calculated as wageRate * hoursWorked, assuming hoursWorked represents the standard monthly hours (e.g., 160 for full-time).
 * Design rationale: Hourly employees have variable pay based on time worked; this class encapsulates that variability while inheriting common attributes.
 * Fields are immutable post-construction to maintain data consistency (e.g., preventing retroactive changes to historical pay calculations).
 * Educational note for juniors: In real systems, consider validating hoursWorked (e.g., 0 < hours <= 173.33 for a month) and wageRate (>0); here, we assume valid inputs for simplicity.
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
     * Design rationale: Keeps calculation straightforward; extend with overtime logic if needed in subclasses or via composition.
     * Educational note: Double multiplication is exact for this use case, but monitor for precision loss in larger systems (e.g., use BigDecimal for financial apps).
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
     * Uses exact double comparison via Double.compare == 0, suitable for business identifiers where values are set precisely (not measured).
     * Design rationale: Class-specific equality (not superclass) to ensure type safety; avoids inheritance pitfalls like Liskov Substitution for equals.
     * Educational note for juniors: Overriding equals requires overriding hashCode; use getClass() check to prevent instanceof issues with subclasses.
     * Inline: First, check reference equality for efficiency.
     * Then, null/type check to avoid NPE/ClassCastException.
     * Finally, compare all fields: strings/dates via equals (handles nulls safely), doubles via Double.compare for total order without overflow.
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
        HourlyEmployee that = (HourlyEmployee) obj;
        // Field comparisons: doubles use Double.compare for exact match, others use equals
        return Double.compare(that.wageRate, wageRate) == 0 &&
               Double.compare(that.hoursWorked, hoursWorked) == 0 &&
               getName().equals(that.getName()) &&
               getHireDate().equals(that.getHireDate());
    }

    /**
     * Generates a hash code based on all fields.
     * Consistent with equals: same fields yield same hash.
     * Uses 31 as multiplier (prime, good distribution) and doubleToLongBits for doubles to handle NaN/-0.0 correctly.
     * Design rationale: Inherited fields (name, hireDate) included; class-specific fields added for completeness.
     * Educational note: Bitwise XOR on long bits ensures 32-bit int hash; avoids floating-point inconsistencies.
     * Inline: Start with name hash, multiply by 31 and add hireDate.
     * Convert doubles to long bits, XOR high/low 32 bits, then incorporate similarly.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        int result;
        long temp;
        result = getName().hashCode();
        result = 31 * result + getHireDate().hashCode();
        // Handle wageRate: convert to long bits for consistent hashing
        temp = Double.doubleToLongBits(wageRate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        // Handle hoursWorked similarly
        temp = Double.doubleToLongBits(hoursWorked);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}