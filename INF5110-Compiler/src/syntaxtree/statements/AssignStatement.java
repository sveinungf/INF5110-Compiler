package syntaxtree.statements;

import java.util.List;

import syntaxtree.AstStringListBuilder;
import syntaxtree.expressions.Expression;
import syntaxtree.expressions.Variable;

public class AssignStatement extends Statement {

	private Variable leftHandSide;
	private Expression rightHandSide;

	public AssignStatement(Variable leftHandSide, Expression rightHandSide) {
		this.leftHandSide = leftHandSide;
		this.rightHandSide = rightHandSide;
	}

	public Variable getLeftHandSide() {
		return leftHandSide;
	}

	public Expression getRightHandSide() {
		return rightHandSide;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("ASSIGN_STMT").addIndented(
				leftHandSide, rightHandSide).build();
	}
}