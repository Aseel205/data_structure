
import java.util.NoSuchElementException;
import java.util.ArrayList;
import java.util.List;

abstract public class AbstractSkipList {
    final protected Node head;
    final protected Node tail;

    public AbstractSkipList() {
        head = new Node(Integer.MIN_VALUE);
        tail = new Node(Integer.MAX_VALUE);
        increaseHeight();
    }

    public void increaseHeight() {
        head.addLevel(tail, null);
        tail.addLevel(null, head);
    }

    abstract Node find(int key);

    abstract public int generateHeight();

    public Node search(int key) {
        Node curr = find(key);

        return curr.key() == key ? curr : null;
    }

    public Node insert(int key) {
        int nodeHeight = generateHeight();
        int counter =0;
        while (nodeHeight > head.height()) {
            increaseHeight();
            counter++;
        }
        if (counter!=0)
            genrateskip_array ();

        Node prevNode = find(key);
        if (prevNode.key() == key) {
            return null;
        }

        Node newNode = new Node(key);

        for (int level = 0; level <= nodeHeight && prevNode != null; ++level) {
            Node nextNode = prevNode.getNext(level);

            newNode.addLevel(nextNode, prevNode);
            prevNode.setNext(level, newNode);
            nextNode.setPrev(level, newNode);

            while (prevNode != null && prevNode.height() == level) {
                prevNode = prevNode.getPrev(level);
            }
        }
        //////////////
        newNode.skip_array= new int[nodeHeight+1];
        newNode.height=nodeHeight;
        update_me(newNode);
        update_other(newNode);



        return newNode;
    }




    private void update_me(Node input) {
        int counter = 0;
        int level = 0;
        boolean stop = false;
        Node temp = input.getPrev(0);
        while (level < input.height() & !stop) {
            if (temp.height() > level) {
                level++;
                temp.next.get(level).skip_array[level] = counter;
            } else {
                counter = counter + 1 + temp.skip_array[level];

                if (temp.getPrev(level) == null)
                    stop = true;

                temp = temp.getPrev(level);
            }
        }
    }

private void genrateskip_array(){

   int[] ans= new int [head.height];
           for(int i =0;i< ans.length;i++)
               ans[i]=0;
        head.skip_array=ans;
}


    private void update_other(Node input) {
        int level = 0;

        Node temp_node=input.getNext(0);
        while (temp_node.key !=Integer.MAX_VALUE) {

            if (temp_node.height > level) {
                level++;
                if (temp_node.getPrev(level).key==input.key)
                    temp_node.skip_array[level]= temp_node.skip_array[level] - (input.skip_array[level]);
                else
                    temp_node.skip_array[level]++;
            }

            else temp_node = temp_node.getNext(level);

        }

    }

    private void update_other_delete(Node input) {
        int level = 0;

        Node temp_node = input.getNext(0);
        while (temp_node.key != Integer.MAX_VALUE) {

            if (temp_node.height > level) {
                level++;
                if (temp_node.getPrev(level).key == input.key)
                    temp_node.skip_array[level] += (input.skip_array[level]);
                else
                    temp_node.skip_array[level]--;
            }
            else temp_node = temp_node.getNext(level);


        }
    }
    public boolean delete(Node node) {
        ///////////////////////////////////////////////////////
       update_other_delete(node);
            //////////////////////////////////////////////////////////////////
        for (int level = 0; level <= node.height(); ++level) {
            Node prev = node.getPrev(level);
            Node next = node.getNext(level);
            prev.setNext(level, next);
            next.setPrev(level, prev);
        }

        return true;
    }

    public int predecessor(Node node) {
        return node.getPrev(0).key();
    }

    public int successor(Node node) {
        return node.getNext(0).key();
    }

    public int minimum() {
        if (head.getNext(0) == tail) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return head.getNext(0).key();
    }

    public int maximum() {
        if (tail.getPrev(0) == head) {
            throw new NoSuchElementException("Empty Linked-List");
        }

        return tail.getPrev(0).key();
    }

    private void levelToString(StringBuilder s, int level) {
        s.append("H    ");
        Node curr = head.getNext(level);

        while (curr != tail) {
            s.append(curr.key);
            s.append("    ");

            curr = curr.getNext(level);
        }

        s.append("T\n");
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int level = head.height(); level >= 0; --level) {
            levelToString(str, level);
        }

        return str.toString();
    }

    public static class Node {
        final private List<Node> next;
        final private List<Node> prev;
        private int height;
        final private int key;

        public int [] skip_array;




        public Node(int key) {
            next = new ArrayList<>();
            prev = new ArrayList<>();
            this.height = -1;
            this.key = key;
            this.skip_array = null;

        }

        public Node(int key,int hieght) {
            this(key);
            this.height=hieght;
            this.skip_array=new int[height+1];

        }

        public Node getPrev(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return prev.get(level);
        }

        public Node getNext(int level) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            return next.get(level);
        }

        public void setNext(int level, Node next) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.next.set(level, next);
        }

        public void setPrev(int level, Node prev) {
            if (level > height) {
                throw new IllegalStateException("Fetching height higher than current node height");
            }

            this.prev.set(level, prev);
        }

        public void addLevel(Node next, Node prev) {
            ++height;
            this.next.add(next);
            this.prev.add(prev);
        }

        public int height() {
            return height;
        }

        public int key() {
            return key;
        }
          public void skip(List<Node> temp) {




          }


    }
}