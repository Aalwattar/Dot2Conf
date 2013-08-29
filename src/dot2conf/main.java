/**
 * Description: dot2conf
 * @author Jennfier Winer
 */
package dot2conf;

import java.io.File;

// FIXME - have an interface between conf and dot as the main (not this pooly named class ...)

/**
 * This class is the procedural main class that runs the program
 * 
 * @author Jennifer Winer
 */
public class main {
    
    public static void main(String[] args) {
        File folder = new File("dot_files");
        File[] listOfFiles = folder.listFiles();

        for (int i = 0; i < listOfFiles.length; i++) {
            try{
                DotGraph original = new DotGraph("dot_files/" + listOfFiles[i].getName());
                ConfFormat.writeToFile(listOfFiles[i].getName(), original.getNodes());
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }
}


/** The names of all of our open source benchmarks **/
/** Taken from http://express.ece.ucsb.edu/benchmark/ **/

//Auto Regression Filter.dot
//Cosine 1.dot
//Cosine 2.dot
//Elliptic Wave Filter.dot
//EPIC - Collapse_pyr.dot
//Finite Input Response Filter 11.dot
//Finite Input Response Filter 2.dot
//HAL.dot
//JPEG - Forward Discrete Cosine Transform.dot
//JPEG - Inverse Discrete Cosine Transform.dot
//JPEG - Smooth Downsample.dot
//JPEG - Write BMP Header.dot
//MESA - Feedback Points.dot
//MESA - Horner Bezier.dot
//MESA - Interpolate Aux.dot
//MESA - Invert Matrix.dot
//MESA - Matrix Multiplication.dot
//MESA - Smooth Triangle.dot
//MPEG - Inverse Discrete Cosine Transform.dot
//MPEG - Motion Vectors.dot