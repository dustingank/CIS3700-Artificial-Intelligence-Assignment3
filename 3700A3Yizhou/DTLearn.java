import java.util.ArrayList;

class DTLearn {

    Scheme schemeObject;

    public DTLearn(){
        schemeObject = null;
    }
    
    private void setScheme(Scheme schemeObject) {
        this.schemeObject = schemeObject;
    }

    public static void main(String[] args) {
        UTIL startDT = new UTIL();
        DTLearn startLearning = new DTLearn();

        String schemeFileName = args[0];
        String dataFileName = args[1];
        String schemeContents, dataContents;
        

        schemeContents = startDT.parserFile(schemeFileName);
        Scheme schemeObj = new Scheme(schemeContents);
        ArrayList<Attribute> tempAttributesList = new ArrayList<Attribute>();

        for (int a = 0; a < schemeObj.attributesList.size(); a++) { // deep copy
            tempAttributesList.add(schemeObj.attributesList.get(a));
        }
       
        dataContents = startDT.parserFile(dataFileName);
        Sample sampleObj = new Sample(dataContents, schemeObj);

        startLearning.setScheme(schemeObj);
        Node rootNode = startLearning.learnDecisionTree(sampleObj, tempAttributesList, -2);

        int totalNode = startDT.getTotalNode(rootNode) + 1;
        int totalDepth = startDT.getDepth(rootNode, 0) + 1;
        //startDT.setIndex(rootNode, 0);
        System.out.println(String.format("\nDecision Tree (%d nodes, %d depth)",totalNode, totalDepth));
        startDT.printTree(rootNode, totalNode);

   }  


    public Node learnDecisionTree(Sample g, ArrayList<Attribute> attribs, int sMajor) {
       // g.showSampel();
       // attribList.showList();
        Attribute b, fun;
        int valueM;

        //Testing Start
         
        if ((g.getSampleList()).size() == 0) {
            String[] possibleValues = ((attribs.get(attribs.size() - 1)).getValue()).split("\\s+");
            return new Node(possibleValues[sMajor]); // return a node labeled sMajor
        }

        if (g.isAllSameClass()) {
            int valueQ = g.getFuncValue();
            String[] possibleValues = ((attribs.get(attribs.size() - 1)).getValue()).split("\\s+");
            return new Node(possibleValues[valueQ]);// return a node labeled q
        }

        if (attribs.size() == 1) { // it still have func in the attribute
            valueM = g.majorityValue();
            String[] possibleValues = ((attribs.get(attribs.size() - 1)).getValue()).split("\\s+");
            return new Node(possibleValues[valueM]); // return a node labeled majorityValue(g )
        }

        b = g.getAttribute(attribs);
        attribs.remove(b);

        String[] possibleValues = b.getValue().split("\\s+");
        Node tr = new Node(b.getName()); // this is a root / subRoot, not a node
        valueM = g.majorityValue();
        
        for (String s: possibleValues) {
            int sValueIndex = b.getValueIndex(s); // get the number value
            Sample subg = new Sample(g.getSubgList(b, sValueIndex), schemeObject); // {examples in g with b = v}
            Node subtr = learnDecisionTree(subg, attribs, valueM);
            subtr.LinkNode(tr, s);
        }
        
        return tr;
    }

}