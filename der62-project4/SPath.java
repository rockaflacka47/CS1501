public class SPath{
    int prevLoc;
    int weightToHere;
    public SPath(){
        prevLoc=0;
        weightToHere=2147483647;
    }
    public SPath(int loc, int weight){
        prevLoc=loc;
        weightToHere=weight;
    }
}