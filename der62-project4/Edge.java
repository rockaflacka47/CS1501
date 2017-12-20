public class Edge{
        int city1;
        int city2;
        int distance;
        double price;
        int weightToHere;
        public Edge(){
            city1=0;
            city2=0;
            distance=0;
            price =0;
            weightToHere=0;
        }
        public Edge(int cit1, int cit2, int weigh, double cost, int weight){
            city1=cit1;
            city2=cit2;
            distance=weigh;
            price=cost;
            weightToHere=weight;
        }
    }