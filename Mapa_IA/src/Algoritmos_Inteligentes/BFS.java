package Algoritmos_Inteligentes;

import Métodos.Estado;
import Métodos.EstadoVisual;
import Métodos.No;
import java.util.ArrayList;
import java.util.HashMap;

public class BFS {

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
				//so inclui na borda caminhos, desconsidera paredes
				if(proximoNo.quadrado==EstadoVisual.PAREDE)continue;	
				//impressao do loop
				System.out.println(proximoNo);
				//bfs apenas inclui no fim da borda
				if (!borda.contains(proximoNo))borda.add(proximoNo);
				sucessores.put(proximoNo, noAtual);					
			}	
		}
		if(noAtual != noFinal){
			//nao encontrou solucao
			return null;
		}
		
		//apos detectar que noAtual eh igual ao final
		caminho.add(noAtual);
		while(noAtual != noInicial) {			
			noAtual = sucessores.get(noAtual);		
			//caminho formado pelo nos
			caminho.add(noAtual);			
		}
		
		int contPassos= 1;
		for(No no : caminho){
			if(no.estado.comparaEstado(noInicial.estado) || no.estado.comparaEstado(noFinal.estado))continue;
			//caminho formado pelas coordenadas dos nos
			vetorCaminho.add(no.estado);
			contPassos++;
		}	

		System.out.println(contPassos + " movimentos para solucao");
		return vetorCaminho;
	}		
}
