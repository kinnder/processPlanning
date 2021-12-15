package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

public interface XMLSchema<T> {

	// TODO (2021-12-15 #50): рассмотреть возможность объединения всех схем в одну

	public String getSchemaName();

	public T parse(Element element) throws DataConversionException;

	public Element combine(T object);
}
