import org.w3c.dom.Node;

public class SkipListExperimentUtils {


    public static double measureLevels(double p, int x) {
        IndexableSkipList myskiplist = new IndexableSkipList(p);
        double ans = 0;
        for (int i = 0; i < x; i++)
            ans = ans + myskiplist.generateHeight();

        return ans / (double) x + 1;
    }


    public static int[] insertion_random_array(int size) {
        int[] array = new int[size];

        // Fill the array with values in order
        for (int i = 0; i < array.length; i++) {
            array[i] = 2 * i;
        }

        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        return array;
    }


    public static int[] search_random_Array(int size) {
        int[] array = new int[(2 * size)];

        // Fill the array with values in order
        for (int i = 0; i < array.length; i++) {
            array[i] = i;
        }

        // Shuffle the array using Fisher-Yates algorithm
        for (int i = array.length - 1; i > 0; i--) {
            int j = (int) (Math.random() * (i + 1));
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }
    // Example usage


    /*
     * The experiment should be performed according to these steps:
     * 1. Create the empty Data-Structure.
     * 2. Generate a randomly ordered list (or array) of items to insert.
     *
     * 3. Save the start time of the experiment (notice that you should not
     *    include the previous steps in the time measurement of this experiment).
     * 4. Perform the insertions according to the list/array from item 2.
     * 5. Save the end time of the experiment.
     *
     * 6. Return the DS and the difference between the times from 3 and 5.
     */
    public static Pair<AbstractSkipList, Double> measureInsertions(double p, int size) {
        IndexableSkipList ans = new IndexableSkipList(p);
        int[] values = insertion_random_array(size);
        double begin = System.nanoTime();

        for (int i : values)
            ans.insert(i);

        double end = System.nanoTime();

        return new Pair<>(ans, (end - begin) / size);
    }


    public static double measureSearch(AbstractSkipList skipList, int size) {
        int[] temp = search_random_Array(size);
        double begin = System.nanoTime();
        for (int i : temp)
            skipList.search(i);

        double end = System.nanoTime();
        return (end - begin) / size * 2;


    }

    public static double measureDeletions(AbstractSkipList skipList, int size) {
        int[] temp = insertion_random_array(size);
        AbstractSkipList.Node temp_node;
        double beggin;
        double end;
        double sum = 0;
        int counter = 0;
        for (int i : temp) {
            temp_node = skipList.search(i);
            if (temp_node != null) {
                counter++;
                beggin = System.nanoTime();
                skipList.delete(temp_node);
                end = System.nanoTime();
                sum += (end - beggin);
            }
        }
        return sum / counter;


    }

    public static void main(String[] args) {
    /*
        double sum1 = 0;
        double sum2 = 0;
        double sum3 = 0;
        int size=50000;

        for (int i = 0; i < 30; i++) {
            Pair test = (measureInsertions(0.9, size));
            sum1 += (double) test.second();
         sum2 += measureSearch((AbstractSkipList) test.first(),size);
         sum3+= measureDeletions((AbstractSkipList)test.first(),size);



        }
        System.out.println("insert =  " + sum1/30);
        System.out.println("search =  " + sum2/30);
        System.out.println("delete =  " + sum3/30);

    }

     */

     IndexableSkipList temp = new IndexableSkipList(0.5);
     for (int i=1 ;i<20; i++)
         temp.insert(i);
     for (int i=1;i<10;i++)
         temp.delete(temp.find(i));

         //System.out.println( "the real index "+  temp.search(5).index);



    }

}
