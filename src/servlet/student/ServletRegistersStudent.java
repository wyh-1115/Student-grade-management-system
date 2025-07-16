package servlet.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import service.Impl.StudentServiceImpl;
import service.Impl.UserServiceImpl;

import model.Student;
import model.User;

public class ServletRegistersStudent extends HttpServlet{

	private User user;
	private Student student;
	private UserServiceImpl userServiceImpl = new UserServiceImpl();
	private StudentServiceImpl studentServiceImpl =new StudentServiceImpl();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String stuNum = request.getParameter("stuNum");
		if(StringUtils.isEmpty(stuNum)) {
			request.setAttribute("msg", "ѧ�Ų���Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String stuName = request.getParameter("stuName");
		if(StringUtils.isEmpty(stuName)) {
			request.setAttribute("msg", "��������Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String pwd = request.getParameter("password");
		if(StringUtils.isEmpty(pwd)) {
			request.setAttribute("msg", "���벻��Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String stuSex = request.getParameter("stuSex");
		if(StringUtils.isEmpty(stuSex)) {
			request.setAttribute("msg", "�Ա���Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}

		if(StringUtils.isEmpty(request.getParameter("stuAge"))) {
			request.setAttribute("msg", "���䲻��Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		int stuAge = Integer.parseInt(request.getParameter("stuAge"));

		if(StringUtils.isEmpty(request.getParameter("stuClass"))) {
			request.setAttribute("msg", "�༶����Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String stuClass = request.getParameter("stuClass");

		if(StringUtils.isEmpty(request.getParameter("major"))) {
			request.setAttribute("msg", "רҵ����Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String major = request.getParameter("major");
		if(StringUtils.isEmpty(request.getParameter("department"))) {
			request.setAttribute("msg", "רҵ����Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String department = request.getParameter("department");
		if(StringUtils.isEmpty(request.getParameter("phone"))) {
			request.setAttribute("msg", "�ֻ�����Ϊ��!");
			request.getRequestDispatcher("register/register.jsp").forward(request, response);
		}
		String phone = request.getParameter("phone");
		int roleID = Integer.parseInt(request.getParameter("role"));
		user = new User(stuNum, stuName, pwd, phone, roleID);
		student = new Student(stuNum, stuName, stuSex, stuAge, stuClass, major,
				department);

			int rsUser=userServiceImpl.addUser(user);
			int rsStu=studentServiceImpl.addStu(student);
			if(rsStu>0 && rsUser>0){
				request.setAttribute("massage", "ע��ɹ�����");
				request.getRequestDispatcher("login/login.jsp").forward(request, response);

			}else{
				request.setAttribute("msg", "ע��ʧ�ܣ���");
				request.getRequestDispatcher("register/register.jsp").forward(request, response);
			}


	}

}
