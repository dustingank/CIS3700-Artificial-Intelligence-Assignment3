class Attribute {

    String attributeName; 
    int attributeValueCount;
    String attributeValueName;

    public Attribute(String attributeName, int attributeValueCount, String attributeValueName) {
        this.attributeName = attributeName;
        this.attributeValueCount = attributeValueCount;
        this.attributeValueName = attributeValueName;
    }

    public String getName() {
        return this.attributeName;
    }

    public int getCount() {
        return this.attributeValueCount;
    }

    public String getValue() {
        return this.attributeValueName;
    }

    public String getAttribute() {
        return attributeName + " " + attributeValueCount + " " + attributeValueName;
    }

    public int getValueIndex(String Value) {
        String tempArray[] = attributeValueName.split("\\s+");

        for (int a = 0; a < tempArray.length; a++) {
           
            if ( tempArray[a].equals(Value) ) {
                return a;
            }
        }
        return -1;
    }
 
}