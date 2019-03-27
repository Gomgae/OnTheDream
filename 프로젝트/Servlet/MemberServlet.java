package Servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import model.Member;
import service.MemberService;

public class MemberServlet extends HttpServlet {
    private MemberService service;
    public MemberServlet(){
        service= new MemberService();
    }
 
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doProc(req,resp);
    }
 
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        doProc(req,resp);
    }
    
    protected void doProc(HttpServletRequest req, HttpServletResponse resp) 
            throws ServletException, IOException {
        // TODO Auto-generated method stub
        String contextPath = req.getContextPath();
        String reqUri = req.getRequestURI();
//---------------------------------------------------------------------------------------        
        //�α���
        if(reqUri.equals(contextPath + "/login.do"))
        {
            String id = req.getParameter("id");
            String pass = req.getParameter("pass");
			if(service.login(id, pass)){
                req.setAttribute("msg", "�α��� ����");
                req.getSession().setAttribute("id", id);
            }
            else{
                req.setAttribute("msg", "�α��� ����");
            }
            req.getRequestDispatcher("loginResult.jsp").forward(req, resp);
        }
        else if(reqUri.equals(contextPath + "/loginForm.do"))   //loginForm������
        {
            req.getRequestDispatcher("loginForm.jsp").forward(req, resp);
        }
        else if(reqUri.equals(contextPath + "/main.do"))   //main������
        {
            if(req.getSession().getAttribute("id") == null)
            {
                
                    resp.sendRedirect("loginForm.do");
                    //��� ����Ǳ� ������ �� �����������
                    return;
            }
            
                req.getRequestDispatcher("main.jsp").forward(req, resp);
        }
//-----------------------------------------------------------------------------------------
            
//                memberUpdateForm : ȸ������ ���� ���� �����޶�� ��û
//                �Ķ���� id�� ������ ������ ȸ��������Ʈ
            else if(reqUri.equals(contextPath + "/memberUpdateForm.do"))
            {
                
                String id = req.getParameter("id");
                Member member = service.getMember(id); //id�� �ش��ϴ� ȸ��������Ʈ �����͸� ������
                req.setAttribute("member", member);
                req.getRequestDispatcher("memberUpdateForm.jsp") //memberUpdateForm.jsp�� ������
                .forward(req, resp);
            }
//-----------------------------------------------------------------------------------------
 
//            memberUpdate.do����������
//            memberUpdate : ȸ���������� �����ش޶�� ��û
//            �Ķ���� : ȸ������ �������
//            �����������ʹ� X
//            main��û���� redirect
            else if(reqUri.equals(contextPath + "/memberUpdate.do"))
            {
            String id = req.getParameter("id");
            String pw = req.getParameter("pw");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String contact = req.getParameter("contact");
            service.update(id, pw, name, email,contact);
            resp.sendRedirect("main.do");
            return;
            }
//-----------------------------------------------------------------------------------------
//            ���� ���������� ��üȸ������ ��ư�� ������ �߻���û
//            memberList : ��� ȸ�������� �����޶�� ��û
//            �Ķ���� X , ������ ������ ��� ȸ������ ����
//            �̵��� ������ : memberList.jsp�� ������
            else if(reqUri.equals(contextPath + "/memberList.do"))
            {
                req.setAttribute("memberList", service.getMemberList());
                req.getRequestDispatcher("memberList.jsp")
                .forward(req, resp);
            }
//-----------------------------------------------------------------------------------------    
//            joinFrom : ȸ������ ���� �����޶�� ��û
//            �Ķ���� X, ������ ������ ����
//            �̵��� �������� loginForm.jsp�� ������
            else if(reqUri.equals(contextPath + "/joinForm.do"))
            {
                req.getRequestDispatcher("joinForm.jsp")
                .forward(req, resp);
            }
            
//-----------------------------------------------------------------------------------------
//            join : ȸ������ó���� �ش޶�� ��û
//            �Ķ���� : id, pw, name, email    ������ �����ʹ� ����
//            �̵��� �������� loginForm ��û���� redirect
            
            else if(reqUri.equals(contextPath + "/join.do"))
            {
            String id = req.getParameter("id");
            String pass = req.getParameter("pass");
            String name = req.getParameter("name");
            String email = req.getParameter("email");
            String contact = req.getParameter("contact");
            service.join(id, pass, name, email,contact);
            resp.sendRedirect("loginForm.do");
            return;
            }
            
        }
        
    }
