package cppbuilder.utility;

import java.util.Random;

/** √енератор случайных величин */
public class RandomGenerator {

	private static Random random = new Random();

	/** ѕолучение случайной строки */
	public static String getString(int length) {
		char result[] = new char[length];
		for (int i = 0; i < result.length; i++) {
			result[i] = (char) (random.nextInt(26) + 'a');
		}
		return new String(result);
	}

	/** ѕолучение случайной строки из множества */
	public static String getStringFrom(String[] variants) {
		int id = random.nextInt(variants.length);
		return variants[id];
	}

	/** ѕолучение случайного целого числа из указанного интервала целых */
	public static int getInteger(int min, int max) {
		int bound = max - min;
		return random.nextInt(bound) + min;
	}

	/** ѕолучение случайного целого числа из множества */
	public static int getIntegerFrom(int[] variants) {
		int id = random.nextInt(variants.length);
		return variants[id];
	}

	/** ѕолучение случайного логического */
	public static boolean getBool() {
		if (getInteger(0, 10000) > 5000) {
			return true;
		}
		return false;
	}

	/**
	 * ѕолучение случайного вещественного числа из указанного интервала вещественных
	 * чисел
	 */
	public static double getDouble(int min, int max, int precision) {
		// формирование целой части
		double result = getInteger(min, max);
		// формирование дробной части
		double rational = 0.0;
		int weight = 1;
		for (int i = 0; i < precision; i++) {
			rational += getInteger(0, 9) * weight;
			weight *= 10;
		}
		// объединение частей
		result += rational / weight;
		return result;
	}
}
