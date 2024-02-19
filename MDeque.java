
package project3;
import java.util.Iterator;

/**
 * A linear collection that supports element insertion and removal at three
 * points: front, middle and back. The name mdeque is short for "double ended
 * queue" (deque) with m for "middle" and is pronounced "em-deck". MDeque has no
 * fixed limits on the number of elements it contains. The remove operations all
 * return null values if the mdeque is empty. The structure does not allow null
 * as an element.
 * 
 * All pop..., push..., and peek... operations (from all three points of access)
 * are constant time operations.
 *
 * 
 * 
 * @author Peter D'Angelo
 */
public class MDeque<E> implements Iterable<E> {
	private Node head;
	private Node tail;
	private int size;

	/*
	 * Constructor for MDeque
	 * 
	 * creates a new MDeque with null values and size 0
	 */
	public MDeque() {
		this.head = null;
		this.tail = null;
		this.size = 0;
	}

	/*
	 * returns an iterator that iterates forward over the MDeque
	 * 
	 * @return Iterator<E> for mdeque
	 */
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			Node current = head;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				if (hasNext()) {
					E data = current.data;
					current = current.next;
					return data;
				}
				return null;
			}

		};
	}

	/*
	 * returns an iterator that iterates backwards over the MDeque
	 * 
	 * @return Iterator<E> for mdeque
	 */
	public Iterator<E> reverseIterator() {
		return new Iterator<E>() {

			Node current = tail;

			@Override
			public boolean hasNext() {
				return current != null;
			}

			@Override
			public E next() {
				if (hasNext()) {
					E data = current.data;
					current = current.previous;
					return data;
				}
				return null;
			}

		};
	}

	/*
	 * returns the value held at the tail of the MDeque
	 * 
	 * @returns E value held by tail, null if empty
	 */
	public E peekBack() {
		if (size() == 0) {
			return null;
		}
		return tail.data;
	}

	/*
	 * returns the value held at the head of the MDeque
	 * 
	 * @returns E value held by head, null if empty
	 */
	public E peekFront() {
		if (size() == 0) {
			return null;
		}
		return head.data;
	}

	/*
	 * returns the value held at the middle of the MDeque
	 * 
	 * @returns E value held by middle, null if empty
	 */
	public E peekMiddle() {
		if (size() == 0) {
			return null;
		}
		int count = 0;
		Node temp = head;
		while (count < (size() / 2) + 1) {
			temp = temp.next;
			count++;
		}
		return temp.data;
	}

	/*
	 * returns the value held at the tail of the MDeque, and removes it from the
	 * MDeque
	 * 
	 * @returns E value held by tail
	 */
	public E popBack() {
		// If queue is empty, return NULL.
		if (this.tail == null) {
			return null;
		}
		// Store previous front and move front one node
		// ahead
		Node temp = this.tail;
		if (size() > 1) {
			this.tail = this.tail.previous;
			this.tail.next = null;
		} else {
			this.head.next = null;
			this.head.previous = null;
			this.tail.next = null;
			this.tail.previous = null;
			head = null;
			tail = null;
		}
		// If front becomes NULL, then change rear also as
		// NULL
		if (this.tail == null) {
			this.head = null;

		}
		return temp.data;

	}

	/*
	 * returns the value held at the head of the MDeque, and removes it from the
	 * MDeque
	 * 
	 * @returns E value held by head
	 */
	public E popFront() {
		// If queue is empty, return NULL.
		if (this.head == null) {
			return null;
		}
		// Store previous front and move front one node
		// ahead
		Node temp = this.head;
		if (size() > 1) {
			this.head = this.head.next;
			this.head.previous = null;
		} else {
			this.head.next = null;
			this.head.previous = null;
			this.tail.next = null;
			this.tail.previous = null;
			head = null;
			tail = null;
		}
		// If front becomes NULL, then change rear also as
		// NULL
		if (this.head == null) {
			this.tail = null;
		}
		return temp.data;
	}

	/*
	 * returns the value held at the middle of the MDeque, and removes it from the
	 * MDeque
	 * 
	 * @returns E value held by middle
	 */
	public E popMiddle() {
		if (size == 1) {
			head = null;
			tail = null;
			return head.data;

		}
		int count = 0;
		Node middle = head;
		while (count < (size() / 2)) {
			middle = middle.next;
			count++;
		}
		// If queue is empty, return NULL.
		if (middle == null)
			return null;

		// Store previous front and move front one node
		// ahead
		Node temp = middle;
		middle.previous.next = middle.next;
		middle.next.previous = middle.previous;
		if (size % 2 == 0) {

			middle = middle.previous;
		} else {
			middle = middle.next;
		}

		// If front becomes NULL, then change rear also as
		// NULL
		if (middle == null) {
			this.head = null;
			this.tail = null;
		}
		return temp.data;
	}

	/*
	 * pushes a given item into the back of the MDeque
	 * 
	 * @param E object of whatever the MDeque holds
	 * 
	 * @throws IllegalArgumentException if passed with a null value
	 */
	public void pushBack(E item) {
		if (item == null) {
			throw new IllegalArgumentException("Error: cannot push null items to MDeque.");
		}
		Node temp = new Node(item);
		if (tail == null) {
			tail = temp;
			head = temp;
		} else {
			tail.next = temp;
			temp.previous = tail;
			tail = temp;
			if (head == null) {
				head = tail;
			}
		}

	}

	/*
	 * pushes a given item into the front of the MDeque
	 * 
	 * @param E object of whatever the MDeque holds
	 * 
	 * @throws IllegalArgumentException if passed with a null value
	 */
	public void pushFront(E item) {
		if (item == null) {
			throw new IllegalArgumentException("Error: cannot push null items to MDeque.");
		}
		Node temp = new Node(item);
		if (head == null) {
			head = temp;
			tail = temp;
		} else {
			head.previous = temp;
			temp.next = head;
			head = temp;
			if (tail == null) {
				tail = head;
			}
		}
	}

	/*
	 * pushes a given item into the middle of the MDeque
	 * 
	 * @param E object of whatever the MDeque holds
	 * 
	 * @throws IllegalArgumentException if passed with a null value
	 */
	public void pushMiddle(E item) {
		if (item == null) {
			throw new IllegalArgumentException("Error: cannot push null items to MDeque.");
		}
		if (size() == 0) {
			Node temp = new Node(item);
			head = temp;
			tail = temp;
			tail.next = null;
			tail.previous = null;
			head.next = null;
			head.previous = null;
		} else {
			int count = 0;
			Node middle = head;
			while (count < (size() / 2)) {
				middle = middle.next;
				count++;
			}
			Node temp = new Node(item);
			if (size % 2 == 0) {
				middle.previous.next = temp;
				temp.previous = middle.previous;
				middle.previous = temp;
				temp.next = middle;
			} else {
				middle.next.previous = temp;
				temp.next = middle.next;
				middle.next = temp;
				temp.previous = middle;
			}
		}
	}

	/*
	 * returns the amount of items stored within the MDeque
	 * 
	 * @returns int representing MDeque size
	 */
	public int size() {
		int ret = 0;
		Node cur = head;
		while (cur != null) {
			ret++;
			cur = cur.next;
		}
		this.size = ret;
		return ret;
	}

	/*
	 * returns a readable string representation of the MDeque
	 * 
	 * @returns String with all Mdeque values separated by commas in between
	 * brackets
	 */
	@Override
	public String toString() {
		if (size() == 0) {
			return "[]";
		}
		String result = "[";
		result = result + toString(head);
		result = result.substring(0, result.length() - 3);
		result = result + "]";
		return result;
	}

	private String toString(MDeque<E>.Node node) {
		if (node == null) {
			return " ";
		} else {
			return node.data + ", " + toString(node.next);
		}

	}

//Node class for the linked list MDeque
	private class Node {
		private E data;
		private Node next;
		private Node previous;

		/*
		 * constructor for the node to be used by the MDeque
		 * 
		 * @param E object of whatever the MDeque is composed of
		 */
		public Node(E data) {
			this.data = data;
			next = null;
			previous = null;
		}

	}

}
