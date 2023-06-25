package fr.uness.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet("/query")
public class SqlQueryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			try {

				String query = request.getParameter("sparql");
				Connection conn = VirtuosoDatabase.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("sparql " + query); 	
				
	            ResultSetMetaData m = rs.getMetaData();
	            int ccount = m.getColumnCount();

	            Map<String, String> map = new HashMap<String, String>();

	            while (rs.next()) {
	                for (int i = 1; i <= ccount; i++) {
	                  map.put(m.getColumnName(i),rs.getString(i)); 
	                }
	            System.out.println(map.toString());
	            }

	            rs.close();

	            st.close();

	            conn.close();

	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }
}