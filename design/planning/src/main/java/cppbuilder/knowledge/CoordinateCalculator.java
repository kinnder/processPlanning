package cppbuilder.knowledge;

/** Преобразование позиции в координаты */
public class CoordinateCalculator {

	/** Шаг координат по X */
	public int x_step = 2;

	/** Координата 1 позиции по X */
	public int x_zero = 4;

	/** Шаг координат по Y */
	public int y_step = 3;

	/** Координата 1 позиции по Y */
	public int y_zero = 12;

	/** Шаг координат по Z */
	public int z_step = 10;

	/** Координата 1 позиции по Z */
	public int z_zero = 0;

	/** Получение X координаты */
	public int getXCoordinateForPosition(int position) {
		position = (position - 1) % 5;
		return position * x_step + x_zero;
	}

	/** Получение Y координаты */
	public int getYCoordinateForPosition(int position) {
		return (position > 5) ? y_zero : y_zero + y_step;
	}

	/** Получение Z координаты */
	public int getZCoordinateForPosition(int position) {
		return z_zero + z_step * position;
	}
}
