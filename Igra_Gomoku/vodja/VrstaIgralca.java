package vodja;

public enum VrstaIgralca {
	minimax, alfabeta, random, C; //samo zamenjal oznake 
	
	@Override
	public String toString() {
		return (this.equals(C)) ? "Človek" : "Računalnik";		
	}

}
