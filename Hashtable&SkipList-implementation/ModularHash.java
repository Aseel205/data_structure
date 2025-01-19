import java.math.BigInteger;
import java.util.Random;

public class ModularHash implements HashFactory<Integer> {
    protected long p;
    protected int a;
    protected int b;
    public ModularHash() {
        HashingUtils temp = new HashingUtils();
        Integer[] array = temp.genUniqueIntegers(2);
        this.a = array[0];
        this.b = array[1];
        boolean stop = false;
        long temp_num=0;
        while (!stop) {
            temp_num = temp.genLong(Integer.MAX_VALUE + 1, Long.MAX_VALUE);
            stop = temp.runMillerRabinTest(temp_num, 50);
        }
        this.p= temp_num;
    }


    public HashFunctor<Integer> pickHash(int k) {
        int m =(int) Math.pow(2,k);
            return new Functor(m);

            }

    public class Functor implements HashFunctor<Integer> {
        final private int a;
        final private int b;
        final private Long  p;
        final private int m;

        public Functor(int m) {
            ModularHash temp = new ModularHash();
            this.a = temp.a;
            this.b = temp.b;
            this.p = temp.p;
            this.m = m;

        }


        public int hash(Integer key) {
            return (int) (HashingUtils.mod(HashingUtils.mod((a * key + b), p), m));
        }

        public int a() {
            return a;
        }

        public int b() {
            return b;
        }

        public Long  p() {
            return p;
        }

        public int m() {
            return m;
        }
    }
}
