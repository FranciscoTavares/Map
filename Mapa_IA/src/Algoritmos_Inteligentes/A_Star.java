package Algoritmos_Inteligentes;

import Métodos.Estado;
import Métodos.EstadoVisual;
import Métodos.No;
import java.util.ArrayList;
import java.util.HashMap;

public class A_Star {
	
	public static ArrayList<Estado> resolver(No noInicial,No noFinal){	
		ArrayList<No> borda = new ArrayList<No>();
		HashMap<No,No> sucessores = new HashMap<No,No>();
		HashMap<No,Integer> custoDaDistancia = new HashMap<No,Integer>();		
		ArrayList<No> caminho = new ArrayList<No>();
		ArrayList<Estado> vetorCaminho = new ArrayList<Estado>();
		
		borda.add(noInicial);		
		sucessores.put(noInicial, noInicial);	
		custoDaDistancia.put(noInicial, 0);
		
		No noAtual = null;		
		Integer custo = 0;
		
		while(!borda.isEmpty()){			
			noAtual = borda.remove(0);
			if(noAtual == noFinal)break;			
			for(No proximoNo: noAtual.getVizinho()){
				if(proximoNo.quadrado==EstadoVisual.PAREDE)continue;
				custo = 1 + custoDaDistancia.get(noAtual);
				if(!custoDaDistancia.containsKey(proximoNo) || custo < custoDaDistancia.get(proximoNo)){
					//atualizacao do custo de chegar ate o no
					custoDaDistancia.put(proximoNo, custo);
					//atualizacao do custo heuristico
					proximoNo.custoH = custo + heuristica(proximoNo.estado,noFinal.estado)+proximoNo.custoH;
					borda.add(proximoNo);
					sucessores.put(proximoNo, noAtual);					
				}				
			}				
		}
		if(noAtual != noFinal){
			return null;
		}
		caminho.add(noAtual);
		while(noAtual != noInicial) {			
			noAtual = sucessores.get(noAtual);		
			caminho.add(noAtual);			
		}
		int contPassos=1;
		for(No no: caminho){
			if(no.estado.comparaEstado(noInicial.estado) || no.estado.comparaEstado(noFinal.estado))continue;
			vetorCaminho.add(no.estado);
			contPassos++;
		}	
		
		System.out.println(contPassos + " movimentos para solucao");
		return vetorCaminho;
	}
	
	private static Integer heuristica(Estado a, Estado b){
		return (Math.abs(a.x - b.x) + Math.abs(a.y - b.y));
	}	
}
