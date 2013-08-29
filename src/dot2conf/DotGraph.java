/**
 * Description: dot2conf
 * @author Jennfier Winer
 */
package dot2conf;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Parses a graph in DOT format
 * The graph is represented internally by a series of nodes.
 * @author jennifer
 */
public class DotGraph {

    private Scanner input;
    private ArrayList<Node> node;
    private int input_counter;
    private int output_counter;
    private int register_counter;

    /*
     * Constructor that imports a DFG from a DOT file
     * @param filename The name of the task graph DOT format
     * @throws Exception if the provided file was improperly formatted
     */
    DotGraph(String filename) throws Exception {
        importNodes(filename);
        importEdges(filename);
        initIO();
    }
    
    /*
     * Initialize the nodes of the DFG
     * @param filename The name of the task graph DOT format
     * @throws Exception if the file could not be opened (or found)
     */
    private void importNodes(String filename) throws Exception {
        String buffer;
        
        input = new Scanner(new File(filename));
        node = new ArrayList<>();

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

    
    /*
     * Initialize the edges of the DFG
     * @param filename The name of the task graph DOT format
     * @throws Exception if the file could not be opened (or found)
     */
    private void importEdges(String filename) throws Exception {
        String buffer;

        input = new Scanner(new File(filename));

        while (input.hasNextLine()) {
            buffer = input.nextLine();

            if (buffer.contains("->")) {
                parseEdge(buffer);
            }
        }
        input.close();
    }

    
    /*
     * Connect two nodes in the DFG with a directed edge 
     * @param line The DOT formatted edge
     */
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
    
    /*
     * Label the input and output of each node
     * This function should only be called LAST (after all nodes and edges have been imported)
     */
    private void initIO(){
        Node cur_node;
        int i, j;
        
        input_counter = output_counter = register_counter = 0;
        
        for(i=0; i<node.size(); i++){
            cur_node = node.get(i);
            
            while(cur_node.getNumInputs() < 2)
                cur_node.addInput("c" + input_counter++);
            
            if(cur_node.getNumOutputs() < 1)
                cur_node.setOutput("o" + output_counter++);
        }
    }
       
    // FIXME - make an interface instead of just passing structures!
    /*
     * Return the internal representation of the DFG ...  a horrible idea, I know.
     */
    ArrayList<Node> getNodes(){
        return new ArrayList<>(node);
    }
}
