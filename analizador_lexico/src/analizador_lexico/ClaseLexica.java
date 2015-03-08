package analizador_lexico;

public enum ClaseLexica {
	
	SEP, 			// #
	COMMENT, 		// @
	SEMICOLON, 		// ;
	VARID, 			// (letra|barrabaja)(letra|digito|barrabaja)*
	IDEN,			// letra*
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
	FIELD, 			// letra*
	INVERTEDV, 		// ^
	// DESIGNADOR, 	// (THIS|SUPER|VAR)((PUNTO CAMPO)|(APCOR|CAMPO|CECOR)|(INVERTEDV))
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
	// CASTING, 	// (APPAR TIPO CIPAR)
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
	
	// AUXILIARES
	
	DIGIT,	 		// 0|POSDIGIT
	UNDERSCORE,		// _
	POSDIGIT,		// 1..9
	EOF, 			// EOF
	LETTER,			// (a..z|A..Z)
	WORD, 			// (A..Z|a..z)+
	
	/*
	FINDEC,
	SEP,
	TIPO,
	INT,
	REAL,
	CAP,
	CCIERRE,
	REC,
	ENDREC,
	POINTER,
	OBJECT,
	ENOBJECT,
	EXTENDS,
	VAR,
	END,
	FUN,
	METHOD,
	RETURNS,
	THIS,
	SUPER,
	PUNTO,
	APUNTADOR,
	MENOR,
	MAYOR,
	MENORIGUAL,
	MAYORIGUAL,
	IGUAL,
	DISTINTO,
	OR,
	MAS,
	MENOS,
	POR,
	DIV,
	MOD,
	AND,
	NOT,
	PAP,
	PCIERRE,
	NULL,
	LITENTPOS,
	ASIGN,
	IN,
	OUT,
	ALLOC,
	FREE,
	IF,
	ELSE,
	ELSEIF,
	THEN,
	ENDIF,
	WHILE,
	DO,
	ENDWHILE,
	CARACTER,
	// Clases auxiliares
	DIGITOPOS,
	BARRABAJA,
	EOF
	*/
}

