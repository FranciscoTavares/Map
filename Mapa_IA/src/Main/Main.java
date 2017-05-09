package Main;

import Métodos.No;
import Métodos.FormaVisual;
import Métodos.Forma;
import Métodos.Estado;
import Algoritmos_Inteligentes.BFS;
import Algoritmos_Inteligentes.A_Star;
import Algoritmos_Inteligentes.Guloso;
import Algoritmos_Inteligentes.DFS;
import Métodos.EstadoVisual;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public class Main {
	
	private static int tamanhoQuadrado = 15;
	private static int larguraLabirinto = Integer.parseInt(JOptionPane.showInputDialog("SUGESTAO 1 - Deixar o tamanho do labirinto como parametro\n Informe a Largura do Labirinto em Pixels"));
	private static int alturaLabirinto = Integer.parseInt(JOptionPane.showInputDialog("Informe a Altura do Labirinto em Pixels"));
	private static int percentualParedes = Integer.parseInt(JOptionPane.showInputDialog("SUGESTAO 3 - Definir o Percentual de Paredes\n 0 = 0%\n 1 = 25%\n 2 = 50%\n 3 = 75%"));
	public static int algoritmo = Integer.parseInt(JOptionPane.showInputDialog("Escolha o Algoritmo\n1 - Bfs\n2 - Dfs\n3 - Guloso\n4 - A Estrela"));
		
	static int linha = larguraLabirinto / tamanhoQuadrado;
	static int coluna = alturaLabirinto / tamanhoQuadrado;
	static Forma labirinto;
	static ArrayList<Estado> caminhoSolucao;
	static JanelaLabirinto janelaLabirinto;

	public static void main(String args[]) {

		while (null == caminhoSolucao) {
			labirinto = new Forma(linha, coluna, percentualParedes);						
			switch (algoritmo) {
			case 1:
				caminhoSolucao = BFS.resolver(labirinto.getInicio(),labirinto.getFim());				
				break;
			case 2:
				caminhoSolucao = DFS.resolver(labirinto.getInicio(),labirinto.getFim());
				break;
			case 3:
				caminhoSolucao = Guloso.resolver(labirinto.getInicio(),labirinto.getFim());	
				break;
			case 4:
				caminhoSolucao = A_Star.resolver(labirinto.getInicio(),labirinto.getFim());	
				break;
			default:
				break;
			}			
		}

		SwingUtilities.invokeLater(() -> {
                    janelaLabirinto = new JanelaLabirinto(tamanhoQuadrado, larguraLabirinto, alturaLabirinto, labirinto);
                    janelaLabirinto.pack();
                    janelaLabirinto.getContentPane().setPreferredSize(
                            new Dimension(larguraLabirinto, alturaLabirinto));
                    janelaLabirinto.pack();
                    janelaLabirinto.setLocationRelativeTo(null);
                    janelaLabirinto.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    janelaLabirinto.setResizable(true);
                    janelaLabirinto.setVisible(true);
                    janelaLabirinto.addCaminho(caminhoSolucao);
                });
	}
}

class JanelaLabirinto extends JFrame implements Runnable  {	
	private Thread threadLabirinto;
	private int tamanhoQuadrado, largura, altura;
	private FormaVisual labirintoVisual;
	Forma novoLabirinto;
	ArrayList<Estado> caminhoSolucao;
	
    public JanelaLabirinto(int tamanhoQuadrado,int largura, int altura,Forma novoLabirinto) {
    	this.tamanhoQuadrado=tamanhoQuadrado;
    	this.largura=largura;
    	this.altura=altura;
    	this.novoLabirinto = novoLabirinto;
        iniciaInterface(tamanhoQuadrado,largura,altura,novoLabirinto);       
    }

    private void iniciaInterface(final int tamanhoQuadrado,int largura, int altura,Forma labirinto) {
    	labirintoVisual = new FormaVisual(tamanhoQuadrado,largura,altura,labirinto.getListaDeNos());    	
    	
    	labirintoVisual.addMouseMotionListener(new MouseAdapter() {
                     @Override
    		 public void mouseMoved(MouseEvent e) {    			 
    			 	setTitle("Agentes em Labirinto - Coordena do Ponto Final - X : " + (e.getX()/tamanhoQuadrado) + " Y : " + e.getY()/tamanhoQuadrado);    		      
    		    }    		 
    	});
    	
        labirintoVisual.addMouseListener(new MouseAdapter() {
                     @Override
    		 public void mouseClicked(MouseEvent e) {    			 
    			 labirintoVisual.limpaCaminho();    			 
    			 Estado novoFim = new Estado(e.getX()/tamanhoQuadrado,e.getY()/tamanhoQuadrado);
    			 Estado antigoFim = new Estado(novoLabirinto.fim.estado);
    			 novoLabirinto.fim.quadrado=EstadoVisual.CAMINHO;
    			 for(No no: novoLabirinto.getListaDeNos()){
    				 if(no.estado.comparaEstado(novoFim)){
    					 no.quadrado=EstadoVisual.FIM;
    					 novoLabirinto.fim=no;    					 
    					 break;
    				 }
    			 }
    			 labirintoVisual.mudaFim(antigoFim, novoFim);
    			 switch (Main.algoritmo) {
    				case 1:
    					caminhoSolucao = BFS.resolver(novoLabirinto.getInicio(),novoLabirinto.getFim());				
    					break;
    				case 2:
    					caminhoSolucao = DFS.resolver(novoLabirinto.getInicio(),novoLabirinto.getFim());
    					break;
    				case 3:
    					caminhoSolucao = Guloso.resolver(novoLabirinto.getInicio(),novoLabirinto.getFim());	
    					break;
    				case 4:
    					caminhoSolucao = A_Star.resolver(novoLabirinto.getInicio(),novoLabirinto.getFim());	
    					break;
    				default:
    					break;
    				}    			    			 
    			 labirintoVisual.addCaminho(caminhoSolucao);
    		    }
    	});    	
    	add(labirintoVisual);    	
    }
    
    public void addCaminho(ArrayList<Estado> caminho){
    	this.caminhoSolucao = caminho;
    	Collections.reverse(caminho);
    	threadLabirinto = new Thread(this,"Display");
    	threadLabirinto.start();
    }

	public void run() {
		try {
			Thread.sleep(1000);
			labirintoVisual.addCaminho(caminhoSolucao);			
		} catch (Exception e1) {
			e1.printStackTrace();
		}		
	}
}