import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * LinkedList, defines a double linked list.
 * @author Nagoshi, Vincent
 */
public class LinkedList<E> implements Iterable<E>{

  /**
   * LinkedListIterator, an iterator for the LinkedList
   * @author Nagoshi, Vincent
   */
  @SuppressWarnings("hiding")
  public class LinkedListIterator<E> implements Iterator<E>{
    private int iteratorLocation;

    /**
     * Default constructor
     */
    public LinkedListIterator(){
      iteratorLocation = 0;
    }

    /**
     * checks if there is an element next to the iterator location.
     * @return returns true if there is an element next to the iterator. Otherwise returns false.
     * @throws throws IndexOutOfBoundsException if the iterator is not bordering an element.
     */
    @Override
    public boolean hasNext() {
      if(iteratorLocation < 0 || iteratorLocation > size){
        throw new IndexOutOfBoundsException();
      }
      if(getNode(iteratorLocation) != null){
        return true;
      }
      else{
        return false;
      }
    }

    /**
     * Checks if there is an element before the iterator location.
     * @return returns true if there is an element before the iterator. Otherwise returns false.
     * @throws throws IndexOutOfBoundsException if the iterator is not bordering an element.
     */
    public boolean hasPrevious() {
      if(iteratorLocation < 0 || iteratorLocation > size){// may have problems
        throw new IndexOutOfBoundsException();
      }
      if(iteratorLocation == 0){
        return false;
      }
      else if(getNode(iteratorLocation - 1) != null){
        return true;
      }
      else{
        return false;
      }
    }

    /**
     * Returns element next to the iterator and moves the iterator forward one space.
     * @return returns the element next to the iterator.
     * @throws throws NoSuchElementException if there is no element to return.
     */
    @SuppressWarnings("unchecked")
    public E next(){
      if(this.hasNext()){
        E entry = (E) getNode(iteratorLocation).data;
        iteratorLocation++;
        return entry;
      }
      else{
        throw new NoSuchElementException();
      }
    }

    /**
     * Returns the index of the element that the iterator is before.
     * @return returns the the index the iterator is before.
     * @throws throws IndexOutOfBoundsException if the iterator is not bordering a valid index.
     */
    public int nextIndex() {
      if(iteratorLocation < 0 || iteratorLocation > size){
        throw new IndexOutOfBoundsException();
      }
      return iteratorLocation;
    }

    /**
     * Returns element before the iterator and moves the iterator back one space.
     * @return returns the element before the iterator.
     * @throws throws NoSuchElementException if there is no element to return.
     */
    @SuppressWarnings("unchecked")
    public E previous() {
      if(this.hasPrevious()){
        E entry = (E) getNode(iteratorLocation - 1).data;
        iteratorLocation--;
        return entry;
      }
      else{
        throw new NoSuchElementException();
      }
    }

    /**
     * Returns the index of the element that the iterator is after.
     * @return returns the the index the iterator is after.
     * @throws throws IndexOutOfBoundsException if the iterator is not bordering a valid index.
     */
    public int previousIndex() {
      if(iteratorLocation <= 0 || iteratorLocation >= size){// may have problems
        throw new IndexOutOfBoundsException();
      }
      return iteratorLocation - 1;
    }
  }

  /**
   * DLinkedNode, defines a double linked node.
   * @author Nagoshi, Vincent
   */
  @SuppressWarnings("hiding")
  private class DLinkedNode<E>{
    E data;
    DLinkedNode<E> next;
    DLinkedNode<E> prev;

    /**
     * Default Constructor
     * @param entry, the element to store in the node.
     */
    private DLinkedNode(E entry){
      this.data = entry;
      next = null;
      prev = null;
    }

    /**
     * Constructor with specified next and prev values.
     * @param entry, the element to store in the node.
     * @param nextRef, the next node in the list.
     * @param prevRef, the previous node in the list.
     */
    private DLinkedNode(E entry, DLinkedNode<E> nextRef, DLinkedNode<E> prevRef){
      this.data = entry;
      this.next = nextRef;
      this.prev = prevRef;
    }
  }

  /** The first node in the list. */
  private DLinkedNode<E> head;
  /** The last node in the list. */
  private DLinkedNode<E> tail;
  /** The size of the list. */
  private int size;

  /**
   * Adds a node to the list in the head position of the list.
   * @param entry, the element to store in the node.
   */
  private void addFirst(E entry){
    head = new DLinkedNode<E>(entry, head, null);
    size++;
  }

  /**
   * Adds a node after a given node in the list.
   * @param node, the node to place the new node after.
   * @param entry, the element to store in the node.
   */
  private void addAfter(DLinkedNode<E> node, E entry){
    node.next = new DLinkedNode<E>(entry, node.next, node);
    size++;
  }

