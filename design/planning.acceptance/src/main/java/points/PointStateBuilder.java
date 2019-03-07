package points;

import model.SystemState;
import points.objects.Point;

public class PointStateBuilder {

	public static SystemState SinglePoint(int x, int y, int z) {
		SystemState state = new SystemState();
		state.objects.add(new Point("точка", x, y, z));
		return state;
	}
}
