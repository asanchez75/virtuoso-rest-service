package fr.uness.servlets;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
				Connection conn = VirtuosoDatabase.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("select __id2in(s), __id2in(p), __ro2sq(o) from rdf_quad"); 	
				while (rs.next()) {
					String s =  rs.getString(1); 
					String p =  rs.getString(2); 
					String o =  rs.getString(3); 
					System.out.println(s + "|" + p + "|" + o);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }
}