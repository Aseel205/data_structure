import java.util.*;

public class ChainedHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 2;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    public double capacity;
    private HashFunctor<K> hashFunc;

    private LinkedList<Pair<K,V>>[] array;
    double size;
    int k;


    /*
     * You should add additional private members as needed.
     */

    public ChainedHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ChainedHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = (int) Math.pow(2,k);
        this.hashFunc = hashFactory.pickHash(k);
        this.array = new LinkedList[(int) capacity];
        for (int i = 0; i < capacity; i++)
            array[i] = new LinkedList();
        this.size = 0;
        this.k = k;
    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        Iterator iter = array[index].iterator();
        while (iter.hasNext()) {
            Pair curr = (Pair) iter.next();
            if (curr.first() .equals( key))
                return (V) curr.second();
        }
        return null;
    }

    public void insert(K key, V value) {
        int index = hashFunc.hash(key);
        Pair temp = new Pair(key,value);
        array[index].add(temp);
        size++;
          if((size/ capacity)>=maxLoadFactor)
        insert_rehashing();

    }

  public boolean delete(K key) {
        int index = hashFunc.hash(key);
        Iterator iter = array[index].iterator();
        Pair curr;
        while (iter.hasNext()) {
            curr = (Pair) iter.next();
            if (curr.first().equals( key)) {
                array[index].remove(curr);
                size--;

             //   if((size/ capacity)<=0.25*maxLoadFactor)
               //     delete_rehashing();
                return true;
            }

        }
        return false;
    }




  

private void insert_rehashing() {
        this.hashFunc = hashFactory.pickHash(k + 1);
        this.k = k + 1;
        this.capacity = (int) Math.pow(2, k);
        int index;
        Pair curr;
        LinkedList<Pair<K, V>>[] new_array = new LinkedList[(int) capacity];
        for (int i = 0; i < capacity; i++) {
            new_array[i] = new LinkedList<Pair<K, V>>();
        }

        for (int i = 0; i < array.length; i++) {
            Iterator<Pair<K, V>> iter = array[i].iterator();
            while (iter.hasNext()) {
                curr = iter.next();
                index = hashFunc.hash((K) curr.first());
                new_array[index].addFirst((Pair<K, V>) new Pair<>(curr.first(), curr.second()));
            }
        }
        this.array = new_array;
    }





 