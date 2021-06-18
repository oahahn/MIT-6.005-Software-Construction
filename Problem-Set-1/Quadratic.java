package warmup;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;

public class Quadratic {

    /**
     * Find the integer roots of a quadratic equation, ax^2 + bx + c = 0.
     * @param a coefficient of x^2
     * @param b coefficient of x
     * @param c constant term.  Requires that a, b, and c are not ALL zero.
     * @return all integers x such that ax^2 + bx + c = 0.
     */
    public static Set<Integer> roots(int a, int b, int c) {
        // Make sure a, b, and c are not ALL zero
        try {
            assert !((a == 0) && (b == 0) && (c == 0));
        } catch (AssertionError e) {
            System.out.println("a, b and c are all zero.");
        }
        // If a and b are zero the equation is nonsense
        try {
            assert !((a == 0) && (b == 0));
        } catch (AssertionError e) {
            System.out.println("The equation is nonsense.");
        }
        Set<Integer> integerRoots = new HashSet<>();
        // If a is zero the equation has only one root
        if (a == 0) {
            integerRoots.add(-c / b);
        }
        else {
            double determinant = Math.pow(b, 2) - 4 * (double) a * (double) c;
            // Check to see if the roots are imaginary
            try {
                assert (determinant >= 0);
            } catch (AssertionError e) {
                System.out.println("The roots are imaginary.");
            }
            double root1 = (-b + Math.sqrt(determinant)) / (2 * a);
            double root2 = (-b - Math.sqrt(determinant)) / (2 * a);
            if (root1 == (int) root1) {
                integerRoots.add((int) root1);
            }
            if (root2 == (int) root2) {
                integerRoots.add((int) root2);
            }
        }
        return integerRoots;
    }


    /**
     * Main function of program.
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        System.out.println("For the equation x^2 - 4x + 3 = 0, the possible " +
                "solutions are:");
        Set<Integer> result = roots(0, 1, -3);
        System.out.println(result);
    }
}
