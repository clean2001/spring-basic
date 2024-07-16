package com.beyond.basic.servletjsp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello/servlet/jsp/post")
public class HelloServletJspPost extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
//        System.out.println("이름!: " + req.getPart("name"));

        System.out.println(name);
        System.out.println(email);
        System.out.println(password);

        Hello hello = new Hello(name, email, password);
        System.out.println(hello);

        resp.setContentType("text/plain");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter printWriter = resp.getWriter();
        printWriter.println("ok");
    }
}
