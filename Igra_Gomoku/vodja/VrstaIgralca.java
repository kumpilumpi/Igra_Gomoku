package vodja;

public enum VrstaIgralca {
	R, C;
	
	@Override
	public String toString() {
		return (this.equals(C)) ? "Človek" : "Računalnkik";		
	}

}
