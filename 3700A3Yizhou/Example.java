import java.util.ArrayList;

class Example {

    ArrayList<Integer> exampleValues;
    Scheme schemeObj;

    public Example(String fileContents, Scheme schemeObj) {
        exampleValues = new ArrayList<Integer>();
        this.schemeObj = schemeObj;
        String[] fileContentsArray = fileContents.split("\\s+");
      
        for (int i = 0; i < fileContentsArray.length; i++) {

            int number = schemeObj.getAttribute(i).getValueIndex(fileContentsArray[i]);

            if (number == -1) {
                System.out.println("Error: Unable to recognize value in attribute " + schemeObj.getAttribute(i).getName() + " with unknow value " + fileContentsArray[i] + "\nExiting....");
                System.exit(0);
            }

            exampleValues.add((schemeObj.getAttribute(i)).getValueIndex(fileContentsArray[i]));
        }

    }

    public int getFuncValue() {
        return exampleValues.get(exampleValues.size() - 1);
    }

    public ArrayList<Integer> getValueList() {
        return exampleValues;
    }

    public int getAttributeIndex(Attribute attrib) {
        
        for (Attribute a: (schemeObj.getAttributeList())) {
            //System.out.println(a.getName() + attrib.getName());
            if (a.getName().equals(attrib.getName())) {
                return schemeObj.getAttributeList().indexOf(a);
            }
        }

        return -1;
    }

    public void showList() {
        for (int a: exampleValues) {
            System.out.print(a);
        }
        System.out.println();
    } 

}