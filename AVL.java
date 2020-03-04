import java.util.*;


class AVL {

    class Node 
    {

        Node left;
        Node right;
        Node parent;
        int val;
        int height;
        // states whether the node comes from the left or right child of the parent
        // value "root" if root of the tree and has no parent
        String parentsLeftOrRightChild;

        Node(int newVal)
        {
            val = newVal;
            left = null;
            right = null;
            parent = null;
            height = 1;
            parentsLeftOrRightChild = "root";
        }
    } 

  Node root;
  int traversalCount = 0;

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
                currNode.right.parent = currNode;
                currNode.right.parentsLeftOrRightChild = "r";
                reBalanceIter(currNode, newVal);
                break;
            }
            else
            {
                currNode = currNode.right;
                traversalCount = traversalCount + 1;
            }


        }
        if ( newVal < currNode.val)
        {
            // Add new value when encountering a left null child
            if ( currNode.left == null )
            {
                currNode.left = new Node(newVal);
                currNode.left.parent = currNode;
                currNode.left.parentsLeftOrRightChild = "l";
                reBalanceIter(currNode, newVal);
                break;
            }
            else
            {
                currNode = currNode.left;
                traversalCount = traversalCount + 1;
            }
        }
        // Duplicate values won't be added
        if ( newVal == currNode.val )
            return;

    } // End while loop

} 

