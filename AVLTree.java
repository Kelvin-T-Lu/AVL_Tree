/**
 * An AVL Tree. 
 * AVL Tree inherited's methods was written by Professor Tessema M. Mengistu.
 * @author Kelvin Lu
 * @param <T> The data type used of the stored data elements. 
 */
public class AVLTree<T extends Comparable<? super T>> extends BinarySearchTree<T> {
	//Tester methods
   /*public static void main(String[] arg){
      AVLTree<Integer> tree= new AVLTree<Integer>(); 
      Integer x[] = {15, 9, 17, 5 ,16, 13, 7, 14, 26,27,8};
      for(Integer i: x) {
    	  tree.add(i); 
      }
      tree.remove(14);
      tree.remove(13); 
      tree.remove(17);
      tree.remove(26);
      tree.remove(16);
   }*/
	public AVLTree() {
		super();
	}

	public AVLTree(T rootEntry) {
		super();
		setRootNode(new AVLNode<T>(rootEntry)); 
	}

	/**
	 * {@inheritDoc}
	 */
	public T add(T data) { 
		//Root Node was added. 
		if(isEmpty()) {
			setRootNode(new AVLNode<T>(data)); 
			return null;
		}
		T result = super.add(data); 
		//Converts the Binary Node into a AVLNode<T>. 
		AVLNode<T> rootN = (AVLNode<T>) getRootNode(); 
		//Updates the height of root.
		rootN.setLeftChild(getRootNode().getLeftChild());
		if(result == null) {//A new node was added. 
			AVLNode<T> temp = (AVLNode<T>) findParentNode(getRootNode(), data);
			if(isLeftChild(temp,data)) {
				temp.setLeftChild(new AVLNode<T>(data));
			}else {
				temp.setRightChild(new AVLNode<T>(data));
			}
			updateHeight((AVLNode<T>)getRootNode()); 
		}
		
		//Re-balance Tree if applicable. 
		BinaryNode<T> unbalancedNode = findUnbalanceNode(findParentNode(getRootNode(),data)); 
		while(unbalancedNode != null) {
			//Sets a root-node if root-node is being re-balanced. 
			if(compareTo(getRootNode(), unbalancedNode)==0){
				setRootNode(rebalance(unbalancedNode)); 
				updateHeight((AVLNode<T>) getRootNode()); 
				return result; 
			}
			BinaryNode<T> parent = findParentNode(getRootNode(), unbalancedNode.getData());
			if(isLeftChild(parent, unbalancedNode.getData())) {
				parent.setLeftChild(rebalance(unbalancedNode));
			}else {
				parent.setRightChild(rebalance(unbalancedNode));
			}

			updateHeight((AVLNode<T>) getRootNode()); 
			unbalancedNode = findUnbalanceNode(findParentNode(getRootNode(), data)); 
		}
		return result;
	}
	
	private void updateHeight(AVLNode<T> node) {

		if(node.isLeaf()) {
			node.setLeftChild(null); 
		}
		
		AVLNode<T> temp; 
		if(node.hasLeftChild()) {
			temp = (AVLNode<T>) node.getLeftChild();
			node.setLeftChild(temp);
			updateHeight(temp);
		}
		
		if(node.hasRightChild()) {
			temp = (AVLNode<T>) node.getRightChild();
			node.setRightChild(temp);; 
			updateHeight(temp); 
		}
	}
	/**
	 * {@inheritDoc}
	 */
	public T remove(T entry) {
		//Entry does not exists and no removal was formed. 
		if(!contains(entry)) {
			return null;
		}
		BinaryNode<T> temp = findParentNode(getRootNode(), entry);
		boolean isLeftChild = isLeftChild(temp, entry);
		
		T result = super.remove(entry);
		
		//Sets the location of balanced node to the location of
		//the removed entry if the entry was not a leaf.
		if(isLeftChild && temp.hasLeftChild()) {
			temp = temp.getLeftChild(); 
		}else if(!isLeftChild && temp.hasRightChild()){
			temp = temp.getRightChild();
		}
		//Re-balance Tree if applicable. 
		BinaryNode<T> unbalancedNode = findUnbalanceNode(temp); 
		while(unbalancedNode != null) {
			//Sets a root-node if root-node is being re-balanced. 
			if(compareTo(getRootNode(), unbalancedNode)==0){
				setRootNode(rebalance(unbalancedNode)); 
				updateHeight((AVLNode<T>) getRootNode()); 
				return result; 
			}
			BinaryNode<T> parent = findParentNode(getRootNode(), unbalancedNode.getData());
			//Sets the new nodes after being rebalanced. 
			if(isLeftChild(parent, unbalancedNode.getData())) {
				parent.setLeftChild(rebalance(unbalancedNode));
			}else {
				parent.setRightChild(rebalance(unbalancedNode));
			}

			updateHeight((AVLNode<T>) getRootNode()); 
			unbalancedNode = findUnbalanceNode(temp);
		}
		return result;
	}
	
	
	/**
	 * Checks if a node is balanced with a difference of 1. 
	 * @param node The node being checked. 
	 * @return True if node is balanced, false otherwise. 
	 */
	private boolean isBalance(BinaryNode<T> node) {
		if(node.isLeaf()) {
			return true;
		}

		//Assertion: The current node has one child.
		
		//One Right child and no left child.
		if(!node.hasLeftChild() || !node.hasRightChild()) {
			return (node.getHeight() <= 1);
		}
		
		//Assertion: The current Node has two children. 
		int x = node.getLeftChild().getHeight(); 
		int y = node.getRightChild().getHeight();
		return (Math.abs(x-y))<=1; 
	}
	
