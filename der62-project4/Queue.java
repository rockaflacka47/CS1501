public class Queue{
    Edge []queue;
    int currIndexAdd;
    int currIndexGet;
    public Queue(){
        queue = new Edge [128];
        currIndexAdd=0;
        currIndexGet=0;
    }
    public void push(Edge city){
        if(currIndexAdd==(queue.length))
            resize();
        queue[currIndexAdd++] = city;
    }
    public Edge pop(){
        return queue[currIndexGet++];
    }
    public boolean isEmpty(){
        if(currIndexAdd==currIndexGet) return true;
        else return false;
    }
    public void resize(){
        Edge [] temp = new Edge[queue.length*2];
        System.arraycopy(queue, 0, temp, 0, queue.length);
        queue =temp;
    }
}