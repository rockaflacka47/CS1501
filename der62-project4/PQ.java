public class PQ{
    private Edge [] heap;
    private int numItems ;
    //initialize PQ
    public PQ(){
        heap = new Edge[128];
        numItems=0;
    }
    //Add a new value and check heap property based on if its price or room heap
    public void add(Edge weight, char choice){
        
        heap[numItems] = weight;
        if(choice =='p')
            checkPropertyPrice(numItems, 'a');
        else
            checkProperty(numItems, 'a');
        numItems++;
    }
    public void add(Edge weight){
        
        heap[numItems] = weight;
        numItems++;
    }
    
    
   //check heap property based on distance
    public void checkProperty(int currLoc, char choice){
        try{
        if(heap[currLoc] ==null) return;
        else{
            if(heap[currLoc].distance<(heap[(currLoc-1)/2].distance)){               
                    Edge temp = heap[currLoc];
                    heap[currLoc] = heap[(currLoc-1)/2];
                    heap[(currLoc-1)/2] = temp;
                    checkProperty((currLoc-1)/2,'a');
                    if(choice=='r'){
                        checkProperty((2*currLoc)+1,'r');
                        checkProperty((2*currLoc)+2,'r');
                    }
            }
        }
        }
        catch(NullPointerException e){
            heap[(currLoc-1)/2]=heap[currLoc];
        }
                

    }
      public void checkPropertyPrice(int currLoc, char choice){
        try{
        if(heap[currLoc] ==null) return;
        else{
            if(heap[currLoc].price<(heap[(currLoc-1)/2].price)){               
                    Edge temp = heap[currLoc];
                    heap[currLoc] = heap[(currLoc-1)/2];
                    heap[(currLoc-1)/2] = temp;
                    checkPropertyPrice((currLoc-1)/2,'a');
                    if(choice=='r'){
                        checkPropertyPrice((2*currLoc)+1,'r');
                        checkPropertyPrice((2*currLoc)+2,'r');
                    }
            }
        }
        }
        catch(NullPointerException e){
            heap[(currLoc-1)/2]=heap[currLoc];
        }
                

    }
    //resize the heap
    public void resize(){
        Edge [] temp = new Edge [heap.length*2];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap =temp;
    }
    public Edge getLowest(){
            Edge temp = heap[0];
            //System.out.println(placement);
            if(numItems!=0){
                heap[0]=heap[numItems-1];
                heap[numItems-1]=null;
                checkProperty(1,'r');
                checkProperty(2,'r');
                numItems--;
            }
            if(numItems==0){
                heap = new Edge[128];
                numItems=0;
            }
            return temp;

    }
    public boolean isEmpty(){
        if (heap[0] ==null) return true;
        else return false;
    }
    
    //search through the heap array and find/print the lowest price apt from a given city
   /* public void getCityLowest(String city){
        Node temp = null;
        boolean filled = false;
        for(int i =0; i<heap.length;i++){
            if(heap[i] == null){}
            else if(heap[i].city.equals(city) && !filled){
                temp = heap[i];
                filled = true;
            }
            else if(heap[i].city.equals(city) && heap[i].rent < temp.rent){
                temp = heap[i];
            }
        }
        if(temp != null){
            System.out.println("Address: " + temp.address);
            System.out.println("Apartment Number: " + temp.aptNum);
            System.out.println("City: " + temp.city);
            System.out.println("Zipcode: " + temp.zipcode);
            System.out.println("Rent: " + temp.rent);
            System.out.println("Square Feet: " + temp.sqFT);
        }
         else{
            System.out.println("No apartments found in city: " + city);
        }
    }
     //search through the heap array and find/print the highest room apt from a given city
    public void getCityHighest(String city){
        Node temp = null;
        boolean filled = false;
        for(int i =0; i<heap.length;i++){
            if(heap[i] == null){}
            else if(heap[i].city.equals(city) && !filled){
                temp = heap[i];
                filled = true;
            }
            else if(heap[i].city.equals(city) && heap[i].sqFT > temp.sqFT){
                temp = heap[i];
            }
        }
        if(temp !=null){
            System.out.println("Address: " + temp.address);
            System.out.println("Apartment Number: " + temp.aptNum);
            System.out.println("City: " + temp.city);
            System.out.println("Zipcode: " + temp.zipcode);
            System.out.println("Rent: " + temp.rent);
            System.out.println("Square Feet: " + temp.sqFT);
        }
        else{
            System.out.println("No apartments found in city: " + city);
        }
    }
    public class Node{
        String address;
        int aptNum;
        String city;
        int zipcode;
        double rent;
        double sqFT;
        public Node(){
            address = null;
            aptNum =0;
            city = null;
            zipcode = 0;
            rent =0;
            sqFT =0;
        }
        public Node(String adr, int apt, String c, int zip, double rnt, double sq){
            address = adr;
            aptNum =apt;
            city = c;
            zipcode = zip;
            rent =rnt;
            sqFT =sq;
        }
    }
    public class Values{
        int loc;
        boolean hashed;
        public Values(){
            loc =0;
            hashed =false;
        }
        public Values(int toLoc, boolean wasHashed){
            loc = toLoc;
            hashed = wasHashed;
        }
    }
    */
}