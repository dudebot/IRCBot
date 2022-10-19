/*****************************************************************************

JEP - Java Math Expression Parser 2.3.1
      January 26 2006
      (c) Copyright 2004, Nathan Funk and Richard Morris
      See LICENSE.txt for license information.

*****************************************************************************/
/* Generated By:JJTree: Do not edit this line. ASTInteger.java */
package org.nfunk.jep;

/**
 * Constant Node
 */
public class ASTConstant extends SimpleNode {
	private Object value;
	
	public ASTConstant(int id) {
		super(id);
	}
	
	public ASTConstant(Parser p, int id) {
		super(p, id);
	}
	
	public void setValue(Object val) {
		value = val;
	}
	
	public Object getValue() {
		return value;
	}

	/** Accept the visitor. **/
	public Object jjtAccept(ParserVisitor visitor, Object data)  throws ParseException
	{
		return visitor.visit(this, data);
	}
	
	public String toString() {
		return "Constant: " + value;
	}
}