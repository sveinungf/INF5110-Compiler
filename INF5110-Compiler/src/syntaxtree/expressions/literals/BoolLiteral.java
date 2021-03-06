package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHBOOL;

public class BoolLiteral extends Literal {

	private Boolean bool;

	public BoolLiteral(Boolean bool) {
		this.bool = bool;
	}

	public Boolean getBool() {
		return bool;
	}

	@Override
	public DataType getDataType() {
		return new DataType(Type.BOOL);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		procedure.addInstruction(new PUSHBOOL(bool));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("BOOL_LITERAL").addInline(
				bool.toString()).build();
	}
}
