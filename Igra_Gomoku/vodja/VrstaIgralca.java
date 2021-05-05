package vodja;

public enum VrstaIgralca {
	R, C; //samo zamenjal oznake 
	
	@Override
	public String toString() {
		return (this.equals(C)) ? "Človek" : "Računalnkik";		
	}

}
