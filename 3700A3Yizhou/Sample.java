import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.HashMap;

class Sample {

    ArrayList<Example> sampleList;
    Scheme schemeObj;

    public Sample(String fileContent, Scheme schemeObj) {
        sampleList = new ArrayList<Example>();
        this.schemeObj = schemeObj;

        String tempStringArray[] = fileContent.split("[\\r\\n]+");
        String valueName[] = tempStringArray[0].split(" ");
        ArrayList<String> attributeList = new ArrayList<String>();
        int index = 0;

        for (Attribute b: schemeObj.getAttributeList()) {
            attributeList.add(b.getName());
        }

        for (int i = 0; i < valueName.length; i++) {
            if (attributeList.contains(valueName[i])) {
                continue;
            } else {
                System.out.println("Unknow Attribute in Data File: " + valueName[i]);
                System.exit(0);
            }
        }

        for (Attribute a: schemeObj.getAttributeList()) {
            if (a.getName().equals(valueName[index])) {
                index++;
            } else {
                System.out.println("Incorrect order in Data file and Scheme File \nExiting...");
                System.exit(0);
            }
        }

        for (int a = 1; a < tempStringArray.length; a++) {
            sampleList.add(new Example(tempStringArray[a], schemeObj));
        }
    }

    public Sample(ArrayList<Example> input, Scheme scheme) {
        sampleList = new ArrayList<Example>();
        for (Example a: input) {
            sampleList.add(a);
        }
        this.schemeObj = scheme;
    }

    public int getFuncValue() {
        return ((sampleList.get(0)).getFuncValue());
    }

    public int majorityValue() {
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        for (int count = 0; count < sampleList.size(); count++) {
            arrayList.add((sampleList.get(count)).getFuncValue());
        }

        HashMap<Integer,Integer> hashMap = new HashMap<Integer,Integer>();
        int max  = 1;
        int temp = 0;

        for(int i = 0; i < arrayList.size(); i++) {
            if (hashMap.get(arrayList.get(i)) != null) {
                int count = hashMap.get(arrayList.get(i));
                count++;
                hashMap.put(arrayList.get(i), count);
                if(count > max) {
                    max  = count;
                    temp = arrayList.get(i);
                }
            } else {
                hashMap.put(arrayList.get(i),1);
            }
        }
        return temp;
    }

    public boolean isAllSameClass() { // check if all examples in g have class q
        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (sampleList.size() == 1) {
            return true;
        }
       
        for (int count = 0; count < sampleList.size(); count++) {
            arrayList.add((sampleList.get(count)).getFuncValue());
        }

        Set<Integer> s = new HashSet<>(arrayList);
        return (s.size() == 1);

    }

    public void showSample() {
        for (Example a : sampleList) {
            a.showList();
        }
    }

    public ArrayList<Example> getSampleList() {
        return sampleList;
    }

    public ArrayList<Example> getSubgList(Attribute toBeSearch, int valueIndex) {
        int index = sampleList.get(1).getAttributeIndex(toBeSearch);
        ArrayList<Example> toBeReturn = new ArrayList<Example>();

        for (Example a: sampleList) {
            if (a.getValueList().get(index) == valueIndex) {
                toBeReturn.add(a);
            }
        }

        return toBeReturn;
    }

    public Attribute getAttribute (ArrayList<Attribute> tempAttribs) {
        Attribute bestA = tempAttribs.get(0);

        int K = tempAttribs.get(tempAttribs.size() - 1).getCount();
        double I = getInfo(K);
        double maxGain = -1;
        double remainder = 0;
        double gain = 0;

        for (int a = 0; a < tempAttribs.size() - 1; a++) {
            Attribute current = tempAttribs.get(a);
            remainder = getRmd(current, K);
            gain = I - remainder;

            if (gain > maxGain) {
                maxGain = gain;
                bestA = current;
            }

            String strDoubleI = String.format("%.4f", I);
            String strDoubleRmd = String.format("%.4f", remainder);
            String strDoubleGain = String.format("%.4f", gain);
            System.out.println("Test " + current.getName() + ": info=" + strDoubleI + " rmd=" + strDoubleRmd + " gain=" + strDoubleGain); 
        }

        System.out.println("\t\tSelect attribute " + bestA.getName());
        
        return bestA;
    }


    private double getRmd(Attribute b, int K) {
        int size = sampleList.size();
        int m = b.getCount();
        Sample[] subg = splitFunction(b);
        int[] subcnt = new int[m];

        for (int i = 0; i < m; i++) {
            subcnt[i] = subg[i].sampleList.size();
        }

        double remainder = 0;
        for (int j = 0; j < m; j++) {
            double pr = (double)subcnt[j] / size;
            double I = subg[j].getInfo(K);
            remainder += (double)pr * I;
        }

        return remainder;
    }

    private Sample[] splitFunction(Attribute split) {
        int count = split.getCount(); // get how many values
        int indexAttrib = schemeObj.getAttributeIndex(split);
        ArrayList<Example> tempList = new ArrayList<Example>();
        Sample[] toBeReturn = new Sample[count];

        for (int i = 0; i < count; i++) {
            //System.out.println("Value i :" + i);
            for (Example e: sampleList) {
                //System.out.println("Attribute Name :" + split.getName());
                //e.showList();
                if (i == e.exampleValues.get(indexAttrib)) {
                    tempList.add(e);
                }
            }
            toBeReturn[i] = new Sample(tempList, schemeObj);
            tempList = new ArrayList<Example>();
        }
        return toBeReturn;
    }


    private double getInfo(int K) {
        
        int size = sampleList.size();
        int count[] = new int[K];

        for(int i = 0; i < K; i++){
            for(Example e: sampleList){
                if(e.getFuncValue() == i){
                    count[i]++;
                }
            }
        }

        if(size == 0){
            return 0;
        }

        double I = 0;
        for(int i = 0; i < K; i++){
            double pr = (double)count[i] / size;
            if(pr > 0){
                I = I - (pr * Log2(pr));
            }
        }
        return I;
    }

    private double Log2(double value){
        return Math.log(value) / Math.log(2);
    }

}