package syntaxtree;

import java.util.List;

import syntaxtree.datatypes.Type;
import syntaxtree.declarations.Decl;
import syntaxtree.declarations.ProcedureDecl;
import bytecode.CodeFile;

import compiler.ErrorMessage;
import compiler.SymbolTable;
import compiler.exception.SemanticException;

public class Program extends AstNode {

	private static final String MAIN = "Main";

	private List<Decl> decls;

	public Program(List<Decl> decls) {
		this.decls = decls;
	}

	public List<Decl> getDecls() {
		return decls;
	}

	public void checkSemantics() throws SemanticException {
		checkSemantics(null);
	}

	@Override
	public void checkSemantics(SymbolTable parentSymbolTable)
			throws SemanticException {
		SymbolTable symbolTable = new SymbolTable();
		boolean hasValidMain = false;

		for (Decl decl : decls) {
			symbolTable.insert(decl.getName(), decl);

			decl.checkSemantics(symbolTable);

			if (!hasValidMain) {
				hasValidMain = isValidMainProcedure(decl);
			}
		}

		if (!hasValidMain) {
			throw new SemanticException(ErrorMessage.MISSING_MAIN);
		}
	}

	private boolean isValidMainProcedure(Decl decl) {
		boolean validMain = false;

		if (MAIN.equals(decl.getName().getString())
				&& decl instanceof ProcedureDecl) {

			ProcedureDecl main = (ProcedureDecl) decl;

			validMain = main.getParameterDecls().isEmpty()
					&& main.getReturnType().getType() == Type.VOID;
		}

		return validMain;
	}

	@Override
	public List<String> makeAstStringList() {
		return new AstStringListBuilder("PROGRAM").addIndented(decls).build();
	}

	public void generateCode(CodeFile codeFile) {
		// TODO Auto-generated method stub
	}
}
