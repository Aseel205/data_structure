import java.util.List;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.Iterator;

public class ProbingHashTable<K, V> implements HashTable<K, V> {
    final static int DEFAULT_INIT_CAPACITY = 4;
    final static double DEFAULT_MAX_LOAD_FACTOR = 0.75;
    final private HashFactory<K> hashFactory;
    final private double maxLoadFactor;
    public double capacity;
    private HashFunctor<K> hashFunc;

    public double size;
    private Pair<K, V>[] array;

    int k;


    public ProbingHashTable(HashFactory<K> hashFactory) {
        this(hashFactory, DEFAULT_INIT_CAPACITY, DEFAULT_MAX_LOAD_FACTOR);
    }

    public ProbingHashTable(HashFactory<K> hashFactory, int k, double maxLoadFactor) {
        this.hashFactory = hashFactory;
        this.maxLoadFactor = maxLoadFactor;
        this.capacity = 1 << k;
        this.hashFunc = hashFactory.pickHash(k);
        this.size = 0;
        this.array = new Pair[(int) capacity];
        this.k = k;
        for (Pair e : array)
            e = null;


    }

    public V search(K key) {
        int index = hashFunc.hash(key);
        while (array[index] != null) {
            if (array[index].first() == key)
                return (V) array[index].second();
            else
                index = (int) HashingUtils.mod(index + 1, (int) capacity);

        }
        return null;
    }

    public void insert(K key, V value) {
     //   if (size / capacity >= maxLoadFactor)
    //        rehashing(k);

        int index = hashFunc.hash(key);
        Pair temp = new Pair(key, value);
        while (array[index] != null)
            index = (int) HashingUtils.mod(index + 1, (int) capacity);

        array[index] = temp;
        size++;

        if (size / capacity >= maxLoadFactor)
            rehashing(k);


    }


    public boolean delete(K key) {
        int index = hashFunc.hash(key);
        while (array[index] != null && array[index].first() != key)
            index = (int) HashingUtils.mod(index + 1, (int) capacity);

        if (array[index] == null)
            return false;

        array[index] = null;
        size--;
        index = (int) HashingUtils.mod(index + 1, (int) capacity);

        while (array[index] != null) {
            K temp_key = (K) array[index].first();
            V temp_value = (V) array[index].second();
            array[index] = null;
            insert(temp_key, temp_value);
            index = (int) HashingUtils.mod(index + 1, (int) capacity);
        }
        return true;
    }


    public HashFunctor<K> getHashFunc() {
        return hashFunc;
    }

    public int capacity() {
        return (int) capacity;
    }

    private void rehashing(int k) {
        this.hashFunc = hashFactory.pickHash(k + 1);
        this.k = k + 1;
        this.capacity = (int) Math.pow(2, this.k);
        int index;
        Pair<K, V>[] new_array = new Pair[(int) capacity];
        for (Pair e : new_array)
            e = null;

        Pair<K, V>[] old_array = this.array;
        this.array = new_array;
        this.size = 0;
        for (int i = 0; i < old_array.length; i++) {
              if (old_array[i]!=null)
            insert((K) old_array[i].first(), (V) old_array[i].second());
        }

    }
    
}
    