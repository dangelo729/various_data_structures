package project4;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Stack;
import java.lang.reflect.ParameterizedType;
/*
An implementation of a binary search tree. The elements are ordered using their natural ordering.
This implementation provides guaranteed O(H) (H is the height of this tree which could be as low as logN for balanced trees, but could be as large as N for unbalanced trees) time cost for the basic operations (add, remove and contains).

This class implements many of the methods provided by the Java framework's TreeSet class. .
 @author Peter D'Angelo
*/
public class BST<E extends Comparable<E>> implements Iterable<E> {
	private Node<E> root;
	int size = 0;

	Class<E> type = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass())
			.getActualTypeArguments()[0];

	/*
	 * this is the node class for the BST it stores a node with a generic variable
	 */
	private class Node<E extends Comparable<E>> {

		private E data;
		private Node<E> leftChild;
		private Node<E> rightChild;
		private Node<E> parent;

		/*
		 * this is the node constructor
		 * 
		 * @param E d, generic value to be stored
		 */
		private Node(E d) {
			data = d;
		}

		/*
		 * //parent getter
		 * 
		 * @return parent Node<E>
		 */
		public Node<E> getParent() {
			return parent;

		}

		/*
		 * parent setter
		 * 
		 * @param node
		 */
		public void setParent(Node<E> node) {
			parent = node;

		}

		/*
		 * data getter
		 * 
		 * @return data
		 */
		public E getData() {
			return data;
		}

		/*
		 * data setter
		 * 
		 * @param E d
		 */
		public void setData(E d) {
			data = d;
		}

		/*
		 * left child setter
		 * 
		 * @param node left
		 */
		public void setLeft(Node<E> left) {
			leftChild = left;
		}

		/*
		 * right child setter
		 * 
		 * @param node right
		 */
		public void setRight(Node<E> right) {

			rightChild = right;
		}

		/*
		 * left getter
		 * 
		 * @return left node
		 */
		public Node<E> getLeft() {
			return leftChild;
		}

		/*
		 * right getter
		 * 
		 * @return right node
		 */
		public Node<E> getRight() {
			return rightChild;
		}

	}

	/*
	 * Adds the specified element to this set if it is not already present. More
	 * formally, adds the specified element e to this tree if the set contains no
	 * element e2 such that Objects.equals(e, e2). If this set already contains the
	 * element, the call leaves the set unchanged and returns false. This operation
	 * should be O(H).
	 * 
	 * @param e - element to be added to this set
	 * 
	 * @return true if this set did not already contain the specified element
	 * 
	 * @throws NullPointerException - if the specified element is null and this set
	 * uses natural ordering, or its comparator does not permit null elements
	 */
	public boolean add(E e) {
		if (e == null) {
			throw new NullPointerException("Error: cannot add null value to BST");
		}
		if (isEmpty()) {
			root = new Node<>(e);
			size++;
			return true;
		} else {
			
			return add(e, root);

		}
	}

//helper method for the add method
	private boolean add(E e, Node<E> node) {
		if (e.compareTo(node.getData()) < 0) {
			if (node.getLeft() == null) {
				Node<E> addNode = new Node<>(e);
				addNode.setParent(node);
				size++;
				node.setLeft(addNode);

				return true;
			} else {
				return add(e, node.getLeft());
			}
		} else if (e.compareTo(node.getData()) > 0) {
			if (node.getRight() == null) {
				Node<E> addNode = new Node<>(e);
				addNode.setParent(node);
				size++;
				node.setRight(addNode);

				return true;
			} else {
				return add(e, node.getRight());
			}
		} else {
			return false;
		}

	}

	/*
	 * Returns the height of this tree. The height of a leaf is 1. The height of the
	 * tree is the height of its root node. This operation should be O(1).
	 * 
	 * @return the height of this tree or zero if the tree is empty
	 */
	public int height() {
		if (isEmpty()) {
			return 0;
		}
		return findHeight(root);
	}

