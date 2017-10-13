/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Stud;


import DBWorks.DBConnection;
import DbObjects.Course;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.annotation.WebServlet;

/**
 *
 * @author Richie
 */
@WebServlet(name = "RetriveCourseInformation", urlPatterns = {"/RetriveCourseInformation"})
public class RetriveCourseInformation extends HttpServlet{
        /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        //Fetching all the classes in a certain semester. 
        //Ignoring sememeter in the query right now.
        String msg = "";
                response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println(msg);
        try{
            
            String CourseID = (String)request.getParameter("CourseID");
            System.out.println(CourseID);
            String Query = "SELECT ACourseID, ASectionID, ACourseName, AInstName, ABlocks FROM AssignedSchedule A WHERE A.ACourseID = \"" +CourseID+ "\";";
            System.out.println(Query);
             
            ResultSet rs = DBConnection.ExecQuery(Query);
            ResultSetMetaData md = rs.getMetaData();
            
            
            List<String> columns = new ArrayList<String>();
            int rowCount = md.getColumnCount();
            for (int i = 1; i <= rowCount; i++) {
                columns.add(md.getColumnName(i));
                }
            List<String[]> courseInformation = new ArrayList<String[]>();
            while(rs.next()){
                String[] row = new String[rowCount];
                for(int i = 1; i <= rowCount; i++){
                    row[i-1] = rs.getString(columns.get(i-1));
                    System.out.println(i + " " +rs.getString(columns.get(i-1)));
                }
                courseInformation.add(row);
            }
            String lunchCheck = (String)request.getParameter("LunchPeriod");
            if (lunchCheck != null){
                request.getSession(true).setAttribute("LunchPeriod", lunchCheck);
            }
            else{
                request.getSession(true).setAttribute("LunchPeriod", null);
            }
            
            request.getSession(true).setAttribute("courseInfomation", courseInformation);
            request.getSession(true).setAttribute("openModal", "openModal");
            response.sendRedirect("Stud/desiredSearch.jsp");
        }
        catch(Exception e ){
            e.printStackTrace();
        }
    }
    
   // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
