
package com.emergentes.controlador;

import com.emergentes.modelo.GestorVacunas;
import com.emergentes.modelo.Vacuna;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(name = "Controller", urlPatterns = {"/Controller"})
public class Controller extends HttpServlet {

  

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Vacuna objTarea=new Vacuna();
       int id;
       int pos;
       String op = request.getParameter("op");
       
       if(op.equals("nuevo")){
           HttpSession ses=request.getSession();
           GestorVacunas agenda= (GestorVacunas) ses.getAttribute("agenda");
           objTarea.setId(agenda.obtieneId());
           request.setAttribute("op", op);
           request.setAttribute("miTarea", objTarea);
           request.getRequestDispatcher("editar.jsp").forward(request, response);
       }
       if(op.equals("modificar")){
           id=Integer.parseInt(request.getParameter("id"));
           HttpSession ses = request.getSession();
           GestorVacunas agenda=(GestorVacunas) ses.getAttribute("agenda");
           pos=agenda.ubicarVacuna(id);
           objTarea=agenda.getLista().get(pos);
           
           request.setAttribute("op", op);
           request.setAttribute("miTarea", objTarea);
           request.getRequestDispatcher("editar.jsp").forward(request, response);;
           
               
       }
       if(op.equals("eliminar")){
           id=Integer.parseInt(request.getParameter("id"));
           HttpSession ses = request.getSession();
           GestorVacunas agenda=(GestorVacunas) ses.getAttribute("agenda");
           pos= agenda.ubicarVacuna(id);
           agenda.eliminarVacuna(pos);
           ses.setAttribute("agenda", agenda);
           response.sendRedirect("index.jsp");
       }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       Vacuna objTarea = new Vacuna();
       int pos;
       String op=request.getParameter("op");
       
       if(op.equals("grabar")){
           objTarea.setId(Integer.parseInt(request.getParameter("id")));
           objTarea.setNombre(request.getParameter("nombre"));
           objTarea.setPeso(Double.parseDouble(request.getParameter("peso")));
           objTarea.setTalla(Double.parseDouble(request.getParameter("talla")));
           objTarea.setVacuna(request.getParameter("vacuna"));
           
           
           HttpSession ses = request.getSession();
           GestorVacunas agenda=(GestorVacunas) ses.getAttribute("agenda");
           String opg=request.getParameter("opg");
           if(opg.equals("nuevo")){
               agenda.insertVacuna(objTarea);
           }else{
               pos=agenda.ubicarVacuna(objTarea.getId());
               agenda.modificarVacuna(pos, objTarea);
           }
           ses.setAttribute("agenda", agenda);
           response.sendRedirect("index.jsp");
           
       }
    }
    }



