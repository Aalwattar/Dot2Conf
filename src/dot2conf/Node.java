/**
 * Description: dot2conf
 * @author Jennfier Winer
 */
package dot2conf;

import java.util.ArrayList;

/*
 * Represents one node in a DFG
 */
public class Node {
    private String name;    // label of that node
    private String type;    // task type associated with that node
    
    private ArrayList<String> input;    // The names of the inputs to each node
    private String output;              // The name of the output

    /*
     * Constructor
     * @param dotLine The DOT formatted declaration of a node
     */
    Node(String dotLine) {
        String token[] = dotLine.split("\\[ *label *=|\\] *;");

        name = token[0].trim();
        type = token[1].trim();
        
        input = new ArrayList<>();
        output = null;
    }

    String getName() {
        return name;
    }

    String getType() {
        return type;
    }
    
    ArrayList<String> getInputs() {
        return new ArrayList<>(input);
    }
    String getOutput() {
        return output;
    }
    
    int getNumInputs(){
        return input.size();
    }
    
    int getNumOutputs(){
        if(output == null)
            return 0;
        else 
            return 1;
    }

    
    void setOutput(String output_name){
        output = output_name;
    }
    
    void addInput(String input_name){
        input.add(input_name);
    }
    
}
