import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class applrun{
    public static void main(String []args) throws IOException {
        Random rand = new Random();
        Scanner sc = new Scanner(System.in);
        System.out.println("Select the hash table dor which the demo should run");
        System.out.println("1.Bloom Filter\n2.Counting Bloom Filter\n3.Coded Bloom filter");
        int option=sc.nextInt();
        FileWriter output = new FileWriter("output_proj2_" + option + ".txt");
        if(option==1){
            System.out.println("Enter the number of elements to encoded for set A");
            int numelementsA=sc.nextInt();
            System.out.println("Enter the number of elements to encoded for set B");
            int numelementsB=sc.nextInt();
            System.out.println("Enter the number of hashes");
            int numhash=sc.nextInt();
            System.out.println("Enter the number of bits in bitmap");
            int numbits=sc.nextInt();
            BloomFilter bf = new BloomFilter(numbits, numhash);
            int []setA=new int[numelementsA];
            int []setB=new int[numelementsB];

            for (int i = 0; i < numelementsA; i++) {
                setA[i] = Math.abs(rand.nextInt());
                bf.encode(setA[i]);
            }
            for (int i = 0; i < numelementsB; i++) {
                setB[i] = Math.abs(rand.nextInt());
            }
            int numelefoundA=0;
            for (int i = 0; i < numelementsA; i++) {
                if (bf.lookup(setA[i])){
                    numelefoundA++;
                }
            }
            int numelefoundB=0;
            for (int i = 0; i < numelementsB; i++) {
                if (bf.lookup(setB[i])){
                    numelefoundB++;
                }
            }
            output.write("Number of elements found from set A = " + numelefoundA+"\n");
            System.out.println("Number of elements found from set A= " + numelefoundA);
            output.write("Number of elements found from set B = " + numelefoundB+"\n");
            System.out.println("Number of elements found from set B = " +numelefoundB);
        }
        else if (option==2) {
            System.out.println("Enter the number of elements to encoded for set A");
            int numelementsA=sc.nextInt();
            System.out.println("Enter the number of elements to be added");
            int numadd=sc.nextInt();
            System.out.println("Enter the number of elements to be removed");
            int numrem=sc.nextInt();
            System.out.println("Enter the number of hashes");
            int numhash=sc.nextInt();
            System.out.println("Enter the number of counters in filter");
            int numcounters=sc.nextInt();
            CountingBloomFilter cbf = new CountingBloomFilter(numcounters, numhash);
            int []setA=new int[numelementsA];
            for (int i = 0; i < numelementsA; i++) {
                setA[i]=Math.abs(rand.nextInt());
                cbf.encode(setA[i]);
            }
            Set<Integer> h=Arrays.stream(setA).boxed().collect(Collectors.toSet());
            for (int i = 0; i < numrem; i++) {
                cbf.remove(setA[i]);
                h.remove((setA[i]));
            }

            for (int i = 0; i < numadd; i++) {
                int j=Math.abs(rand.nextInt());
                if (h.contains(j)){
                    i--;
                }
                else {
                    cbf.encode(j);
                }
            }

            int numelefoundA=0;
            for (int i = 0; i < numelementsA; i++) {
                if (cbf.lookup(setA[i])){
                    numelefoundA++;
                }
            }
            output.write("Number of elements found from set A = " + numelefoundA+"\n");
            System.out.println("Number of elements found from set A= " + numelefoundA);
        }
        else if (option==3) {
            System.out.println("Enter the number of elements for each set");
            int numelements=sc.nextInt();
            System.out.println("Enter the number of sets");
            int numsets=sc.nextInt();
            System.out.println("Enter the number of filters");
            int numfilters=sc.nextInt();
            System.out.println("Enter the number of bits");
            int numbits=sc.nextInt();
            System.out.println("Enter the number of hash");
            int numhash=sc.nextInt();
            CodedBloomFilter cbf = new CodedBloomFilter(numsets,numelements,  numfilters, numbits, numhash);
            for (int j=0;j<numsets;j++){
                for (int i = 0; i < numelements; i++) {
                    cbf.setarr[j][i]=Math.abs(rand.nextInt());
                    cbf.encode(cbf.setarr[j][i],j+1);
                }}
            int numelefound=0;

            for (int j=0;j<numsets;j++){
                for (int i = 0; i < numelements; i++) {
                    if (cbf.lookup(cbf.setarr[j][i],j+1)){
                        numelefound++;
                    }
                }}
            output.write("Number of elements found = " + numelefound+"\n");
            System.out.println("Number of elements found= " + numelefound);
        }
        output.close();
    }}
