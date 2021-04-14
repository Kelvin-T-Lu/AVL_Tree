
import java.util.*;

public class Driver<T extends Comparable<T>> {
	
	
	AVLTree<T> make_tree(T[] arr){
		AVLTree<T> tree = new AVLTree<T>();
		for (T i: arr) {
			tree.add(i);
		}
		return tree;
	}

	public String display(AVLTree<T> tree)
	{
		Queue<AVLNode<T>> nodeQueue = new ArrayDeque<AVLNode<T>>();
		AVLNode<T> root = (AVLNode<T>)tree.getRootNode();
		String result ="";

		if (root != null)
			nodeQueue.add(root);
 		int nodesPerLevel = 1;
		while (!nodeQueue.isEmpty()) {
			result+=displayNextLevel(nodesPerLevel, nodeQueue);
	//	result+="\n";
		}
		return result;
	} // end display

	private String displayNextLevel(int nodesPerLevel, Queue<AVLNode<T>> nodeQueue)
	{
		int nextLevelCount = 0;
		String result ="";
		for (int count = 0; count < nodesPerLevel; count++)
		{
			if (!nodeQueue.isEmpty())
			{
				AVLNode<T> nextNode = nodeQueue.remove();
				AVLNode<T> leftChild = (AVLNode<T>)nextNode.getLeftChild();
				AVLNode<T> rightChild = (AVLNode<T>)nextNode.getRightChild();

				// add to queue in order of recursive calls
				if (leftChild != null)
				{	nodeQueue.add(leftChild);
					nextLevelCount++;
				} // end if

				if (rightChild != null)
				{	nodeQueue.add(rightChild);
					nextLevelCount++;
				} // end if

				result+=nextNode.getData() + " ";//
			}
			else
				{  result+="\n";}
			
		} // end for
		//System.out.println();
		//result+="\n";
		return result;
	} // end displayNextLevel
	
	
}