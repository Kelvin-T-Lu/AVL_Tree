/**
 * A node of an AVL tree.
 * Binary Node template provided by Professor Tessema M. Mengistu. 
 * @author Kelvin Lu
 * @param <T> The data type of the data contained in Node. 
 */
public class AVLNode<T> extends BinaryNode<T> {
	/** 
	 * The height of the Node in a tree.  
	 */
	private int height; 

	/**
	 * The height of the current Node in a tree. 
	 */
	@Override public int getHeight() {
		return this.height;
	}


	/**
	 * An AVL node with empty attributes. 
	 */
	public AVLNode() {
		super(); 
		height = -1; 
	}

	/**
	 * An AVL node with T data as root. 
	 * @param data
	 */
	public AVLNode(T data) {
		super(data); 
		height = 0; 
	}

	/**
	 * An AVLNode with a root data and sub tree nodes. 
	 * @param data
	 * @param leftNode
	 * @param rightNode
	 */
	public AVLNode(T data, AVLNode<T> leftNode, AVLNode<T> rightNode) {
		super(data); 
		setLeftChild(leftNode);
		setRightChild(rightNode);
		//Height already called in the set trees. 
	}

	/**
	 * Sets the left child of this node.
	 */
	public void setLeftChild(AVLNode<T> leftChild) {
		super.setLeftChild(leftChild);
		height = super.getHeight(); 
	}

	/**
	 * Sets the right child of this node.
	 */
	public void setRightChild(AVLNode<T> rightChild) {
		super.setRightChild(rightChild);
		height = super.getHeight(); 
	}

}
