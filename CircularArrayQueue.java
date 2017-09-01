import java.util.NoSuchElementException;

/**
 * CircularArrayQueue, defines an array based queue using circular referencing to avoid memory drain.
 * @author Nagoshi, Vincent
 */
public class CircularArrayQueue<E> {
  private int front;
  private int rear;
  private E[] data;
  private int size;

  /**
   * Default Constructor
   */
  @SuppressWarnings("unchecked")
  public CircularArrayQueue() {
    data = (E[]) new Object[10];
    front = 0;
    rear = data.length - 1;
    size = 0;
  }

  /**
   * Adds an element to the end of the queue.
   * @param e, the element to add to the queue.
   * @return returns true if the element was added successfully. Returns true.
   */
  public boolean add(E e) {
    if (size == data.length) {
      reallocate();
    }
    size++;
    rear = (rear + 1) % data.length;
    data[rear] = e;
    return true;
  }

  /**
   * Adds an element to the end of the queue.
   * @param e, the element to add to the queue.
   * @return returns true if the element was added successfully. Returns true.
   */
  public boolean offer(E e) {
    add(e);
    return false;
  }

  /**
   * Looks at the first element in the queue.
   * @return returns the first element in the queue.
   * @throws throws NoSuchElementException if the queue is empty.
   */
  public E element() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    return data[front];
  }

  /**
   * Looks at the first element in the queue.
   * @return returns the first element in the queue. Returns null if the quque is empty.
   */
  public E peek() {
    if (size == 0) {
      return null;
    }
    return data[front];
  }

  /**
   * Gets and removes the first element in the queue.
   * @return returns the first element in the queue. Returns null if the queue is empty.
   */
  public E poll() {
    if (size == 0) {
      return null;
    }
    E result = data[front];
    front = (front + 1) % data.length;
    size--;
    return result;
  }

  /**
   * Gets and removes the first element in the queue.
   * @return returns the first element in the queue.
   * @throws throws NoSuchElementException if the queue is empty.
   */
  public E remove() {
    if (size == 0) {
      throw new NoSuchElementException();
    }
    E result = data[front];
    front = (front + 1) % data.length;
    size--;
    return result;
  }

  /**
   * Gets the size of the queue.
   * @return returns the size of the queue.
   */
  public int size() {
    return size;
  }

  /**
   * Adds more capacity to the data array and resets the front and rear pointers.
   */
  @SuppressWarnings("unchecked")
  private void reallocate() {
    E[] newData = (E[]) new Object[data.length * 2];
    int j = front;
    for (int i = 0; i < size; i++) {
      newData[i] = data[j];
      j = (j + 1) % data.length;
    }
    front = 0;
    rear = size - 1;
    data = newData;
  }
}
