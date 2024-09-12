import java.util.Random;


public class StringHash implements HashFactory<String> {
    private int q;
    private int c;

    public StringHash() {
        HashingUtils temp = new HashingUtils();
        int temp_num=0;
        boolean stop =false;
        while (!stop) {
            temp_num = (int) temp.genLong(Integer.MAX_VALUE/2, Integer.MAX_VALUE);
            stop = temp.runMillerRabinTest(temp_num, 50);
        }
        this.q=temp_num;
        this.c= (int) temp.genLong(2,q);

    }

    @Override
    public HashFunctor<String> pickHash(int k) {
        return new Functor(k);
    }

    public class Functor implements HashFunctor<String> {
        final private HashFunctor<Integer> carterWegmanHash;
        final private int c;
        final private int q;
        public Functor(int k){
        StringHash temp= new StringHash();
        this.q= temp.q;
        this.c= temp.c;
        this.carterWegmanHash= new ModularHash().pickHash(k);
        }

        public int hash(String key) {
            int s=key.length();
            int sum =0;
            for (int i =0;i<s;i++)
             sum+= HashingUtils.mod( (int) key.charAt(i)*(HashingUtils.fastModularPower((long) c,(long) s-i, (long)q)),q);

            HashingUtils.mod(sum,q);

            int ans = carterWegmanHash.hash(sum);
            return ans;





        }

        public int c() {
            return c;
        }

        public int q() {
            return q;
        }

        public HashFunctor carterWegmanHash() {
            return carterWegmanHash;
        }
    }
}
