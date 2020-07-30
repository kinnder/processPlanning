package application.domain.cuttingProcess;

import java.util.UUID;

public interface CuttingProcess {

	static final String ID_OBJECT_WORKPIECE = UUID.randomUUID().toString();

	static final String ID_OBJECT_CYLINDER_SURFACE = UUID.randomUUID().toString();

	static final String ID_OBJECT_REQUIREMENT_SURFACE_A = UUID.randomUUID().toString();

	static final String ID_OBJECT_REQUIREMENT_SURFACE_B = UUID.randomUUID().toString();

	static final String ID_OBJECT_REQUIREMENT_SURFACE_C = UUID.randomUUID().toString();

	/** Заготовка */
	static final String OBJECT_WORKPIECE = "OBJECT-WORKPIECE";

	/** Цилиндрическая поверхность */
	static final String OBJECT_CYLINDER_SURFACE = "OBJECT-CYLINDER-SURFACE";

	/** Требование к поверхности a */
	static final String OBJECT_REQUIREMENT_SURFACE_A = "OBJECT-REQUIREMENT-SURFACE-A";

	/** Требование к поверхности b */
	static final String OBJECT_REQUIREMENT_SURFACE_B = "OBJECT-REQUIREMENT-SURFACE-B";

	/** Требование к поверхности c */
	static final String OBJECT_REQUIREMENT_SURFACE_C = "OBJECT-REQUIREMENT-SURFACE-C";

	/** Является частью */
	static final String LINK_IS_PART_OF = "LINK-IS-PART-OF";

	/** Является требованием */
	static final String LINK_IS_REQUIREMENT_OF = "LINK-IS-REQUIREMENT-OF";

	/** Является требованием диаметра */
	static final String LINK_IS_DIAMETER_REQUIREMENT = "LINK-IS-DIAMETER-REQUIREMENT";

	/** Является требованием длины */
	static final String LINK_IS_LENGTH_REQUIREMENT = "LINK-IS-LENGTH-REQUIREMENT";

	/** Сторона поверхности справа */
	static final String LINK_SURFACE_SIDE_RIGHT = "LINK-SURFACE-SIDE-RIGHT";

	/** Сторона поверхности слева */
	static final String LINK_SURFACE_SIDE_LEFT = "LINK-SURFACE-SIDE-LEFT";

	/** Диаметр */
	static final String ATTRIBUTE_DIAMETER = "ATTRIBUTE-DIAMETER";

	/** Требование диаметра */
	static final String ATTRIBUTE_DIAMETER_REQUIREMENT = "ATTRIBUTE-DIAMETER-REQUIREMENT";

	/** Состояние требования диаметра */
	static final String ATTRIBUTE_DIAMETER_REQUIREMENT_STATUS = "ATTRIBUTE-DIAMETER-REQUIREMENT-STATUS";

	/** Есть требование диаметра */
	static final String ATTRIBUTE_HAS_DIAMETER_REQUIREMENT = "ATTRIBUTE-HAS-DIAMETER-REQUIREMENT";

	/** Требование длины */
	static final String ATTRIBUTE_LENGTH_REQUIREMENT = "ATTRIBUTE-LENGTH-REQUIREMENT";

	/** Состояние требования длины */
	static final String ATTRIBUTE_LENGTH_REQUIREMENT_STATUS = "ATTRIBUTE-LENGTH-REQUIREMENT-STATUS";

	/** Есть требование длины */
	static final String ATTRIBUTE_HAS_LENGTH_REQUIREMENT = "ATTRIBUTE-HAS-LENGTH-REQUIREMENT";

	/** Длина */
	static final String ATTRIBUTE_LENGTH = "ATTRIBUTE-LENGTH";

	/** Заготовка */
	static final String ATTRIBUTE_WORKPIECE = "ATTRIBUTE-WORKPIECE";

	/** Цилиндрическая поверхность */
	static final String ATTRIBUTE_CYLINDER_SURFACE = "ATTRIBUTE-CYLINDER-SURFACE";

	/** Требование */
	static final String ATTRIBUTE_REQUIREMENT = "ATTRIBUTE-REQUIREMENT";

	/** Точить цилиндрическую поверхность */
	static final String OPERATION_CUT_CYLINDER_SURFACE = "OPERATION-CUT-CYLINDER-SURFACE";

	/** Разделить цилиндрическую поверхность */
	static final String OPERATION_SPLIT_CYLINDER_SURFACE = "OPERATION-SPLIT-CYLINDER-SURFACE";

	/** Подрезать цилиндрическую поверхность */
	static final String OPERATION_TRIM_CYLINDER_SURFACE = "OPERATION-TRIM-CYLINDER-SURFACE";

	/** Разница диаметров */
	static final String PARAMETER_DIAMETER_DELTA = "PARAMETER-DIAMETER-DELTA";

	/** Разница длин */
	static final String PARAMETER_LENGTH_DELTA = "PARAMETER-LENGTH-DELTA";

	static final String ELEMENT_CUT_CYLINDER_SURFACE = "ELEMENT-CUT-CYLINDER-SURFACE";

	static final String ELEMENT_TRIM_CYLINDER_SURFACE = "ELEMENT-TRIM-CYLINDER-SURFACE";

	static final String ELEMENT_SPLIT_CYLINDER_SURFACE = "ELEMENT-SPLIT-CYLINDER-SURFACE";

	static final String ID_WORKPIECE = "ID-WORKPIECE";

	static final String ID_CYLINDER_SURFACE = "ID-CYLINDER-SURFACE";

	static final String ID_REQUIREMENT = "ID-REQUIREMENT";

	static final String ID_REQUIREMENT_L = "ID-REQUIREMENT-L";

	static final String ID_REQUIREMENT_R = "ID-REQUIREMENT-R";
}
