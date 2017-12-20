public class PQ{
    private Node [] heap;
    private int hashValue [];
    private int numApts =0;
    //initialize PQ
    public PQ(){
        heap = new Node[128];
        hashValue= new int[1637];
    }
    //Add a new value and check heap property based on if its price or room heap
    public void add(String address, int aptNum, String city, int zipcode, double rent, double sqFT, char which){
        int placement =0;
        for (int i =0; i<heap.length; i++){
            if(heap[i] == null){
                 placement =i;
                 break;
            }
            else if(heap[i] !=null && i ==heap.length-1){
                resize();
                placement = i+1;
                break;
            }
        }
        Node temp = new Node(address, aptNum, city, zipcode, rent, sqFT);
        heap[placement] = temp;
        Values tempy = new Values();
        if(which =='p')
            tempy = checkPropertyRent(0, placement, 0, tempy);
          
        
        else if(which == 'r')
            tempy = checkPropertyRoom(0, placement, 0, tempy);

        if(!tempy.hashed){
            hash(address, aptNum, placement);
        }
        
        numApts++;
    }
    //get the hashTable location
    private int getHash(String address, int tempNum){
        int temp = 0;
        String tempy = address + " " + tempNum;
        for(int i = 0; i<tempy.length(); i++){
            temp += ((int)tempy.charAt(i) +(256^(tempy.length()-i)));
        }
       
        temp = temp % hashValue.length;
        return temp;
    }
    //put the correct value at the hash table location using horners hash
    private void hash(String address, int tempNum, int loc){
        int temp = 0;
        String tempy = address + " " + tempNum;
        for(int i = 0; i<tempy.length(); i++){
            temp += ((int)tempy.charAt(i) +(256^(tempy.length()-i)));
        }
        temp = temp % hashValue.length;
        hashValue[temp] = loc;
    }
   //check heap property based on rent
    public Values checkPropertyRent(int currLoc, int locOfOriginal, int type, Values tempValue){
        
        if(heap[currLoc] ==null && type ==0) return tempValue;
        else{
            tempValue = checkPropertyRent(2*currLoc+1, locOfOriginal,0, tempValue);
            tempValue = checkPropertyRent(2*currLoc+2, locOfOriginal, 0, tempValue);
            try{
            if(heap[currLoc].rent<(heap[(currLoc-1)/2].rent)){
                if(currLoc == locOfOriginal){
                    tempValue.loc = (currLoc-1)/2;
                    tempValue.hashed = true;
                }
               
                    Node temp = heap[currLoc];
                    heap[currLoc] = heap[(currLoc-1)/2];
                    heap[(currLoc-1)/2] = temp;
                    int parentLocInHash = getHash(heap[((currLoc-1)/2)].address, heap[((currLoc-1)/2)].aptNum);
                    int currLocInHash = getHash(heap[currLoc].address, heap[currLoc].aptNum);
                    hashValue[parentLocInHash] = (currLoc-1)/2;
                    hashValue[currLocInHash]=currLoc; 
                }
            }
                catch(NullPointerException e){
                    heap[(currLoc-1)/2] = heap[currLoc];
                    int parentLocInHash = getHash(heap[((currLoc-1)/2)].address, heap[((currLoc-1)/2) ].aptNum);
                    hashValue[parentLocInHash] = (currLoc-1)/2;
                

            }
        return tempValue;
        
    }
    }
    //check heap property based on room
    public Values checkPropertyRoom(int currLoc, int locOfOriginal, int type, Values tempValue){
        
        if(heap[currLoc] ==null && type ==0) return tempValue;
        else{
            tempValue = checkPropertyRoom(2*currLoc+1, locOfOriginal,0, tempValue);
            tempValue = checkPropertyRoom(2*currLoc+2, locOfOriginal, 0, tempValue);
            try{
            if(heap[currLoc].sqFT>(heap[(currLoc-1)/2].sqFT)){
                if(currLoc == locOfOriginal){
                    tempValue.loc = (currLoc-1)/2;
                    tempValue.hashed = true;
                }
               
                    Node temp = heap[currLoc];
                    heap[currLoc] = heap[(currLoc-1)/2];
                    heap[(currLoc-1)/2] = temp;
                    int parentLocInHash = getHash(heap[((currLoc-1)/2)].address, heap[((currLoc-1)/2)].aptNum);
                    int currLocInHash = getHash(heap[currLoc].address, heap[currLoc].aptNum);
                    hashValue[parentLocInHash] = (currLoc-1)/2;
                    hashValue[currLocInHash]=currLoc; 
                }
            }
                catch(NullPointerException e){
                    heap[(currLoc-1)/2] = heap[currLoc];
                    int parentLocInHash = getHash(heap[((currLoc-1)/2) ].address, heap[((currLoc-1)/2) ].aptNum);
                    hashValue[parentLocInHash] = (currLoc-1)/2;
                }

            }
        return tempValue;
        
    }
    //resize the heap
    public void resize(){
        Node [] temp = new Node [heap.length*2];
        System.arraycopy(heap, 0, temp, 0, heap.length);
        heap =temp;
    }
    //update a value in the heap and recheck the property
    public void update(String address, int aptNum, double newRent, char choice){    
        int temp =0;
        int tempy = 0;
        temp = getHash(address,aptNum);
        tempy = hashValue[temp];
        heap[tempy].rent = newRent;
        Values tempy1 = new Values();
        if(choice == 'p')
            tempy1 = checkPropertyRent(0, tempy,0, tempy1);
    }
    //delete a value from the table
    public void delete(String address, int aptNum, char type){
            numApts--;
            if(numApts ==0)
            {
                heap = new Node[128];
                hashValue = new int[1637];
                return;
            }
            else if(numApts ==1){
                if(heap[0].address.equals(address)){
                    Node temp = heap[1];
                    heap = new Node[128];
                    hashValue = new int[1637];
                    heap[0] = temp;
                    hash(heap[0].address, heap[0].aptNum, 0);
                    return;
                }
                else if(heap[1].address.equals(address)){
                    Node temp = heap[0];
                    heap = new Node[128];
                    hashValue = new int[1637];
                    heap[0] = temp;
                    hash(heap[0].address, heap[0].aptNum, 0);
                    return;
                }
            }
            int placement =0;
            for (int i =0; i<heap.length; i++){
                if(heap[i] !=null && i ==heap.length-1){
                    resize();
                    placement = i+1;
                    break;
                }
                else if(heap[i] == null){
                    placement =i;
                    break;
                }
            }
            placement-=1;
            int temp = getHash(address, aptNum);
            int temp1 = hashValue[temp];
            if(temp!=placement){
                heap[temp1] = heap[placement];
                int tempy = hashValue[getHash(heap[placement].address, heap[placement].aptNum)];
                hashValue[tempy] = temp1;
                heap[placement] = null;
            }
            else{
                heap[temp1] = null;
            }

            if(type =='p')
                checkPropertyRent(0, temp1,1, null);
            else checkPropertyRoom(0, temp1, 1,null);
                    
    }
    //return top of price heap
    public void getLowestRent(){
            System.out.println("Address: " + heap[0].address);
            System.out.println("Apartment Number: " + heap[0].aptNum);
            System.out.println("City: " + heap[0].city);
            System.out.println("Zipcode: " + heap[0].zipcode);
            System.out.println("Rent: " + heap[0].rent);
            System.out.println("Square Feet: " + heap[0].sqFT);
     
    }
    //return top of room heap
    public void getHighestRoom(){
            System.out.println("Address: " + heap[0].address);
            System.out.println("Apartment Number: " + heap[0].aptNum);
            System.out.println("City: " + heap[0].city);
            System.out.println("Zipcode: " + heap[0].zipcode);
            System.out.println("Rent: " + heap[0].rent);
            System.out.println("Square Feet: " + heap[0].sqFT);
    }
    //search through the heap array and find/print the lowest price apt from a given city
    public void getCityLowest(String city){
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
}