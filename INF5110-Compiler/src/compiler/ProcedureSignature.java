package compiler;

import java.util.LinkedList;
import java.util.List;

import syntaxtree.Name;
import syntaxtree.datatypes.DataType;
import syntaxtree.datatypes.Type;
import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ParameterDecl;
import syntaxtree.declarations.ProcedureDecl;
import syntaxtree.statements.Statement;

public class ProcedureSignature {

	private Name name;
	private DataType returnType;
	private List<ParameterDecl> parameters;

	public ProcedureSignature(String name, DataType returnType) {
		this.name = new Name(name);
		this.returnType = returnType;
		parameters = new LinkedList<ParameterDecl>();
	}

	public ProcedureSignature(String name, Type returnType) {
		this(name, new DataType(returnType));
	}

	public Name getName() {
		return name;
	}

	public DataType getReturnType() {
		return returnType;
	}

	public ProcedureSignature addParameter(boolean reference, String paramName,
			DataType paramType) {
		Name name = new Name(paramName);
		parameters.add(new ParameterDecl(reference, name, paramType));

		return this;
	}

	public ProcedureSignature addParameter(boolean reference, String paramName,
			Type paramType) {
		return addParameter(reference, paramName, new DataType(paramType));
	}

	public ProcedureDecl buildDecl() {
		return new ProcedureDecl(name, returnType, parameters,
				new LinkedList<Decl>(), new LinkedList<Statement>());
	}
}
