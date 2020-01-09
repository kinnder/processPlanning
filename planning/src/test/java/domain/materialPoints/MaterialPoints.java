package domain.materialPoints;

import java.util.UUID;

public interface MaterialPoints {

	static final String ID_OBJECT_OBJECT = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_1 = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_2 = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_3 = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_4 = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_A = UUID.randomUUID().toString();

	static final String ID_OBJECT_POINT_B = UUID.randomUUID().toString();

	/** Материальная точка */
	static final String OBJECT_MATERIAL_POINT = "OBJECT-MATERIAL-POINT";

	/** точка-1 **/
	static final String OBJECT_POINT_1 = "OBJECT-POINT-1";

	/** точка-2 **/
	static final String OBJECT_POINT_2 = "OBJECT-POINT-2";

	/** точка-3 **/
	static final String OBJECT_POINT_3 = "OBJECT-POINT-3";

	/** точка-4 **/
	static final String OBJECT_POINT_4 = "OBJECT-POINT-4";

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

	static final String ID_TEMPLATE_OBJECT = "ID-OBJECT";

	static final String ID_TEMPLATE_POINT_A = "ID-POINT-A";

	static final String ID_TEMPLATE_POINT_B = "ID-POINT-B";
}
