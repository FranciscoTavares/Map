package Métodos;

import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Forma {

	public No inicio, fim;
	private Random rand = new Random();      //vizinho direita, abaixo, esquerda, acima 
	protected static final int[][] DESTINO = { { 1, 0 }, { 0, 1 }, { -1, 0 }, { 0, -1 } };
	protected final int LINHAS, COLUNAS;
	protected ArrayList<No> listaDeNos = new ArrayList<No>();

	public Forma(int linhas, int colunas, int percentualParedes){
		this.LINHAS = linhas;
		this.COLUNAS = colunas;
		geraListaDeNos();
		setVizinhoDosNos();		
		gerarLabirinto(percentualParedes);		
	}

	private void geraListaDeNos() { //montando coordenadas do labirinto
		for (int x = 0; x < LINHAS; x++) {
			for (int y = 0; y < COLUNAS; y++) {
				listaDeNos.add(new No(new Estado(x, y)));
			}
		}
	}

	private void setVizinhoDosNos() {
		for (No no : listaDeNos) {
			Estado coordenada = no.estado;
			for (int[] direcao : DESTINO) {
				int nX = coordenada.x + direcao[0];
				int nY = coordenada.y + direcao[1];
				int indice = (nX * COLUNAS) + nY;
				if (nX < 0 || nY < 0 || nX >= LINHAS || nY >= COLUNAS)
					continue; // impede que n�s saiam dos limites
				no.addVizinho(listaDeNos.get(indice));
			}
		}
	}

	private void gerarLabirinto(int percentualParedes){
		Stack<No> pilha = new Stack<>();
		ArrayList<No> naoVisitados = new ArrayList<>();

		for(No no: listaDeNos){
			naoVisitados.add(no);
		}

		ArrayList<No> listaVizinhos;
		No noAtual = naoVisitados.remove(COLUNAS+1);		
		//noAtual.custoH=0;
		noAtual.visitado=true;
		//primeiro do nao visitados sera o inicial
		noAtual.quadrado=EstadoVisual.INICIO;
		inicio=noAtual;

		while(naoVisitados.size()>0){
			noAtual.visitado=true;
			listaVizinhos = new ArrayList<No>();
			for(No noVizinho:noAtual.getVizinho()){
				if(noVizinho.visitado)continue;	
				int count = 0; //definindo paredes
				for(No proximoNoVizinho: noVizinho.getVizinho()){
					if(proximoNoVizinho.quadrado == EstadoVisual.PAREDE)count++;
				}
				if(count>=percentualParedes){
					if(noVizinho.estado.x > 0 && noVizinho.estado.y > 0 && noVizinho.estado.x < LINHAS-1 && noVizinho.estado.y < COLUNAS-1){
						listaVizinhos.add(noVizinho);
					}
				}
			}
			if(listaVizinhos.size()>0){ //pintando como caminho
				int size = listaVizinhos.size();
				pilha.push(noAtual);				
				noAtual = listaVizinhos.remove((int)rand.nextInt(size));			
				noAtual.quadrado=EstadoVisual.CAMINHO;
			}
			else if (!pilha.isEmpty()){
				noAtual = pilha.pop(); //para guaradar ultimo nao visitado				
			}
			else {
				noAtual = naoVisitados.remove((int)rand.nextInt(naoVisitados.size()));
			}			
			if(naoVisitados.size()<=0){ //definindo ultimo nao visitado como noFinal
				noAtual.quadrado=EstadoVisual.FIM;				
				noAtual.custoH=0; //heuristica pra chegar nele sera 0
				noAtual.visitado=true;				
				fim=noAtual;
			}
		}
	}
	
	//lista de retornos de n�s com bordas conectadas por coordenadas XY
	public ArrayList<No> getListaDeNos() {
		return listaDeNos;
	}

	public No getInicio(){
		return inicio;
	}

	public No getFim(){
		return fim;
	} 
}