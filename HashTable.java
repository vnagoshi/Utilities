import java.util.LinkedList;

/**
 * HashTable, Defines a HashTable of E keys and K values.
 * @author Nagoshi, Vincent
 */
public class HashTable <E, K> {

  /**
   * Defines a key-value pair.
   */
  private class keyPair {
    E key;
    K val;

    /**
     * Constructor for a key-value pair.
     * @param key, the key associated with the key-value pair.
     * @param val, the value associated with the key value pair.
     */
    private keyPair (E key, K val) {
      this.key = key;
      this.val = val;
    }
  }

  @SuppressWarnings("unchecked")
  private LinkedList<keyPair>[] table = new LinkedList[1000];

  /**
   * Puts a key-value pair into the HashTable.
   * @param key, the key associated with the key-value pair.
   * @param val, the value associated with the key value pair.
   * @return the value previously associated with the key.
   * @throws NullPointerException if key or val is null.
   */
  public K put(E key, K val) {
    if (key == null || val == null) {
      throw new NullPointerException();
    }
    int index = keyIndex(key);
    if (table[index] == null) {
      table[index] = new LinkedList<keyPair>();
      table[index].add(new keyPair(key, val));
    }
    else {
      for (keyPair e: table[index]) {
        if(e.key.equals(key)) {
          K temp = e.val;
          e.val = val;
          return temp;
        }
      }
      table[index].add(new keyPair(key, val));
    }
    return null;
  }

  /**
   * Gets the value associated with a key from the HashTable.
   * @param key, a key associated with a key-value pair.
   * @return the value associated with the key. Null if no value is associated with the key.
   * @throws NullPointerException if key is null.
   */
  public K get(E key) {
    if(key == null) {
      throw new NullPointerException();
    }
    int index = keyIndex(key);
    if (table[index] == null) {
      return null;
    }
    else {
      for (keyPair e: table[index]) {
        if(e.key.equals(key)) {
          return e.val;
        }
      }
      return null;
    }
  }

  /**
   * Removes a key-value pair from the HashTable
   * @param key, a key associated with a key-value pair.
   * @return the value associated with the key. Null if no value is associated with the key.
   * @throws NullPointerException if key is null.
   */
  public K remove(E key) {
    if(key == null) {
      throw new NullPointerException();
    }
    int index = keyIndex(key);
    if (table[index] == null) {
      return null;
    }
    else {
      for (int i = 0; i < table[index].size(); i++) {
        if(table[index].get(i).key.equals(key)) {
          return table[index].remove(i).val;
        }
      }
      return null;
    }
  }

  /**
   * Convert's the table into a String.
   * @return the table's data as a String.
   */
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("Key-Pairs: ");
    boolean hasItems = false;
    for (int i = 0; i < table.length; i++) {
      if (table[i] != null) {
        for (keyPair e: table[i]) {
          hasItems = true;
          sb.append("[\"");
          sb.append(e.key);
          sb.append("\", \"");
          sb.append(e.val);
          sb.append("\"]");
        }
      }
    }
    if (!hasItems) {
      sb.append("None");
    }
    return sb.toString();
  }

  /**
   * Determines the index of a key in the HashTable.
   * @param key, a key associated with a key-value pair.
   * @return the index of the key in the HashTable.
   */
  private int keyIndex(E key) {
    int temp = key.hashCode() % table.length;
    if(temp < 0) {
      temp += table.length;
    }
    return temp;
  }
}
