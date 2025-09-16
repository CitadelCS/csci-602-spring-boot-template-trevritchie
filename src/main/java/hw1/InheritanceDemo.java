package hw1;

import java.util.*;
import java.time.LocalDate;

/**
 * Demonstration of inheritance and polymorphism using Employee hierarchy.
 * Creates instances of HourlyEmployee and SalariedEmployee, stores in a polymorphic ArrayList<Employee>,
 * sorts by monthly pay using the inherited Comparable implementation, and prints details.
 * Illustrates key OOP concepts: abstraction (Employee base), extension (subclasses), overriding (toString, getMonthlyPay),
 * and runtime polymorphism (calling methods on base references).
 * Design rationale: Uses ArrayList for flexibility; Collections.sort leverages Comparable for pay-based ordering without custom comparators.
 * Educational note for juniors: Polymorphism allows treating subclasses uniformly; sorting works seamlessly due to base class Comparable.
 * No code changes from original; comments added for clarity. Output should match sample: unsorted list, sorted list, individual pays, total.
 *
 * @author Senior Engineer
 * @version 1.0
 * @see Employee
 * @see HourlyEmployee
 * @see SalariedEmployee
 */
public class InheritanceDemo {
    /**
     * Entry point for the demonstration.
     * Steps:
     * 1. Create polymorphic list of Employees (mix of Hourly and Salaried).
     * 2. Print unsorted list using overridden toString().
     * 3. Sort using Collections.sort(), which calls compareTo() (pay-based).
     * 4. Print sorted list.
     * 5. Calculate and print monthly pays using overridden getMonthlyPay(), sum total.
     * Rationale: Demonstrates inheritance benefits - no need for type checks; base methods dispatch correctly.
     * Inline comments explain each step for educational purposes.
     * Assumes Java 8+ for LocalDate; no input validation for demo simplicity.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        // Step 1: Create a polymorphic ArrayList to hold different employee types
        // Design choice: ArrayList for dynamic sizing; Employee reference allows mixed subtypes
        ArrayList<Employee> list = new ArrayList<>();

        // Instantiate HourlyEmployee: John Doe (2009 hire, $50.5/hr, 160 hrs -> ~$8080 monthly)
        HourlyEmployee john = new HourlyEmployee("John Doe", LocalDate.of(2009, 5, 21), 50.5, 160.0);
        list.add(john);

        // Instantiate another HourlyEmployee: Jane Doe (2005 hire, $150.5/hr, 80 hrs -> ~$12040 monthly)
        // Note: Higher rate but fewer hours; sorting will place based on total pay
        HourlyEmployee jane = new HourlyEmployee("Jane Doe", LocalDate.of(2005, 9, 1), 150.5, 80.0);
        list.add(jane);

        // Instantiate SalariedEmployee: Moe Howard (2004 hire, $75k annual -> $6250 monthly)
        SalariedEmployee moe = new SalariedEmployee("Moe Howard", LocalDate.of(2004, 1, 1), 75000.0);
        list.add(moe);

        // Instantiate another SalariedEmployee: Curly Howard (2018 hire, $105k annual -> $8750 monthly)
        SalariedEmployee curly = new SalariedEmployee("Curly Howard", LocalDate.of(2018, 1, 1), 105000.0);
        list.add(curly);

        // Step 2: Print the list before sorting to show original order (addition order)
        // Uses enhanced for-loop with polymorphism: toString() overridden per subtype
        System.out.println("List of Employees (before sorting)");
        for (Employee e : list) {
            System.out.println(e.toString());
        }
        System.out.println();  // Blank line for readability

        // Step 3: Sort the list using Collections.sort, which invokes compareTo() on each pair
        // compareTo uses Double.compare on getMonthlyPay() for safe double ordering (avoids overflow/NaN issues)
        // Result: Low to high pay; handles mixed types uniformly due to base Comparable
        Collections.sort(list);

        // Step 4: Print the sorted list to demonstrate ordering by monthly pay
        System.out.println("List of Employees (after sorting)");
        for (Employee e : list) {
            System.out.println(e.toString());
        }
        System.out.println();  // Blank line

        // Step 5: Calculate and display monthly pay for each, then total
        // Polymorphism: getMonthlyPay() calls subclass implementation automatically
        // Uses printf for formatted currency output (locale-aware in full apps)
        System.out.println("Monthly Pay");
        double totalPay = 0.0;
        for (Employee e : list) {
            double pay = e.getMonthlyPay();  // Subtype-specific calculation
            System.out.printf("%s: $%,.2f\n", e.getName(), pay);
            totalPay += pay;  // Accumulate; double addition fine for demo (use BigDecimal for precision in prod)
        }
        // Output total with formatting: commas for thousands, 2 decimal places
        System.out.printf("Total Monthly Pay: $%,.2f\n", totalPay);
    }
}