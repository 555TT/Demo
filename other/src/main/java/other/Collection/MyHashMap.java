package other.Collection;

import static com.sun.xml.internal.fastinfoset.util.ValueArray.MAXIMUM_CAPACITY;

/**
 * 手写HashMap
 *
 * @author: wanghaoran1
 * @create: 2024-11-03
 */
public class MyHashMap<K, V> {

    //哈希表元素
    private Node<K, V[]> buckets;

    //负载因子
    private double LOAD_FACTOR = 0.75f;

    //哈希表大小
    private int size;

    //默认容量
    private int DEFAULT_CAPACITY = 16;

    public MyHashMap() {
    }

    public MyHashMap(int capacity) {

    }

    public static void main(String[] args) {
        System.out.println(tableSizeFor(6));
        System.out.println(tableSizeFor(4));
        System.out.println(tableSizeFor(3));
        System.out.println(tableSizeFor(30));
    }

    static final int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

    /**
     * 哈希表结点元素
     *
     * @param <K>
     * @param <V>
     */
    class Node<K, V> {
        private K key;
        private V value;
        private Node<K, V> next;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public Node(K key, V value, Node<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
