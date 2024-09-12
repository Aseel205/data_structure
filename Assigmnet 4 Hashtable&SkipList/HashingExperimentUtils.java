import java.util.Collections; // can be useful
import java.util.LinkedList;
import java.util.List;

public class HashingExperimentUtils {
    final private static int k = 16;

    public static Pair<Double, Double> measureOperationsChained(double maxLoadFactor) {
        HashFactory hashFactory = new ModularHash();
        ChainedHashTable hashTable = new ChainedHashTable(hashFactory, k, maxLoadFactor);
        HashingUtils assistant = new HashingUtils();
        Integer[] inputs_array = assistant.genUniqueIntegers((int) Math.pow(2, 20));
        double begin;
        double sum = 0;
        double end;
        double num =  Math.pow(2, 16);
        for (double i = 0;i<num-1; i++) {
            begin = System.nanoTime();
            hashTable.insert(inputs_array[(int) i], 1);
            end = System.nanoTime();
            sum += end - begin;
        }
        double average_insertion_time = sum / hashTable.size;
        int j = (int) Math.pow(2, 20) - 1;
        double begin1;
        double sum1 = 0;
        double end1;

        int num_0f_seraches = (int) (Math.pow(2, 16) * maxLoadFactor);
        for (int i = 0; i < num_0f_seraches; i++) {
            begin1 = System.nanoTime();
            hashTable.search(inputs_array[i]);
            hashTable.search(inputs_array[j]);
            end1 = System.nanoTime();
            sum1 += end1 - begin1;
            j--;
        }
        double average_search_time = sum1 / (num_0f_seraches * 2);

        return new Pair<>(average_insertion_time, average_search_time);
    }

    public static Pair<Double, Double> measureOperationsProbing(double maxLoadFactor) {
        HashFactory hashFactory = new ModularHash();
        ProbingHashTable hashTable = new ProbingHashTable<>(hashFactory,k+2,  maxLoadFactor);
        HashingUtils assistant = new HashingUtils();
        Integer[] inputs_array = assistant.genUniqueIntegers((int) Math.pow(2, 20));
        double begin;
        double sum = 0;
        double end;
        double num =  Math.pow(2, 16);
        for (double i = 0; i <num-1; i++){
            begin = System.nanoTime();
            hashTable.insert(inputs_array[(int) i], 1);
            end = System.nanoTime();
            sum += end - begin;
        }
        double average_insertion_time = sum / hashTable.size;
        int j = (int) Math.pow(2, 20) - 1;
        double begin1;
        double sum1 = 0;
        double end1;
        int num_0f_seraches = (int) (Math.pow(2, 16) * maxLoadFactor);
        for (int i = 0; i < num_0f_seraches; i++) {
            begin1 = System.nanoTime();
            hashTable.search(inputs_array[i]);
            hashTable.search(inputs_array[j]);
            end1 = System.nanoTime();
            sum1 += end1 - begin1;
            j--;
        }
        double average_search_time = sum1 / (num_0f_seraches * 2);

        return new Pair<>(average_insertion_time, average_search_time);

    }

    public static Pair<Double, Double> measureLongOperations() {
        HashFactory hashFactory = new MultiplicativeShiftingHash();
        ChainedHashTable hashTable = new ChainedHashTable(hashFactory, k, 1);
        HashingUtils assistant = new HashingUtils();
        Long[] inputs_array = assistant.genUniqueLong((int) Math.pow(2, 20));
        double begin;
        double sum = 0;
        double end;
        double num =  Math.pow(2, 16);
        for (double i = 0; i <num-1; i++) {
            begin = System.nanoTime();
            hashTable.insert(inputs_array[(int) i], 1);
            end = System.nanoTime();
            sum += end - begin;
        }
        double average_insertion_time = sum / hashTable.size;
        int j = (int) Math.pow(2, 20) - 1;
        double begin1;
        double sum1 = 0;
        double end1;
        int num_0f_seraches = (int) (Math.pow(2, 16) * 1);
        for (int i = 0; i < num_0f_seraches; i++) {
            begin1 = System.nanoTime();
            hashTable.search(inputs_array[i]);
            hashTable.search(inputs_array[j]);
            end1 = System.nanoTime();
            sum1 += end1 - begin1;
            j--;
        }
        double average_search_time = sum1 / (num_0f_seraches * 2);

        return new Pair<>(average_insertion_time, average_search_time);

    }

    public static Pair<Double, Double> measureStringOperations() {
        HashFactory hashFactory = new StringHash();
        ChainedHashTable hashTable = new ChainedHashTable(hashFactory, k, 1);
        HashingUtils assistant = new HashingUtils();
        List<String> inputs_list = assistant.genUniqueStrings((int) Math.pow(2,20), 10, 20);
        double begin;
        double sum = 0;
        double end;
        Object[] inputs_array = inputs_list.toArray();
        double num =  Math.pow(2, 16);
        for (double i = 0; i <num-1; i++) {
            begin = System.nanoTime();
            hashTable.insert(inputs_array[(int) i], 1);
            end = System.nanoTime();
            sum += end - begin;
        }
        double average_insertion_time = sum / hashTable.size;
        int j = (int) Math.pow(2, 20) - 1;
        double begin1;
        double sum1 = 0;
        double end1;
        int num_0f_seraches = (int) (Math.pow(2, 16) * 1);
        for (int i = 0; i < num_0f_seraches; i++) {
            begin1 = System.nanoTime();
            hashTable.search(inputs_array[i]);
            hashTable.search(inputs_array[j]);
            end1 = System.nanoTime();
            sum1 += end1 - begin1;
            j--;
        }
        double average_search_time = sum1 /(num_0f_seraches * 2);

        return new Pair<>(average_insertion_time, average_search_time);

    }   }
