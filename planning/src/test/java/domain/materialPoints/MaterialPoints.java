package domain.materialPoints;

public interface MaterialPoints {

	/** Материальная точка */
	static final String OBJECT_MATERIAL_POINT = "OBJECT-MATERIAL-POINT";

	/** Местоположение */
	static final String LINK_POSITION = "LINK-POSITION";

	/** Сосед справа */
	static final String LINK_NEIGHBOR_RIGHT = "LINK-NEIGHBOR-RIGHT";

	/** Сосед слева */
	static final String LINK_NEIGHBOR_LEFT = "LINK-NEIGHBOR-LEFT";

	/** Сосед сверху */
	static final String LINK_NEIGHBOR_TOP = "LINK-NEIGHBOR-TOP";

	/** Сосед снизу */
	static final String LINK_NEIGHBOR_BOTTOM = "LINK-NEIGHBOR-BOTTOM";

	/** Занята */
	static final String ATTRIBUTE_OCCUPIED = "ATTRIBUTE-OCCUPIED";

	/** Движение вправо */
	static final String OPERATION_MOVE_RIGHT = "OPERATION-MOVE-RIGHT";

	/** Движение влево */
	static final String OPERATION_MOVE_LEFT = "OPERATION-MOVE-LEFT";

	/** Движение вниз */
	static final String OPERATION_MOVE_BOTTOM = "OPERATION-MOVE-BOTTOM";

	/** Движение вверх */
	static final String OPERATION_MOVE_TOP = "OPERATION-MOVE-TOP";

	static final String ELEMENT_MOVE_RIGHT = "ELEMENT-MOVE-RIGHT";

	static final String ELEMENT_MOVE_LEFT = "ELEMENT-MOVE-LEFT";

	static final String ELEMENT_MOVE_TOP = "ELEMENT-MOVE-TOP";

	static final String ELEMENT_MOVE_BOTTOM = "ELEMENT-MOVE-BOTTOM";

	static final String ID_OBJECT = "ID-OBJECT";

	static final String ID_POINT_A = "ID-POINT-A";

	static final String ID_POINT_B = "ID-POINT-B";
}
