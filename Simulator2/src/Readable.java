
/**
 * Describes any objects which can have data read from them
 * @author knispeja
 *
 */
public interface Readable {
	/**
	 * Read from an output.
	 * @param id the id of the output to read from (specified by the readable object)
	 * @return
	 */
	public String read(int id);
}
