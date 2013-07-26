package dot2conf;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class DotGraph {

    private Scanner input;
    private ArrayList<Node> node;
    int input_counter;
    int output_counter;
    int register_counter;
//    private Integer[][] matrix;

    // "dot_files/Auto_Regression_Filter.dot" 
    DotGraph(String filename) throws Exception {
        importNodes(filename);
        importEdges(filename);
    }

//    private void initMatrix() {
//        int num_nodes = node.size();
//        int i, j;
//
//        matrix = new Integer[num_nodes][num_nodes];
//
//        for (i = 0; i < num_nodes; i++) {
//            for (j = 0; j < num_nodes; j++) {
//                matrix[i][j] = 0;
//            }
//        }
//    }

    private void importNodes(String filename) throws Exception {
        String buffer;
        
        input = new Scanner(new File(filename));
        node = new ArrayList<Node>();

        while (input.hasNextLine()) {
            buffer = input.nextLine();

            // FIXME - none of the Vertex names can contain the word node
            if (buffer.contains("digraph") || buffer.contains("node")
                    || buffer.contains("->") || buffer.contains("}")
                    || buffer.trim().length() < 1) {
                continue;
            }

            node.add(new Node(buffer));
        }

        input.close();
    }

    private void importEdges(String filename) throws Exception {
        String buffer;

        input_counter = output_counter = register_counter = 0;
        input = new Scanner(new File(filename));

        while (input.hasNextLine()) {
            buffer = input.nextLine();

            if (buffer.contains("->")) {
                parseEdge(buffer);
            }
        }
        input.close();
        
        initIO();
    }

    private void parseEdge(String line) {
        String token[];
        String parent_name, child_name;
        Node parent, child;
        int p, c;

        token = line.split("->|\\[.*");
        parent_name = token[0].trim();
        child_name = token[1].trim();

        for (p = 0; p < node.size(); p++) {
            parent = node.get(p);
            
            if (parent.getName().equals(parent_name)) {
                for (c = 0; c < node.size(); c++) {
                    child = node.get(c);
                    
                    if (child.getName().equals(child_name)) {
                        if(parent.getNumOutputs() == 1)
                            child.addInput(parent.getOutput());
                        else{
                            parent.setOutput("r" + register_counter);
                            child.addInput("r" + register_counter);
                            register_counter++;
                        }
                    }
                }
            }
        }
    }
    
    private void initIO(){
        Node cur_node;
        int i, j;
        
        for(i=0; i<node.size(); i++){
            cur_node = node.get(i);
            
            while(cur_node.getNumInputs() < 2)
                cur_node.addInput("c" + input_counter++);
            
            if(cur_node.getNumOutputs() < 1)
                cur_node.setOutput("o" + output_counter++);
        }
    }

//    void printMatrix(){
//        int i, j;
//        
//        for(i=0; i<node.size(); i++)
//            System.out.printf("%s\t", node.get(i).getName());
//        
//        for(i=0; i<node.size(); i++){
//            System.out.print("\n" + node.get(i).getName());
//            for(j=0; j<node.size(); j++){
//                System.out.print("\t" + matrix[i][j]);
//            }
//        }
//    }
    
    // FIXME - make an interface instead of just passing structures!
    ArrayList<Node> getNodes(){
        return new ArrayList<>(node);
    }
}
