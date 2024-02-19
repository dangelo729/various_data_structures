//package project2;

import java.util.Iterator;
import java.util.NoSuchElementException;
/**
* This class is a linked list 
* that stores all of the various tree objects.
*
* @author Peter D'Angelo
*/
public class TreeList implements Iterable<Tree> {

	private Node head;
	private Node tail;

	/*
	 * creates a new tree list with the head being null
	 * 
	 */	public TreeList() {
		this.head = null;
	}

	/*
	 * adds a tree to the linked list
	 * 
	 * @param a Tree object
	 * 
	 * @throw IllegalArgumentException if the tree is null
	 */
	public void add(Tree tree) {
		if (tree == null) {
			throw new IllegalArgumentException("Error: Addition to tree list can not be null.");
		}
		if (this.head == null) {

			this.head = new Node(tree);
			this.setTail(this.head);
		} else {
			Node nodeToAdd = new Node(tree);
			nodeToAdd.next = this.head;
			this.head = nodeToAdd;
		}

	}

	/*
	 * returns the total number of trees stored in the linked list
	 * 
	 * @return total number of trees
	 */
	public int getTotalNumberOfTrees() {
		Node on = this.head;
		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {
			ret++;
			on = on.next;
		}
		return ret;
	}

	/*
	 * returns all the trees that have the param as their common name
	 * 
	 * @param a string representing common species name
	 * 
	 * @return an int that is the amount of trees with that common name
	 */	
	public int getCountByCommonName(String speciesName) {
		if (speciesName == null) {
			throw new IllegalArgumentException("Error: Species names cannot be null.");
		}
		Node on = this.head;

		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {

			if (on.data.getCommon().equalsIgnoreCase(speciesName) == true) {
				ret++;
			}
			on = on.next;
		} if(on.next == null) {
			if (on.data.getCommon().equalsIgnoreCase(speciesName) == true) {
				ret++;
			}
		}
		return ret;

	}
	/*
	 * returns all the trees that have the param as their latin name
	 * 
	 * @param a string representing latin species name
	 * 
	 * @return an int that is the amount of trees with that latin name
	 */	
	public int getCountByLatinName(String speciesName) {
		if (speciesName == null) {
			throw new IllegalArgumentException("Error: Species names cannot be null.");
		}
		Node on = this.head;

		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {

			if (on.data.getLatin().equalsIgnoreCase(speciesName) == true) {
				ret++;
			}
			on = on.next;
		} if(on.next == null) {
			if (on.data.getLatin().equalsIgnoreCase(speciesName) == true) {
				ret++;
			}
		}
		return ret;

	}

	/*
	 * returns the amount of trees in a given borough
	 * 
	 * @param string representing borough name
	 * 
	 * @return int that is count of trees in borough
	 */
	public int getCountByBorough(String boroName) {
		if (boroName == null) {
			throw new IllegalArgumentException("Error: Borough cannot be null");
		}
		Node on = this.head;

		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {
			if (on.data.getBoroname() != null) {
				if (on.data.getBoroname().equalsIgnoreCase(boroName) == true) {
					ret++;
				}
			}
			on = on.next;
		}
		return ret;

	}
	/*
	 * returns the amount of trees in a given borough with a common name
	 * 
	 * @param string representing borough name and string for common name
	 * 
	 * @return int that is count of trees in borough with that name
	 */
	public int getCountByCommonNameBorough(String speciesName, String boroName) {
		if (speciesName == null || boroName == null) {
			throw new IllegalArgumentException("Error: Species and borough names cannot be null.");
		}
		
		Node on = this.head;

		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {
			if (on.data.getBoroname() != null) {
				if (on.data.getBoroname().equalsIgnoreCase(boroName) == true
						&& on.data.getCommon().equalsIgnoreCase(speciesName) == true) {
					ret++;
				}
			}
			on = on.next;
		}
		return ret;

	}
	/*
	 * returns the amount of trees in a given borough with a latin name
	 * 
	 * @param string representing borough name and string for latin name
	 * 
	 * @return int that is count of trees in borough with that name
	 */
	public int getCountByLatinNameBorough(String speciesName, String boroName) {
		if (speciesName == null || boroName == null) {
			throw new IllegalArgumentException("Error: Species names cannot be null.");
		}
		Node on = this.head;

		int ret = 0;

		if (this.head == null) {
			return ret;
		}

		while (on.next != null) {
			if (on.data.getBoroname() != null) {
				if (on.data.getBoroname().equalsIgnoreCase(boroName) == true
						&& on.data.getLatin().equalsIgnoreCase(speciesName) == true) {
					ret++;
				}
			}
			on = on.next;
		}if(on.next == null) {
			if (on.data.getBoroname().equalsIgnoreCase(boroName) == true
					&& on.data.getLatin().equalsIgnoreCase(speciesName) == true) {
				
				ret++;
			}
		}
		return ret;

	}

	/*
	 * generates a string displaying the size of the list
	 * 
	 * @return string representing the list
	 */
	@Override
	public String toString() {
		String ret = "There are " + this.getTotalNumberOfTrees() + " trees in this list";

		return ret;
	}
	
	/*
	 * this is the iterator used to traverse the linked list it implements the
	 * Iterator and Iterable class
	 */
	@Override
	public Iterator<Tree> iterator() {
		return new MyIterator();
	}

	public Node getTail() {
		return tail;
	}

	private void setTail(Node t) {
		this.tail = t;
	}

	public Node gethead() {
		return head;
	}

	class MyIterator implements Iterator<Tree> {
		Node current = null;

		@Override
		public boolean hasNext() {
			if (current == null && head != null) {
				return true;
			} else if (current != null) {
				return current.getNext() != null;
			}

			return false;
		}

		@Override
		public Tree next() {
			if (current == null) {
				current = head;
				return head.getTree();
			} else if (current != null) {
				Tree ret = current.getTree();
				current = current.getNext();
				return ret;
			}
			throw new NoSuchElementException();
		}

	}
}

/*
 * this is the node class used in the linked list very simple, it stores a tree
 * object and a reference to the next node in the list
 */
class Node {

	Tree data;
	Node next;

	/*
	 * constructor that creates the node
	 * 
	 * @param tree object
	 */
	public Node(Tree tree) {
		this.data = tree;
	}

	/*
	 * returns the tree stored in the node
	 * 
	 * @return tree object data
	 */
	public Tree getTree() {
		return data;
	}

	/*
	 * returns the next node in the linked list
	 * 
	 * @return Node next
	 */
	public Node getNext() {
		return this.next;
	}

}