package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import service.Impl.ScoreServiceImpl;

import model.PageBean;
import model.Score;
import dao.Impl.ScoreDaoImpl;
import java.util.Map;
import java.util.stream.Collectors;

public class ServletFindAllScore extends HttpServlet{
	
	private Score score;
	private ScoreServiceImpl scoreServiceImpl=new ScoreServiceImpl();
	
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if ("chart".equals(request.getParameter("type"))) {
			List<Score> all = scoreServiceImpl.findAll();   // ���� DAO/Service ����
			Map<String, Long> dist = all.stream()
					.collect(Collectors.groupingBy(
							s -> s.getScoreGrade() < 60 ? "������" :
									s.getScoreGrade() < 80 ? "����" : "����",
							Collectors.counting()));

			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("{\"������\":" + dist.getOrDefault("������", 0L) +
					",\"����\":"   + dist.getOrDefault("����", 0L) +
					",\"����\":"   + dist.getOrDefault("����", 0L) + "}");
			out.flush();
			return;  // �� ֱ�ӷ��أ������� JSP
		}
		int pageNo=1;
		int pageCount=10;
		
		String pageNoStr=request.getParameter("pageNo");
		String pageCountStr=request.getParameter("pageCount");
	
		
		if(pageNoStr!=null){
			pageNo=Integer.parseInt(pageNoStr);
		}
		if(pageCountStr!=null){
			pageCount=Integer.parseInt(pageCountStr);
		}
		
		PageBean list=scoreServiceImpl.scoreListPage(pageNo, pageCount);
		request.setAttribute("list", list);
		request.getRequestDispatcher("admin/scoreAllInfo.jsp").forward(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request,response);
	}
}
