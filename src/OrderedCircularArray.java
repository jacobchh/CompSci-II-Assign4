/**
 * Class represents an ordered circular array designed to store CellData objects
 * 
 * @author Jacob Chun
 *
 * @param <T> Represents any data type that the can be stored in the circular
 *            array
 */
public class OrderedCircularArray<T> implements SortedListADT<T> {

	/**
	 * Instance variables represent the ordered array, integer value indicating
	 * index of the first data item and the last data item, and a count indicating
	 * the number of data items
	 */
	private CellData[] list;
	private int front;
	private int rear;
	private int count;

	/**
	 * Constructor of the class initializes a CellData array where the first value
	 * will be stored at index 1
	 */
	public OrderedCircularArray() {
		front = 1;
		rear = 0;
		count = 0;
		list = (CellData<T>[]) new CellData[5];
	}

	/**
	 * Method inserts a CellData object in order from smallest to largest integer
	 * value
	 * 
	 * @param id    desired CellData identifier
	 * @param value desired CellData integer value
	 */
	public void insert(T id, int value) {
		// if the list is full, we expand capacity
		if (count == list.length) {
			expandCapacity();
		}

		// if there are no items in the list
		if (count == 0) {
			list[front] = new CellData<T>(id, value);
			rear = front;
			count++;
		}

		// if there are already existing items in the list
		else {
			int index = front;
			int tempCount = 0;
			// loop through to find the correct index position
			for (int q = front; q < (count + front); q++) {
				if (value > list[q % list.length].getValue()) {
					index++;
					tempCount++;
				}
			}

			// if we have to add at the end
			if ((index - 1) == rear) {
				list[index % list.length] = new CellData<T>(id, value);
				rear = (index % list.length);
				count++;
			}

			// if we have to add in the middle or front
			else {
				// number of iterations we have to shift items down
				int iterations = count - tempCount;
				rear = (rear + 1) % list.length;
				tempCount = rear;
				for (int q = 0; q < iterations; q++) {
					list[tempCount] = list[mod((tempCount - 1), list.length)];
					tempCount = mod((tempCount - 1), list.length);
				}
				list[index % list.length] = new CellData<T>(id, value);
				count++;
			}
		}
	}

	/**
	 * Method returns the integer value of a CellData object with a given identifier
	 * 
	 * @param id the desired identifier to find in the list
	 * @return the value of a CellData object with a given identifier
	 * @throws InvalidDataItemException if the item could not be found because it
	 *                                  doesn't exist in the list
	 */
	public int getValue(T id) throws InvalidDataItemException {
		for (int i = front; i < front + count; i++) {
			if (list[i % list.length].getId().equals(id)) {
				return list[i].getValue();
			}
		}
		throw new InvalidDataItemException("The data item could not be found");
	}

	/**
	 * Method removes a CellData object with a given identifier
	 * 
	 * @param id identifier of desired object to be removed
	 * @throws InvvalidDataItemException if the item could not be found because it
	 *                                   doesn't exist in the list
	 */
	public void remove(T id) throws InvalidDataItemException {
		boolean found = false;
		int index = front;
		// count + front is the number iterations we have to go through
		for (int i = front; i < (count + front); i++) {
			if (list[i % list.length].getId().equals(id)) {
				found = true;
			}
			if (!found) {
				index++;
			}
		}
		// reset index to the proper circular array index value
		index = index % list.length; 
		if (found) {
			if (index == rear) {
				list[index] = null;
				count--;
				rear = mod((rear - 1), list.length);
			} else {
				list[index] = null;
				// shift down
				for (int i = index; i < count; i++) {
					list[i % list.length] = list[(i + 1) % list.length];
				}
				count--;
				list[rear] = null;
				rear = mod((rear - 1), list.length);
			}
		} else {
			throw new InvalidDataItemException("The data item could not be found");
		}
	}

	/**
	 * Method identifies a given CellData object with the id and replaces its
	 * integer value with a new one
	 * 
	 * @param id       identifier of desired object to be replaced
	 * @param newValue the desired replacement value
	 * @throws InvalidDataItemException if the item could not be found because it
	 *                                  doesn't exist in the list
	 */
	public void changeValue(T id, int newValue) throws InvalidDataItemException {
		boolean found = false;
		for (int i = front; i < (count + front); i++) {
			if (list[i % list.length].getId().equals(id)) {
				remove(id);
				insert(id, newValue);
				found = true;
				break;
			}
		}
		if (!found) {
			throw new InvalidDataItemException("The data item could not be found");
		}
	}

	/**
	 * Method retrieves the CellData object with the smallest integer value
	 * 
	 * @return the smallest data item given by the front position
	 * @throws EmptyListException if the list is empty
	 */
	public T getSmallest() throws EmptyListException {
		if (count == 0) {
			throw new EmptyListException("The ordered list is empty");
		}

		else {
			T temp = (T) list[front].getId();
			list[front] = null;
			front = (front + 1) % list.length;
			count--;
			return temp;
		}
	}

	/**
	 * @return a boolean indicating whether or not the list is empty
	 */
	public boolean isEmpty() {
		if (count == 0) {
			return true;
		}
		return false;
	}

	/**
	 * @return the number of items in the ordered list
	 */
	public int size() {
		return count;
	}

	/**
	 * @return the index position of the first item
	 */
	public int getFront() {
		return front;
	}

	/**
	 * @return the index position of the last item
	 */
	public int getRear() {
		return rear;
	}

	/**
	 * Method expands the capacity of the array by creating a new one of double the
	 * size, keeping the front index the same and shifting items
	 * 
	 */
	private void expandCapacity() {
		CellData<T>[] largerList = (CellData<T>[]) new CellData[list.length * 2];
		int copied = 0;
		int i = front;
		int j = front;

		while (copied < count) {
			largerList[i] = list[j];
			i++;
			j = (j + 1) % list.length;
			copied++;
		}

		rear = (front + count) - 1;
		list = largerList;
	}

	/**
	 * Method performs the modulo operation as specified in linear algebra
	 * 
	 * @param a numerator
	 * @param b denominator
	 * @return modulo result
	 */
	private int mod(int a, int b) {
		int result = a % b;
		if (result < 0)
			result += b;
		return result;
	}

}
