import java.io.*;
import java.util.*;
import java.security.*;
import java.math.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
public class MySign{
    public static void main(String [] args){
        if(args[0].toLowerCase().equals("s")){
            MessageDigest md;
            BigInteger D;
            BigInteger N;
            byte [] data;
            try{
                md = MessageDigest.getInstance("SHA-256");
                Path path = Paths.get(args[1]);
			    data = Files.readAllBytes(path);
                byte [] holdData = Files.readAllBytes(path);
                md.update(data);
			    // generate a hash of the file
			    byte[] digest = md.digest();
                BigInteger result = new BigInteger(1, digest);
                FileInputStream fis = new FileInputStream("privkey.rsa");
                ObjectInputStream ois = new ObjectInputStream(fis);
                D = (BigInteger)ois.readObject();
                N = (BigInteger)ois.readObject();
                result = result.modPow(D, N);
                ois.close();
                fis.close();
                FileOutputStream fos = new FileOutputStream(args[1]+".signed");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(holdData);
                oos.writeObject(result);
                oos.close();
                fos.close();

            }
            catch(Exception e){
                System.out.println(e.toString());
            } 
            
        }
        else if(args[0].toLowerCase().equals("v")){
            MessageDigest md;
            BigInteger E;
            BigInteger N;
            byte [] data;
            try{
                md = MessageDigest.getInstance("SHA-256");
                Path path = Paths.get(args[1]);
			    FileInputStream fis = new FileInputStream(args[1]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                data = (byte [])ois.readObject();
                BigInteger origHash = (BigInteger)ois.readObject();
                md.update(data);
			    // generate a hash of the file
			    byte[] digest = md.digest();
                BigInteger result = new BigInteger(1, digest);
                fis = new FileInputStream("pubkey.rsa");
                ois = new ObjectInputStream(fis);
                E = (BigInteger)ois.readObject();
                N = (BigInteger)ois.readObject();
                origHash = origHash.modPow(E, N);
                //System.out.println(result.toString());
                if(result.equals(origHash)){
                    System.out.println("Signed");
                }
                else{
                    System.out.println("Not Signed");
                }

            }
            catch(Exception e){
                System.out.println(e.toString());
            }
        }
    }
}