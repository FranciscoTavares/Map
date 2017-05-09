package Métodos;

public class Estado {
	
	//cada estado eh uma coordenada XY
	public int x,y;	

	public Estado(int x, int y){
		setXY(x,y);
	}
	
	public Estado(Estado vector){
		setXY(vector.x,vector.y);
	}
	
	public void setXY(int x, int y){
		this.x=x;
		this.y=y;
	}	
	
	public boolean comparaEstado(Estado estado){		
		return ((estado.x == x) && (estado.y == y));
	}	
}