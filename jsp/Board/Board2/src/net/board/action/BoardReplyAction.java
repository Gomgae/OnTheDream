package net.board.action;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import net.board.db.*;


public class BoardReplyAction implements Action {

	@Override
	public ActionForward execute(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// 1. �θ�� ���� �о����
		BoardDAO boarddao = new BoardDAO();
		request.setCharacterEncoding("UTF-8"); // �ѱ� ���ڵ� ����
		int num=Integer.parseInt(request.getParameter("num"));
		BoardVO boardvo = boarddao.getDetail(num);
		
		// 2. �θ�� ������ ��� ���� ����(�������,�ۼ���,����,���) ������Ʈ
		boardvo.setBOARD_SUBJECT(request.getParameter("BOARD_SUBJECT"));
		boardvo.setBOARD_NAME(request.getParameter("BOARD_NAME"));
		boardvo.setBOARD_CONTENT(request.getParameter("BOARD_CONTENT"));
		boardvo.setBOARD_PASS(request.getParameter("BOARD_PASS"));
		
		// 3. �� ������ ��� ����
		boarddao.boardReply(boardvo);
		boarddao.dbclose();
		
		// 4. ���� ������ ����
		
		ActionForward forward = new ActionForward();
		forward.setPath("BoardList.bo");
		forward.setRedirect(true);
		
		return forward;
	}

}
