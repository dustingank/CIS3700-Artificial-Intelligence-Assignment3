import java.util.ArrayList;

class Node {
    
    Node parent;
    String nodeLabal;
    String linkedValue;
    ArrayList<Node> childNodes;
    int indexA = 0;
    int depth = 0;

    public Node(String nodelabal) {
        this.nodeLabal = nodelabal;
        linkedValue = null;
        childNodes = new ArrayList<Node>();
        indexA = 0;
    }

    public void LinkNode(Node parent, String labelOfLink){
        Node current = this;
        this.linkedValue = labelOfLink;
        this.parent = parent;
        (parent.childNodes).add(current);
    }

    public void setIndex(int indexA) {
        this.indexA = indexA;
    }

    public int getIndex() {
        return indexA;
    }



}