import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * ArrayList, Defines an array based list for holding generic objects and contains methods for sorting said list.
 * @author Nagoshi, Vincent
 */
public class ArrayList<E> implements Iterable<E> {

  /**
   * ArrayListIterator, an iterator for the ArrayList
   * @author Nagoshi, Vincent
   */
  @SuppressWarnings("hiding")
  public class ArrayListIterator<E> implements Iterator<E>{
    private int iteratorLocation;

    /**
     * Default constructor
     */
    public ArrayListIterator(){
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
      if(data.length == size){
        reallocate();
      }
      if(data[iteratorLocation] != null){
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
      else if(data[iteratorLocation - 1] != null){
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
        E entry = (E) data[iteratorLocation];
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
      if(iteratorLocation < 0 || iteratorLocation >= size){
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
        E entry = (E) data[iteratorLocation - 1];
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
      if(iteratorLocation <= 0 || iteratorLocation > size){
        throw new IndexOutOfBoundsException();
      }
      return iteratorLocation - 1;
    }
  }


  private E[] data;
  private int size;

  /**
   * Default constructor
   */
  @SuppressWarnings("unchecked")
  public ArrayList(){
    data = (E[]) new Object[10];
    size = 0;
  }

  /**
   * Adds an element to the list as the last element in the list.
   * @param e, the element to add to the list.
   * @return returns true if the method was successful in adding the element. Only returns true.
   */
  public boolean add(E e) {
    if(data.length == size){
      reallocate();
    }
    data[size] = e;
    size++;
    return true;
  }

  /**
   * Adds an element to the list at a given index within the list.
   * @param index, the position (0 based) of the list to place the element.
   * @param element, the element to add to the list.
   * @exception throws ArrayIndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public void add(int index, E element) {
    if(index < 0 || index >= size){
      throw new ArrayIndexOutOfBoundsException(index);
    }
    if(data.length == size){
      reallocate();
    }
    for(int i = size; i > index; i--){
      data[i] = data[i - 1];
    }
    data[index] = element;
    size++;
  }

  /**
   * Gets the element at a specified index within the list.
   * @param index, the index of the element to get.
   * @return returns the element at the specified index within the list.
   * @exception throws ArrayIndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E get(int index) {
    if(index < 0 || index >= size){
      throw new ArrayIndexOutOfBoundsException(index);
    }
    return data[index];
  }

  /**
   * Removes an element form the list at a given index.
   * @param index, the index to remove the element from.
   * @return returns the removed element
   * @exception throws ArrayIndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E remove(int index) {
    if(index < 0 || index >= size){
      throw new ArrayIndexOutOfBoundsException(index);
    }
    E removedObject = data[index];
    for(int i = index; i < size - 1; i++){
      data[i] = data[i + 1];
    }
    data[size - 1] = null;
    size--;
    return removedObject;
  }

  /**
   * Sets a given index of the list to a given element.
   * @param index, the index of the list to set the element to. 
   * @param element, the element to write into the list.
   * @return returns the element overwritten by the method.
   * @exception throws ArrayIndexOutOfBoundsException if the index does not point to an object in the list.
   */
  public E set(int index, E element) {
    if(index < 0 || index >= size){
      throw new ArrayIndexOutOfBoundsException(index);
    }
    E replacedObject = data[index];
    data[index] = element;
    return replacedObject;
  }

  /**
   * Gets the size of the list.
   * @return returns the size of the list.
   */
  public int size() {
    return size;
  }

  /**
   * Adds more space to the data array
   */
  private void reallocate(){
    int capacity = data.length * 2;
    data = Arrays.copyOf(data, capacity);
  }

  /**
   * Sorts the data array using an insertion algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void insertionSort(Comparator<? super E> compare){
    for(int i = 1; i < size; i++){
      int j = i;
      E held = data[i];
      while(j > 0 && compare.compare(data[j - 1], held) > 0){
        data[j] = data[j - 1];
        j--;
      }
      data[j] = held;
    }
  }

  /**
   * Sorts the data array using a bubble algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void bubbleSort(Comparator<? super E> compare){
    for(int i = 0; i < size - 1; i++){
      int numberOfExchagesMadeThisPass = 0;
      for(int j = 0; j < size - i - 1; j++){
        if(compare.compare(data[j], data[j +1]) > 0){
          E temp = data[j];
          data[j] = data[j + 1];
          data[j + 1] = temp;
          numberOfExchagesMadeThisPass++;
        }
      }
      if(numberOfExchagesMadeThisPass <= 0){
        break;
      }
    }
  }

  /**
   * Sorts the data array using a selection algorithm.
   * @param compare, the comparator to compare the data by.
   */
  public void selectionSort(Comparator<? super E> compare){
    int currentSmallestObjectKey;
    for(int i = 0; i < size - 1; i++){
      currentSmallestObjectKey = i;
      for(int j = i + 1; j < size; j++){
        if(compare.compare(data[j], data[currentSmallestObjectKey]) < 0){
          currentSmallestObjectKey = j;
        }	
      }
      E temp = data[i];
      data[i] = data[currentSmallestObjectKey];
      data[currentSmallestObjectKey] = temp;
    }
  }

  /**
   * Sends a new iterator.
   * @return returns an iterator for the list.
   */
  @Override
  public Iterator<E> iterator() {
    Iterator<E> it = new ArrayListIterator<E>();
    return it;
  }
}
