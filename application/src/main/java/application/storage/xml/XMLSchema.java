package application.storage.xml;

import org.jdom2.DataConversionException;
import org.jdom2.Element;

public interface XMLSchema<T> {

	public String getSchemaName();

	public T parse(Element element) throws DataConversionException;

	public Element combine(T object);
}
