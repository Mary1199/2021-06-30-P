package it.polito.tdp.genes.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.genes.model.Adiacenza;
import it.polito.tdp.genes.model.Genes;

//PROVA
public class GenesDao {
	
	public List<Genes> getAllGenes(){
		String sql = "SELECT DISTINCT GeneID, Essential, Chromosome FROM Genes";
		List<Genes> result = new ArrayList<Genes>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Genes genes = new Genes(res.getString("GeneID"), 
						res.getString("Essential"), 
						res.getInt("Chromosome"));
				result.add(genes);
			}
			res.close();
			st.close();
			conn.close();
			return result;
			
		} catch (SQLException e) {
			throw new RuntimeException("Database error", e) ;
		}
	}
		public List<String> getAllLocalitation(){
			String sql = "SELECT DISTINCT Localization FROM classification";
			List<String> result = new LinkedList<String>();
			Connection conn = DBConnect.getConnection();

			try {
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet res = st.executeQuery();
				while (res.next()) {

					String local = new String(res.getString("Localization"));
					result.add(local);
				}
				res.close();
				st.close();
				conn.close();
				return result;
				
			} catch (SQLException e) {
				throw new RuntimeException("Database error", e) ;
			}
	}
		
		public List<Adiacenza> listaAdiacenze(){
			String sql = "SELECT c1.Localization, c2.Localization, COUNT(i.`Type`) AS peso FROM interactions i, classification c1, classification c2 WHERE c1.Localization <> c2.Localization AND c1.GeneID = i.GeneID1 AND c2.GeneID = i.GeneID2 AND c1.GeneID <> c2.GeneID GROUP BY c1.Localization, c2.Localization"; 
			
			List<Adiacenza> result = new LinkedList<Adiacenza>();
			try {
				Connection conn = DBConnect.getConnection();
				PreparedStatement st = conn.prepareStatement(sql);
				ResultSet rs = st.executeQuery();
				
				while (rs.next()) {
					Adiacenza a = new Adiacenza(rs.getString("c1.Localization"), rs.getString("c2.Localization"), rs.getInt("peso"));
					result.add(a);
				} 
				
				rs.close();
				st.close();
				conn.close();
			
				return result;
			}catch (SQLException e) {
				e.printStackTrace();
				return null;
			}
	
		}

	
}
