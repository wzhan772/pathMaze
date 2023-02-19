/**
 * @author William Zhang 251215208 
 * the purpose of this class is to represent an extended stack ADT implementation using a doubly linked list
 *
 */
public class DLStack<T> implements DLStackADT<T> {
	private DoubleLinkedNode<T> top;
	private int numItems;

	/**
	 * creates empty stack
	 */
	public DLStack() {
		top = null;
		numItems = 0;
	}

	/**
	 * adds data to stack
	 * 
	 * @param element is element to push on the stack
	 */
	public void push(T dataItem) {
		if (isEmpty()) {
			top = new DoubleLinkedNode(dataItem);
		} else {
			DoubleLinkedNode<T> newNode = new DoubleLinkedNode(dataItem);
			top.setNext(newNode);
			newNode.setPrevious(top);
			top = newNode;
			top.setNext(null);
		}
		numItems++;
	}

	/**
	 * removes and returns the data item from the top of the stack
	 * 
	 * @return T is the element to be removed from the top
	 */
	public T pop() throws EmptyStackException {
		T result;
		if (isEmpty()) {
			//throw new exception if it is found
			throw new EmptyStackException("Stack is Empty");
		} else if (numItems == 1) {
			result = top.getElement();
			top = null;
			numItems--;
		} else {
			result = top.getElement();
			top.getPrevious().setNext(null);
			top = top.getPrevious();
			numItems--;
		}
		return result;
	}

	/**
	 * removes the returns the k-th data item from the top of the stack
	 * 
	 */
	public T pop(int k) throws InvalidItemException {
		DoubleLinkedNode<T> node = top;
		T result = null;
		int counter = 0;
		if (k > numItems || k <= 0) {
			throw new InvalidItemException("Item is Invalid");
		}
		while (!isEmpty() && node != null) {
			if (counter + 1 == k) {
				result = node.getElement();
				if (k != 1) {
					node.getNext().setPrevious(node.getPrevious());
				} if (k == 1) {
					top = node.getPrevious();
					top.setNext(node.getNext());
				} if (k != numItems) {
					node.getPrevious().setNext(node.getNext());
				}
				numItems--;
			}
			node = node.getPrevious();
			counter++;
		}
		return result;
	}
	
	/**
	 * returns data item at the top of the stack without removing it
	 * 
	 * @return T is the element at the top of the stack
	 */
	public T peek() throws EmptyStackException {
		T output;
		if (isEmpty()) {
			//throw new exception if it is found
			throw new EmptyStackException("Stack is Empty");
		} else {
			output = top.getElement();
		}
		return output;
	}

	/**
	 * returns true or false depending on the whether or not the stack is empty
	 * 
	 * @return true or false
	 */
	public boolean isEmpty() {
		if (top == null && numItems == 0) {
			return true; 
		} else {
			return false;
		}
	}

	/**
	 * returns the number of data items in stack
	 * 
	 * @return number of data items in stack
	 */
	public int size() {
		return numItems;
	}

	/**
	 * returns the top of the stack
	 * 
	 * @return the node at the top of the stack
	 */
	public DoubleLinkedNode<T> getTop() {
		return top;
	}

	/**
	 * returns a string in the specified format
	 * 
	 * @return the translated string
	 */
	public String toString() {
		DoubleLinkedNode<T> crntNode = top;
		String str = "[";
		for (int i = 0; i < numItems; i++) {
			str = str + crntNode.getElement();
			crntNode = crntNode.getNext();
		}
		return str = str + "]";
	}
}