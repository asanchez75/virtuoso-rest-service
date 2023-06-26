package fr.uness.servlets;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import java.util.UUID;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;

//@WebServlet("/query")
public class SqlQueryServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
			try {
				
				UUID uuid = UUID.randomUUID();
				String separator = "|";
                String file = "/data/" + uuid.toString() + ".csv";
				Path filePath = Paths.get(file);  
			    
			    Files.createFile(filePath);

	            // Define the permissions
	            Set<PosixFilePermission> perms = new HashSet<PosixFilePermission>();
	            perms.add(PosixFilePermission.OWNER_READ);
	            perms.add(PosixFilePermission.OWNER_WRITE);
	            perms.add(PosixFilePermission.OWNER_EXECUTE);
	            perms.add(PosixFilePermission.GROUP_READ);
	            perms.add(PosixFilePermission.GROUP_WRITE);
	            perms.add(PosixFilePermission.OTHERS_READ);
	            
	            // Set the permissions
	            Files.setPosixFilePermissions(filePath, perms);
			    
			    int bufferSize = 10240 * 1024; // 1M
			    BufferedWriter bw = new BufferedWriter(new FileWriter(file), bufferSize);
				
                ArrayList<String> header = new ArrayList<String>();
				String query = request.getParameter("sparql");
				String line = "";
				Connection conn = VirtuosoDatabase.getConnection();
				Statement st = conn.createStatement();
				ResultSet rs = st.executeQuery("sparql " + query); 	
				
	            ResultSetMetaData m = rs.getMetaData();
	            int ccount = m.getColumnCount();
	            
	            for (int i = 1; i <= ccount; i++) {
	                header.add(m.getColumnName(i));
	            }
	            System.out.println(StringUtils.join(header, separator)); 
      			bw.write(StringUtils.join(header, separator) + "\n");

	            while (rs.next()) {
                    ArrayList<String> row = new ArrayList<String>();
	                for (int i = 1; i <= ccount; i++) {
	                  row.add(rs.getString(i).replace("\n", "").replace("\r", "")); 
	                }
	                line = StringUtils.join(row, separator);
	                System.out.println(line);
				    bw.write(line + "\n");
	            }

	            
	            bw.close();
	            
	            rs.close();

	            st.close();

	            conn.close();

	            
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

    }
}