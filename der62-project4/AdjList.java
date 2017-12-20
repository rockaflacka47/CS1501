public class AdjList{
    Node [] list;
    int numItems;
    int numEdges;
    public AdjList(){
        list = new Node[128];
        numItems=0;
        numEdges=0;
    }
    public void add(String city, int loc){
        numItems++;
        if(numItems>list.length)
            resize();
        Node temp = new Node(city, loc+1, new Edge());
        list[loc] = temp;
    }
    public void getMST(){
        //calls another method to make the mst then prints it
        System.out.println();
        PQ queue = new PQ();
        boolean [] visited = new boolean[numItems];
        Edge [] MST = new Edge[numItems-1];
        MST = makeMST(MST, visited, list[0], queue,0);
        for(int i=0;i<MST.length;i++){
            System.out.println(list[MST[i].city1].city+","+list[MST[i].city2].city+": "+MST[i].distance);
        }
        System.out.println();
        
    }
    public String [] getRoutes(int index){
        //returns all routes from a given index
        String [] ret = new String [numItems];
        Node curr = list[index];
        Node temp = curr;
        int la =0;
        while (temp.next!=null){
            temp = temp.next;
            String bleh = curr.loc +" " + temp.loc + " " + temp.distance + " " + temp.cost;
            ret[la] = bleh;
            la++;
        }
        return ret;
    }
    public String [] returnCities(){
        //returns a string of the cities
        String [] ret = new String[numItems];
        for(int i=0;i<numItems;i++){
            ret[i]=list[i].city;
        }
        return ret;
    }
    public Edge[] makeMST(Edge[] MST, boolean []visited, Node curr, PQ queue, int count){
        //implements prims to create the mst
        while(count<numItems-1){
            if(!visited[curr.loc-1]){
                visited[curr.loc-1]=true;
                Node temp=curr;
                while(temp.next!=null){
                    temp = temp.next;
                    queue.add(temp.edge, 'a');
                }
                Edge minEdge = queue.getLowest();
                
                while(visited[minEdge.city2]){
                    minEdge=queue.getLowest();
                }
                MST[count]=minEdge;
                curr = list[minEdge.city2];
                count++;
            }
            
        }
        
        return MST;
    }
    public void getShortest(String citty1, String citty2, char choice){
        System.out.println();
        int city1 = 0;
        int city2=0;
        for(int i=0; i<numItems;i++){
            if(city1 !=0 && city2 != 0)break;
            if(list[i].city.equals(citty1)) city1 = list[i].loc-1;
            if(list[i].city.equals(citty2)) city2 = list[i].loc-1;
        }
        PQ queue = new PQ();
        boolean [] visited = new boolean[numItems];
        SPath MST = new SPath();
        SPath []tracker = new SPath[numItems];
        for(int i=0;i<numItems;i++)
            tracker[i]=new SPath();
        tracker[city1].prevLoc =2147483647;
        tracker[city1].weightToHere=0;
        //after creating all needed variables calls another method that implements dijkstras algorithm.
        tracker = makeShortest(tracker,visited, list[city1], city1,queue,0, city2, choice);
        int count =city2;
        int totDistance = 0;
        if(choice=='p')
            System.out.println("Lowest cost from: " +list[city1].city + " to: "+ list[city2].city + " is $" + tracker[city2].weightToHere);
        else
            System.out.println("Shortest distance from: " +list[city1].city + " to: "+ list[city2].city + " is " + tracker[city2].weightToHere);
        System.out.println("Path in reverse order: ");
        double totPrice=0;
        while(tracker[count].prevLoc!=2147483647){
            System.out.println(list[count].city);
            count = tracker[count].prevLoc;
        }
        System.out.println(list[count].city);
        System.out.println();
    }
    public SPath [] makeShortest(SPath [] tracker,boolean []visited, Node curr, int current,PQ queue, int count, int dest, char choice){
       //implements dijkstras algorithm
        do{
            if(curr==list[dest])break;
            if(!visited[curr.loc-1]){
                visited[curr.loc-1]=true;
            Node temp = curr;
            while(temp.next!=null){
                temp=temp.next;
                if(choice=='p'){
                    if((int)temp.edge.price+tracker[curr.loc-1].weightToHere<tracker[temp.loc-1].weightToHere){
                        tracker[temp.loc-1].weightToHere=(int)temp.edge.price+tracker[curr.loc-1].weightToHere;
                        tracker[temp.loc-1].prevLoc = curr.loc-1;
                    }
                }
                else{
                    if(temp.edge.distance+tracker[curr.loc-1].weightToHere<tracker[temp.loc-1].weightToHere){
                        tracker[temp.loc-1].weightToHere=temp.edge.distance+tracker[curr.loc-1].weightToHere;
                        tracker[temp.loc-1].prevLoc = curr.loc-1;
                    }
                }
                queue.add(temp.edge, choice);
                }
                Edge minEdge = queue.getLowest();
                while(visited[minEdge.city2]){
                    if(queue.isEmpty()) break;
                    minEdge=queue.getLowest();
                }
                curr = list[minEdge.city2];
                count++;
            }

        }while(!queue.isEmpty());
        return tracker;
    }
    public void getUShortest(String citty1, String citty2){
        //supposed to create a route of the fewest hops. The previous location does not get set correctly so route is found but when printed is incorrect
        System.out.println();
        int city1 = 0;
        int city2=0;
        for(int i=0; i<numItems;i++){
            if(city1 !=0 && city2 != 0)break;
            if(list[i].city.equals(citty1)) city1 = list[i].loc-1;
            if(list[i].city.equals(citty2)) city2 = list[i].loc-1;
        }
        boolean [] visited = new boolean[numItems];
        Queue queue = new Queue();
        int count =0;
        Node curr = list[city1];
        Node temp = list[city1];
        Edge minEdge=null;
        int lengthPath =0;
        do{
            temp =curr;
            visited[temp.loc-1]=true;
            while(temp.next!=null){
                temp = temp.next;
                ///\System.out.println(temp.edge.city1 + " " + temp.edge.city2);
                queue.push(temp.edge);
            }
            minEdge=queue.pop();
            while(visited[minEdge.city2]){
                minEdge=queue.pop();
                if(minEdge.city2==city2) break;
            }
            lengthPath++;
            curr = list[minEdge.city2];
            if(minEdge.city2==city2) break;  
        }while(!queue.isEmpty());
        int numHops =0;
        int bleh =0;
        while(bleh < lengthPath){
            System.out.println(curr.city + " ");
            System.out.println(city1 + " " +city2);
            curr = list[curr.edge.city1];
            numHops++;
            bleh++;
        }
        System.out.println(temp.city + " Number of hops: " + numHops);
        System.out.println();

    }
    public void getUnderPrice(int highestPrice){
        //supposed to implement a brute force search of all the routes but does not work fully.Only prints some routes and repeats some
        for(int i=0; i<numItems; i++){
            boolean [] visited = new boolean [numItems];
            double currPrice =0;
            int currIndex =1;
            Node [] path = new Node[numItems];
            path[0] = list[i];
            Node origLoc = list[i];
            visited[origLoc.loc-1] = true;
            Node temp = origLoc;
            Node temp2 = origLoc;
              int numJumps =0;
            while(temp.next !=null){
                temp = temp.next;
                if(temp2.next!=null)
                    temp2=temp2.next;
                if(currPrice+temp.cost < highestPrice){
                    if(!visited[temp.loc-1]){
                        currPrice+= temp.cost;
                        path[currIndex++]=temp;
                        visited[temp.loc-1]=true;
                        temp = list[temp.loc-1];
                        numJumps++;
                        if(path[1]!=null){
                        for(int j =0; j<path.length;j++){
                            if(path[j]==null){
                                break;
                            }
                            System.out.print(path[j].city + " ");
                        }
                        System.out.println(currPrice);
                        System.out.println();
                    }
                    }
                }
                else if(temp.next==null){
                    if(path[1]!=null){
                        int tempcurrPrice =0;
                        for(int j =0; j<path.length;j++){
                            if(path[j]==null){
                                tempcurrPrice-=path[j-1].cost;
                                break;
                            }
                            System.out.print(path[j].city + " ");
                        }
                        System.out.println(currPrice);
                        currPrice=tempcurrPrice;
                        System.out.println();
                    }
                    temp = temp2;
                    currIndex--;
                }
                /*else{
                    if(path[1]!=null){
                        int la =0;
                        int count =0;
                        for(int j =0; j<path.length;j++){
                            if(path[j]==null){
                                la=j-1;
                                break;
                            }
                            System.out.print(path[j].city + " ");
                            count++;
                        }
                        for(int j=la; j>numJumps; j--){
                            currPrice= currPrice - path[j].cost;
                        }
                        System.out.println(currPrice);
                        System.out.println();
                    }
                    currIndex-=numJumps;
                    temp = temp2;
                }
            }
        }*/
            }
        }
    }

     public void update(int city1, int city2, int distance, double cost, int locCit1, int locCit2, char choice){
        numEdges++;
        Edge edge = new Edge(city1, city2, distance, cost,2147483647);
        Node temp = new Node(list[city2].city, distance, edge, cost, null, locCit2);
        Node temp2 = list[city1];
        while(temp2.next !=null){
            temp2 = temp2.next;
        }
        //adds children of the original locations with all the information of the route
        temp2.next = temp;
        edge = new Edge(city2, city1, distance, cost,2147483647);
        temp = new Node(list[city1].city, distance, edge, cost, null, locCit1);
        temp2 = list[city2];
        while(temp2.next !=null){
            temp2 = temp2.next;
        }
        temp2.next = temp;
        if(choice =='u'){
            System.out.println();
            System.out.println("Bi-directional route added between: \n" + list[city1].city +" and " + list[city2].city);
            System.out.println("Distance: " + distance + " Cost: " + cost);
            System.out.println();
        }
    }
    public void update(String citty1, String citty2, int distance, double cost, char choice){
        numEdges++;
       int city1 = 0;
        int city2=0;
        int locCit1 = 0;
        int locCit2 =0;
        for(int i=0; i<numItems;i++){
            if(city1 !=0 && city2 != 0)break;
            if(list[i].city.equals(citty1)) {
                city1 = list[i].loc-1;
                locCit1 = list[i].loc;
            }
            if(list[i].city.equals(citty2)){
                 city2 = list[i].loc-1;
                locCit2=list[i].loc;
            }
        }
        //after finding the locs of the cities it creates a new child with all of the information of both cities original locs
        Edge edge = new Edge(city1, city2, distance, cost,2147483647);
        Node temp = new Node(list[city2].city, distance, edge, cost, null, locCit2);
        Node temp2 = list[city1];
        while(temp2.next !=null){
            temp2 = temp2.next;
        }
        temp2.next = temp;
        edge = new Edge(city2, city1, distance, cost,2147483647);
        temp = new Node(list[city1].city, distance, edge, cost, null, locCit1);
        temp2 = list[city2];
        while(temp2.next !=null){
            temp2 = temp2.next;
        }
        temp2.next = temp;
        if(choice =='u'){
            System.out.println();
            System.out.println("Bi-directional route added between: \n" + list[city1].city +" and " + list[city2].city);
            System.out.println("Distance: " + distance + " Cost: " + cost);
            System.out.println();
        }
    }
    public void remove(String citty1, String citty2){
        numEdges--;
        int city1 = 0;
        int city2=0;
        for(int i=0; i<numItems;i++){
            if(city1 !=0 && city2 != 0)break;
            if(list[i].city.equals(citty1)) city1 = list[i].loc-1;
            if(list[i].city.equals(citty2)) city2 = list[i].loc-1;
        }
        //after finding the locs of the cities removes the routes from both
        Node temp =list[city1];
        Node temp2 = null;
        if(list[city1].next !=null){
            temp2 = list[city1].next;
            while(!temp2.city.equals(list[city2].city)){
                temp = temp.next;
                temp2 = temp2.next;
            }
            temp.next = temp2.next;
        }
        else    
            temp.next =null;
        temp = list[city2];
        if(list[city2].next !=null){
            temp2 = list[city2].next;
            while(!temp2.city.equals(list[city1].city)){
                temp=temp.next;
                temp2 = temp2.next;
            }
            temp.next = temp2.next;
        }
        else
            temp.next =null;
        System.out.println();
        System.out.println("Bi-directional route between " + list[city1].city + " and "+list[city2].city + " removed.");
        System.out.println();
    }
    public void print(){
        for(int i=0; i<list.length;i++){
            if(list[i]==null) break;
            else{
                Node temp = list[i];
                while(temp.next!=null){
                    temp = temp.next;
                    System.out.println("From : " + list[i].city);
                    System.out.println("To: " + temp.city);
                    System.out.println("Distance: " +temp.distance);
                    System.out.println("Price: $" +temp.cost);
                    System.out.println();
                }
            }
        }
    }
    public void resize(){
        Node [] temp = new Node [list.length*2];
        System.arraycopy(list, 0, temp, 0, list.length);
        list =temp;
    }
    private class Node{
        int loc;
        String city;
        int distance;
        Edge edge;
        double cost;
        Node next;
        public Node(){
            loc =0;
            city = null;
            distance =0;
            edge =null;
            cost =0;
            next =null;
        }
        public Node(String cit, int location, Edge ed){
            loc =location;
            city = cit;
            distance =0;
            edge =ed;
            cost =0;
            next =null;
        }
        public Node(String cit, int dist, Edge edg,double price, Node temp, int location){
            loc = location;
            city = cit;
            distance = dist;
            edge = edg;
            cost =price;
            next=temp;
        }
    }
    
}