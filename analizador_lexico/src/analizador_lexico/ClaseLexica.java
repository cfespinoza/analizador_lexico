package analizador_lexico;

public enum ClaseLexica {
	
	SEP, 			// #
	COMMENT, 		// @
	SEMICOLON, 		// ;
	IDEN, 			// (letra|barrabaja)(letra|digito|barrabaja)*
	TIPO, 			// tipo
	INT,			// int
	REAL, 			// real
	APCOR, 			// [ 
	CICOR, 			// ]
	REC, 			// rec
	ENDREC, 		// endrec
	POINTER, 		// pointer
	OBJECT, 		// object
	ENDOBJECT, 		// endobject
	EXTENDS, 		// extends
	END, 			// end
	FUN, 			// fun
	METHOD, 		// method
	RETURN, 		// return
	AMPERSAN, 		// &
	THIS, 			// this
	SUPER, 			// super
	APPAR, 			// (
	CIPAR, 			// )
	DOT, 			// .
	INVERTEDV, 		// ^
	GREATERTHAN, 	// >
	LOWERTHAN, 		// <
	GREATEREQ, 		// >=
	LOWEREQ, 		// <=
	EQUAL, 			// ==
	DISTINCT, 		// !=
	OR, 			// \|\|
	PLUS, 			// +
	MINUS, 			// -
	MULTI, 			// \*
	DIVISION, 		// /
	MOD, 			// %
	AND, 			// &&
	NEGATION, 		// !
	NULL, 			// null	
	ASSIG, 			// =
	IN, 			// in
	OUT, 			// out
	ALLOC, 			// alloc
	FREE, 			// free
	IF, 			// if
	ELSE, 			// else
	FI, 			// fi
	ELSEIF, 		// elseif
	THEN, 			// then
	WHILE, 			// while
	DO, 			// do
	ENDWHILE, 		// endwhile
	ARROBA,			// @
	NUMBER,			// NUMENTERO | NUMREAL
	
	// AUXILIARES
	
	NUMENTERO,	 	// POSDIGIT CERO
	NUMREAL,		// digitoPos(digito)*(punto(digito)(digitoPos)*|e(digitoPos)(digito)*|e(menos)(digitoPos)(digito))
	CERO, 			// 0
	UNDERSCORE,		// _
	POSDIGIT,		// 1..9
	EOF, 			// EOF
	LETTER			// (a..z|A..Z)

}