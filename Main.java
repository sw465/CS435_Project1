import java.util.*;


class Main
{
    public static void main(String[] args) {

        // Create BST and AVL trees that will insert a random array
        AVL avl = new AVL();
        BST bst = new BST();

        long startTime;
        long endTime;
        
        ArrayList<Integer> randomArray = avl.getRandomArray(10000);

        startTime = System.nanoTime();

        for ( int i = 0; i < randomArray.size(); i++ )
            avl.insertIter(randomArray.get(i));

        endTime = System.nanoTime();
        long timeToInsert_AVL = (endTime - startTime);

        startTime = System.nanoTime();

        for ( int i = 0; i < randomArray.size(); i++ )
            bst.insertIter(randomArray.get(i));

        endTime = System.nanoTime();
        long timeToInsert_BST = (endTime - startTime);



        System.out.println("AVL random count = " + avl.traversalCount);
        System.out.println("BST random count = " + bst.traversalCount);



        ArrayList<Integer> sortedArray = avl.getSortedArray(10000);

        // Create BST and AVL trees that will insert a sorted array
        AVL avlSorted = new AVL();
        BST bstSorted = new BST();

        for ( int i = 0; i < sortedArray.size(); i++ )
            avlSorted.insertIter(sortedArray.get(i));
        
        for ( int i = 0; i < sortedArray.size(); i++ )
            bstSorted.insertIter(sortedArray.get(i));
        

        System.out.println("AVL sorted count = " + avlSorted.traversalCount);
        System.out.println("BST sorted count = " + bstSorted.traversalCount);
        System.out.println();


        // Timing delete functions
        startTime = System.nanoTime();

        for ( int i = 0; i < randomArray.size(); i++ )
            avl.deleteIter(randomArray.get(i));

        endTime = System.nanoTime();
        long timeToDelete_AVL = (endTime - startTime);


        startTime = System.nanoTime();

        for ( int i = 0; i < randomArray.size(); i++ )
            bst.deleteIter(randomArray.get(i));

        endTime = System.nanoTime();
        long timeToDelete_BST = (endTime - startTime);
        

        System.out.println("Time to input 10,000 elements into AVL tree = " + timeToInsert_AVL);
        System.out.println("Time to input 10,000 elements into BST tree = " + timeToInsert_BST);
        System.out.println();
        System.out.println("Time to delete 10,000 elements from AVL tree = " + timeToDelete_AVL);
        System.out.println("Time to delete 10,000 elements from BST tree = " + timeToDelete_BST);
        
    }
}

// https://people.ok.ubc.ca/ylucet/DS/AVLtree.html
// http://www.cs.ecu.edu/karl/3300/spr16/Notes/DataStructure/Tree/balance.html