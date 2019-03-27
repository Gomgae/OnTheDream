package service;
import java.util.List;
 
import dao.MemberDao;
import model.Member;
 
// ��� Dao�� ����ؾ���
public class MemberService {
    private MemberDao memberDao;
//    �̱��� ���� ����
    public MemberService(){
        memberDao = MemberDao.getInstance();
    }
//    �α��� ó���ϴ� ���(�Ķ���� id, pw, ���Ϸα��� ��������)
//    �Է¹��� id�� �ش��ϴ� �����Ͱ� ��� �ִ��� Ȯ��
//    ������ �� �������� pw�� �Է¹��� pw�� ������ Ȯ��
    
    public boolean login(String id, String pass)
    {
        Member member = memberDao.selectOne(id);
//        �α��� ���̵� ������ ����
        if(member == null)
            return false;
        else
        {//���̵� �ִµ� �α��� �Ҷ� ��й�ȣ�� ��ġ�ϸ� ����
            if(member.getpass().equals(pass))
                return true;
            //�ƴϸ� ����
            else 
                return false;
        }
    }
    
//    id�� �ش��ϴ� ������ ������ �ִ� ���(�Ķ���� id, ���� ȸ��������)
//    �Է¹��� id�� �ش��ϴ� �����͵��� ���� �ֱ�
//    ��Ʋ����
    public Member getMember(String id)
    {
        return memberDao.selectOne(id);
    }
    
//    ȸ�������� �������ִ� ���(�Ķ���� ȸ��������)
//    ȸ���������� id�� �ش��ϴ� �����͸� ��񿡼�ã�Ƽ� �� pw�� ȸ���������� pw�� ��ġ�Ѵٸ� 
//    �ش絥������ name, email�� �Ķ���ͷ� �Ѿ�� ȸ���������� name, email�� ����
    public boolean update(String id, String pass, String name, String email,String contact)
    {//ȸ������ �ɷηη� �޾Ƽ�
        Member member = new Member();
        member.setid(id);
        member.setpass(pass);
        member.setname(name);
        member.setemail(email);
        member.setcontact(contact);
        Member originMember = memberDao.selectOne(id);
        if(originMember.getpass().equals(member.getpass())) //��й�ȣ�� ������ 
        {
            memberDao.updateMember(member);
            return true;
        }
        else
            return false;
    }
    
//    ��� ȸ����������Ʈ�� ���� ���� ���(�Ķ���� x, ���� ȸ���������� �迭)
//    ��� ȸ����������Ʈ�� �������
    public List<Member> getMemberList()
    {
        return memberDao.selectAll();
    }
    
//    ȸ�������ϱ� ���(�Ķ���� : ȸ��������, ���� : ���Լ�������)
//    ȸ���������� id�� �ش��ϴ� �����Ͱ� �ִ��� Ȯ��
//    ������ ȸ�������� �����͸� ��� �߰�
    public boolean join(String id, String pass, String name, String email,String contact)
    {
        if(memberDao.selectOne(id) == null)
        {
            Member member = new Member();
            member.setid(id);
            member.setpass(pass);
            member.setname(name);
            member.setemail(email);
            member.setcontact(contact);
            memberDao.insertMember(member);
            return true;
        }
        else 
            return false;
    }
}
