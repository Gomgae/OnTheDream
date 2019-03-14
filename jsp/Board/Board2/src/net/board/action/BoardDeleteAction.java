package net.board.action;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;

public class BoardDeleteAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
	
		int num=Integer.parseInt(request.getParameter("num"));
		String pass=request.getParameter("pass");
		
		BoardDAO boarddao=new BoardDAO();
		if(boarddao.isBoardWriter(num, pass)) { //1. ��й�ȣ Ȯ�� ( �۾��� Ȯ�� )
			boarddao.bodardDelete(num); 		//2.  ���� ���� 
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('���� ����')");
			out.println("location.href='BoardList.bo'");
			out.println("</script>");
		}else {
			response.setContentType("text/html;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<script>");
			out.println("alert('��й�ȣ�� Ʋ�Ƚ��ϴ�')");
			out.println("</script>");
		}
		boarddao.dbclose();
		return null;
		
		/*
		 
		ActionForward forward=new ActionForward();
		forward.setPath("BoardList.bo");
		forward.setRedirect(true);		
		return forward;
		
		*/
	
	}

}
