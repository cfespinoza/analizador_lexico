package analizador_lexico;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;

public class AnalizadorLexico {
	
	private Reader input;
	private StringBuffer lex;
   	private int sigCar;
   	private int filaInicio;
   	private int columnaInicio;
   	private int filaActual;
   	private int columnaActual;
   	private HashMap<String, ClaseLexica> clasesLexicasById;
   	private static String NL = System.getProperty("line.separator");
   	private static enum Estado {
   		INICIO
   	}

    private Estado estado;
	/*
	private static enum Estado {
		END_FINDEC,
		END_SEP,
		END_TIPO,
		END_INT,
		END_REAL,
		END_CAP,
		END_CCIERRE,
		END_REC,
		END_ENDREC,
		END_POINTER,
		END_OBJECT,
		END_EXTENDS,
		END_VAR,
		END_END,
		END_FUN,
		END_METHOD,
		END_RETURNS,
		END_THIS,
		END_SUPER,
		END_PUNTO,
		END_APUNTADOR,
		END_MENOR,
		END_MAYOR,
		END_MENORIGUAL,
		END_MAYORIGUAL,
		END_IGUAL,
		END_DISTINTO,
		END_OR,
		END_MAS,
		END_MENOS,
		END_POR,
		END_DIV,
		END_MOD,
		END_AND,
		END_NOT,
		END_PAP,
		END_PCIERRE,
		END_NULL,
		END_LITENTPOS,
		END_ASIGN,
		END_IN,
		END_OUT,
		END_ALLOC,
		END_FREE,
		END_IF,
		END_ELSE,
		END_ELSEIF,
		END_THEN,
		END_ENDIF,
		END_WHILE,
		END_DO,
		END_ENDWHILE,
		END_CARACTER,
		// Clases auxiliares
		END_DIGITO,
		END_DIGITOPOS,
		END_BARRABAJA,
		END_EOF
	}
	*/

	public AnalizadorLexico(Reader input) throws IOException {
		this.input = input;
		lex = new StringBuffer();
		sigCar = input.read();
		filaActual = 1;
		columnaActual = 1;
		this.clasesLexicasById = this.createClassesMap();
	}
	
	private HashMap<String, ClaseLexica> createClassesMap(){
		HashMap<String, ClaseLexica> classes = new HashMap<String, ClaseLexica>();
		classes.put("rec", ClaseLexica.REC);
		classes.put("endrec", ClaseLexica.ENDREC);
		classes.put("tipo", ClaseLexica.TIPO);
		classes.put("pointer", ClaseLexica.POINTER);
		classes.put("object", ClaseLexica.OBJECT);
		classes.put("endobject", ClaseLexica.ENDOBJECT);
		classes.put("extends", ClaseLexica.EXTENDS);
		classes.put("end", ClaseLexica.END);
		classes.put("fun", ClaseLexica.FUN);
		classes.put("method", ClaseLexica.METHOD);
		classes.put("return", ClaseLexica.RETURN);
		classes.put("this", ClaseLexica.THIS);
		classes.put("super", ClaseLexica.SUPER);
		classes.put("null", ClaseLexica.NULL);
		classes.put("in", ClaseLexica.IN);
		classes.put("out", ClaseLexica.OUT);
		classes.put("alloc", ClaseLexica.ALLOC);
		classes.put("free", ClaseLexica.FREE);
		classes.put("alloc", ClaseLexica.ALLOC);
		classes.put("if", ClaseLexica.IF);
		classes.put("else", ClaseLexica.ELSE);
		classes.put("fi", ClaseLexica.FI);
		classes.put("elseif", ClaseLexica.ELSEIF);
		classes.put("then", ClaseLexica.THEN);
		classes.put("while", ClaseLexica.WHILE);
		classes.put("do", ClaseLexica.DO);
		classes.put("endwhile", ClaseLexica.ENDWHILE);

		classes.put("#", ClaseLexica.SEP);
		classes.put(";", ClaseLexica.SEMICOLON);
		classes.put("[", ClaseLexica.APCOR);
		classes.put("]", ClaseLexica.CICOR);
		classes.put("&", ClaseLexica.AMPERSAN);
		classes.put("(", ClaseLexica.APPAR);
		classes.put(")", ClaseLexica.CIPAR);
		classes.put(".", ClaseLexica.DOT);
		classes.put("^", ClaseLexica.INVERTEDV);
		classes.put(">", ClaseLexica.GREATERTHAN);
		classes.put("<", ClaseLexica.LOWERTHAN);
		classes.put(">=", ClaseLexica.GREATEREQ);
		classes.put("<=", ClaseLexica.LOWERTHAN);
		classes.put("==", ClaseLexica.EQUAL);
		classes.put("!=", ClaseLexica.DISTINCT);
		classes.put("||", ClaseLexica.OR);
		classes.put("+", ClaseLexica.PLUS);
		classes.put("-", ClaseLexica.MINUS);
		classes.put("*", ClaseLexica.MULTI);
		classes.put("/", ClaseLexica.DIVISION);
		classes.put("%", ClaseLexica.MOD);
		classes.put("&&", ClaseLexica.AND);
		classes.put("!", ClaseLexica.NEGATION);
		classes.put("=", ClaseLexica.ASSIG);
		
		return classes;
	}
	
	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0, lex.length());
		while (true) {
		}
	}
	
	private UnidadLexica unidadId() {
		String auxLex = lex.toString();
		if (clasesLexicasById.get(auxLex).equals(null)){
			return new UnidadLexicaMultivaluada(filaInicio, columnaInicio,
					ClaseLexica.IDEN, lex.toString());
		}
		else{
			return new UnidadLexicaMultivaluada(filaInicio, columnaInicio,
					clasesLexicasById.get(auxLex), lex.toString());
		}
	}

	private void error() {
		System.err.println("(" + filaActual + ',' + columnaActual
				+ "):Caracter inexperado");
		System.exit(1);
	}
	
	public static void main(String arg[]) throws IOException {
		Reader input = new InputStreamReader(new FileInputStream("input.txt"));
		AnalizadorLexico al = new AnalizadorLexico(input);
		UnidadLexica unidad;
		do {
			unidad = al.sigToken();
			System.out.println(unidad);
		} while (unidad.clase() != ClaseLexica.EOF);
	}
	
}
