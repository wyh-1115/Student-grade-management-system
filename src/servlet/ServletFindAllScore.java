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
			List<Score> all = scoreServiceImpl.findAll();   // 已有 DAO/Service 方法
			Map<String, Long> dist = all.stream()
					.collect(Collectors.groupingBy(
							s -> s.getScoreGrade() < 60 ? "不及格" :
									s.getScoreGrade() < 80 ? "及格" : "优秀",
							Collectors.counting()));

			response.setContentType("application/json;charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.print("{\"不及格\":" + dist.getOrDefault("不及格", 0L) +
					",\"及格\":"   + dist.getOrDefault("及格", 0L) +
					",\"优秀\":"   + dist.getOrDefault("优秀", 0L) + "}");
			out.flush();
			return;  // ★ 直接返回，不再走 JSP
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
