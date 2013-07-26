package dot2conf;

import java.util.ArrayList;

public class Node {
    private String name;
    private String type;
    
    private ArrayList<String> input;
    private String output;

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
