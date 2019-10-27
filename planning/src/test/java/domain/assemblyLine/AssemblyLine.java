package domain.assemblyLine;

public interface AssemblyLine {

	static final String ID_PLANE_Z_BOTTOM = "ID-PLANE-Z-BOTTOM";

	static final String ID_PLANE_Z_TOP = "ID-PLANE-Z-TOP";

	static final String ID_PACKAGE_BOX_POSITION = "ID-PACKAGE-BOX-POSITION";

	static final String ID_PLANE_Z = "ID-PLANE-Z";

	static final String ID_PLANE_X = "ID-PLANE-X";

	static final String ID_PLANE_Y = "ID-PLANE-Y";

	static final String ID_PACKAGE_BOX = "ID-PACKAGE-BOX";

	static final String ID_PLANE_Y_SOURCE = "ID-PLANE-Y-SOURCE";

	static final String ID_PLANE_Y_TARGET = "ID-PLANE-Y-TARGET";

	static final String ID_PLANE_X_SOURCE = "ID-PLANE-X-SOURCE";

	static final String ID_PLANE_X_TARGET = "ID-PLANE-X-TARGET";

	static final String ID_ROBOT = "ID-ROBOT";

	static final String ELEMENT_MOVE_WITHOUT_LOAD = "ELEMENT-MOVE-WITHOUT-LOAD";

	static final String ELEMENT_MOVE_WITH_LOAD = "ELEMENT-MOVE-WITH-LOAD";

	static final String ELEMENT_LOWER_DOWN = "ELEMENT-LOWER-DOWN";

	static final String ELEMENT_LIFT_UP = "ELEMENT-LIFT-UP";

	static final String ELEMENT_CLOSE_GRAB = "ELEMENT-CLOSE-GRAB";

	static final String ELEMENT_OPEN_GRAB = "ELEMENT-OPEN-GRAB";

	static final String ELEMENT_TURN_WITH_LOAD = "ELEMENT-TURN-WITH-LOAD";

	static final String ELEMENT_TURN_WITHOUT_LOAD = "ELEMENT-TURN-WITHOUT-LOAD";

	/** Переместить без нагрузки */
	static final String OPERATION_MOVE_WITHOUT_LOAD = "OPERATION-MOVE-WITHOUT-LOAD";

	/** Переместить с нагрузкой */
	static final String OPERATION_MOVE_WITH_LOAD = "OPERATION-MOVE-WITH-LOAD";

	/** Опустить вниз */
	static final String OPERATION_LOWER_DOWN = "OPERATION-LOWER-DOWN";

	/** Поднять вверх */
	static final String OPERATION_LIFT_UP = "OPERATION-LIFT-UP";

	/** Закрыть захват */
	static final String OPERATION_CLOSE_GRAB = "OPERATION-CLOSE-GRAB";

	/** Открыть захват */
	static final String OPERATION_OPEN_GRAB = "OPERATION-OPEN-GRAB";

	/** Повернуть c нагрузкой */
	static final String OPERATION_TURN_WITH_LOAD = "OPERATION-TURN-WITH-LOAD";

	/** Повернуть без нагрузки */
	static final String OPERATION_TURN_WITHOUT_LOAD = "OPERATION-TURN-WITHOUT-LOAD";

	/** положение в плоскости Z */
	static final String LINK_PLANE_Z_POSITION = "LINK-PLANE-Z-POSITION";

	/** положение в плоскости Y */
	static final String LINK_PLANE_Y_POSITION = "LINK-PLANE-Y-POSITION";

	/** положение в плоскости X */
	static final String LINK_PLANE_X_POSITION = "LINK-PLANE-X-POSITION";

	/** место тары */
	static final String LINK_PACKAGE_BOX_POSITION = "LINK-PACKAGE-BOX-POSITION";

	/** положение вертикального привода */
	static final String LINK_VERTICAL_DRIVE_POSITION = "LINK-VERTICAL-DRIVE-POSITION";

	/** положение линейного привода */
	static final String LINK_LINEAR_DRIVE_POSITION = "LINK-LINEAR-DRIVE-POSITION";

	/** положение поворотного привода */
	static final String LINK_ROTARY_DRIVE_POSITION = "LINK-ROTARY-DRIVE-POSITION";

	/** положение захвата */
	static final String LINK_GRAB_POSITION = "LINK-GRAB-POSITION";

	/** цель */
	static final String PARAMETER_TARGET = "PARAMETER-TARGET";

	/** Плоскость Z */
	static final String ATTRIBUTE_PLANE_Z = "ATTRIBUTE-PLANE-Z";

	/** Плоскость Y */
	static final String ATTRIBUTE_PLANE_Y = "ATTRIBUTE-PLANE-Y";

	/** Плоскость X */
	static final String ATTRIBUTE_PLANE_X = "ATTRIBUTE-PLANE-X";

	/** Место тары */
	static final String ATTRIBUTE_PACKAGE_BOX_POSITION = "ATTRIBUTE-PACKAGE-BOX-POSITION";

	/** Тара */
	static final String ATTRIBUTE_PACKAGE = "ATTRIBUTE-PACKAGE";

	/** Робот-перекладчик */
	static final String ATTRIBUTE_PICK_AND_PLACE_ROBOT = "ATTRIBUTE-PICK-AND-PLACE-ROBOT";

	/** плоскость Z низ */
	static final String OBJECT_PLANE_Z_BOTTOM = "OBJECT-PLANE-Z-BOTTOM";

	/** плоскость Z вверх */
	static final String OBJECT_PLANE_Z_TOP = "OBJECT-PLANE-Z-TOP";

	/** плоскоть Y внутри станции */
	static final String OBJECT_PLANE_Y_INSIDE = "OBJECT-PLANE-Y-INSIDE";

	/** плоскость Y снаружи станции */
	static final String OBJECT_PLANE_Y_OUTSIDE = "OBJECT-PLANE-Y-OUTSIDE";

	/** плоскость X стол 2 */
	static final String OBJECT_PLANE_X_TABLE_2 = "OBJECT-PLANE-X-TABLE-2";

	/** плоскость X стол 1 */
	static final String OBJECT_PLANE_X_TABLE_1 = "OBJECT-PLANE-X-TABLE-1";

	/** Стол 2 */
	static final String OBJECT_TABLE_2 = "OBJECT-TABLE-2";

	/** Стол 1 */
	static final String OBJECT_TABLE_1 = "OBJECT-TABLE-1";

	/** Шаттл */
	static final String OBJECT_SHUTTLE = "OBJECT-SHUTTLE";

	/** Тара */
	static final String OBJECT_PACKAGE_BOX = "OBJECT-PACKAGE-BOX";

	/** Робот-перекладчик */
	static final String OBJECT_PICK_AND_PLACE_ROBOT = "OBJECT-PICK-AND-PLACE-ROBOT";
}
