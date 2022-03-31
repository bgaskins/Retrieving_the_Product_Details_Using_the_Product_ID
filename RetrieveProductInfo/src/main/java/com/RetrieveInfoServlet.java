package com;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/productsearch")
public class RetrieveInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			PrintWriter out = response.getWriter();		
			try {
				//Load driver class.
				Class.forName("com.mysql.jdbc.Driver");
				
				//Create the connection object and connect database information.
				Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/products", "root", "Stokstad22!");
				PreparedStatement ps = con.prepareStatement("select * from productdetails where productid=?");
				
				//Pass through Product ID from index.html. 
				int ProdID = Integer.parseInt(request.getParameter("inputProdID"));
				ps.setInt(1, ProdID);
				response.setContentType("text/html");
				ResultSet rs = ps.executeQuery();
					
				//Read the data from the user's query.
				//Correct Product ID will retrieve the Product ID, Product and the number of products in the Inventory.
				if(rs.next()) { 
					out.print("Product ID and Inventory Results:");
					out.print("<table border='1'><tr><th>Product ID</th><th>Product</th><th>In Stock</th>");
					out.print("<tr>");
					out.print("<td>" + rs.getInt(1) + "</td>");
					out.print("<td>" + rs.getString(2) + "</td>"); 
					out.print("<td>" + rs.getInt(3) + "</td>");
					out.print("</tr>");
				
				} else {
					out.println("Invalid product ID or product not in stock.");
				}
			
					con.close();
					
				}
				catch (Exception e) {
					e.printStackTrace();
				}
				out.print("</table>");
				out.close();
				
		}
		
}