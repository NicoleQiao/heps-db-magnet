/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package heps.db.magnet.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import heps.db.magnet.jpa.DesignAPI;

/**
 *
 * @author qiaoys
 */
public class QueryDesign extends HttpServlet {

    public Integer precalcInt(Object obj) {
        if (obj.toString().isEmpty()) {
            return null;
        } else {
            return Integer.parseInt(obj.toString());
        }
    }

    public Double precalcDouble(Object obj) {
        if (obj.toString().isEmpty()) {
            return null;
        } else {
            return Double.parseDouble(obj.toString());
        }
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
//            out.println("<!DOCTYPE html>");
//            out.println("<html>");
//            out.println("<head>");
//            out.println("<title>Servlet QueryDesign</title>");            
//            out.println("</head>");
//            out.println("<body>");
//            out.println("<h1>Servlet QueryDesign at " + request.getContextPath() + "</h1>");
//            out.println("</body>");
//            out.println("</html>");
//        }
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
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
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");        
        String result = new String();
        String type;
        Integer family;
        Double lengthmin, lengthmax;
        Integer intensity;
        Double intensitymin, intensitymax;
        Integer bylength;
        Integer bytype;
        Integer byfamily;
        Integer byintensity;

        DesignAPI a = new DesignAPI();
        a.init();
        type = request.getParameter("magtype");
        family = precalcInt(request.getParameter("magfamily"));
        lengthmin = precalcDouble(request.getParameter("lengthmin"));
        lengthmax = precalcDouble(request.getParameter("lengthmax"));
        intensity = precalcInt(request.getParameter("selintensity"));
        intensitymin = precalcDouble(request.getParameter("intensitymin"));
        intensitymax = precalcDouble(request.getParameter("intensitymax"));

        if (type.equals("none")) {
            bytype = 0;
        } else {
            bytype = 1;
        }
        if (family == -1) {
            byfamily = 0;
        } else {
            byfamily = 1;
        }
        if (lengthmin == null & lengthmax == null) {
            bylength = 0;
        } else {
            bylength = 1;
        }
        if (intensity == -1) {
            byintensity = 0;
        } else {
            byintensity = 1;
            // System.out.println(intensitymin+"----"+intensitymax);
        }

        if (bytype == 0 && byfamily == 0 && bylength == 0 && byintensity == 0) {//no query conditions
            result = a.queryDesignAll();
        } else if (bytype == 1 && byfamily == 0 && bylength == 0 && byintensity == 0) {//query by type             
            result = a.queryDesignByType(type);
        } else if (bytype == 0 && byfamily == 1 && bylength == 0 && byintensity == 0) {//query by family
            result = a.queryDesignByFamily(family);
        } else if (bytype == 0 && byfamily == 0 && bylength == 1 && byintensity == 0) {//query by length
            result = a.queryDesignbyLength(lengthmin, lengthmax);
        } else if (bytype == 0 && byfamily == 0 && bylength == 0 && byintensity == 1) {//query by intensity
            result = a.queryDesignbyIntensity(intensity, intensitymin, intensitymax);
        } else if (bytype == 1 && byfamily == 1 && bylength == 0 && byintensity == 0) {//query by type & family
            result = a.queryDesignByTypeFamily(type, family);
        } else if (bytype == 1 && byfamily == 0 && bylength == 1 && byintensity == 0) {//query by type & length
            result = a.queryDesignbyTypeLength(type, lengthmin, lengthmax);
        } else if (bytype == 1 && byfamily == 0 && bylength == 0 && byintensity == 1) {//query by type & intensity
            result = a.queryDesignbyTypeIntensity(type, intensity, intensitymin, intensitymax);
        } else if (bytype == 0 && byfamily == 1 && bylength == 1 && byintensity == 0) {//query by family & length
            result = a.queryDesignbyFamilyLength(family, lengthmin, lengthmax);
        } else if (bytype == 0 && byfamily == 1 && bylength == 0 && byintensity == 1) {//query by family & intensity
            result = a.queryDesignbyFamilyIntensity(family, intensity, intensitymin, intensitymax);
        } else if (bytype == 0 && byfamily == 0 && bylength == 1 && byintensity == 1) {//query by length & intensity
            result = a.queryDesignbyLengthIntensity(lengthmin, lengthmax, intensity, intensitymin, intensitymax);
        } else if (bytype == 1 && byfamily == 1 && bylength == 1 && byintensity == 0) {//query by type & family & length
            result = a.queryDesignbyTypeFamilyLength(type, family, lengthmin, lengthmax);
        } else if (bytype == 1 && byfamily == 1 && bylength == 0 && byintensity == 1) {//query by type & family & intensity
            result = a.queryDesignbyTypeFamilyIntensity(type, family, intensity, intensitymin, intensitymax);
        } else if (bytype == 0 && byfamily == 1 && bylength == 1 && byintensity == 1) {//query by family & length & intensity
            result = a.queryDesignbyFamilyLengthIntensity(family, lengthmin, lengthmax, intensity, intensitymin, intensitymax);
        } else if (bytype == 1 && byfamily == 0 && bylength == 1 && byintensity == 1) {//query by type & length & intensity
            result = a.queryDesignbyTypeLengthIntensity(type, lengthmin, lengthmax, intensity, intensitymin, intensitymax);
        } else if (bytype == 1 && byfamily == 0 && bylength == 1 && byintensity == 1) {//query by type & family & length & intensity
            result = a.queryDesignbyTypeFamilyLengthIntensity(type, family, lengthmin, lengthmax, intensity, intensitymin, intensitymax);
        }
        a.destroy();
        request.getSession().setAttribute("designvalue", "{\"rows\":" + result + "}");
        request.getSession().setAttribute("magtype", type);
        request.getSession().setAttribute("magfamily", family);
        request.getSession().setAttribute("lengthmin", lengthmin);
        request.getSession().setAttribute("lengthmax", lengthmax);
        request.getSession().setAttribute("intensity", intensity);
        request.getSession().setAttribute("intensitymin", intensitymin);
        request.getSession().setAttribute("intensitymax", intensitymax);
        request.getRequestDispatcher("designresult.jsp").forward(request, response);
        // processRequest(request, response);
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
