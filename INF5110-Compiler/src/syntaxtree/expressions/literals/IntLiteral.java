package syntaxtree.expressions.literals;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import bytecode.CodeProcedure;
import bytecode.instructions.PUSHINT;

public class IntLiteral extends Literal {

	private Integer number;

	public IntLiteral(Integer number) {
		this.number = number;
	}

	public Integer getNumber() {
		return number;
	}

	@Override
	public DataType getDataType() {
		return new DataType(Type.INT);
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		procedure.addInstruction(new PUSHINT(number));
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("INT_LITERAL").addInline(
				number.toString()).build();
	}
}
