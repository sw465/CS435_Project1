import java.util.*;


class BST {

  class Node {
    Node left;
    Node right;
    int val;

    Node(int newVal)
    {
      val = newVal;
      left = null;
      right = null;
    }
  } 

  Node root;

  void insertIter( int newVal )
  {
    

    if ( this.root == null )
        this.root = new Node(newVal);

    Node currNode = this.root;

    while ( currNode != null )
    {
        if ( newVal > currNode.val )
        {
          // Add new value when encountering a right null child
          if ( currNode.right == null )
          {
            currNode.right = new Node(newVal);
            break;
          }
          else
            currNode = currNode.right;


        }
        if ( newVal < currNode.val)
        {
          // Add new value when encountering a left null child
          if ( currNode.left == null )
          {
            currNode.left = new Node(newVal);
            break;
          }
          else
            currNode = currNode.left;
        }
        // Duplicate values won't be added
        if ( newVal == currNode.val )
            return;

    } // End while loop

  } 

  public void insertRec( Node currNode, int newVal )
  {
    // set root node of tree is empty
    if ( this.root == null ) {
        this.root = new Node(newVal);
        return;
    }

    // BST is empty
    if ( currNode == null )
      currNode = new Node(newVal);

    // newVal already in BST
    if ( currNode.val == newVal )
      return;

    // If newVal is greater than current currNode, add newVal to right side or check 
    // right currNode
    if ( newVal > currNode.val )
    {
      if ( currNode.right == null )
        currNode.right = new Node(newVal);
      else
        insertRec( currNode.right, newVal );
    }
    else
    {
      if ( currNode.left == null )
        currNode.left = new Node(newVal);
      else
        insertRec( currNode.left, newVal);
    }
  } // End insertRec


  Node deleteIter( int valToDelete )
  {
    Node currNode = this.root;

    Node root = currNode;
    Node parent = null;
    // Check if tree is empty
    if ( currNode == null )
      return null;

    while ( currNode != null )
    {
      if ( currNode.val == valToDelete )
      {
        if ( currNode.left == null && currNode.right == null )
        {
          // Find which child of parent needs to be set and delete it
          if ( parent.val > currNode.val ) 
            parent.left = null;
          else
            parent.right = null;
          break;
        }
        // Set the parent to the entire left subtree of the node we delete
        if ( currNode.left != null && currNode.right == null )
        {
          // Find which child of parent needs to be set and set it to 
          if ( parent.val > currNode.val ) 
            parent.left = currNode.left;
          else
            parent.right = currNode.left;
          break;
        }
        // Set the parent to the entire right subtree of the node we delete
        if ( currNode.right != null && currNode.left == null )
        {
          if ( parent.val > currNode.val ) 
            parent.left = currNode.right;
          else
            parent.right = currNode.right;
          break;
        }
        // convert the value of the node to be deleted to the next largest value.
        // Continue traversing tree to delete the next highest value node
        if ( currNode.left != null && currNode.right != null )
        {
          currNode.val = findNextIter( currNode );
          valToDelete = currNode.val;
          parent = currNode;
          currNode = currNode.right;
        }
      }
      else if ( valToDelete > currNode.val )
      {
        parent = currNode;
        currNode = currNode.right;
      }
      else
      {
        parent = currNode;
        currNode = currNode.left;
      }

    }
    
    return root;
  }

  static Node deleteRec( Node currNode, int valToDelete )
  {
    
    // If tree is empty or valToDelete is not in tree
    if ( currNode == null )
      return null;
    
    if ( currNode.val == valToDelete )
    {
      if ( currNode.left == null && currNode.right == null )
      {
        return null;
      }
      if ( currNode.left != null && currNode.right == null )
      {
        return currNode.left;
      }
      if ( currNode.right != null && currNode.left == null )
      {
        return currNode.right;
      }
      if ( currNode.right != null && currNode.left != null )
      {
        // swap with smallest value in right subtree ( next largest value in the entire main BST )
        // Smallest will be guaranteed to only have 1 or less nodes
        currNode.val = findNextRec( currNode );
        // Delete the smallest value node in right subtree that was swapped
        currNode.right = deleteRec( currNode.right, currNode.val  );
      }
    }
    else if ( valToDelete > currNode.val )
    {
      currNode.right = deleteRec( currNode.right, valToDelete  );
    }
    else
    {
      currNode.left = deleteRec( currNode.left, valToDelete  );
    }

    return currNode;
  }

  // Returns the bottom left leaf
  static int findMinIter( Node currNode )
  {
    // Check if tree is empty
    if ( currNode == null )
      return -1;

    while ( currNode.left != null )
      currNode = currNode.left;

    return currNode.val;
  }

  // Returns the bottom right leaf
  static int findMaxIter( Node currNode )
  {
    // Check if tree is empty
    if ( currNode == null )
      return -1;

    while ( currNode.right != null )
      currNode = currNode.right;

    return currNode.val;

  }

  static int findNextIter( Node currNode )
  {
    // Check if tree is empty
    if ( currNode == null )
      return -1;
    // There is no larger value in tree
    if ( currNode.right == null )
      return currNode.val;
    else
      return findMinIter(currNode.right);
  }

  static int findPrevIter( Node currNode )
  {
    // Check if tree is empty
    if ( currNode == null )
      return -1;
    // There is no smaller value in tree
    if ( currNode.left == null )
      return currNode.val;
    else
      return findMaxIter(currNode.left);

  }

  static int findMinRec( Node currNode )
  {
    if ( currNode.left == null )
          return currNode.val;

    return findMinRec(currNode.left);
  }

  // Returns the bottom right leaf
  static int findMaxRec( Node currNode )
  {
    if ( currNode.right == null )
          return currNode.val;

    return findMaxRec(currNode.right);
  }

  static int findNextRec( Node node )
  {
    return findMinRec ( node.right );
  }

  static int findPrevRec( Node node )
  {
    return findMaxRec ( node.left );
  }

  static List<Integer> sort( List<Integer> unsortedList )
  {
    System.out.println("test");
    BST bst = new BST();
    
    for (int i = 0; i < unsortedList.size(); i++) 
      bst.insertIter(unsortedList.get(i));

    List<Integer> sortedList = new ArrayList<Integer>();

    createInOrderList(sortedList, bst.root);

    return sortedList;
    
  }

  static void createInOrderList(List<Integer> sortedList, Node root)
  {
      if ( root == null )
          return;

      createInOrderList(sortedList, root.left);
      sortedList.add(root.val);
      createInOrderList(sortedList, root.right);
  }

  static ArrayList<Integer> getRandomArray( int n )
  {
    ArrayList<Integer> array = new ArrayList<>(n);

    array.add((int)(Math.random() * 100) + 1);
    for ( int i = 1; i < n - 1; i++ )
        array.add(array.get(i - 1) + (int)(Math.random() * 100) + 1 );

    Collections.shuffle(array);

    return array;
  }

  static ArrayList<Integer> getSortedArray( int n )
  {
    ArrayList<Integer> array = new ArrayList<>(n);

    for ( int i = n; i > 0; i-- )
        array.add(i);

    return array;
  }

  public static void main(String[] args) {
      
  }

}


https://www.geeksforgeeks.org/java-math-random-method-examples/
https://www.geeksforgeeks.org/tree-traversals-inorder-preorder-and-postorder/
https://stackoverflow.com/questions/30559758/java-setting-object-to-null-within-a-method-has-no-effect-reusing-code
https://stackoverflow.com/questions/10265576/java-retain-information-in-recursive-function