	/**
	 * Determines if an element is apart of the the node's left child subtree. 
	 * @return True, if element is apart of the left child subtree, false for right Child subtree. 
	 */
	private boolean isLeftChild(BinaryNode<T> node, T element) {
		//Assumptions: Element is not = to the current node. 
		
		if(element.compareTo(node.getData()) <0) {
			return true; 
		}
		else {
			return false;
		}
	}
	
	private int compareTo(BinaryNode<T> node1, BinaryNode<T> node2) {
		return node1.getData().compareTo(node2.getData());
	}
	
	/**
	 * Gets the sub-tree where element is rooted at from node.
	 * @param node The Node being derived. 
	 * @param element The comparison element. 
	 * @return The sub-tree of where element is located in. 
	 */
	private BinaryNode<T> getChild(BinaryNode<T> node, T element){
		//Assumptions: 
		//Element is in the tree. 
		//Element does not equal to the current node. 
		if(isLeftChild(node, element)) {
			return node.getLeftChild(); 
		}
		else {
			return node.getRightChild(); 
		}
	}
	
	private BinaryNode<T> findParentNode(BinaryNode<T> node, T element) {
		//Assumptions: 
		//Tree of height>= one, RootNode will be parent. 
		
		//Tree with a height > 1. 
		BinaryNode<T> temp = getChild(node, element); 
		if(temp.getData().compareTo(element) ==0) {
			return node; 
		}
		return findParentNode(temp, element); 
	}
	

	
	private BinaryNode<T> rebalance(BinaryNode<T> grandParent) {
		//If unbalanced, RootNode is assumed to have a height greater than >2
		
		BinaryNode<T> parent = traverseDown(grandParent.getLeftChild(), grandParent.getRightChild());
		BinaryNode<T> child = traverseDown(parent.getLeftChild(), parent.getRightChild());
	
		//Left subtree
		if(isLeftChild(grandParent, parent.getData())){
			//Left subtree
			if(isLeftChild(parent, child.getData())) {//LL Rotation
				return rotateRight(grandParent); 
			}
			else {//LR Rotation
				grandParent.setLeftChild(rotateLeft(parent));
				return rotateRight(grandParent);
			}
		}else {//Right Subtree
			if(!isLeftChild(parent, child.getData())) {//RR Rotation
				return rotateLeft(grandParent); 
			}else {//RL rotation
				grandParent.setRightChild(rotateRight(parent));
				return rotateLeft(grandParent); 
			}
		}
	}
	
	/**
	 * Find the unbalanced node at the greatest height level. 
	 * @param node The parent node of the element being added/removed.
	 * @return The next node from bottom to top of the tree that is unbalanced. 
	 */
	private BinaryNode<T> findUnbalanceNode(BinaryNode<T> parent) {
		//Parent of the child will be 
		//No unbalanced Nodes. 
		if(compareTo(parent, getRootNode())==0 &&isBalance(getRootNode())) {
			return null;
		}
		if(!isBalance(parent)) {
			return parent; 
		}
		return findUnbalanceNode(findParentNode(getRootNode(), parent.getData()));

	}
	
	/**
	 * Return the child tree with the greater height. 
	 * @param leftChild The left child. 
	 * @param rightChild The right child. 
	 * @return
	 */
	private BinaryNode<T> traverseDown(BinaryNode<T> leftChild,BinaryNode<T> rightChild ){
		
		//One child.
		if(leftChild == null) {
			return rightChild; 
		}else if(rightChild ==null) {
			return leftChild; 
		}
		
		//Two Children.
		if(leftChild.getHeight() < rightChild.getHeight()) {
			return rightChild; 
		}
		return leftChild; 
	}
	/**
	 * AVL Tree left rotation. 
	 * @param gp The grandpa node of the child. 
	 * @return The parent node after rotation. 
	 */
	private BinaryNode<T> rotateLeft(BinaryNode<T> gp) {
		BinaryNode<T> parent = gp.getRightChild(); 
		gp.setRightChild(parent.getLeftChild());
		parent.setLeftChild(gp);; 
		return parent; 
	}
	/**
	 * AVL Tree right rotation.
	 * @param gp Grandpa node for the rotation. 
	 * @return The parent node after rotation.
	 */
	private BinaryNode<T> rotateRight(BinaryNode<T> gp) {
		BinaryNode<T> parent = gp.getLeftChild(); 
		gp.setLeftChild(parent.getRightChild());
		parent.setRightChild(gp);; 
		return parent; 
	}
	
}