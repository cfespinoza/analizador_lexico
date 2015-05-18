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
		INICIO, REC_SEP, REC_MUL, REC_DIV, REC_PAP, REC_PCIERR, REC_PCOMA, 
		REC_IGUAL, REC_MAS, REC_MENOS, REC_ID, REC_ENTERO, REC_REAL, REC_MENOR, 
		REC_MAYOR, REC_MENORIGUAL, REC_MAYORIGUAL, REC_IGUALCOMP, REC_CCIERR, 
		REC_CAPER, REC_IDIST, REC_DIST, REC_AMPER, REC_2AMPER, REC_COM, 
		REC_EOF, REC_INVERTEDV, REC_MOD, REC_FINDREAL, REC_REAL_FINAL, REC_BAR, 
		REC_2BAR, REC_FINDREAL_E, REC_AUX_REAL
	}

	private Estado estado;

	public AnalizadorLexico(Reader input) throws IOException {
		this.input = input;
		lex = new StringBuffer();
		sigCar = input.read();
		filaActual = 1;
		columnaActual = 1;
		this.clasesLexicasById = this.createClassesMap();
	}

	private HashMap<String, ClaseLexica> createClassesMap() {
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
		classes.put("int", ClaseLexica.INT);
		classes.put("float", ClaseLexica.REAL);
		classes.put("eof", ClaseLexica.EOF);
		classes.put("EOF", ClaseLexica.EOF);

		return classes;
	}

	private boolean hayLetra() {
		return (sigCar >= 'a' && sigCar <= 'z' || sigCar >= 'A' && sigCar <= 'Z');
	}

	private boolean hayDigitoPos() {
		return sigCar >= '1' && sigCar <= '9';
	}

	private boolean hayCero() {
		return sigCar == '0';
	}
	
	private boolean hayNumero() {
		return hayDigitoPos() || hayCero();
	}

	private boolean haySuma() {
		return sigCar == '+';
	}

	private boolean hayResta() {
		return sigCar == '-';
	}

	private boolean hayMul() {
		return sigCar == '*';
	}

	private boolean hayDiv() {
		return sigCar == '/';
	}

	private boolean hayPAp() {
		return sigCar == '(';
	}

	private boolean hayPCierre() {
		return sigCar == ')';
	}

	private boolean hayIgual() {
		return sigCar == '=';
	}

	private boolean hayMenor() {
		return sigCar == '<';
	}

	private boolean hayMayor() {
		return sigCar == '>';
	}

	private boolean hayExclamacion() {
		return sigCar == '!';
	}

	private boolean hayPComa() {
		return sigCar == ';';
	}

	private boolean hayAlmohadilla() {
		return sigCar == '#';
	}

	private boolean haySep() {
		return sigCar == ' ' || sigCar == '\t' || sigCar == '\n';
	}

	private boolean hayNL() {
		return sigCar == '\r' || sigCar == '\b' || sigCar == '\n';
	}

	private boolean hayEOF() {
		return sigCar == -1;
	}

	private boolean hayAmper() {
		return sigCar == '&';
	}

	private boolean hayGuionBajo() {
		return sigCar == '_';
	}

	private boolean hayPunto() {
		return sigCar == '.';
	}

	private boolean hayInvertedV() {
		return sigCar == '^';
	}

	private boolean hayBar() {
		return sigCar == '|';
	}

	private boolean hayMod() {
		return sigCar == '%';
	}

	private boolean hayArroba() {
		return sigCar == '@';
	}

	private boolean hayElevado() {
		return sigCar == 'e';
	}
	
	private boolean hayCAper(){
		return sigCar == '[';
	}
	
	private boolean hayCCierr(){
		return sigCar == ']';
	}

	public UnidadLexica sigToken() throws IOException {
		estado = Estado.INICIO;
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		lex.delete(0, lex.length());
		while (true) {
			switch (estado) {
			case INICIO:
				if (hayLetra()||hayGuionBajo())
					transita(Estado.REC_ID);
				else if (hayDigitoPos())
					transita(Estado.REC_ENTERO);
				else if (haySuma())
					transita(Estado.REC_MAS);
				else if (hayResta())
					transita(Estado.REC_MENOS);
				else if (hayMul())
					transita(Estado.REC_MUL);
				else if (hayDiv())
					transita(Estado.REC_DIV);
				else if (hayPAp())
					transita(Estado.REC_PAP);
				else if (hayPCierre())
					transita(Estado.REC_PCIERR);
				else if (hayIgual())
					transita(Estado.REC_IGUAL);
				else if (hayMenor())
					transita(Estado.REC_MENOR);
				else if (hayMayor())
					transita(Estado.REC_MAYOR);
				else if (hayExclamacion())
					transita(Estado.REC_IDIST);
				else if (hayAmper())
					transita(Estado.REC_AMPER);
				else if (hayPComa())
					transita(Estado.REC_PCOMA);
				else if (hayInvertedV())
					transita(Estado.REC_INVERTEDV);
				else if (hayMod())
					transita(Estado.REC_MOD);
				else if (hayCAper())
					transita(Estado.REC_CAPER);
				else if (hayCCierr())
					transita(Estado.REC_CCIERR);
				else if (hayAlmohadilla())
					transita(Estado.REC_SEP);
				else if (hayArroba())
					transitaIgnorando(Estado.REC_COM);
				else if (haySep()||hayNL())
					transitaIgnorando(Estado.INICIO);
				else if (hayEOF())
					transita(Estado.REC_EOF);
				else if (hayBar())
					transita(Estado.REC_BAR);
				else
					error();
				break;
			case REC_MOD:
				return unidadMod();
			case REC_SEP:
				return unidadSep();
			case REC_ID:
				if (hayLetra() || hayNumero() || hayGuionBajo())
					transita(Estado.REC_ID);
				else
					return unidadId();
				break;
			case REC_ENTERO:
				if(hayNumero())
					transita(Estado.REC_ENTERO);
				else if (hayPunto())
					transita(Estado.REC_FINDREAL);
				else if (hayElevado())
					transita(Estado.REC_FINDREAL_E);
				else
					return unidadEntero();
				break;
			case REC_FINDREAL:
				// digitoPos(digito)*(punto(digito)(digitoPos)*|e(digitoPos)(digito)*|e(menos)(digitoPos)(digito))
				if (hayNumero()){
					transita(Estado.REC_REAL);
				}
				else 
					error();
				break;
			case REC_FINDREAL_E:
				if (hayResta())
					transita(Estado.REC_AUX_REAL);
				else if (hayDigitoPos())
					transita(Estado.REC_REAL_FINAL);
				else 
					error();
				break;
			case REC_AUX_REAL:
				if(hayDigitoPos())
					transita(Estado.REC_REAL_FINAL);
				else
					error();
				break;
					
			case REC_REAL:
				if (hayDigitoPos()){
					transita(Estado.REC_REAL);
				}
				else return unidadReal();
				
			case REC_REAL_FINAL: 
				if(hayNumero()){
					transita(Estado.REC_REAL_FINAL);
				}
				else return unidadReal();
				break;				
				
			case REC_MAS:
				return unidadMas();
			case REC_MENOS:
				return unidadMenos();
			case REC_MUL:
				return unidadMul();
			case REC_DIV:
				return unidadDiv();
			case REC_PAP:
				return unidadApPar();
			case REC_PCIERR:
				return unidadCiPar();
			case REC_IGUAL:
				if (hayIgual())
					transita(Estado.REC_IGUALCOMP);
				else
					return unidadAssig();
				break;
			case REC_MENOR:
				if (hayIgual())
					transita(Estado.REC_MENORIGUAL);
				else
					return unidadMenor();
				break;
			case REC_MAYOR:
				if (hayIgual())
					transita(Estado.REC_MAYORIGUAL);
				else
					return unidadMayor();
				break;
			case REC_MENORIGUAL:
				return unidadMenorIgual();
			case REC_MAYORIGUAL:
				return unidadMayorIgual();
			case REC_IGUALCOMP:
				return unidadEqual();
			case REC_IDIST:
				if (hayIgual())
					transita(Estado.REC_DIST);
				else 
					return unidadNeg();
				break;
			case REC_DIST:
				return unidadDistinto();
			case REC_AMPER:
				if (hayAmper())
					transita(Estado.REC_2AMPER);
				else
					return unidadAmpersan();
				break;
			case REC_2AMPER:
				return unidad2Amper();
			case REC_PCOMA:
				return unidadPComa();
			case REC_COM:
				if (hayNL())
					transitaIgnorando(Estado.INICIO);
				else if (hayEOF())
					transita(Estado.REC_EOF);
				else
					transitaIgnorando(Estado.REC_COM);
				break;
			case REC_BAR:
				if(hayBar()){
					transita(Estado.REC_2BAR);
				}
				else error();
				break;
			case REC_2BAR:
				return unidad2Bar();
			case REC_INVERTEDV:
				return unidadInvertedV();
			case REC_CAPER:
				return unidadApCor();
			case REC_CCIERR: 
				return unidadCiCor();
			case REC_EOF:
				return unidadEof();
			}
		}
	}

	private UnidadLexica unidadVarId() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.IDEN, lex.toString());
	}

	private UnidadLexica unidadReal() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.NUMREAL, lex.toString()); 
	}

	private UnidadLexica unidadEntero() {
		return new UnidadLexicaMultivaluada(filaInicio,columnaInicio,ClaseLexica.NUMENTERO, lex.toString()); 
	}

	private void transita(Estado sig) throws IOException {
		lex.append((char) sigCar);
		sigCar();
		estado = sig;
	}

	private void transitaIgnorando(Estado sig) throws IOException {
		sigCar();
		filaInicio = filaActual;
		columnaInicio = columnaActual;
		estado = sig;
	}

	private void sigCar() throws IOException {
		sigCar = input.read();
		if (sigCar == NL.charAt(0))
			saltaFinDeLinea();
		if (sigCar == '\n') {
			filaActual++;
			columnaActual = 0;
		} else {
			columnaActual++;
		}
	}

	private void saltaFinDeLinea() throws IOException {
		for (int i = 1; i < NL.length(); i++) {
			sigCar = input.read();
			if (sigCar != NL.charAt(i))
				error();
		}
		sigCar = '\n';
	}

	
	
	private UnidadLexica unidadSep() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.SEP);
	}
	
	private UnidadLexica unidadPComa() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.SEMICOLON);
	}
	
	private UnidadLexica unidadApCor() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.APCOR);
	}
	
	private UnidadLexica unidadCiCor() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.CICOR);
	}
	
	private UnidadLexica unidadAmpersan() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.AMPERSAN);
	}

	private UnidadLexica unidadApPar() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.APPAR);
	}

	private UnidadLexica unidadCiPar() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.CIPAR);
	}
	
	private UnidadLexica unidadDot() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.DOT);
	}
	
	private UnidadLexica unidadMas() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.PLUS);
	}

	private UnidadLexica unidadMenos() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.MINUS);
	}

	private UnidadLexica unidadMul() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.MULTI);
	}

	private UnidadLexica unidadDiv() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.DIVISION);
	}

	private UnidadLexica unidadInvertedV() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio, ClaseLexica.INVERTEDV);
	}

	private UnidadLexica unidadAssig() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.ASSIG);
	}
	
	private UnidadLexica unidadEqual() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.EQUAL);
	}

	private UnidadLexica unidadMenor() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.LOWERTHAN);
	}

	private UnidadLexica unidadMayor() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.GREATERTHAN);
	}

	private UnidadLexica unidadMenorIgual() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.LOWEREQ);
	}

	private UnidadLexica unidadMayorIgual() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.GREATEREQ);
	}

	private UnidadLexica unidadDistinto() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.DISTINCT);
	}

	private UnidadLexica unidad2Amper() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.AND);
	}
	
	private UnidadLexica unidad2Bar() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.OR);
	}
	
	private UnidadLexica unidadNeg(){
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.NEGATION);
	}
	
	private UnidadLexica unidadMod(){
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.MOD);
	}

	private UnidadLexica unidadEof() {
		return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
				ClaseLexica.EOF);
	}
	
	private UnidadLexica unidadId() {
		String auxLex = lex.toString();
		ClaseLexica clase = clasesLexicasById.get(auxLex);
		if (clase == null) {
			return unidadVarId();
		} else {
			return new UnidadLexicaUnivaluada(filaInicio, columnaInicio,
					clasesLexicasById.get(auxLex));
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
