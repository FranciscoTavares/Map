package Métodos;

import java.util.ArrayList;

public class No implements Comparable<No> {

	public Estado estado;
	public boolean visitado = false;
	public Integer custoH = 0;
	public EstadoVisual quadrado = EstadoVisual.PAREDE;
	private ArrayList<No> vizinho = new ArrayList<No>(); //analogo a filhos

	public No(Estado estado) {
		this.estado = estado;
	}

	public void addVizinho(No no) {
		vizinho.add(no);
	}

	public ArrayList<No> getVizinho() {
		return vizinho.size() > 0 ? vizinho : null;
	}	

	public int compareTo(No objeto) {
		if (custoH < objeto.custoH)
			return -1;
		if (custoH > objeto.custoH)
			return 1;
		else
			return 0;
	}
	
	public String toString() {
		return "[Coordenada X"+this.estado.x+" Coordenada Y "+this.estado.y+" Estado >>> "+this.quadrado+"]";
	}
}
