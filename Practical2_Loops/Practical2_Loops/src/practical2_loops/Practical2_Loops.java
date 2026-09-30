/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package practical2_loops;
import javax.swing.JOptionPane;
/**
 *
 * @author iramatladi
 */
public class Practical2_Loops {

    /**
     * @param args the command line arguments
     */
    
     public static int calculateSum(int number1, int number2){
        return number1 + number2;
        }
    public static void main(String[] args) {
        // TODO code application logic here
        
       int choice = 1;
       int limit = 5;
       
        while (choice<limit){//The loop is asking us the enter the number for as long as choice is less than limit
        String Num1 = JOptionPane.showInputDialog("Enter the first number");
        String Num2 = JOptionPane.showInputDialog("Enter the second number");
        
        int firstNum = Integer.parseInt(Num1);
        int secondNum = Integer.parseInt(Num2);
        
        int sum = calculateSum(firstNum,secondNum );
       
        JOptionPane.showMessageDialog(null, "The sum is " + sum + " Current value of choice/iteration " + choice);
      //Come and print the sum for every iteration here
        choice = choice +1;//This will allow choice to increment everytime there is an iteration, until the value of choice is equal to limit
    }
        
        //We are now starting with the while loop
//         int counter = 1;
//          while (counter <=3){
//          JOptionPane.showInputDialog("While loop iteration" + counter);
//          counter++;
//          }
    }
    
}
