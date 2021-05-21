package planning.model;

public interface ActionFunction {

	void invoke(SystemVariant systemVariant);

	Boolean invokeAndReturnBoolean(SystemVariant systemVariant);
}
