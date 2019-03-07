package utility;

/**
 * Интерфейс - операция соответствия
 */
public interface IMatchable {

	/**
	 * Соответствует
	 *
	 * @param pattern - шаблон
	 * @return true - объект соответствует шаблону<br>
	 *         false - объект не соответствует шаблону
	 *
	 */
	boolean matches(Object pattern);
}
