package cppbuilder.utility;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/** Логические операторы */
public class LogicOperators<Type> {

	/** Оператор равенства */
	public boolean operator_equality(Type value) {
		return equal(value);
	}

	/** Оператор неравенства */
	public boolean operator_not_equality(Type value) {
		return !equal(value);
	}

	/** Предикат - равняется */
	public boolean equal(Type value) {
		return isSameObject(value) || allFieldsAreEqual(value);
	}

	/** Предикат - совпадает */
	public boolean matches(Type value) {
		return isSameObject(value) || haveMatchingFields(value);
	}

	/** Предикат - включает */
	public boolean includes(Type value) {
		return isSameObject(value) || haveSubset(value);
	}

	/** Предикат - является одинаковым оъектом */
	protected boolean isSameObject(Type value) {
		return this == value;
	}

	/** Предикат - все поля равны */
	protected boolean allFieldsAreEqual(Type value) {
		throw new NotImplementedException();
	}

	/** Предикат - есть совпадающие поля */
	protected boolean haveMatchingFields(Type value) {
		throw new NotImplementedException();
	}

	/** Предикат - включает множество */
	protected boolean haveSubset(Type value) {
		throw new NotImplementedException();
	}

	/** Представить в виде XML строки */
	public String toXMLString() {
		throw new NotImplementedException();
	}
}
