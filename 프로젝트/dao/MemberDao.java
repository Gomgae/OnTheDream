package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Member;
 
public class MemberDao {
//    �̱��� �����ϱ�
//    1.�ڱ��ڽ��� ���������� static���� ����
//    2.�����ڸ� private���� ����
//    3.1�� ���� getter�� ����� 1�� null�� �� ��ü�Ҵ�
 
//    1.
    private static MemberDao instance;
//    3.
    public static MemberDao getInstance(){
        if(instance == null)
            instance = new MemberDao();
        return instance;
    }
//    4.
    private Connection conn;
    private static String URL="jdbc:mysql://localhost/jhp";
    private static String USERNAME="zreok";
    private static String PASSWORD="1234";
//    2.
    private MemberDao(){
        try {     //5.�����ڿ��� Ŀ�ؼ� ���
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
         catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
//    �� ��Ű���� �ִ� Member�� �����ͼ� ����
//    ȸ�������� ������ �߰��ϱ�
    public void insertMember(Member member)
    {
//        ������ �غ� ó���� "" ���·�
        String sql = "insert into member values (?,?,?,?,?)";
        PreparedStatement pstmt = null;
//        conn��ü�� �̿ϼ� ������ �غ�Ȱ� ������ ������ü ȹ��
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getid());
            pstmt.setString(2, member.getpass());
            pstmt.setString(3, member.getname());
            pstmt.setString(4, member.getemail());
            pstmt.setString(5, member.getcontact());
//        ������ü������
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
    
    public void updateMember(Member member)
    {
        String sql = "update member set pass=?, name=?, email=?,contact =? where id=?";
        PreparedStatement pstmt = null;
 
        try {
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, member.getid());
            pstmt.setString(2, member.getpass());
            pstmt.setString(3, member.getname());
            pstmt.setString(4, member.getemail());
            pstmt.setString(4, member.getcontact());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null && !pstmt.isClosed())
                    pstmt.close();
            } catch (SQLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
//    id�� �ش��ϴ� ��� ��ȸ�ϱ�
    public Member selectOne(String id)
    {
        String sql = "select * from member where id = ?";
//        ������ü, ������ ��ü, ����� ���������� �غ�
        PreparedStatement pstmt = null;
        Member member = null;//������ ��ü��������
        ResultSet rs = null;//����� ���������� �غ�
        try {
//            ������üȹ��
            pstmt = conn.prepareStatement(sql);
//            �����ϼ�
            pstmt.setString(1, id);
//            ���� ������ result set ȹ��
            rs = pstmt.executeQuery();
//            Resultset Ž��
            if( rs.next() )
            {
                member = new Member();
                member.setid( rs.getString("id"));
                member.setpass(rs.getString("pass"));
                member.setname( rs.getString("name"));
                member.setemail(rs.getString("email"));
                member.setcontact(rs.getString("contact"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        finally{
            try{
                if( pstmt != null && !pstmt.isClosed())
                    pstmt.close();
                if( rs != null && !rs.isClosed())
                    pstmt.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return member;
    }
    
//    member�� ���̺����� ��ü��ȸ�ϱ�
    public List<Member> selectAll() {
        String sql = "select * from member";
        PreparedStatement pstmt = null;
//        ����� Ž��
        ResultSet rs = null;
        List<Member> memberList = new ArrayList<Member>();
        
        try {
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            while( rs.next() )
            {
                Member member = new Member();
                member.setid( rs.getString("id") );
                member.setpass(rs.getString("pw"));
                member.setname(rs.getString("name"));
                member.setemail(rs.getString("email"));
                member.setcontact(rs.getString("contact"));
                memberList.add(member);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
//        ������ü�� Resultset��ü �ݾ��ֱ�
        finally{
            try{
                if( pstmt != null && !pstmt.isClosed())
                    pstmt.close();
                if( rs != null && !rs.isClosed())
                    pstmt.close();
            }
            catch(SQLException e)
            {
                e.printStackTrace();
            }
        }
        return memberList;
        
    }
}