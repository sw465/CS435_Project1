import java.util.*;



class Main {

  static class Node {
  Node left;
  Node right;
  int val;

  Node(int newVal)
  {
    val = newVal;
  }
} 

  static void insertRec(int newVal, Node currNode)
  {
    // BST is empty
    if ( currNode == null )
      currNode = new Node(newVal);

    // newValber already in BST
    if ( currNode.val == newVal )
      return;

    // If newVal is greater than current currNode, add newVal to right side or check 
    // right currNode
    if ( newVal > currNode.val )
    {
      if ( currNode.right == null )
        currNode.right = new Node(newVal);
      else
        insertRec( newVal, currNode.right );
    }
    else
    {
      if ( currNode.left == null )
        currNode.left = new Node(newVal);
      else
        insertRec( newVal, currNode.left );
    }
  } // End insertRec

  public static void inOrder(Node root)
  {
      if ( root == null )
          return;

      inOrder(root.left);
      System.out.println(root.val);
      inOrder(root.right);
  }

  static Node deleteRec(int valToDelete, Node currNode)
  {
    // If tree is empty or valToDelete is not in tree
    if ( currNode == null )
      return currNode;
    
    // We want to delete the node when they are equal
    if ( currNode.val == valToDelete )
    {
      // Check if left and right are null
      if ( currNode.left == null && currNode.right == null )
      {
        return null;
      }
      // Check if left has node and right is null
      else if ( currNode.left != null && currNode.right == null )
      {
        return currNode.left;
      }
      // check if right has node and left is null
      else if ( currNode.right != null && currNode.left == null )
      {
        return currNode.right;
      }
      // check if there is a right and left node
      else if ( currNode.right != null && currNode.left != null )
      {
        // swap with smallest value in right subtree ( next largest value in // the entire main BST )
        // Smallest will be guaranteed to only have 1 or less nodes
        currNode.val = findNextRec( currNode );
        // **** This is what I originally had ****
        // currNode.val = findMinRec( currNode.right );
        currNode.right = deleteRec( currNode.val, currNode.right );
      }
    }
    else if ( valToDelete > currNode.val )
    {
      currNode.right = deleteRec( valToDelete, currNode.right );
    }
    else
    {
      currNode.left = deleteRec( valToDelete, currNode.left );
    }

    return currNode;
  }

  // Returns the first node visited in inOrder traversal 
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

  public static boolean isBST(Node root, int topRoot)
    {

        boolean flag = true;
        
        if ( root == null )
            return true;
        if ( root.left == null && root.right == null )
            return true;
        
        if ( root.val == topRoot ) // This is the very first currNode
        {
            if ( root.left != null)
          {  if ( root.left.val < root.val )
                flag = isBST(root.left, topRoot);
            else
                return false;}
          if ( root.right != null)
          {  
            if ( root.right.val > root.val )
                flag = isBST(root.right, topRoot);
            else
                return false;
          }
        }
        
        if ( root.val < topRoot ) // This is a left subtree
        {
          if ( root.left != null)
            {  if ( root.left.val < root.val )
                  flag = isBST(root.left, topRoot);
              else
                  return false;}
          if ( root.right != null)
            {  if ( root.right.val > root.val && root.right.val < topRoot )
                  flag = isBST(root.right, topRoot);
                else
                  return false;}
          }
        
        if ( root.val > topRoot ) // This is a right 
        {
          if ( root.right != null)
          {  if ( root.right.val > root.val )
                flag = isBST(root.right, topRoot);
            else
                return false;}
        if ( root.left != null)
          {  if ( root.left.val < root.val && root.left.val > topRoot )
                flag = isBST(root.left, topRoot);
            else
                return false;}
        }
        
        return flag;
        
    }

  public static void checkIsBST( Node root )
  {
    if (isBST(root, root.val))
      System.out.println("true");
    else
      System.out.println("false");
  }

  public static void main(String[] args) {
    Node root = new Node(7);

    


    insertRec(8, root);
    insertRec(3, root);
    insertRec(12, root);
    insertRec(1, root);
    insertRec(4, root);

    checkIsBST( root );
    inOrder( root );

    root = deleteRec(3, root);
    insertRec(3, root);

    checkIsBST( root );
    inOrder( root );

    System.out.println(findMaxRec(root));

    

  }

}

