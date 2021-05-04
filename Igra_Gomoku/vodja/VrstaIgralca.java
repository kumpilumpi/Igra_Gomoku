package vodja;

public enum VrstaIgralca {
	Racunalnik, Clovek;
	
	@Override
	public String toString() {
		return (this.equals(Clovek)) ? "Človek" : "Računalnkik";		
	}

}
