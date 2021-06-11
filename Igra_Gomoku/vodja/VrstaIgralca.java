package vodja;

public enum VrstaIgralca {
	minimax, alfabeta, random, C;
	
	@Override
	public String toString() {
		return (this.equals(C)) ? "Človek" : "Računalnik";		
	}

}
