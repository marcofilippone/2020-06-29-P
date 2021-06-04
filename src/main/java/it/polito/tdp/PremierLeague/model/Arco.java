package it.polito.tdp.PremierLeague.model;

public class Arco implements Comparable<Arco>{
	private Match a;
	private Match b;
	private Integer peso;
	public Arco(Match a, Match b, Integer peso) {
		super();
		this.a = a;
		this.b = b;
		this.peso = peso;
	}
	public Match getA() {
		return a;
	}
	public void setA(Match a) {
		this.a = a;
	}
	public Match getB() {
		return b;
	}
	public void setB(Match b) {
		this.b = b;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(Arco other) {
		return -this.peso.compareTo(other.peso);
	}
	@Override
	public String toString() {
		return this.getA().getTeamHomeNAME()+" - "+this.getA().getTeamAwayNAME()+", "+this.getB().getTeamHomeNAME()+" - "+this.getB().getTeamAwayNAME()+", "+this.peso;
	}
	
	

}
