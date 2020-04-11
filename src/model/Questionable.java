package model;

/**
 * <p><strong>Questionable</strong> interface<p>
 * <p>This interface is used for object which can be queried</p>
 * @author François Poguet
 */
public interface Questionable {
	
	/**
	 * Query an object
	 * @param h The hunter used for the process
	 */
	public void process(Hunter h);
}
