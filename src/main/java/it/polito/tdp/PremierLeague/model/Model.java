package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	
	private SimpleWeightedGraph<Match, DefaultWeightedEdge> grafo;
	private PremierLeagueDAO dao;
	private Map<Integer, Match> idMap;
	private List<Arco> listaArchi;
	private List<Match> soluzioneMigliore;
	private int sommaPeso;
	private int sommaPesoBest;
	private Match partenza;
	private Match arrivo;
	
	public Model() {
		dao = new PremierLeagueDAO();
	}
	
	public void creaGrafo(int min, int mese) {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		idMap = new HashMap<>();
		dao.getVertexes(mese, idMap);
		Graphs.addAllVertices(this.grafo, idMap.values());
		listaArchi = dao.getEdges(mese, min, idMap);
		for(Arco a : listaArchi) {
			if(grafo.containsVertex(a.getA()) && grafo.containsVertex(a.getB()))
			Graphs.addEdgeWithVertices(grafo, a.getA(), a.getB(), a.getPeso());
		}
	}
	
	public Set<Match> getVertici(){
		return grafo.vertexSet();
	}
	
	public Set<DefaultWeightedEdge> getArchi(){
		return grafo.edgeSet();
	}
	
	public List<Arco> connessioni(){
		List<Arco> listaOrd = new LinkedList<>(listaArchi);
		List<Arco> listaPesi = new LinkedList<>();
		Collections.sort(listaOrd);
		listaPesi.add(listaOrd.get(0));
		for(Arco a : listaOrd) {
			if(!listaPesi.contains(a) && a.getPeso()==listaPesi.get(listaPesi.size()-1).getPeso())
				listaPesi.add(a);
		}
		return listaPesi;
	}
	
	public List<Match> percorso(Match partenza, Match arrivo){
		this.partenza=partenza;
		this.arrivo=arrivo;
		List<Match> parziale = new LinkedList<>();
		parziale.add(partenza);
		soluzioneMigliore = new LinkedList<>();
		sommaPeso=0;
		sommaPesoBest=0;
		calcola(parziale);
		return soluzioneMigliore;
	}
	
	private void calcola(List<Match> parziale) {
		if(parziale.get(parziale.size()-1).equals(this.arrivo)) {
			if(sommaPesoBest==0 || sommaPeso>sommaPesoBest) {
				soluzioneMigliore = new LinkedList<>(parziale);
				sommaPesoBest = sommaPeso;
			}
			return;
		}
		
		for(Match m : Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1))) {
			if(!parziale.contains(m) && m.getTeamHomeID()!=parziale.get(parziale.size()-1).getTeamHomeID() && m.getTeamHomeID()!=parziale.get(parziale.size()-1).getTeamAwayID() && m.getTeamAwayID()!=parziale.get(parziale.size()-1).getTeamHomeID() && m.getTeamAwayID()!=parziale.get(parziale.size()-1).getTeamAwayID() ) {
				parziale.add(m);
				calcola(parziale);
				parziale.remove(parziale.get(parziale.size()-1));
			}
		}
	}
	
	public int getPesoPercorso() {
		return this.sommaPesoBest;
	}
	
}
