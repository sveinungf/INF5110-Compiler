package syntaxtree.expressions.operator;

import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.expressions.Expression;
import syntaxtree.operators.RelationalOperator;
import bytecode.CodeProcedure;
import bytecode.instructions.EQ;
import bytecode.instructions.GT;
import bytecode.instructions.GTEQ;
import bytecode.instructions.Instruction;
import bytecode.instructions.LT;
import bytecode.instructions.LTEQ;
import bytecode.instructions.NEQ;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.throwable.SemanticException;

public class RelationalOperatorExpression extends
		BinaryOperatorExpression<RelationalOperator> {

	public RelationalOperatorExpression(Expression leftExpression,
			RelationalOperator operator, Expression rightExpression) {
		super(leftExpression, operator, rightExpression);
	}

	@Override
	public DataType getDataType() {
		return new DataType(Type.BOOL);
	}

	@Override
	public void checkSemantics(SymbolTable symbolTable)
			throws SemanticException {
		leftExpression.checkSemantics(symbolTable);
		rightExpression.checkSemantics(symbolTable);

		DataType leftType = leftExpression.getDataType();
		DataType rightType = rightExpression.getDataType();

		if (!isAllowed(leftType) || !isAllowed(rightType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
		}

		if (!leftType.isA(rightType) && !rightType.isA(leftType)) {
			throw new SemanticException(ErrorMessage.UNALLOWED_TYPE_RELATIONAL);
		}

	}

	@Override
	public void generateCode(CodeProcedure procedure) {
		leftExpression.generateCode(procedure);
		rightExpression.generateCode(procedure);
		procedure.addInstruction(getByteCodeInstruction());
	}

	private Instruction getByteCodeInstruction() {
		switch (operator) {
		case EQUAL:
			return new EQ();
		case GREATER:
			return new GT();
		case GREATER_EQUAL:
			return new GTEQ();
		case LESS:
			return new LT();
		case LESS_EQUAL:
			return new LTEQ();
		case NOT_EQUAL:
			return new NEQ();
		default:
			return null;
		}
	}

	private boolean isAllowed(DataType type) {
		switch (operator) {
		case EQUAL:
		case NOT_EQUAL:
			return isAllowedInEquality(type);
		case GREATER:
		case GREATER_EQUAL:
		case LESS:
		case LESS_EQUAL:
			return isAllowedInRelational(type);
		default:
			return false;
		}
	}

	private static boolean isAllowedInEquality(DataType type) {
		return type.getType() != Type.VOID;
	}

	private static boolean isAllowedInRelational(DataType type) {
		return type.getType() == Type.FLOAT || type.getType() == Type.INT;
	}
}
