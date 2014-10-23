/**
 * Describes any object which can be written to.
 * @author knispeja
 *
 */
public interface Writable {
	/**
	 * Write to a writable object.
	 * @param id the id of the input to write into
	 * @param data the data to write to the object
	 */
	public void write(int id, String data);
}
