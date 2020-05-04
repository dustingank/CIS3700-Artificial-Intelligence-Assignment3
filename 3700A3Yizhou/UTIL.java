import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

class UTIL {

    public UTIL() {

    }

    public String parserFile(String fileName) {
        String fileContents = "";

        fileName = ((fileName.contains(".txt")) ? fileName : fileName.concat(".txt")); //ternary operator
        
        try{
            fileContents = new String(Files.readAllBytes(Paths.get(fileName)));
        }
        catch(Exception e){
            System.out.println("Error opening file: " + fileName);
            System.out.println("Exiting...");
            System.exit(0);
        }
        
        return fileContents;
    }

    public void printTree(Node rootNode, int totalNode) {
        int depth = getDepth(rootNode, 0);
        int indexNumber = 0;
        ArrayList<Node> linerList = new ArrayList<Node>();
        linerList = LinearizeDT(rootNode, 0, linerList);
        int currentParentIndex = 0;

        for (Node n: linerList) {
            n.indexA = indexNumber;
            indexNumber++;
        }

        System.out.println(rootNode.nodeLabal + "\n" + rootNode.getIndex());
        System.out.println();

        for (int i = 1; i <= depth; i++) {
            ArrayList<Node> currentDepth = new ArrayList<Node>();
            for (Node n: linerList) {
                if (i == n.depth) {
                    currentDepth.add(n);
                }
            }
            PrintTree_AtDepth(currentDepth);
        }
    }

    private static void PrintTree_AtDepth(ArrayList<Node> nodes){
       
        int size = nodes.size();



        //: Print the parent node of each node
        for(int i = 0; i < size; i++){
            System.out.print(nodes.get(i).parent.indexA + "          ");
        }
        System.out.print("\n");


        //: Print a line for each node
        for(int i = 0; i < size; i++){
            System.out.print("|          ");
        }
        System.out.print("\n");


        //: Print a line for each node, with the name of the attribut value that connects this node to its parent
        for(int i = 0; i < size; i++){
            System.out.print("|" + nodes.get(i).linkedValue);

            for(int j = nodes.get(i).linkedValue.length(); j <= 9; j++){
                System.out.print(" ");
            }
        }
        System.out.print("\n");


        //: Print a chevron for each node
        for(int i = 0; i < size; i++){
            System.out.print("v          ");
        }
        System.out.print("\n");


        //: Print the label/output of each node
        for(int i = 0; i < size; i++){
            System.out.print(nodes.get(i).nodeLabal);

            for(int j = nodes.get(i).nodeLabal.length(); j <= 10; j++){
                System.out.print(" ");
            }
        }
        System.out.print("\n");


        //: Print each nodes indexA at the current depth
        for(int i = 0; i < size; i++){
            System.out.print(nodes.get(i).getIndex() + "          ");
            //nodes.get(i).indexA = i;
        }
        System.out.print("\n");


        System.out.print("\n");

    }

    private static ArrayList<Node> LinearizeDT(Node node, int depth, ArrayList<Node> list) {
        node.depth = depth;
        list.add(node);
        depth++;

        for (Node n: node.childNodes) {
            LinearizeDT(n, depth, list);
        }

        return list;
    } 
    
    public int getDepth(Node root, int depth) {
        depth++;
        if (root.childNodes.size() != 0) {
            for (Node subNode: root.childNodes) {
                return depth + getDepth(subNode, depth);
            }
        } 

        return depth;
    }

    public int getTotalNode(Node rootNode) {
        int count = 0;

        if (rootNode.childNodes.size() != 0) {
            count = rootNode.childNodes.size();
           // System.out.println("Node Name: " + rootNode.nodeLabal + " Has subnode: " + count);

            for (Node subNode: rootNode.childNodes) {
               count = count + getTotalNode(subNode);
            }
        } 

        return count;
    }

    /*
    public void setIndex(Node root, int currentIndex) {
        root.setIndex(currentIndex);
        currentIndex++;

        if (root.childNodes.size() != 0) {
            for (Node subNode: root.childNodes) {
                setIndex(subNode, currentIndex + root.childNodes.indexOf(subNode) + subNode.depth);
            }
        } 
    }

    */


}