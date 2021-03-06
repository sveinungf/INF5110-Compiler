package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import bytecode.CodeProcedure;
import bytecode.instructions.RETURN;

import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class ReturnStatement extends Statement {

	private Expression expression;

	public ReturnStatement() {
		this(null);
	}

	public ReturnStatement(Expression expression) {
		this.expression = expression;
	}

	public Expression getExpression() {
		return expression;
	}

	public DataType getReturnType() {
		if (expression != null) {
			return expression.getDataType();
		}

		return new DataType(Type.VOID);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		if (expression != null) {
			expression.checkSemantics(symbolTable);
		}
	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		if (expression != null) {
			expression.generateCode(procedure);
		}

		procedure.addInstruction(new RETURN());
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("RETURN_STMT").addInline(expression)
				.build();
	}
}
