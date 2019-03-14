package net.board.action;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.board.db.BoardDAO;
import net.board.db.BoardVO;

public class BoardList implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		BoardDAO boarddao=new BoardDAO();
		int count=boarddao.getListCount();
		int page=1; // ��û ������ ��ȣ 
		int limit=10;
		
		if(request.getParameter("page")!=null) // ����ڰ� �������� ��û�Ҷ�
		page=Integer.parseInt(request.getParameter("page")); // ������ ��ȣ Ȯ��
		int maxpage = (int)(((double)(count/10))+0.95); // �� ������ ��
		
		ArrayList<BoardVO> list=boarddao.getBoardList(page, limit); // ������ ������
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
		forward.setPath("BoardList.jsp");
		forward.setRedirect(false);
		return forward;
	}

	
	
	
	
	
	
	
	
	
	
}
