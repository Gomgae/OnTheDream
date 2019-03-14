package net.board.action;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;


import net.board.db.BoardDAO;
import net.board.db.BoardVO;

public class BoardListSearchAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BoardDAO boarddao=new BoardDAO();
		String searchItem = request.getParameter("searchItem"); // �˻� �׸�
		String searchWord = request.getParameter("searchWord"); // �˻� �ܾ�
		
		int count=boarddao.getSearchCount(searchItem,searchWord); // �˻��� ���� �� ����� Ȯ��
		int page=1; // ��û ������ ��ȣ 
		
		if(request.getParameter("page")!=null) // ����ڰ� �������� ��û�Ҷ�
		page=Integer.parseInt(request.getParameter("page")); // ������ ��ȣ Ȯ��
		int maxpage = (int)(((double)(count/10))+0.95); // �� ������ ��
		
		ArrayList<BoardVO> list=boarddao.getBoardSearch(page, 10,searchItem,searchWord); // ������ ������
		boarddao.dbclose();	
		
		int startpage=(int)((double)( page/10 + 0.9 )-1)*10+1; // ���� ���������� ������ ������ ȭ�� ��
		int endpage=startpage+10-1; // ���� �������� ������ ������ ������ ��
		if(endpage > maxpage) endpage = maxpage;
		
		request.setAttribute("page", page);  // ���� ������ ȭ�� �����ֱ�
		request.setAttribute("count", count); // ��ü�� ��
		request.setAttribute("startpage", startpage);
		request.setAttribute("endpage", endpage);
		request.setAttribute("list", list); // ������ ������ ȭ��

		
		ActionForward forward = new ActionForward();
			forward.setPath("BoardSearchList.jsp");
		forward.setRedirect(false);
		return forward;
	}

}
