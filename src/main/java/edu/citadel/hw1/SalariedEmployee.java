package edu.citadel.hw1;

import java.time.LocalDate;
import java.util.Objects;
import lombok.Getter;

/**
 * Represents a salaried employee with a fixed annual salary.
 * Extends Employee to include annual salary, from which monthly pay is derived by dividing by 12.
 * Suitable for full-time staff with consistent compensation regardless of hours worked.
 * Design rationale: Salaried model simplifies payroll for predictable income; contrasts with HourlyEmployee for flexible staffing needs.
 * Fields are immutable post-construction to ensure salary integrity (e.g., prevents unauthorized adjustments).
 * Educational note for juniors: Division by 12 assumes a 12-month year; in real payroll, account for fiscal year variations or bonuses separately.
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
     * Design rationale: Monthly pay provides consistent view for comparison/sorting with hourly employees.
     * Educational note: Floating-point division is appropriate here; for exact financials, consider BigDecimal to avoid rounding errors over time.
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
     * Design rationale: Class-specific equality ensures type safety; leverages Objects utility for robustness.
     * Educational note for juniors: Objects.equals handles nulls gracefully; Double.compare avoids direct == on doubles due to precision issues, but ==0 checks for logical equality in set values.
     * Inline: Reference equality first.
     * Null/type check next.
     * Field comparisons: salary via compare, others via Objects.equals.
     *
     * @param obj the object to compare (may be null)
     * @return true if equal
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        SalariedEmployee that = (SalariedEmployee) obj;
        return Double.compare(that.annualSalary, annualSalary) == 0 &&
               Objects.equals(getName(), that.getName()) &&
               Objects.equals(getHireDate(), that.getHireDate());
    }

    /**
     * Generates a hash code based on all fields.
     * Consistent with equals: uses Objects.hash for simplicity and null-safety.
     * Includes inherited fields (name, hireDate) and class-specific (annualSalary).
     * Design rationale: Objects.hash distributes well; automatic handling of doubles.
     * Educational note: Prefer Objects.hash over manual computation for maintainability; it uses 31 multiplier internally.
     *
     * @return the hash code
     */
    @Override
    public int hashCode() {
        return Objects.hash(getName(), getHireDate(), annualSalary);
    }
}