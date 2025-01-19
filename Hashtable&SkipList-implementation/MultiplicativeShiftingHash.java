import java.util.Random;
public class MultiplicativeShiftingHash implements HashFactory<Long> {
    final Integer a;
    public MultiplicativeShiftingHash() {
        HashingUtils temp = new HashingUtils();
        Integer[] array = temp.genUniqueIntegers(1);
        this.a=array[0];
    }

    @Override
    public HashFunctor<Long> pickHash(int k) {
            return new Functor(k);
        }

    public class Functor implements HashFunctor<Long> {
        final public static long WORD_SIZE = 64;
        final private long a;
        final private long k;

        public Functor(int k){
            MultiplicativeShiftingHash temp =new MultiplicativeShiftingHash();
            this.a=temp.a;
            this.k=k;

        }

        @Override
        public int hash(Long key) {
            return  (int) (a *key) >>> (WORD_SIZE- k);
        }

        public long a() {
            return a;
        }

        public long k() {
            return k;
        }
    }
}