//helper method for my height finder method
	private int findHeight(Node<E> node) {
		if (node == null) {
			return -1;
		}

		int lefth = findHeight(node.getLeft());
		int righth = findHeight(node.getRight());

		if (lefth > righth) {
			return lefth + 1;
		} else {
			return righth + 1;
		}
	}

	/*
	 * Returns the least element in this tree greater than or equal to the given
	 * element, or null if there is no such element. This operation should be O(H).
	 * 
	 * @param e value to match
	 * 
	 * @return least element >=e
	 * 
	 * @throws ClassCastException - if the specified element cannot be compared with
	 * the elements currently in the set NullPointerException - if the specified
	 * element is null
	 */
	public E ceiling(E e) {
		if (e == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(e)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		// Check for empty tree
		if (root == null) {
			return null;
		}

		// Start at the root of the tree
		Node<E> current = root;

		// Keep track of the least element that is greater than or equal to the given
		// element
		E result = null;

		while (current != null) {
			// Compare the current element to the given element
			int comparison = e.compareTo(current.getData());

			// If the current element is greater than or equal to the given element, update
			// the result
			if (comparison <= 0) {
				result = current.getData();
				current = current.getLeft();
			} else {
				// If the current element is less than the given element, search for a ceiling
				// in the right subtree
				current = current.getRight();
			}
		}

		// Return the result
		return result;

	}

	/*
	 * Removes all of the elements from this set.
	 */
	public void clear() {
		size = 0;
		root = null;
	}

	/*
	 * Returns true if this set contains the specified element. More formally,
	 * returns true if and only if this set contains an element e such that
	 * Objects.equals(o, e).
	 * 
	 * @param o - object to be checked for containment
	 * 
	 * @returns true if there, false if not
	 * 
	 * @throws classCastException if obj cannot be compared, nullPointerException if
	 * o is null
	 */
	public boolean contains(Object o) {
		if (o == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(o)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		E e = convertToE(o, type);
		Node<E> current = root;
		while (current != null) {
			if (current.getData().compareTo(e) == 0) {
				return true;
			} else if (current.getData().compareTo(e) > 0) {
				current = current.getLeft();
			} else {
				current = current.getRight();
			}
		}
		return false;
	}

	// helpeer method used to convert objects to generic E type
	private static <E> E convertToE(Object o, Class<E> clazz) {
		try {
			return clazz.cast(o);
		} catch (ClassCastException e) {
			return null;
		}
	}

	/*
	 * Compares the specified object with this tree for equality. Returns true if
	 * the given object is also a tree, the two trees have the same size, and every
	 * member of the given tree is contained in this tree.
	 * 
	 * @param object to test
	 * 
	 * @returns true if equal, false if not
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj == this) {
			return true;
		}
		if (!(obj instanceof BST)) {
			return false;
		}
		BST<E> other = (BST<E>) obj;
		if (size() == other.size()) {
			Iterator<E> itr = other.iterator();
			Iterator<E> itr2 = iterator();
			while (itr2.hasNext() && itr.hasNext()) {
				if (itr.next().compareTo(itr2.next()) != 0) {
					return false;
				}
			}
		} else {

			return false;
		}
		return true;
	}

	/*
	 * Returns the first (lowest) element currently in this tree.
	 * 
	 * @return lowest item in list
	 * 
	 * @throws NoSuchElementException if set is empty
	 */
	public E first() {
		if (root == null) {
			throw new NoSuchElementException("Error: BST is empty");
		} else {
			Node<E> temp = root;
			while (temp.getLeft() != null) {
				temp = temp.getLeft();
			}
			return temp.getData();
		}

	}

	/*
	 * Returns the greatest element in this set less than or equal to the given
	 * element, or null if there is no such element.
	 * 
	 * @param e value to match
	 * 
	 * @returns greatest element <= e
	 * 
	 * @throws ClassCastException - if the specified element cannot be compared with
	 * the elements currently in the set NullPointerException - if the specified
	 * element is null
	 */
	public E floor(E e) {
		if (e == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(e)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		// Check for empty tree
		if (root == null) {
			return null;
		}

		// Start at the root of the tree
		Node<E> current = root;

		// Keep track of the greatest element that is less than or equal to the given
		// element
		E result = null;

		while (current != null) {
			// Compare the current element to the given element
			int comparison = e.compareTo(current.getData());

			// If the current element is less than or equal to the given element, update the
			// result
			if (comparison >= 0) {
				result = current.getData();
				current = current.getRight();
			} else {
				// If the current element is greater than the given element, search for a floor
				// in the left subtree
				current = current.getLeft();
			}
		}

		// Return the result
		return result;
	}

	/*
	 * Returns the element at the specified position in this tree. The order of the
	 * indexed elements is the same as provided by this tree's iterator. The
	 * indexing is zero based (i.e., the smallest element in this tree is at index 0
	 * and the largest one is at index size()-1).
	 * 
	 * @param index to return
	 * 
	 * @returns item at index
	 * 
	 * @throws IndexOutOfBoundsException - if the index is out of range (index < 0
	 * || index >= size())
	 */
	public E get(int index) {
		// Check for empty tree
		if (root == null) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: 0");
		}

		// Check for out of bounds index
		if (index < 0 || index >= size()) {
			throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size());
		}

		// Start at the root of the tree
		Node<E> current = root;

		// Keep track of the current position in the tree
		int pos = 0;

		while (current != null) {
			// If the current position is equal to the target index, return the element at
			// the current node
			if (pos == index) {
				return current.getData();
			}

			// Determine which subtree to search based on the current position and target
			// index
			if (pos < index) {
				// If the current position is less than the target index, search the right
				// subtree
				current = current.getRight();
				pos = (pos * 2) + 2;
			} else {
				// If the current position is greater than the target index, search the left
				// subtree
				current = current.getLeft();
				pos = (pos * 2) + 1;
			}
		}

		// If the target index was not found, return null
		return null;
	}

	/*
	 * Returns the least element in this tree strictly greater than the given
	 * element, or null if there is no such element.
	 * 
	 * @param e value to match
	 * 
	 * @returns least element > e or null
	 * 
	 * @throws ClassCastException - if the specified element cannot be compared with
	 * the elements currently in the set NullPointerException - if the specified
	 * element is null
	 */
	public E higher(E e) {
		if (e == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(e)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		// Check for empty tree
		if (root == null) {
			return null;
		}

		// Start at the root of the tree
		Node<E> current = root;

		// Keep track of the least element that is strictly greater than the given
		// element
		E result = null;

		while (current != null) {
			// Compare the current element to the given element
			int comparison = e.compareTo(current.getData());

			// If the current element is strictly greater than the given element, update the
			// result
			if (comparison < 0) {
				result = current.getData();
				current = current.getLeft();
			} else {
				// If the current element is greater than or equal to the given element, search
				// for a higher element in the right subtree
				current = current.getRight();
			}
		}

		// Return the result
		return result;
	}

	/*
	 * Returns true if this set contains no elements. \
	 * 
	 * @return boolean true or false
	 */
	public boolean isEmpty() {
		return root == null;

	}

	/*
	 * Returns an iterator over the elements in this tree in ascending order.
	 * 
	 * @returns Iterator<E>
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {
			private Stack<Node<E>> nodeStack = new Stack<>();
			private Node<E> current = root;

			@Override
			public boolean hasNext() {

				return (!nodeStack.isEmpty() || current != null);
			}

			@Override
			public E next() {
				while (current != null) {
					nodeStack.push(current);
					current = current.getLeft();
				}
				current = nodeStack.pop();
				Node<E> ret = current;
				current = current.getRight();
				return ret.getData();

			}

		};
	}

	/*
	 * Returns the last (highest) element currently in this tree.
	 * 
	 * @returns highest element in tree
	 */
	public E last() {
		if (root == null) {
			throw new NoSuchElementException("Error: BST is empty");
		} else {
			Node<E> temp = root;
			while (temp.getRight() != null) {
				temp = temp.getRight();
			}
			return temp.getData();
		}
	}
	/*
	 * Returns the greatest element in this set strictly less than the given
	 * element, or null if there is no such element.
	 * 
	 * @param e value to match
	 * 
	 * @returns greatest element less than e or null
	 * 
	 * @throwsClassCastException - if the specified element cannot be compared with
	 * the elements currently in the set NullPointerException - if the specified
	 * element is null
	 */
	
	public E lower(E e) {
		if (e == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(e)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		// Check for empty tree
		if (root == null) {
			return null;
		}

		// Start at the root of the tree
		Node<E> current = root;

		// Keep track of the greatest element that is strictly less than the given
		// element
		E result = null;

		while (current != null) {
			// Compare the current element to the given element
			int comparison = e.compareTo(current.getData());

			// If the current element is strictly less than the given element, update the
			// result
			if (comparison > 0) {
				result = current.getData();
				current = current.getRight();
			} else {
				// If the current element is less than or equal to the given element, search for
				// a lower element in the left subtree
				current = current.getLeft();
			}
		}

		// Return the result
		return result;
	}

	/*
	 * * Returns an iterator over the elements in this tree in postorder order.
	 * 
	 * @returns Iterator<E>
	 */
	public Iterator<E> postorderIterator() {
		return new Iterator<E>() {
			private Stack<Node<E>> nodeStack = new Stack<>();
			private Node<E> current = root;

			@Override
			public boolean hasNext() {

				return current != null || !nodeStack.isEmpty();
			}

			@Override
			public E next() {
				while (current != null) {
					nodeStack.push(current);
					current = current.getLeft();
				}

				Node<E> node = nodeStack.pop();
				current = node.getRight();

				return node.data;
			}

		};
	}
	/*
	 * * Returns an iterator over the elements in this tree in preorder order.
	 * 
	 * @returns Iterator<E>
	 */
	public Iterator<E> preorderIterator() {
		return new Iterator<E>() {

			private Stack<Node<E>> nodeStack = new Stack<>();
			boolean count = false;

			@Override
			public boolean hasNext() {
				if (count == false) {
					nodeStack.push(root);
					count = true;
				}
				return (!nodeStack.isEmpty());

			}

			@Override
			public E next() {
				if (count == false) {
					nodeStack.push(root);
					count = true;
				}
				Node<E> node = nodeStack.pop();
				if (node.getRight() != null) {
					nodeStack.push(node.getRight());
				}
				if (node.getLeft() != null) {
					nodeStack.push(node.getLeft());
				}
				return node.getData();
			}

		};
	}

	/*
	 * Removes the specified element from this tree if it is present. More formally,
	 * removes an element e such that Objects.equals(o, e), if this tree contains
	 * such an element. Returns true if this tree contained the element (or
	 * equivalently, if this tree changed as a result of the call). (This tree will
	 * not contain the element once the call returns.)
	 * 
	 * @param o obj to be removed
	 * 
	 * @return true if item is contained
	 * 
	 * @throws ClassCastException - if the specified object cannot be compared with
	 * the elements currently in this tree NullPointerException - if the specified
	 * element is null
	 * 
	 * 
	 */
	public boolean remove​(Object o) {
		
		if (o == null) {
			throw new NullPointerException("Error: Comparison value cannot be null");
		}
		if (!type.isInstance(o)) {
			throw new ClassCastException("Error: cannot add values of different type to bst");
		}
		Node<E> current = this.root;
		Node<E> parent = null;
		while (current != null) {
			int cmp = ((Comparable) o).compareTo(current.getData());
			if (cmp == 0) {
				break;
			} else if (cmp < 0) {
				parent = current;
				current = current.getLeft();
			} else {
				parent = current;
				current = current.getRight();
			}
		}
		if (current == null) {
			return false;
		}
		if (current.getRight() == null) {
			if (parent == null) {
				this.root = current.getLeft();
			} else {
				if (parent.getLeft() == current) {
					parent.setLeft(current.getLeft());
				} else {
					parent.setRight(current.getLeft());
				}
			}
		} else {
			Node<E> min = current.getRight();
			Node<E> minParent = current;
			while (min.getLeft() != null) {
				minParent = min;
				min = min.getLeft();
			}
			if (minParent.getLeft() == min) {
				minParent.setLeft(min.getRight());
			} else {
				minParent.setRight(min.getRight());
			}
			current.setData(min.getData());
		}
		this.size--;
		return true;

	}

	/*
	 * Returns the number of elements in this tree.
	 * 
	 * @returns number of elements in the tree
	 * 
	 */
	public int size() {
		return size;
	}

	/*
	 * Returns a string representation of this tree. The string representation
	 * consists of a list of the tree's elements in the order they are returned by
	 * its iterator (inorder traversal), enclosed in square brackets ("[]").
	 * Adjacent elements are separated by the characters ", " (comma and space).
	 * Elements are converted to strings as by String.valueOf(Object).
	 * 
	 * @return string representation of tree
	 */
	@Override
	public String toString() {
		String ret = "[";
		Iterator<E> itr = iterator();
		while (itr.hasNext()) {
			ret += itr.next();
			if (itr.hasNext()) {
				ret += ", ";
			}
		}
		ret += "]";
		return ret;
	}

	/*
	 * Produces tree like string representation of this tree. Returns a string
	 * representation of this tree in a tree-like format. The string representation
	 * consists of a tree-like representation of this tree. Each node is shown in
	 * its own line with the indentation showing the depth of the node in this tree.
	 * The root is printed on the first line, followed by its left subtree, followed
	 * by its right subtree.
	 * 
	 * @returns string containing tree like rep
	 * 
	 */
	public String toStringTreeFormat() {
		StringBuilder treeBuilder = new StringBuilder();
		toStringTreeFormat(root, 0, treeBuilder);
		return treeBuilder.toString();
	}
	
	//helper method for the tree format string method

	private void toStringTreeFormat(Node<E> node, int depth, StringBuilder builder) {
		if (node == null) {
			return;
		}
		for (int i = 0; i < depth; i++) {
			builder.append("  ");
		}
		builder.append(node.data);
		builder.append("\n");
		toStringTreeFormat(node.getLeft(), depth + 1, builder);
		toStringTreeFormat(node.getRight(), depth + 1, builder);
	}

	/*
	 * finds the depth of a given value in the BST
	 * 
	 * @returns int depth
	 * 
	 *  * @throws ClassCastException - if the specified object cannot be compared with
	 * the elements currently in this tree NullPointerException - if the specified
	 * element is null
	 * 
	 */
	public int depth(E e) {

		return findDepth(root, e);
	}
//helper method for the depth
	private int findDepth(Node<E> root, E e) {


		if (root == null)
			return -1;


		int dist = -1;


		if ((root.getData().equals(e)) ||


				(dist = findDepth(root.getLeft(), e)) >= 0 ||

			
				(dist = findDepth(root.getRight(), e)) >= 0)

			return dist + 1;

		return dist;
	}

	public ArrayList<E> pathToBottom(E e) {
		ArrayList<E> path = new ArrayList<E>();
		ArrayList<E> realPath = new ArrayList<>();
		Node<E> start = root;
		Iterator<Node<E>> nItr = noderator();
		while (nItr.hasNext() == true) {
			start = nItr.next();
			if (start.getData().equals(e)) {
				break;
			}

		}
		while (start != root) {
			path.add(start.getData());
			start = start.getParent();
		}
		path.add(root.getData());
		for (int i = path.size() - 1; i >= 0; i--) {
			realPath.add(path.get(i));
		}
		return realPath;

	}
//helper iterator that returns nodes as opposed to values
	private Iterator<Node<E>> noderator() {

		return new Iterator<Node<E>>() {
			private Stack<Node<E>> nodeStack = new Stack<>();
			private Node<E> current = root;

			@Override
			public boolean hasNext() {

				return (!nodeStack.isEmpty() || current != null);
			}

			@Override
			public Node<E> next() {
				while (current != null) {
					nodeStack.push(current);
					current = current.getLeft();
				}
				current = nodeStack.pop();
				Node<E> ret = current;
				current = current.getRight();
				return ret;

			}

		};
	}

}