// Called to rebalance tree after inserting or deleting from the iter functions
void reBalanceIter( Node currNode, int newVal )
{

	while ( currNode != null )
	{
		currNode.height = 1 + getMaxHeight( currNode.left, currNode.right );
		int balanceFactor = getBalanceFactor( currNode );

		// BF is right subtree height - left subtree height. 
		// BF > 1 is larger right subtree. BF < -1 is larger left subtree
		// Getting val of right or left node will only be called if unbalanced in which case right or left will not be null

		// Check for larger right subtree; right right
		if ( balanceFactor > 1 && currNode.right.val < newVal )
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = leftRotate( currNode );
				this.root.parent = null;
				this.root.left.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = leftRotate( currNode );
			else
			    currNode.parent.left = leftRotate( currNode );
		}    
		// Check for larger right subtree; right left
		else if ( balanceFactor > 1 && currNode.right.val > newVal )
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = RL_Rotate( currNode );
				this.root.parent = null;
				this.root.left.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = RL_Rotate( currNode );
			else
			    currNode.parent.left = RL_Rotate( currNode );
		}   
			

		// Check for larger left subtree; left left
		else if ( balanceFactor < -1 && currNode.left.val > newVal ) 
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = rightRotate( currNode );
				this.root.parent = null;
				this.root.right.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = rightRotate( currNode );
			else
			    currNode.parent.left = rightRotate( currNode );

		}   

		// Check for larger left subtree; left right
		else if ( balanceFactor < -1 && currNode.left.val < newVal ) 
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = LR_Rotate( currNode );
				this.root.parent = null;
				this.root.right.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = LR_Rotate( currNode );
			else
			    currNode.parent.left = LR_Rotate( currNode );
		}   

        // Traverse through all parents since they all may have been affected by insert/delete
		currNode = currNode.parent;
	}// End while loop
}

  public void insertRec( Node currNode, int newVal )
  {
    // set root node ff tree is empty
    if ( this.root == null ) {
        this.root = new Node(newVal);
        return;
    }

    // AVL is empty
    if ( currNode == null )
      currNode = new Node(newVal);

    // newVal already in AVL
    if ( currNode.val == newVal )
      return;

    // If newVal is greater than current currNode, add newVal to right side or check 
    // right currNode
    if ( newVal > currNode.val )
    {
        if ( currNode.right == null )
        {
            currNode.right = new Node(newVal);
            currNode.right.parent = currNode;
            currNode.right.parentsLeftOrRightChild = "r";
        }
        else
            insertRec( currNode.right, newVal );
    }
    else
    {
        if ( currNode.left == null )
        {
            currNode.left = new Node(newVal);
            currNode.left.parent = currNode;
            currNode.left.parentsLeftOrRightChild = "l";
        }
        else
            insertRec( currNode.left, newVal);
    }

    currNode.height = 1 + getMaxHeight( currNode.left, currNode.right );
    int balanceFactor = getBalanceFactor( currNode );

    // BF is right subtree height - left subtree height. 
    // BF > 1 is larger right subtree. BF < -1 is larger left subtree
    // Getting val of right or left node will only be called if unbalanced in which case right or left will not be null

    // Check for larger right subtree; right right
    if ( balanceFactor > 1 && currNode.right.val < newVal )
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {

            this.root = leftRotate( currNode );
            this.root.parent = null;
            this.root.left.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
        {
          currNode.parent.right = leftRotate( currNode );
        }
        else
        {
          currNode.parent.left = leftRotate( currNode );
        }
    }    
    // Check for larger right subtree; right left
    else if ( balanceFactor > 1 && currNode.right.val > newVal )
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = RL_Rotate( currNode );
            this.root.parent = null;
            this.root.left.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
        {
          currNode.parent.right = RL_Rotate( currNode );
        }
        else
        {
          currNode.parent.left = RL_Rotate( currNode );
        }
   }   
        

    // Check for larger left subtree; left left
    else if ( balanceFactor < -1 && currNode.left.val > newVal ) 
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = rightRotate( currNode );
            this.root.parent = null;
            this.root.right.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
        {
          currNode.parent.right = rightRotate( currNode );
        }
        else
        {
          currNode.parent.left = rightRotate( currNode );
        }
   }   

    // Check for larger left subtree; left right
    else if ( balanceFactor < -1 && currNode.left.val < newVal ) 
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = LR_Rotate( currNode );
            this.root.parent = null;
            this.root.right.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
        {
          currNode.parent.right = LR_Rotate( currNode );
        }
        else
        {
          currNode.parent.left = LR_Rotate( currNode );
        }
   }   
  } // End insertRec

  public void inOrder(Node root)
  {
      if ( root == null )
          return;

        inOrder(root.left);
        if ( root.parent != null )
            System.out.print( root.val + " " + root.parent.val + " " + root.height + " " + root.parentsLeftOrRightChild );
        else
            System.out.print( root.val + " null " + root.height + " " + root.parentsLeftOrRightChild );
        System.out.println();
        inOrder(root.right);
  }

  Node deleteIter( int valToDelete )
  {
    Node currNode = this.root;

    Node root = currNode;
    // Check if tree is empty
    if ( currNode == null )
      return null;

    while ( currNode != null )
    {
      if ( currNode.val == valToDelete )
      {
        if ( currNode.left == null && currNode.right == null )
        {
			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = null;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = null;
			else
				currNode.parent.left = null;

			reBalanceAfterDeleteIter(currNode.parent);
			break;
        }
        // Set the parent to the entire left subtree of the node we delete
        if ( currNode.left != null && currNode.right == null )
        {
			currNode.left.parent = currNode.parent;
			currNode.left.parentsLeftOrRightChild = currNode.parentsLeftOrRightChild;
			
			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = currNode.left;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = currNode.left;
			else
				currNode.parent.left = currNode.left;

			reBalanceAfterDeleteIter(currNode.parent);
         	break;
        }
        // Set the parent to the entire right subtree of the node we delete
        if ( currNode.right != null && currNode.left == null )
        {
            // Child of node to delete will have a new parent, node to delete's parent
			currNode.right.parent = currNode.parent;
			currNode.right.parentsLeftOrRightChild = currNode.parentsLeftOrRightChild;

			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = currNode.right;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = currNode.right;
			else
				currNode.parent.left = currNode.right;

			reBalanceAfterDeleteIter(currNode.parent);
            break;
		}
		
        // convert the value of the node to be deleted to the next largest value.
        // Continue traversing tree to delete the next highest value node
        if ( currNode.left != null && currNode.right != null )
        {
			// Replace currNode with the next largest value and update valToDelete 
			// so the original next largest value node will get deleted
			currNode.val = findNextIter( currNode );
			valToDelete = currNode.val;
			// Since we replace currNode with the next largest, our next step is to the right
			currNode = currNode.right;
        }
      }
      else if ( valToDelete > currNode.val )
        currNode = currNode.right;
      else
        currNode = currNode.left;

    }
    
    return root;
}

