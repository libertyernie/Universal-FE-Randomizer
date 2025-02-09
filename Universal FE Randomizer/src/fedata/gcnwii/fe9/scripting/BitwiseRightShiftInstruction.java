package fedata.gcnwii.fe9.scripting;

import io.gcn.GCNCMBFileHandler;

public class BitwiseRightShiftInstruction extends ScriptInstruction {

	public BitwiseRightShiftInstruction() {
		
	}

	@Override
	public String displayString() {
		return "BITWISE_RIGHT_SHIFT";
	}

	@Override
	public byte[] rawBytes() {
		return new byte[] {0x2E};
	}

	@Override
	public byte opcode() {
		return 0x2E;
	}

	@Override
	public int numArgBytes() {
		return 0;
	}

	@Override
	public ScriptInstruction createWithArgs(byte[] args, GCNCMBFileHandler handler) {
		return new BitwiseRightShiftInstruction();
	}
}
