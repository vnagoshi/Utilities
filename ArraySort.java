
import java.util.Comparator;

/**
 * ArraySort, Contains six sorting algorithms for sorting Arrays.
 * @author Nagoshi, Vincent
 */
public class ArraySort<E> {
  private int numberOfComparisons;
  private int numberOfSwaps;
  private long startTime;
  private long endTime;

  /**
   * Sorts an Array using an insertionSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void insertionSort(E[] data, Comparator<? super E> compare){
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    for(int i = 1; i < data.length; i++){
      int j = i;
      E held = data[i];
      while(j > 0 && compare(compare, data[j - 1], held) > 0){
        data[j] = data[j - 1];
        numberOfSwaps++;
        j--;
      }
      data[j] = held;
      numberOfSwaps++;
    }
    endTime = System.nanoTime();
    System.out.println("insertionSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Sorts an Array using a bubbleSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void bubbleSort(E[] data, Comparator<? super E> compare){
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    for(int i = 0; i < data.length - 1; i++){
      int numberOfExchagesMadeThisPass = 0;
      for(int j = 0; j < data.length - i - 1; j++){
        if(compare(compare, data[j], data[j +1]) > 0){
          swap(data, j, j + 1);
          numberOfExchagesMadeThisPass++;
        }
      }
      if(numberOfExchagesMadeThisPass <= 0){
        break;
      }
    }
    endTime = System.nanoTime();
    System.out.println("bubbleSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Sorts an array using a selectionSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void selectionSort(E[] data, Comparator<? super E> compare){
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    int currentSmallestObjectKey;
    for(int i = 0; i < data.length - 1; i++){
      currentSmallestObjectKey = i;
      for(int j = i + 1; j < data.length; j++){
        if(compare(compare, data[j], data[currentSmallestObjectKey]) < 0){
          currentSmallestObjectKey = j;
        }	
      }
      swap(data, i, currentSmallestObjectKey);
    }
    endTime = System.nanoTime();
    System.out.println("selectionSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Sorts an Array using a heapSort algorithm.
   * @param data, an array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void heapSort(E[] data, Comparator<? super E> compare) {
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    int n = 1;
    while (n < data.length) {
      n++;
      int child = n - 1;
      int parent = (child - 1) / 2;
      while (parent >= 0 && compare(compare, data[parent], data[child]) < 0) {
        swap(data, parent, child);
        child = parent;
        parent = (child - 1) / 2;
      }
    }
    n = data.length;
    while (n > 0) {
      n--;
      swap(data, 0, n);
      int parent = 0;
      while (true) {
        int leftChild = 2 * parent + 1;
        if (leftChild >= n) {
          break;
        }
        int rightChild = 2 * parent + 2;
        int maxChild = leftChild;
        if (rightChild < n && compare(compare, data[leftChild], data[rightChild]) < 0) {
          maxChild = rightChild;
        }
        if (compare(compare, data[parent], data[maxChild]) < 0) {
          swap(data, parent, maxChild);
          parent = maxChild;
        }
        else {
          break;
        }
      }
    }
    endTime = System.nanoTime();
    System.out.println("heapSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Sorts an Array using a mergeSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void mergeSort(E[] data, Comparator<? super E> compare) {
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    merge(data, compare);
    endTime = System.nanoTime();
    System.out.println("mergeSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Recursively sorts an Array using a merge algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  @SuppressWarnings("unchecked")
  private void merge(E[] data, Comparator<? super E> compare) {
    if (data.length > 1) {
      int halfSize = data.length / 2;
      E[] left = (E[]) new Object[halfSize];
      System.arraycopy(data, 0, left, 0, halfSize);
      E[] right = (E[]) new Object[data.length - halfSize];
      System.arraycopy(data, halfSize, right, 0, data.length - halfSize);
      merge(left, compare);
      merge(right, compare);
      int i = 0;
      int j = 0;
      int k = 0;
      while (i < left.length && j < right.length) {
        if (compare(compare, left[i], right[j]) < 0) {
          data[k++] = left[i++];
          numberOfSwaps++;
        }
        else {
          data[k++] = right[j++];
          numberOfSwaps++;
        }
      }
      while (i < left.length) {
        data[k++] = left[i++];
        numberOfSwaps++;
      }
      while (j < right.length) {
        data[k++] = right[j++];
        numberOfSwaps++;
      }
    }
  }

  /**
   * Sorts an Array using a quickSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   */
  public void quickSort(E[] data, Comparator<? super E> compare) {
    startTime = System.nanoTime();
    numberOfComparisons = 0;
    numberOfSwaps = 0;
    quickSort(data, compare, 0, data.length - 1);
    endTime = System.nanoTime();
    System.out.println("quickSort took " + (endTime - startTime) + " nanoseconds to sort the array.");
    System.out.println("Number of comparisons: " + numberOfComparisons + "; Number of swaps: " + numberOfSwaps + "; Size of Array: " + data.length);
  }

  /**
   * Recursively sorts an Array using a quickSort algorithm.
   * @param data, an Array of Objects.
   * @param compare, a Comparator element to sort the Array by.
   * @param first, the first index of the Array to sort.
   * @param last, the last index of the Array to sort.
   */
  private void quickSort(E[] data, Comparator<? super E> compare, int first, int last) {
    if (first < last) {
      int pivotIndex;
      E pivot = data[first];
      int up = first;
      int down = last;
      do {
        while ((up < last) && compare(compare, pivot, data[up]) >= 0) {
          up++;
        }
        while (compare(compare, pivot, data[down]) < 0) {
          down--;
        }
        if (up < down) {
          swap (data, up, down);
        }
      } while (up < down);
      swap (data, first, down);
      pivotIndex = down;
      quickSort(data, compare, first, pivotIndex - 1);
      quickSort(data, compare, pivotIndex + 1, last);
    }
  }

  /**
   * Compares two Objects.
   * @param compare, a Comparator to compare two Objects with.
   * @param obj1, the first Object.
   * @param obj2, the second Object.
   * @return returns 0 if the Objects are equivalent, a negative integer if obj1 is less than obj2, or a positive integer if obj1 id greater than obj2.
   */
  private int compare(Comparator<? super E> compare, E obj1, E obj2) {
    numberOfComparisons++;
    return compare.compare(obj1, obj2);
  }

  /**
   * Swaps to Objects in an Array.
   * @param data, the Array.
   * @param first, the index of the first Object to swap.
   * @param second, the index of the second Object to Swap.
   */
  private void swap(E[] data, int first, int second) {
    numberOfSwaps++;
    E temp = data[first];
    data[first] = data[second];
    data[second] = temp;
  }
}