class BloomFilter{
    int numhash;
    int numbits;
    int []s;
    int [] bitmap;
    Random rand=new Random();
    BloomFilter(int numbits, int numhash){
        this.numhash=numhash;
        this.numbits=numbits;
        this.s=new int[numhash];
        this.bitmap=new int[numbits];
        Arrays.fill(bitmap,0);
        hashfunctions();
    }
    public void encode(int element){

        for(int i=0;i<numhash;i++){
            int xor=element^s[i];
            if (bitmap[xor%numbits]==0) {
                bitmap[xor % numbits]= 1;
            }
        }
    }
    public boolean lookup(int element){
        for(int i=0;i<numhash;i++){
            int xor=element^s[i];
            if (bitmap[xor%numbits]==1) {
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }
    public void hashfunctions(){
        for(int i=0;i<this.numhash;i++){
            s[i]=Math.abs(rand.nextInt());
        }
    }
}
class CountingBloomFilter{
    int numhash;
    int numcounter;
    int []s;
    int [] counterarr;
    Random rand=new Random();
    CountingBloomFilter(int numcounter, int numhash){
        this.numhash=numhash;
        this.numcounter=numcounter;
        this.s=new int[numhash];
        this.counterarr=new int[numcounter];
        Arrays.fill(counterarr,0);
        hashfunctions();
    }
    public void encode(int element){
        for(int i=0;i<numhash;i++){
            int xor=element^s[i];
            counterarr[xor%numcounter]++;
        }
    }
    public boolean lookup(int element){
        for(int i=0;i<numhash;i++){
            int xor=element^s[i];
            if (counterarr[xor%numcounter]>0) {
                continue;
            }
            else{
                return false;
            }
        }
        return true;
    }
    public void remove(int element){
        for(int i=0;i<numhash;i++){
            int xor=element^s[i];
            counterarr[xor%numcounter]--;
        }
    }
    public void hashfunctions(){
        for(int i=0;i<this.numhash;i++){
            s[i]=Math.abs(rand.nextInt());
        }
    }
}
class CodedBloomFilter{
    int numsets;
    int numelements;
    int numfilters;
    int numbits;
    int numhash;
    int []s;
    int [][] setarr;
    int [][] filterarr;
    Map<Integer, String> setnumbin;
    Random rand=new Random();
    CodedBloomFilter(int numsets,int numelements, int numfilters, int numbits, int numhash){
        this.numhash=numhash;
        this.numelements=numelements;
        this.numsets=numsets;
        this.numbits=numbits;
        this.numfilters =numfilters;
        this.s=new int[numhash];
        this.setarr=new int[numsets][numelements];
        this.filterarr=new int[numfilters][numbits];
        for(int i=0;i<numfilters;i++){
            Arrays.fill(filterarr[i],0);
        }
        this.setnumbin=new HashMap();
        for(int i=1;i<=numsets;i++){
            this.setnumbin.put(i,String.format("%"+String.valueOf(numfilters)+"s",Integer.toBinaryString(i)).replace(" ","0"));
        }
        hashfunctions();
    }

    public void encode(int element,int setnumber){
        String b=setnumbin.get(setnumber);
        for (int j = 0; j < b.length(); j++) {
            if (b.charAt(j) == '1') {
                for(int i=0;i<numhash;i++){
                    int xor=element^s[i];
                    filterarr[j][xor % numbits] = 1;
                }
            }}}
    public boolean lookup(int element,int setnumber){
        String b=setnumbin.get(setnumber);
        StringBuilder q=new StringBuilder();
        for (int j=0;j<numfilters;j++){
            boolean flag=true;
            for(int i=0;i<numhash;i++){
                int xor=element^s[i];
                if (filterarr[j][xor % numbits]==1) {
                    continue;
                }
                else{
                    flag=false;
                }
            }
            if(flag){
                q.append('1');
            }
            else{
                q.append('0');
            }

        }
        return q.toString().equals(b);
    }
    public void hashfunctions(){
        for(int i=0;i<this.numhash;i++){
            s[i]=Math.abs(rand.nextInt());
        }
    }
}


