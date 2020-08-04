/**
 * Class represents data items to be stored in our circular array, each object
 * will contain an identifier / T object value and an integer value
 * 
 * @author Jacob Chun
 *
 * @param <T> Represents any data type that the CellData can accept
 */
public class CellData<T> {

	/**
	 * Instance variables for the identifier object and the integer value to be
	 * stored
	 */
	private T id;
	private int value;

	/**
	 * Constructor of the class creates a CellData object
	 * 
	 * @param theId    desired identifier object
	 * @param theValue desired integer value
	 */
	public CellData(T theId, int theValue) {
		id = theId;
		value = theValue;
	}

	/**
	 * 
	 * @return the integer value
	 */
	public int getValue() {
		return value;
	}

	/**
	 * 
	 * @return the identifier object
	 */
	public T getId() {
		return id;
	}

	/**
	 * Method sets a new integer value
	 * 
	 * @param newValue desired integer value
	 */
	public void setValue(int newValue) {
		value = newValue;
	}

	/**
	 * Method sets a new identifier object
	 * 
	 * @param newId desired identifier
	 */
	public void setId(T newId) {
		id = newId;
	}

}
