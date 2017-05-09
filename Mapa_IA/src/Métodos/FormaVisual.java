package Métodos;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import javax.swing.JPanel;

public class FormaVisual extends JPanel {
	
	private int tamanhoQuadrado, largura, altura;
	private ArrayList<No> nosLabirinto;
	private ArrayList<Estado> caminho = new  ArrayList<Estado>();
	
	public FormaVisual(int tamanhoQuadrado,int largura, int altura,ArrayList<No> nosLabirinto){		
		this.tamanhoQuadrado = tamanhoQuadrado;
		this.largura = largura;
		this.altura = altura;
		this.nosLabirinto = nosLabirinto;
	}
	
	private void geraDesenho(Graphics g) {
        //desenha o painel quadriculado com suas respectivas cores
		Graphics2D g2d = (Graphics2D) g;  
        for(No no : nosLabirinto){
				g2d.setColor(no.quadrado.cor);			
    			g2d.fillRect((no.estado.x*tamanhoQuadrado), (no.estado.y*tamanhoQuadrado),tamanhoQuadrado,tamanhoQuadrado);    			
        }			
    }

	public void addNoCaminho(Estado atual){
		this.caminho.add(atual);
	}
	
	public void addCaminho(ArrayList<Estado> caminho){
		this.caminho = caminho;
		desenhaCaminho();
	}
	
	private void desenhaCaminho() {
		Graphics2D g2d =(Graphics2D) getGraphics();
		 for(Estado point : caminho){
	        	//caminhos da solucao sao azuis
			 	g2d.setColor(Color.cyan);
				g2d.fillRect((point.x*tamanhoQuadrado), (point.y*tamanhoQuadrado),tamanhoQuadrado,tamanhoQuadrado);
	        }
	}
	
	public void mudaFim(Estado antigoFim, Estado novoFim){
		Graphics2D g2d = (Graphics2D) getGraphics();
		//quando quiser atualizar o labirinto indicando outro noFinal. 
		g2d.setColor(Color.white); //noFinal antigo fica branco
		g2d.fillRect((antigoFim.x*tamanhoQuadrado), (antigoFim.y*tamanhoQuadrado),tamanhoQuadrado,tamanhoQuadrado);
		
		g2d.setColor(Color.RED); //noFinal atual eh vermelho
		g2d.fillRect((novoFim.x*tamanhoQuadrado), (novoFim.y*tamanhoQuadrado),tamanhoQuadrado,tamanhoQuadrado);
	}
	
	public void limpaCaminho(){
		limpaCaminho(getGraphics());
	}
	
	private void limpaCaminho(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		//percorrre o caminho que antes estava azul e pinta de branco
		for(Estado point : caminho){
        	g2d.setColor(Color.white);
			g2d.fillRect((point.x*tamanhoQuadrado), (point.y*tamanhoQuadrado),tamanhoQuadrado,tamanhoQuadrado);
        }		
		 g2d.dispose();
	}
	
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);       
        geraDesenho(g);
    }	
}