void reBalanceAfterDeleteIter( Node currNode )
{
	while ( currNode != null )
	{
		currNode.height = 1 + getMaxHeight( currNode.left, currNode.right );
		int balanceFactor = getBalanceFactor( currNode );

		// BF is right subtree height - left subtree height. 
		// BF > 1 is larger right subtree. BF < -1 is larger left subtree
		// Getting val of right or left node will only be called if unbalanced in which case right or left will not be null

		// Check for larger right subtree; right right
		if ( balanceFactor > 1 && getBalanceFactor( currNode.right ) >= 0 )
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{

				this.root = leftRotate( currNode );
				this.root.parent = null;
				this.root.left.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = leftRotate( currNode );
			else
			    currNode.parent.left = leftRotate( currNode );
		}    
		// Check for larger right subtree; right left
		else if ( balanceFactor > 1 && getBalanceFactor( currNode.right ) < 0 )
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = RL_Rotate( currNode );
				this.root.parent = null;
				this.root.left.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = RL_Rotate( currNode );
			else
			    currNode.parent.left = RL_Rotate( currNode );
		}   
			

		// Check for larger left subtree; left left
		else if ( balanceFactor < -1 && getBalanceFactor( currNode.left ) <= 0 ) 
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = rightRotate( currNode );
				this.root.parent = null;
				this.root.right.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = rightRotate( currNode );
			else
			    currNode.parent.left = rightRotate( currNode );
		}   

		// Check for larger left subtree; left right
		else if ( balanceFactor < -1 && getBalanceFactor( currNode.left ) > 0 ) 
		{
			if ( currNode.parentsLeftOrRightChild == "root" )
			{
				this.root = LR_Rotate( currNode );
				this.root.parent = null;
				this.root.right.parent = this.root;
				this.root.parentsLeftOrRightChild = "root";
			}
			else if ( currNode.parentsLeftOrRightChild == "r" )
			    currNode.parent.right = LR_Rotate( currNode );
			else
			    currNode.parent.left = LR_Rotate( currNode );
		}

		currNode = currNode.parent;
	} // End hile loop
}

void deleteRec( Node currNode, int valToDelete )
{
    
    // If tree is empty or valToDelete is not in tree
    if ( currNode == null )
    	return;
    
    if ( currNode.val == valToDelete )
    {
		if ( currNode.left == null && currNode.right == null )
		{
			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = null;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = null;
			else
				currNode.parent.left = null;
		}
		if ( currNode.left != null && currNode.right == null )
		{
			currNode.left.parent = currNode.parent;
			currNode.left.parentsLeftOrRightChild = currNode.parentsLeftOrRightChild;
			
			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = currNode.left;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = currNode.left;
			else
				currNode.parent.left = currNode.left;
		}
		if ( currNode.right != null && currNode.left == null )
		{
			currNode.right.parent = currNode.parent;
			currNode.right.parentsLeftOrRightChild = currNode.parentsLeftOrRightChild;

			if ( currNode.parentsLeftOrRightChild == "root")
				this.root = currNode.right;
			else if ( currNode.parentsLeftOrRightChild == "r" )
				currNode.parent.right = currNode.right;
			else
				currNode.parent.left = currNode.right;
		}
		if ( currNode.right != null && currNode.left != null )
		{
			// swap with smallest value in right subtree ( next largest value in the entire main AVL )
			// Smallest will be guaranteed to only have 1 or less nodes
			currNode.val = findNextRec( currNode );
			// Delete the smallest value node in right subtree that was swapped
			deleteRec( currNode.right, currNode.val  );
		}
    }
    else if ( valToDelete > currNode.val )
    {
    	deleteRec( currNode.right, valToDelete  );
    }
    else
    {
    	deleteRec( currNode.left, valToDelete  );
	}

	currNode.height = 1 + getMaxHeight( currNode.left, currNode.right );
    int balanceFactor = getBalanceFactor( currNode );

    // BF is right subtree height - left subtree height. 
    // BF > 1 is larger right subtree. BF < -1 is larger left subtree
    // Getting val of right or left node will only be called if unbalanced in which case right or left will not be null

    // Check for larger right subtree; right right
    if ( balanceFactor > 1 && getBalanceFactor( currNode.right ) >= 0 )
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {

            this.root = leftRotate( currNode );
            this.root.parent = null;
            this.root.left.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
          currNode.parent.right = leftRotate( currNode );
        else
          currNode.parent.left = leftRotate( currNode );
    }    
    // Check for larger right subtree; right left
    else if ( balanceFactor > 1 && getBalanceFactor( currNode.right ) < 0 )
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = RL_Rotate( currNode );
            this.root.parent = null;
            this.root.left.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
          currNode.parent.right = RL_Rotate( currNode );
        else
          currNode.parent.left = RL_Rotate( currNode );
   }   
        

    // Check for larger left subtree; left left
    else if ( balanceFactor < -1 && getBalanceFactor( currNode.left ) <= 0 ) 
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = rightRotate( currNode );
            this.root.parent = null;
            this.root.right.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
          currNode.parent.right = rightRotate( currNode );
        else
          currNode.parent.left = rightRotate( currNode );
   }   

    // Check for larger left subtree; left right
    else if ( balanceFactor < -1 && getBalanceFactor( currNode.left ) > 0 ) 
    {
        if ( currNode.parentsLeftOrRightChild == "root" )
        {
            this.root = LR_Rotate( currNode );
            this.root.parent = null;
            this.root.right.parent = this.root;
            this.root.parentsLeftOrRightChild = "root";
        }
        else if ( currNode.parentsLeftOrRightChild == "r" )
          currNode.parent.right = LR_Rotate( currNode );
        else
          currNode.parent.left = LR_Rotate( currNode );
   }  
	
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


