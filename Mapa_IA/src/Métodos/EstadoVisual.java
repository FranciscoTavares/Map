package Métodos;

import java.awt.Color;

public enum EstadoVisual {	
	
	//definicoes dos tipos de quadrados	graficamente
	INICIO("S",Color.GREEN,false),
	FIM("E",Color.RED,false),
	CAMINHO(" ",Color.WHITE,false),
	SOLUCAO("o",Color.CYAN,false),
	PAREDE("I",Color.BLACK,true);
	
	public final String caractere;
	public final Color cor;
	public final boolean solida;
	
	EstadoVisual(String caractere,Color cor, boolean solida){
		this.caractere=caractere;
		this.cor = cor;
		this.solida = solida;
	}
}
