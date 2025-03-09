public class MyHashMap<K, V> {
    private static final int Initial_Capacity = 125003;
    private static final double Load_Factor = 0.75;

    private myElement<K, V>[] hashTable;
    private int size; // number of total entries

    public MyHashMap() {
        hashTable = new myElement[Initial_Capacity];
        size = 0;
    }
    private int getHashTableIndex(K key) { // converts key to value
        return Math.abs(key.hashCode() % hashTable.length);
    }
    public void put(K key, V value) {
        int index = getHashTableIndex(key);

        if (hashTable[index] == null) { // if there is no element in this cell add new myElement
            hashTable[index] = new myElement<>(key, value);
            size++;
        } else { // if this cell is full add element to the next
            myElement<K, V> current = hashTable[index];
            while (current.next != null) {
                current = current.next; // find the null one
            }
            current.next = new myElement<>(key, value);
            size++;
        }

        if ((float) size / hashTable.length > Load_Factor) { // check the load factor
            resize();
        }
    }

    public V get(K key) {
        int index = getHashTableIndex(key);

        myElement<K, V> current = hashTable[index];
        while (current != null) {
            if (current.key.equals(key)) {
                return current.value;
            }
            current = current.next;
        }
        return null;
    }
    private void resize() {
        myElement<K, V>[] oldHashTable = hashTable;
        hashTable = new myElement[oldHashTable.length * 2 + 1];
        size = 0;

        for (myElement<K, V> element : oldHashTable) {
            while (element != null) {
                put(element.key, element.value);
                element = element.next;
            }
        }
    }

    private static class myElement<K, V> { // key value pairs
        K key;
        V value;
        myElement<K, V> next; // pointer for linked list

        public myElement(K key, V value) {
            this.key = key;
            this.value = value;
            this.next = null;
        }
    }


}
