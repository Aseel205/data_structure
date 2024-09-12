import org.w3c.dom.Node;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

public class MyDataStructure {

  IndexableSkipList skip_list;
   ChainedHashTable hashTable;
    int size;
    public MyDataStructure(int N) {
     HashFactory temp= new ModularHash();
     this.skip_list=new IndexableSkipList(0.5);
     hashTable = new ChainedHashTable<>(temp,power_of_N(N),1);




    }
private int power_of_N (int N) {

    int ans = 0;
    while (N >= 1){
        N = N / 2;
          ans++;
}
    return ans;
}
    /*
     * In the following functions,
     * you should REMOVE the place-holder return statements.
     */
    public boolean insert(int value) {
        boolean ans=true;

        if (hashTable.delete(value))
            ans=false;

        AbstractSkipList.Node node = (AbstractSkipList.Node) skip_list.insert(value);
        hashTable.insert(value,node);

        return ans;


    }

    public boolean delete(int value) {

        boolean ans = false;

        AbstractSkipList.Node node = (AbstractSkipList.Node) hashTable.search(value);
        if (hashTable.delete(value)) {
            skip_list.delete((AbstractSkipList.Node) node);
            ans = true;
        }
        return ans;
    }

    public boolean contains(int value) {
        return !(hashTable.search(value) ==null);


    }

    public int rank(int value) {
        return skip_list.rank(value);
    }

    public int select(int index) {
        return skip_list.select(index);
    }

    public List<Integer> range(int low, int high) {
        if (contains(low)) {
            LinkedList ans = new LinkedList();

            AbstractSkipList.Node node = (AbstractSkipList.Node) hashTable.search(low);

            while (node.key() <= high) {
                ans.addLast(node.key());
                node = node.getNext(0);

            }
            return ans;
        }
        return null;
    }

    public static void main(String[] args) {

MyDataStructure ds= new MyDataStructure(300);
for (int i =1 ;i<250;i++)
    ds.insert(i);
ds.delete(21);
LinkedList temp= (LinkedList) ds.range(-5,30);
        Iterator iter = temp.iterator();
while (iter.hasNext())
    System.out.println(iter.next());

    }
    }
