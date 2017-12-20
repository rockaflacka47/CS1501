import java.util.*;
import java.io.*;
import java.math.*;
public class MyKeyGen{
    private static String [] lala = {"17", "31", "53", "71"};
    public static void main(String [] args){
        BigInteger P = getPrime();
        if(!checkPrime(P)){
            boolean temp = false;
            do{
                P = getPrime();
                temp = checkPrime(P);
            }while(!temp);
        }
        BigInteger Q = getPrime();
        if(!checkPrime(Q)){
            boolean temp = false;
            do{
                Q = getPrime();
                temp = checkPrime(Q);
            }while(!temp);
        }

       BigInteger N = P.multiply(Q);
       byte [] temp = {1};
       BigInteger tempy = new BigInteger(temp);
       BigInteger PHIN = ((P.subtract(tempy)).multiply((Q.subtract(tempy))));
       BigInteger E = new BigInteger(lala[0]);
       for(int i=1; i<4; i++){
        if(verify(E, PHIN)) break;
        E = new BigInteger(lala[i]);
       }
       byte [] temmp = {-1};
       tempy = new BigInteger(temmp);
       BigInteger D = (E.modPow(tempy, PHIN));
       /*System.out.println("P: " + P);
       System.out.println("Q: " + Q);
       System.out.println("N: " + N);
       System.out.println("PHIN: " + PHIN);
       System.out.println("E: " + E);
       System.out.println("D: " + D);*/
       try{
        FileOutputStream fos = new FileOutputStream("pubkey.rsa");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(E);
        //oos.writeBytes("\n");
        oos.writeObject(N);
        oos.close();
        fos = new FileOutputStream("privkey.rsa");
        oos = new ObjectOutputStream(fos);
        oos.writeObject(D);
       // oos.writeBytes("\n");
        oos.writeObject(N);
        oos.close();

       }
       catch(Exception e){

       }
    }
    public static boolean verify(BigInteger E, BigInteger PHIN){
        byte [] temp = {0};
        BigInteger tempy = new BigInteger(temp);
        if(PHIN.mod(E).equals(temp)) return false;
        else return true;
    }
    public static boolean checkPrime(BigInteger temp){
        for(int i=2;i<9;i++){
             byte [] tempa = {(byte)i};
            BigInteger tempy = new BigInteger(tempa);
            if(temp.mod(tempy).equals(0)) return false;
        }
        return true;
    }
    public static BigInteger getPrime(){
        BigInteger temp = BigInteger.probablePrime(512, new Random());
        return temp;
    }
}