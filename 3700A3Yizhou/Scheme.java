import java.util.ArrayList;

class Scheme{ // shceme should have a list of attributes nd the function, the list is made by attribute

    ArrayList<Attribute> attributesList;
    int totalSymbol = 0, attValueCount = 0;
    String attName, attValueName;


    public Scheme(String schemeContents) {
        attributesList = new ArrayList<Attribute>();
        parseString(schemeContents);
        //showList();
    }

    public Scheme(ArrayList<Attribute> tempList) {
        attributesList = tempList;
    }


    public ArrayList<Attribute> getAttributeList() {
        return attributesList;
    }

    public String toString() {
        String toBeReturn = null;

        for(Attribute a : attributesList) {
            toBeReturn += a.getName(); 
        }

        return toBeReturn;
    }

    public Attribute getAttribute(int index) {
        return attributesList.get(index);
    }

    public int getAttributeIndex(Attribute a) {
        return attributesList.indexOf(a);
    }

    public Attribute getLastAttribute() {
        return attributesList.get(attributesList.size() - 1);
    }

    public int getTotalSymbol() {
        return totalSymbol;
    }

    public void showList() {
        for (Attribute a : attributesList) {
            System.out.println(a.getAttribute());
        }
    }

    private void parseString(String schemeContents) {
        String[] schemeFileLines = schemeContents.split("[\\r\\n]+"); // splits the file content
        this.totalSymbol = Integer.parseInt(schemeFileLines[0]); // get the first line which is the total number of symbol puls function

        for (int i = 1; i < schemeFileLines.length; i+=3) {
            attName = schemeFileLines[i];
            attValueCount = Integer.parseInt(schemeFileLines[i + 1]);
            attValueName = schemeFileLines[i + 2];

            attributesList.add(new Attribute(attName, attValueCount, attValueName));
        }
    }

    

}