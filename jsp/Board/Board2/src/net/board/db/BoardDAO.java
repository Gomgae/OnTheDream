package net.board.db;

import java.sql.*;
import java.util.*;

import javax.naming.*;
import javax.sql.*;

import net.board.action.ActionForward;

public class BoardDAO {
	
	Connection conn;
	PreparedStatement pstmt;
	ResultSet rs;
	
	public BoardDAO() {
		conn = null;
		try{
			Context init = new InitialContext();
			DataSource ds = (DataSource) init.lookup("java:comp/env/jdbc/aaa");
			conn = ds.getConnection();
			System.out.println("DB���� ����");
		}catch(Exception e){
			System.out.println("DB���� ����");
			e.printStackTrace();
		}
	}
	
	//�Խñ� ���� ���ϱ�
	public int getListCount() {
		int count=0;
		try {
			pstmt=conn.prepareStatement("select count(*) from board");
			rs=pstmt.executeQuery();
			if(rs.next());
				count=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		return count;
	}
	
	//�� ���� ����
	public BoardVO getDetail(int num) {
		
		BoardVO board=null;
		
		try {
			pstmt=conn.prepareStatement("select * from board where board_num=?");
			pstmt.setInt(1, num);
			rs=pstmt.executeQuery();
		
			board = new BoardVO();
			
			if(rs.next()) {
			board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
			board.setBOARD_NAME(rs.getString("BOARD_NAME"));
			board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
			board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
			board.setBOARD_PASS(rs.getString("BOARD_PASS"));
			board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
			board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
			board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
			board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
			board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
			}
					
		}catch(Exception e) {
			System.out.println("�ۻ󼼺��� ����"+ e);
		}finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return board;
	
	}
	
	//dbĿ�ؼ� �ݱ�
	public void dbclose() {
		try {
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	//�Խñ� ��� ����
	public ArrayList<BoardVO> getBoardList(int page, int limit) {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();	
		BoardVO board=null;
		int startrow=(page-1)*limit; //�б� ������ row ��ȣ
		
		try {
			pstmt=conn.prepareStatement("select * from BOARD order by  BOARD_RE_REF desc ,BOARD_RE_SEQ asc limit ?,?");
			pstmt.setInt(1, startrow); 
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
				
			while(rs.next()) {
				board = new BoardVO();
				
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_PASS(rs.getString("BOARD_PASS"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_RE_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_RE_SEQ"));
				list.add(board);
			}
		}catch(Exception e) {
				System.out.println("�۸�Ϻ��� ����"+ e);
		}finally {
				try {if(rs!=null)rs.close();} catch (SQLException e) {
					e.printStackTrace();
				}
				try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
		
	}
	
	//�ۻ���
	public boolean bodardDelete(int num) {
		int result=0;
		try {
			pstmt=conn.prepareStatement("update BOARD set BOARD_SUBJECT='������', BOARD_CONTENT='������' where BOARD_NUM=?");
			pstmt.setInt(1, num); 
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("�ۻ��� ����"+ e);
		}finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		if(result!=0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	//�ۼ���
	public boolean boardModify(BoardVO modifyboard) {
		int result=0;
		try {
			pstmt=conn.prepareStatement("update BOARD set BOARD_SUBJECT=?, BOARD_CONTENT=? where BOARD_NUM=?");
			pstmt.setString(1, modifyboard.getBOARD_SUBJECT());
			pstmt.setString(2, modifyboard.getBOARD_CONTENT());
			pstmt.setInt(3, modifyboard.getBOARD_NUM()); 
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("�ۻ��� ����"+ e);
		}finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		if(result!=0) {
			return true;
		}else {
			return false;
		}
		
	}
	
	// �۾��� Ȯ��
	 // ��, ���� ã���� boolean �� ���� �Ѵ�
	public boolean isBoardWriter(int num,String pass) {
		boolean result=false;
		try {
		pstmt=conn.prepareStatement("select board_pass from board where board_num=?");
		pstmt.setInt(1,num);
		rs=pstmt.executeQuery();
		rs.next();
		if(pass.equals(rs.getString("board_pass"))) {
			result = true;
		}else {
			result = false;
		}
	}catch(Exception e){
		System.out.println("���� ����"+e);
	}finally {
		try {if(rs!=null)rs.close();} catch (SQLException e) {
			e.printStackTrace();
		}
		try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		return  result; 
}
	// �۾��� 
	
	public boolean boardWrite(BoardVO addboard) {
		int result=0;
		int count=0;
		
		try {
			//1. �۹�ȣ ���ϱ�
			
			pstmt=conn.prepareStatement("select max (board_num) from board");
			rs=pstmt.executeQuery();
			if(rs.next());
				count=rs.getInt(1);
			//2. �۾���
			pstmt=conn.prepareStatement("insert into BOARD values(?,?,?,?,?,now() ,?,?,?,?)");
			pstmt.setInt(1, count+1);
			pstmt.setString(2, addboard.getBOARD_NAME());
			pstmt.setString(3, addboard.getBOARD_SUBJECT());
			pstmt.setString(4, addboard.getBOARD_CONTENT());
			pstmt.setString(5, addboard.getBOARD_PASS());
			pstmt.setInt(6,0);
			pstmt.setInt(7, count); // REF
			pstmt.setInt(8, 0); // LEV
			pstmt.setInt(9, 0); // SEQ
			
			result=pstmt.executeUpdate();
		}catch(Exception e) {
			System.out.println("�۵�� ����"+ e);
		}finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return false;
	}
	
	// �� �亯 

	public boolean boardReply(BoardVO boardvo) {
		int result = 0;
		int count = 0;   // �۹�ȣ 
		int maxReplynum = 0;
		
		try {
		pstmt=conn.prepareStatement("select max (board_num) from board");
		rs=pstmt.executeQuery();
		if(rs.next());
			count=rs.getInt(1);
			
			
		//1-1. ������ �޷��� �� ����	
		pstmt=conn.prepareStatement("select max(BOARD_RE_SEQ) from BOARD where BOARD_RE_LEV=? and BOARD_RE_REF=?");
		pstmt.setInt(1,boardvo.getBOARD_RE_LEV()+1);
		pstmt.setInt(2,boardvo.getBOARD_RE_REF());
		rs=pstmt.executeQuery();
		rs.next();
		
		if(maxReplynum==0) {
			System.out.println("rs.next�� ����.");
			maxReplynum=rs.getInt(1);
			System.out.println("�θ�۹�ȣ " +maxReplynum);
		}else {
			
			System.out.println("maxReplynum");
			System.out.println(pstmt.toString());
		}
			
			
		//2 ��� �ڿ� ���� �б�
		pstmt=conn.prepareStatement("update board set board_re_seq=board_re_seq+1 where BOARD_RE_REF=? and BOARD_RE_SEQ >= ?");
		pstmt.setInt(1,boardvo.getBOARD_RE_REF()+1);
		pstmt.setInt(2,maxReplynum); // ## ���� ���� ##
		result = pstmt.executeUpdate();
		

		//3. ��� ����
		pstmt=conn.prepareStatement("insert into BOARD values(?,?,?,?,?,now() ,?,?,?,?)");
		pstmt.setInt(1, count+1);
		pstmt.setString(2, boardvo.getBOARD_NAME());
		pstmt.setString(3, boardvo.getBOARD_SUBJECT());
		pstmt.setString(4, boardvo.getBOARD_CONTENT());
		pstmt.setString(5, boardvo.getBOARD_PASS());
		pstmt.setInt(6,0); // ��ȸ��
		pstmt.setInt(7, boardvo.getBOARD_RE_REF()+1); // REF (* �θ� ���� ��ȣ ) 
		pstmt.setInt(8, boardvo.getBOARD_RE_LEV()+1); // LEV ( * ��ȣ +1 )
		pstmt.setInt(9, maxReplynum+1); // SEQ ���� ���� �ֱ�
		result = result + pstmt.executeUpdate();


	}catch(Exception e) {
		System.out.println("��� ��� ����"+ e);
	}finally {
		try {if(rs!=null)rs.close();} catch (SQLException e) {
			e.printStackTrace();
		}
		try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
			e.printStackTrace();
		}
	}
		if(result==2)
			return true;
		else
			return false;
	}

	
		// �Խñ� ��� ����
	
	public ArrayList<BoardVO> getBoardSearch(int page, int limit, String searchItem, String searchWord) {
		ArrayList<BoardVO> list = new ArrayList<BoardVO>();	
		BoardVO board=null;
		int startrow=(page-1)*limit; //�б� ������ row ��ȣ
		
		
		try {
			String str="select * from BOARD where " +searchItem+ " like '%"+searchWord+"%' order by  BOARD_RE_REF desc ,BOARD_SEQ asc limit ?,?";
			pstmt=conn.prepareStatement(str);
			pstmt.setInt(1, startrow); 
			pstmt.setInt(2, limit);
			rs=pstmt.executeQuery();
				
			while(rs.next()) {
				board = new BoardVO();
				
				board.setBOARD_NUM(rs.getInt("BOARD_NUM"));
				board.setBOARD_NAME(rs.getString("BOARD_NAME"));
				board.setBOARD_SUBJECT(rs.getString("BOARD_SUBJECT"));
				board.setBOARD_CONTENT(rs.getString("BOARD_CONTENT"));
				board.setBOARD_PASS(rs.getString("BOARD_PASS"));
				board.setBOARD_DATE(rs.getDate("BOARD_DATE"));
				board.setBOARD_READCOUNT(rs.getInt("BOARD_READCOUNT"));
				board.setBOARD_RE_REF(rs.getInt("BOARD_RE_REF"));
				board.setBOARD_RE_LEV(rs.getInt("BOARD_LEV"));
				board.setBOARD_RE_SEQ(rs.getInt("BOARD_SEQ"));
				list.add(board);
			}
		}catch(Exception e) {
				System.out.println("�۸�Ϻ��� ����"+ e);
		}finally {
				try {if(rs!=null)rs.close();} catch (SQLException e) {
					e.printStackTrace();
				}
				try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
					e.printStackTrace();
				}
		}
		return list;
		
	}
	
	//�˻��� ���� ���ϱ�
	public int getSearchCount(String searchItem, String searchWord) {
		int count=0;
		String str="select count(*) from board where " +searchItem+ " like '%"+searchWord+"%'";
		try {
			pstmt=conn.prepareStatement(str);
			rs=pstmt.executeQuery();
			if(rs.next());
				count=rs.getInt(1);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {if(rs!=null)rs.close();} catch (SQLException e) {
				e.printStackTrace();
			}
			try {if(pstmt!=null)pstmt.close();} catch (SQLException e) {
				e.printStackTrace();
			}
		
		}
		
		return count;
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
	
	
	
	
	
	
	




	