  /**
   * removes a node from the list.
   * @param node, the node to remove.
   * @return, returns the data from the removed node.
   */
  private E remove(DLinkedNode<E> node){
    /** Tests of the node to be removed is the head. */
    if(node.prev == null){
      head = node.next;
    }
    else{
      node.prev.next = node.next;
    }
    /** Tests if the node to be removed is the tail. */
    if(node.next == null){
      tail = node.prev;
    }
    else{
      node.next.prev = node.prev;
    }
    size--;
    return node.data;
  }

  /**
   * Gets a node from the list.
   * @param index, the index of the node to get.
   * @return returns the node at the given index from the list.
   */
  private DLinkedNode<E> getNode(int index){
    DLinkedNode<E> node = head;
    for(int i = 0; i < index && node != null; i++){
      node = node.next;
    }
    return node;
  }


  /**
   * Default Constructor
   */
  public LinkedList(){
    this.head =  null;
    this.tail = null;
    this.size = 0;
  }


  /**
   * Adds an element to the list.
   * @param entry, the data to store in the list.
   * @return returns true.
   */
  public boolean add(E entry){
    add(size, entry);
    return true;
  }

  /**
   * Adds an element to the list at a given index.
   * @param index, the index to place the element at.
   * @param element, the data to store in the list.
   * @exception throws IndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public void add(int index, E element) {
    if(index < 0 || index > size){
      throw new IndexOutOfBoundsException();
    }
    if(index == 0){
      addFirst(element);
    }
    else{
      DLinkedNode<E> node = getNode(index - 1);
      addAfter(node, element);
    }
    if(index == size - 1){
      tail = getNode(index);
    }
  }
  /**
   * Gets an element from the list.
   * @param index, the index of the element to get from the list.
   * @return, returns the data from the given index of the list.
   * @exception throws IndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E get(int index) {
    if(index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    DLinkedNode<E> node = getNode(index);
    return node.data;
  }

  /**
   * Removes an element at a given index from the list.
   * @param index, the index of the element to remove from the list.
   * @return returns the data from the removed index. 
   * @exception throws IndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E remove(int index) {
    if(index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    DLinkedNode<E> node = getNode(index);
    return remove(node);
  }

  /**
   * Sets the data of a given index of the list.
   * @param index, the index at which to set the data in the list.
   * @param element, the data to store in the list.
   * @return returns the replaced data.
   * @exception throws IndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E set(int index, E element) {
    if(index < 0 || index >= size){
      throw new IndexOutOfBoundsException();
    }
    DLinkedNode<E> node = getNode(index);
    E old = node.data;
    node.data = element;
    return old;
  }

  /**
   * Gets the current size of the list.
   * @return returns the size of the list.
   */
  public int size() {
    return size;
  }

  /**
   * Sorts the list using an insertion algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void insertionSort(Comparator<? super E> compare){
    for(int i = 1; i < size; i++){
      int j = i;
      E held = getNode(i).data;
      while(j > 0 && compare.compare(getNode(j - 1).data, held) > 0){
        getNode(j).data = getNode(j - 1).data;
        j--;
      }
      getNode(j).data = held;
    }
  }

  /**
   * Sorts the list using a bubble algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void bubbleSort(Comparator<? super E> compare){
    for(int i = 0; i < size - 1; i++){
      int numberOfExchagesMadeThisPass = 0;
      for(int j = 0; j < size - i - 1; j++){
        if(compare.compare(getNode(j).data, getNode(j + 1).data) > 0){
          E temp = getNode(j).data;
          getNode(j).data = getNode(j + 1).data;
          getNode(j + 1).data = temp;
          numberOfExchagesMadeThisPass++;
        }
      }
      if(numberOfExchagesMadeThisPass <= 0){
        break;
      }
    }
  }

  /**
   * Sorts the list using a selection algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void selectionSort(Comparator<? super E> compare){
    int currentSmallestObjectKey;
    for(int i = 0; i < size - 1; i++){
      currentSmallestObjectKey = i;
      for(int j = i + 1; j < size; j++){
        if(compare.compare(getNode(j).data, getNode(currentSmallestObjectKey).data) < 0){
          currentSmallestObjectKey = j;
        }	
      }
      E temp = getNode(i).data;
      getNode(i).data = getNode(currentSmallestObjectKey).data;
      getNode(currentSmallestObjectKey).data = temp;
    }
  }

  /**
   * Sends a new iterator.
   * @return returns an iterator for the list.
   */
  @Override
  public Iterator<E> iterator() {
    return new LinkedListIterator<E>();
  }
}
