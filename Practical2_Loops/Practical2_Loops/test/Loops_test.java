/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import practical2_loops.Practical2_Loops;

/**
 *
 * @author justi
 */
public class Loops_test {
    
    @Test
    public void testCalculateSum() {
        int expected = 4;
        int actual = Practical2_Loops.calculateSum(2, 2);

        assertEquals(expected, actual);
    }
}