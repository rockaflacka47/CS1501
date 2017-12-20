import java.util.*;
import java.io.*;
public class AptTracker{
    static PQ storePrice; 
    static PQ storeRoom;
    static int hashValue [] = new int[1637];
    public static void main(String [] args){
        Scanner in = new Scanner(System.in);
        storePrice = new PQ();
        storeRoom = new PQ();
        boolean repeat = false;
        System.out.println("Welcome to the Apartment Tracker");
        do{
            System.out.println("Please enter what you would like to do:");
            System.out.println("1.  Add an apartment");
            System.out.println("2.  Update an apartment");
            System.out.println("3.  Remove an apartment");
            System.out.println("4.  See the lowest price apartment");
            System.out.println("5.  See the highest square foot apartment");
            System.out.println("6.  See the lowest price apartment by city");
            System.out.println("7.  See the highest square foot apartment by city");
            System.out.println("8.  Exit");
            int choice = in.nextInt();
            in.nextLine();
            switch(choice){
                case 1:
                    String address;
                    int aptNum;
                    String city;
                    int zipcode;
                    double rent;
                    double sqFT;
                    System.out.println("Enter the address: ");
                    address = in.nextLine();
                    try{
                        System.out.println("Enter the apartment number: ");
                        aptNum = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the apartment number with only numbers: ");
                        aptNum = in.nextInt();
                        in.nextLine();
                    }

                    System.out.println("Enter the city: ");
                    city = in.nextLine();
                    try{
                        System.out.println("Enter the zipcode: ");
                        zipcode = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the zipcode with only numbers: ");
                        zipcode = in.nextInt();
                        in.nextLine();
                    }
                    try{
                        System.out.println("Enter the rent: ");
                        rent = in.nextDouble();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the rent with only numbers: ");
                        rent = in.nextDouble();
                        in.nextLine();
                    }
                    try{
                        System.out.println("Enter the square footage: ");
                        sqFT = in.nextDouble();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the square footage with only numbers: ");
                        sqFT = in.nextDouble();
                        in.nextLine();
                    }
                    storePrice.add(address, aptNum, city, zipcode, rent, sqFT, 'p');
                    
                    storeRoom.add(address, aptNum, city, zipcode, rent, sqFT, 'r');
                    break;
                case 2:
                    String adr;
                    int apNum;
                    int zip;
                    System.out.println("Please enter the street address: ");
                    adr = in.nextLine();
                    try{
                        System.out.println("Enter the apartment number: ");
                        apNum = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the apartment number with only numbers: ");
                        apNum = in.nextInt();
                        in.nextLine();
                    }
                    try{
                        System.out.println("Enter the zipcode: ");
                        zip = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the zipcode with only numbers: ");
                        zip = in.nextInt();
                        in.nextLine();
                    }
                    System.out.println("Would you like to update the price?(y/n): ");
                    String ans = in.nextLine().toLowerCase();
                    char todo = ans.charAt(0);
                    //double rent;
                    double rnt;
                    if(todo =='y'){
                        try{
                            System.out.println("Enter the new price: ");
                            rnt = in.nextDouble();
                            in.nextLine();
                        }
                        catch(InputMismatchException e){
                            in.nextLine();
                            System.out.println("Invalid format. Please enter the new price with only numbers: ");
                            rnt = in.nextDouble();
                            in.nextLine();
                        }
                        storePrice.update(adr, apNum, rnt, 'p');
                        //System.out.println("\n\n\n\n\n\n");
                        storeRoom.update(adr, apNum, rnt, 'r');
                    } 
                    
                    break;
                case 3:
                    //String address;
                    //int aptNum;
                    //int zipcode;
                    System.out.println("Please enter the street address: ");
                    address = in.nextLine();
                    try{
                        System.out.println("Enter the apartment number: ");
                        aptNum = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the apartment number with only numbers: ");
                        aptNum = in.nextInt();
                        in.nextLine();
                    }
                    try{
                        System.out.println("Enter the zipcode: ");
                        zipcode = in.nextInt();
                        in.nextLine();
                    }
                    catch(InputMismatchException e){
                        in.nextLine();
                        System.out.println("Invalid format. Please enter the zipcode with only numbers: ");
                        zipcode = in.nextInt();
                        in.nextLine();
                    }
                    System.out.println("Are you sure you would like to delete this entry?(y/n): ");
                    String dec = in.nextLine().toLowerCase();
                    char la = dec.charAt(0);
                    if(la =='y')
                    {
                        //System.err.println("deleting price");
                         storePrice.delete(address,aptNum, 'p');
                         //eSystem.err.println("Deleting room");
                         storeRoom.delete(address,aptNum,'r');
                    }
                    break;
                case 4:
                    try{
                        storePrice.getLowestRent();
                    }
                    catch(NullPointerException e){
                        System.out.println("You have no saved apartments");
                    }
                    break;
                case 5:
                    try{
                        storeRoom.getHighestRoom();
                    }
                    catch(NullPointerException e){
                        System.out.println("You have no saved apartments");
                    }
                    break;
                case 6:
                    String cit;
                    System.out.println("Enter the city");
                    cit = in.nextLine();
                    storePrice.getCityLowest(cit);
                    //storePrice.getCityHighest(cit);
                    break;
                case 7:
                    String cityy;
                    System.out.println("Enter the city");
                    cityy = in.nextLine();
                    //storePrice.getCityLowest(cit);
                    storeRoom.getCityHighest(cityy);
                    break;

            }
            if(choice == 8) break;
            char todo;
            do{
                try{
                System.out.println("Would you like to do anything else?(y/n):");
                String ans = in.nextLine().toLowerCase();
                todo = ans.charAt(0);
                if(todo == 'y' || todo =='n') break;
                }
                catch(Exception e){}
            }while(true);
            if(todo == 'y')
                repeat = true;
            else    
                repeat = false;
        }while(repeat);
    }
    
}