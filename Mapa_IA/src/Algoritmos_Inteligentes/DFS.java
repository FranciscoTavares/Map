package Algoritmos_Inteligentes;

import Métodos.No;
import Métodos.Estado;
import Métodos.EstadoVisual;
import java.util.ArrayList;
import java.util.HashMap;

public class DFS {

	public static ArrayList<Estado> resolver(No noInicial,No noFinal){	
		ArrayList<No> borda = new ArrayList<No>();
		HashMap<No,No> sucessores = new HashMap<No,No>();
		ArrayList<No> caminho = new ArrayList<No>();
		ArrayList<Estado> vetorCaminho = new ArrayList<Estado>();

		borda.add(noInicial);		
		sucessores.put(noInicial, noInicial);	
		No noAtual = null;		

		while(!borda.isEmpty()){			
			noAtual = borda.remove(0);
			if(noAtual == noFinal)break;			
			for(No proximoNo: noAtual.getVizinho()){
				if(proximoNo.quadrado==EstadoVisual.PAREDE)continue;	
				//impressao do loop
				System.out.println(proximoNo);
				//dfs inclui sempre no inicio da borda, caso nao exista na borda
				if (!borda.contains(proximoNo))borda.add(0,proximoNo);
				sucessores.put(proximoNo, noAtual);					
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
		int contPassos= 1;
		for(No no: caminho){
			if(no.estado.comparaEstado(noInicial.estado) || no.estado.comparaEstado(noFinal.estado))continue;
			vetorCaminho.add(no.estado);
			contPassos++;
		}	

		System.out.println(contPassos + " movimentos para solucao");
		return vetorCaminho;
	}		
}