int getBalanceFactor( Node node )
{
    int rightHeight = getHeight( node.right );
    int leftHeight = getHeight( node.left );

    return rightHeight - leftHeight;
}

int getMaxHeight( Node left, Node right )
{
    int max = 0;
    int leftHeight = getHeight(left);
    int rightHeight = getHeight(right);

    if ( leftHeight >= rightHeight )
        max = leftHeight;
    else
        max = rightHeight;

    return max;
}

int getHeight( Node node )
{
    if ( node == null )
        return 0;
    
    return node.height;
}

Node rightRotate( Node node )
{
    Node leftSubTree = node.left;
    Node leftRightSubTree = node.left.right;

    leftSubTree.right = node;
    leftSubTree.parent = node.parent;
    node.parent = leftSubTree;
    node.left = leftRightSubTree;
    if ( leftRightSubTree != null )
    {
        leftRightSubTree.parent = node;
        leftRightSubTree.parentsLeftOrRightChild = "l";
    }
    leftSubTree.parentsLeftOrRightChild = node.parentsLeftOrRightChild;
    node.parentsLeftOrRightChild = "r";

    node.height = 1+ getMaxHeight( node.left, node.right );
    leftSubTree.height = 1 + getMaxHeight( leftSubTree.left , leftSubTree.right );

    return leftSubTree;
}

Node leftRotate( Node node )
{
    Node rightSubTree = node.right;
    Node rightLeftSubTree = node.right.left;

    rightSubTree.left = node;
    rightSubTree.parent = node.parent;
    node.parent = rightSubTree;
    node.right = rightLeftSubTree;
    if ( rightLeftSubTree != null )
    {
        rightLeftSubTree.parent = node;
        rightLeftSubTree.parentsLeftOrRightChild = "r";
    }
    rightSubTree.parentsLeftOrRightChild = node.parentsLeftOrRightChild;
    node.parentsLeftOrRightChild = "l";

    node.height = 1 + getMaxHeight( node.left, node.right );
    rightSubTree.height = 1 + getMaxHeight( rightSubTree.left , rightSubTree.right );

    return rightSubTree;
}

Node LR_Rotate( Node node )
{
    node.left = leftRotate( node.left );
    return rightRotate( node );
}

Node RL_Rotate( Node node )
{
    node.right = rightRotate( node.right );
    return leftRotate( node );
}


  static List<Integer> sort( List<Integer> unsortedList )
  {
    System.out.println("test");
    AVL avl = new AVL();
    
    for (int i = 0; i < unsortedList.size(); i++) 
      avl.insertIter(unsortedList.get(i));

    List<Integer> sortedList = new ArrayList<Integer>();

    createInOrderList(sortedList, avl.root);

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

  public ArrayList<Integer> getRandomArray( int n )
  {
    ArrayList<Integer> array = new ArrayList<>(n);

    array.add((int)(Math.random() * 100) + 1);
    for ( int i = 1; i < n; i++ )
        array.add(array.get(i - 1) + (int)(Math.random() * 100) + 1 );

    Collections.shuffle(array);

    return array;
  }

  public ArrayList<Integer> getSortedArray( int n )
  {
    ArrayList<Integer> array = new ArrayList<>(n);

    for ( int i = n; i > 0; i-- )
        array.add(i);

    return array;
  }

}




