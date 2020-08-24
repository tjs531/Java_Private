package com.myhome.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.Action;

import com.myhome.board.action.ActionForward;
import com.myhome.board.action.BoardListAction;
import com.myhome.board.action.BoardWriteAction;

@WebServlet("*.do") // *do�� ������ ��� ���� �������� ��û�ϰڴ�
public class Controller extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// URI���� ����ڰ� ��û�� ������ ����
		String requestURI = request.getRequestURI(); // ��û�� URI(�ּ�) : "/myhome/board/BoardList.do"
		int lstIndex = requestURI.lastIndexOf("/") + 1; // 13 + 1 --> 14
		//������ �������� �� ���� ��ġ�ϴ�? ->�׷��� BoardList.do�� �����ϱ� ���ؼ� ��ġ ���ϱ�
		String requestPage = requestURI.substring(lstIndex); // "BoardList.do"

		System.out.println("requestURI : " + requestURI); // "/myhome/board/BoardList.do"
		System.out.println("requestPage : " + requestPage); // "BoardList.do"

		Action action = null;
		ActionForward actionForward = null;
		try {
			if (requestPage.equals("BoardList.do")) {
				action = new BoardListAction();
				actionForward = action.execute(request, response);
				// nextPath : "BoardListView.do"
				// isRedirect : false
			} else if (requestPage.equals("BoardListView.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardListView.jsp");
				actionForward.setRedirect(false);
				// nextPath : "boardListView.jsp"
				// isRedirect : false
			} else if (requestPage.equals("BoardWriteForm.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardWriteView.jsp");
				actionForward.setRedirect(false);
			} else if (requestPage.equals("BoardWrite.do")) {
				action = new BoardWriteAction();
				actionForward = action.execute(request, response);
			} else if (requestPage.equals("Result.do")) {
				actionForward = new ActionForward();
				actionForward.setNextPath("boardResultView.jsp");
				actionForward.setRedirect(false);
			}

			if (actionForward != null) {
				if (actionForward.isRedirect()) {
					response.sendRedirect(actionForward.getNextPath()); // nextPath�� redirect
				} else {
					request.getRequestDispatcher(actionForward.getNextPath()).forward(request, response);
					// nextPath�� redirect
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}
}
