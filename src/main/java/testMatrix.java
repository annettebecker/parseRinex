
import org.ejml.simple.SimpleMatrix;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Annette
 */
public class testMatrix {

    public static void main(String args[]) {
        SimpleMatrix aMatrix = new SimpleMatrix(2, 2);
        SimpleMatrix bMatrix = new SimpleMatrix(2, 1);

        //Werte setzen
        aMatrix.set(0, 0, 1);
        aMatrix.set(0, 1, 4);
        aMatrix.set(1, 0, 1);
        aMatrix.set(1, 1, 1);

        bMatrix.set(0, 0, 28);
        bMatrix.set(1, 0, 10);
        
        SimpleMatrix cMatrix = aMatrix.mult(bMatrix);
        System.out.println(cMatrix);
        
        SimpleMatrix d = cMatrix.plus(bMatrix);
        System.out.println(d);

    }

}
