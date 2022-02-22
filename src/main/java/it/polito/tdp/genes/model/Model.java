package it.polito.tdp.genes.model;



import java.util.LinkedList;
import java.util.List;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.genes.db.GenesDao;

public class Model {
	private SimpleWeightedGraph<String, DefaultWeightedEdge> grafo;
	private GenesDao dao;

	
	public Model() {
		dao = new GenesDao();
		
	}
	
	public void creaGrafo() {
		grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(grafo, dao.getAllLocalitation());
		
		for(Adiacenza r : dao.listaAdiacenze() ) {
			if(this.grafo.containsVertex(r.l1) && 
					this.grafo.containsVertex(r.l2)) {
					Graphs.addEdgeWithVertices(grafo, r.getL1(), r.getL2(), r.getPeso());
				}
		}
}
	
	public int getNVertici() {
		if(grafo != null)
			return grafo.vertexSet().size();
		
		return 0;
	}
	
	public int getNArchi() {
		if(grafo != null)
			return grafo.edgeSet().size();
		
		return 0;
	}
	
	public List<String> getAllLocal(){
		return this.dao.getAllLocalitation();
	}
	
	public List<String> getAdiacenti(String a){
		List<String> adiacenti = new LinkedList<String>();
		List<Adiacenza> nodi = new LinkedList<Adiacenza>();
		List<String> s = new LinkedList<String>();
		for(String p : this.grafo.vertexSet()) {
			if(p.equals(a)) {
              adiacenti.addAll(Graphs.neighborListOf(grafo, p));
			
			for(String r : adiacenti) {
				nodi.add(new Adiacenza(p, r, (int) this.grafo.getEdgeWeight(this.grafo.getEdge(p, r))));
			}
			}
 	}
		  for(Adiacenza t : nodi) {
	        	s.add( t.getL2() + " " + t.getPeso());
	        }
		  
		  return s;
		
			
		
		
}
}