
import java.util.Comparator;

/**
 * BinarySearchTree, Defines a binary search tree
 * @author Nagoshi, Vincent
 */
public class BinarySearchTree<E> {

  /**
   * Defines a node for storing the tree's structure and data.
   */
  private class BinaryNode {
    private E data;
    private BinaryNode left;
    private BinaryNode right;

    /**
     * Constructor with data given.
     * @param data, the data to store in the node.
     */
    public BinaryNode(E data) {
      this.data = data;
    }
  }


  private Comparator<E> comp;
  private BinaryNode root;
  /** Return item for delete(E target) method */
  private E delReturn;


  /**
   * Creates a new BinarySearchTree.
   * @param c the comparator to use for determining order.
   */
  public BinarySearchTree(Comparator<E> c) {
    this.comp = c;
  }


  /**
   * Puts the data in the tree into a String.
   * @return returns the object's data in order as a String.
   */
  public String inorderString() {
    return inorderString(root);
  }

  /**
   * Recursive helper method for inorderString(). Builds a String from the tree's data in order least to greatest.
   * @param node, the node to start building from.
   * @return returns the object's data in order as a String.
   */
  private String inorderString(BinaryNode node) {
    String rtn = "";
    if (node == null) {
      return rtn;
    }
    rtn += inorderString(node.left);
    rtn += node.data.toString();
    rtn += inorderString(node.right);
    return rtn;
  }


  /**
   * Adds an item to the tree.
   * @param item, the item to add to the tree.
   * @return returns true if the item was added to the tree, false if the item was already in the tree or was null or the tree's comparator was not declared when the tree was built.
   */
  public boolean add(E item) {
    if(item == null || comp == null) {
      return false;
    }
    if(root == null) {
      root = new BinaryNode(item);
      return true;
    }
    return add(root, item);
  }

  /**
   * Recursive helper method for add(E item). Moves down the tree and adds the item to the tree at the correct leaf.
   * @param node, the node to start moving down from.
   * @param item, the item to add to the tree.
   * @return returns true if the item was added to the tree, false if the item was already in the tree.
   */
  private boolean add(BinaryNode node, E item) {
    if (comp.compare(node.data, item) == 0) {
      return false;
    }
    if (comp.compare(node.data, item) > 0) {
      if(node.left != null) {
        return add(node.left, item);
      }
      else {
        node.left = new BinaryNode(item);
        return true;
      }
    }
    else {
      if(node.right != null) {
        return add(node.right, item);
      }
      else {
        node.right = new BinaryNode(item);
        return true;
      }
    }
  }


  /**
   * Determines if an item is in the tree.
   * @param item, the item to search the tree for.
   * @return returns true if the item was in the tree, false if the item was not in the tree or the tree's comparator was not declared when the tree was built.
   */
  public boolean contains(E item) {
    return contains(root, item);
  }

  /**
   * Recursive helper method for contains(E item). Searches for an item in the tree.
   * @param node, the node to search down from.
   * @param item, the item to search the tree for.
   * @return returns true if the item was in the tree, false if the item was not in the tree or the tree's comparator was not declared when the tree was built.
   */
  private boolean contains(BinaryNode node, E item) {
    if (node == null || comp == null) {
      return false;
    }
    if (comp.compare(node.data, item) == 0) {
      return true;
    }
    if (comp.compare(node.data, item) > 0) {
      return contains(node.left, item);
    }
    else {
      return contains(node.right, item);
    }
  }


  /**
   * Finds and returns an item in the tree.
   * @param target, the item to look for in the list.
   * @return returns the target if found, returns null if the item is not in the tree or the tree's comparator was not declared when the tree was built.
   */
  public E find(E target) {
    return find(root, target);
  }

  /**
   * Recursive helper method for find(E target). Looks for and returns an item in the tree if found.
   * @param node, the node to search down from.
   * @param target, the item to look for in the list.
   * @return returns the target if found, returns null if the item is not in the tree or the tree's comparator was not declared when the tree was built.
   */
  private E find(BinaryNode node, E target) {
    if (node == null || comp == null) {
      return null;
    }
    if (comp.compare(node.data, target) == 0) {
      return node.data;
    }
    if (comp.compare(node.data, target) > 0) {
      return find(node.left, target);
    }
    else {
      return find(node.right, target);
    }
  }


  /**
   * Deletes an object from the tree if it is in the tree.
   * @param target, the item to delete in the tree.
   * @return returns the deleted item if it was in the tree, null if the item was not in the tree or the tree's comparator was not declared when the tree was built.
   */
  public E delete(E target) {
    root = delete(root, target);
    return delReturn;
  }

  /**
   * Recursive helper method for delete(E target). Searches for and removes an item from the tree if it is found.
   * @param node, the node to search down from.
   * @param target, the item to delete in the tree.
   * @return returns the deleted item if it was in the tree, null if the item was not in the tree or the tree's comparator was not declared when the tree was built.
   */
  private BinaryNode delete(BinaryNode node, E target) {
    if(node == null || comp == null) {
      delReturn = null;
      return node;
    }
    if (comp.compare(node.data, target) == 0) {
      return shiftNode(node);
    }
    if (comp.compare(node.data, target) > 0) {
      node.left = delete(node.left, target);
      return node;
    }
    else {
      node.right = delete(node.right, target);
      return node;
    }
  }

  /**
   * Shifts nodes upwards into their proper positions when a node is removed from the tree.
   * @param node, the node to replace.
   * @return returns the chain of nodes to move into the previous position of the node in the tree.
   */
  private BinaryNode shiftNode(BinaryNode node) {
    delReturn = node.data;
    if(node.left == null) {
      return node.right;
    }
    else if (node.right == null) {
      return node.left;
    }
    else {
      if(node.right.left == null) {
        node.data = node.right.data;
        node.right = node.right.right;
        return node;
      }
      else {
        node.data = smallestChild(node.right);
        return node;
      }
    }
  }

  /**
   * Searches for the smallest child node of a given node.
   * @param node, the node to start searching down from.
   * @return returns the data in the largest child node.
   */
  private E smallestChild(BinaryNode node) {
    if (node.left.left == null) {
      E returnVal = node.left.data;
      node.right = node.left.right;
      return returnVal;
    }
    else {
      return smallestChild(node.left);
    }
  }

  /**
   * Searches for and removes an item from the tree if it is found.
   * @param target, the item to remove from the tree.
   * @return returns true if the item was removed, false if the item was not in the tree or the tree's comparator was not declared when the tree was built.
   */
  public boolean remove(E target) {
    delete(target);
    if(delReturn == null) {
      return false;
    }
    else {
      return true;
    }
  }
}
