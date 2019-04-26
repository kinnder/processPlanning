package cppbuilder.management;

/** Команда */
public class Command {

	/** Номер устройства */
	public int deviceId;

	/** Номер программы */
	public int programId;

	/** 1-й параметр программы */
	public int parameter1;

	/** 2-й параметр программы */
	public int parameter2;

	/** 3-й параметр программы */
	public int parameter3;

	public Command(int deviceId, int programId, int parameter1, int parameter2, int parameter3) {
		this.deviceId = deviceId;
		this.programId = programId;
		this.parameter1 = parameter1;
		this.parameter2 = parameter2;
		this.parameter3 = parameter3;
	}
}
