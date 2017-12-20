/*************************************************************************
 *  Compilation:  javac LZW.java
 *  Execution:    java LZW - < input.txt   (compress)
 *  Execution:    java LZW + < input.txt   (expand)
 *  Dependencies: BinaryIn.java BinaryOut.java
 *
 *  Compress or expand binary input from standard input using LZW.
 *
 *  WARNING: STARTING WITH ORACLE JAVA 6, UPDATE 7 the SUBSTRING
 *  METHOD TAKES TIME AND SPACE LINEAR IN THE SIZE OF THE EXTRACTED
 *  SUBSTRING (INSTEAD OF CONSTANT SPACE AND TIME AS IN EARLIER
 *  IMPLEMENTATIONS).
 *
 *  See <a href = "http://java-performance.info/changes-to-string-java-1-7-0_06/">this article</a>
 *  for more details.
 *
 *************************************************************************/

public class MyLZW {
    private static final int R = 256;        // number of input chars
    private static int L =512;       // number of codewords = 2^W
    private static int W = 9;         // codeword width

    public static void compress(String []args) {
        BinaryStdOut.write(args[1], 8); 
        String input = BinaryStdIn.readString();  
        TST<Integer> st = new TST<Integer>();
        for (int i = 0; i < R; i++)
            st.put("" + (char) i, i);
        int code = R+1;  // R is codeword for EOF

        int i = 0;
        
        boolean exists =false;
        double compressedData= 0;
        double uncompressedData=0;
        double oldRatio=0;
        double newRatio=0;
        double ratio=0;
        while (input.length() > 0) {
            String s = st.longestPrefixOf(input);  // Find max prefix match s.
            BinaryStdOut.write(st.get(s), W);      // Print s's encoding.
            int t = s.length();
            if(t<input.length()){
            if(args[1].equals("n")){
                if(code==L &&W<16){
                    W++;
                    L = (int)Math.pow(2, W);
                }
            }
            else if(args[1].equals("r")){
                if(code>=L &&W<16){
                    W++;
                    L = (int)Math.pow(2, W);
                }
                else if(W==16 &&code>=L){
                    st = new TST<Integer>();
                    for (int j = 0; j < R; j++)
                       st.put("" + (char) j, j);
                    code = R+1;  // R is codeword for EOF
                    W = 9;
                    L = (int)Math.pow(2, W);
                }
            }
            else if(args[1].equals("m")){
                if(exists){
                    ratio =oldRatio/newRatio;
                }
                 if(ratio > 1.1){
                     st = new TST<Integer>();
                    for (int j = 0; j < R; j++)
                       st.put("" + (char) j, j);
                    code = R+1;  // R is codeword for EOF
                    W = 9;
                    L = (int)Math.pow(2, W);
                    exists =false; 
                    uncompressedData=0;
                    compressedData=0;
                    oldRatio=0;
                    newRatio=0;
                    ratio =0;
                }
                if(code ==L &&W<16){
                    W++;
                      L = (int)Math.pow(2, W);
                }
                else if(W==16 &&code==L){
                    if(!exists){
                        oldRatio=newRatio;
                        ratio=oldRatio/newRatio;
                        exists=true;
                    }
                }
            }
            
            if (code < L)  {  // Add s to symbol table.
                st.put(input.substring(0, t + 1), code++);
            }
            }
            input = input.substring(t);            // Scan past s in input.
            uncompressedData+=s.length()*8;
            compressedData+=W;
            newRatio=uncompressedData/compressedData;
        }
         BinaryStdOut.write(R,W);
        BinaryStdOut.close();
    } 


    public static void expand() {
        char choice = (char)BinaryStdIn.readInt(8);
        String[] st = new String[65536];
        int i; // next available codeword value

        // initialize symbol table with all 1-character strings
        for (i = 0; i < R; i++)
            st[i] = "" + (char) i;
        st[i++] = "";                        // (unused) lookahead for EOF

        int codeword = BinaryStdIn.readInt(W);
        if (codeword == R) return;           // expanded message is empty string
        String val = st[codeword];

        boolean exists = false;
        double compressedData =0;
        double uncompressedData =0;
        double oldRatio=0;
        double newRatio=0;
        double ratio =0;
        while (true) {
         if(choice == 'n'){
                if(i==L &&W<16){
                W++;
                L = (int)Math.pow(2,W);
            }
         }
         else if(choice =='r'){
             if(i==L &&W<16){
                W++;
                L = (int)Math.pow(2,W);
            }
            else if(i==L &&W==16){
                W =9;
                L = (int) Math.pow(2,W);
                st = new String[65536];
                for (i = 0; i < R; i++)
                    st[i] = "" + (char) i;
                st[i++] = "";
            }
            }
         else if(choice=='m'){
                if(exists){
                    ratio =oldRatio/newRatio;
                }
                if(ratio>1.1){
                    st = new String[65536];
                    for (i = 0; i < R; i++)
                        st[i] = "" + (char) i;
                    st[i++] = "";          
                    W =9;
                    L =(int)Math.pow(2,W);
                    exists =false;
                    oldRatio=0;
                    newRatio=0;
                    ratio =0;
                    compressedData=0;
                    uncompressedData=0;
                    
                }
                if(i ==L &&W<16){
                    W++;
                   L = (int)Math.pow(2,W); 
                }
                else if(i==L &&W==16){
                    if(!exists){
                        oldRatio=newRatio;
                        ratio = oldRatio/newRatio;
                        exists =true;
                    }
                }
            }
            BinaryStdOut.write(val);
            codeword = BinaryStdIn.readInt(W);
            if (codeword == R) break;
            String s = st[codeword];
            if (i == codeword) s = val + val.charAt(0);   // special case hack
            if (i < L) {
                st[i++] = val + s.charAt(0);
            }
            uncompressedData+= (val.length()*8);
            compressedData+=W;   
            newRatio = uncompressedData/compressedData;
            val =s;
        
        }
        BinaryStdOut.close();
    }



    public static void main(String[] args) {
        if      (args[0].equals("-")) compress(args);
        else if (args[0].equals("+")) expand();
        else throw new IllegalArgumentException("Illegal command line argument");
    }

}