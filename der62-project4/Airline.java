import java.util.*;
import java.io.*;
public class Airline{
    static AdjList list = new AdjList();
    public static void main(String [] args){
        Scanner in = null;
        boolean correct = false;
        Scanner next = new Scanner(System.in); 
        String la =null;
        while(!correct){
            System.out.println("Enter the file name (with .txt after): " );
            la = next.nextLine();
            try{
                in = new Scanner(new FileReader(la));
                correct = true;
            }
            catch(FileNotFoundException e){
                System.out.println("File not found");
            }
        }
        System.out.println();
        System.out.println("Welcome");
        int numCities = in.nextInt();
        int numRoutes = 0;
        in.nextLine();
        for(int i =0; i<numCities; i++){
            String asdf = in.nextLine();
            list.add(asdf, i);
        }
        while(in.hasNext()){
            numRoutes++;
            int cit1 = in.nextInt();
            int cit2 = in.nextInt();
            int dist = in.nextInt();
            double price = in.nextDouble();
            if(in.hasNext())
                in.nextLine();
            list.update(cit1-1,cit2-1,dist,price, cit1, cit2,'a');       
        }
        in.close();
        int choice =0;
        do{
            System.out.println("1.  Print all direct routes");
            System.out.println("2.  Display a minimum spanning tree");
            System.out.println("3.  Display the shortest route from one location to another");
            System.out.println("4.  Display the lowest cost route from one location to another");
            System.out.println("5.  Display the route with the least layovers from one location to another");
            System.out.println("6.  Display all routes under a given price");
            System.out.println("7.  Enter a new route");
            System.out.println("8.  Remove a route");
            System.out.println("9.  Exit the program");
            System.out.println();
            System.out.println("Enter the number of what you would like to do: ");
            choice = next.nextInt();
            next.nextLine();
            switch(choice){
                case 1:
                    list.print();
                    break;
                case 2:
                    list.getMST();
                    break;
                case 3:
                    System.out.println("Enter the name of the first city: ");
                    String cityy1 = next.nextLine();
                    System.out.println("Enter the name of the second city: ");
                    String cityy2 = next.nextLine();
                    list.getShortest(cityy1, cityy2, 'd');
                    break;
                case 4:
                    System.out.println("Enter the name of the first city: ");
                    String cittyy1 = next.nextLine();
                    System.out.println("Enter the name of the second city: ");
                    String cittyy2 = next.nextLine();
                    list.getShortest(cittyy1, cittyy2, 'p');
                    break;
                case 5:
                    System.out.println("Enter the name of the first city: ");
                    String ciittyy1 = next.nextLine();
                    System.out.println("Enter the name of the second city: ");
                    String ciittyy2 = next.nextLine();
                    list.getUShortest(ciittyy1, ciittyy2);
                    break;
                case 6:
                    System.out.println("Enter the max price: ");
                    int maxPrice = next.nextInt();
                    list.getUnderPrice(maxPrice);
                    break;
                case 7:
                    System.out.println("Enter the city the flight is leaving from: ");
                    String cit1 = next.nextLine();
                    System.out.println("Enter the city the flight is going to: ");
                    String cit2 = next.nextLine();
                    System.out.println("Enter the distance: ");
                    int dist = next.nextInt();
                    System.out.println("Enter the price: ");
                    double price = next.nextDouble();
                    list.update(cit1,cit2,dist,price, 'u');     
                    numRoutes++;
                    break;
                case 8:
                    System.out.println("Enter the city the flight is leaving from: ");
                    cit1 = next.nextLine();
                    System.out.println("Enter the city the flight is going to: ");
                    cit2 = next.nextLine();
                    list.remove(cit1,cit2); 
                    numRoutes--;      
                    break;
                case 9:
                try{
                    FileWriter writer = new FileWriter(la);
                    boolean [] [] written = new boolean[numCities][numCities];
                    writer.write(numCities + "\n");
                    String [] cities = list.returnCities();
                    for(int i=0; i<numCities; i++){
                        writer.write(cities[i] +"\n");
                    }
                    for(int i=0;i<numCities;i++){
                        String [] currRoutes = list.getRoutes(i);
                        for(int j =0;j<currRoutes.length;j++){
                            if(currRoutes[j] ==null) break;
                            String [] tempArray = currRoutes[j].split(" ");
                            int first = Integer.parseInt(tempArray[0])-1;
                            int second = Integer.parseInt(tempArray[1])-1;
                            if(!written[first][second]){
                                written[first][second] = true;
                                written[second][first]=true;
                                writer.write(currRoutes[j] + "\n");
                            }
                        }
                    }
                    writer.close();
                    }

                catch(IOException e){}
            }   
            
        }while(choice !=9);  
    }
}