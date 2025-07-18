package servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Impl.ScoreServiceImpl;

import dao.Impl.ScoreDaoImpl;
import model.Score;

public class ServletaddScore extends HttpServlet{

	private Score score;
	private ServletFindAllScore servletFindAllScore=new ServletFindAllScore();
	private ScoreServiceImpl scoreServiceImpl=new ScoreServiceImpl();

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		if(request.getParameter("stuNum").isEmpty()
				|| request.getParameter("stuName").isEmpty()
				|| request.getParameter("stuClass").isEmpty()
				|| request.getParameter("courseName").isEmpty()
				|| request.getParameter("scoreGrade").isEmpty()
				|| request.getParameter("major").isEmpty()
		){
			request.setAttribute("msg", "请填写全部字段！！");
			request.getRequestDispatcher("admin/addScore.jsp").forward(request, response);
		}
		String stuNum = request.getParameter("stuNum");
		String stuName = request.getParameter("stuName");
		String stuClass = request.getParameter("stuClass");
		String courseName = request.getParameter("courseName");
		double scoreGrade=Double.parseDouble(request.getParameter("scoreGrade"));
		if (scoreGrade < 0 || scoreGrade > 100) {
			request.setAttribute("msg", "成绩必须在 0-100 分之间！");
			request.getRequestDispatcher("admin/addScore.jsp").forward(request, response);
			return;
		}
		String major = request.getParameter("major");

		score=new Score(stuNum,stuName,stuClass,courseName,scoreGrade,major);
		Score scor=scoreServiceImpl.selectScoreInfo(score);
		if(scor==null){
			int rs=scoreServiceImpl.addScore(score);
			if(rs>0){
				request.setAttribute("msg", "添加成功！！");
				servletFindAllScore.doGet(request, response);
			//	request.getRequestDispatcher("admin/addCourse.jsp").forward(request, response);
			}else{
				request.setAttribute("msg", "添加失败！！");
				request.getRequestDispatcher("admin/addScore.jsp").forward(request, response);
			}
		}else{
			request.setAttribute("msg", "该生的本门课成绩已录入，请重新录入！！");
			request.getRequestDispatcher("admin/addScore.jsp").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
