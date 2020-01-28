package com.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import com.bean.InfoBean;
import com.util.DBUtil;

public class InfoDao {
	public static void delete()
	{
		String sql = "delete from info";
		//System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement state = null;
		try {
			state = conn.createStatement();
			state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(state, conn);
		}
	}
	public static void add(String Question,String Question_user,String Question_date,String Question_info,String Answer,String Answer_user,String Answer_date,String Answer_info,String Url)
	{
		String sql = "insert into info(Question,Question_user,Question_date,Question_info,Answer,Answer_user,Answer_date,Answer_info,Url) values('"+Question+"','"+Question_user+"','"+Question_date+"','"+Question_info+"','"+Answer+"','"+Answer_user+"','"+Answer_date+"','"+Answer_info+"','"+Url+"')";
		//System.out.println(sql);
		Connection conn = DBUtil.getConn();
		Statement state = null;
		try {
			state = conn.createStatement();
			state.executeUpdate(sql);
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			DBUtil.close(state, conn);
		}
	}
	public static ArrayList<InfoBean> select( String info)
	{
		Connection conn = DBUtil.getConn();
		ArrayList<InfoBean> userBeans=new ArrayList<InfoBean>();
		InfoBean userBean;
		try {
			Statement state = conn.createStatement();
			ResultSet rs = state.executeQuery("select * from info where Question like '%"+info+"%'");
			while(rs.next()) {
				//如果有结果，是认为是通过验证了
				userBean = new InfoBean();
				userBean.setId(rs.getString("ID"));
				userBean.setQuestion(rs.getString("Question"));
				userBean.setQuestion_user(rs.getString("Question_user"));
				userBean.setQuestion_date(rs.getString("Question_date"));
				userBean.setQuestion_info(rs.getString("Question_info"));
				userBean.setAnswer(rs.getString("Answer"));
				userBean.setAnswer_user(rs.getString("Answer_user"));
				userBean.setAnswer_date(rs.getString("Answer_date"));
				userBean.setAnswer_info(rs.getString("Answer_info"));
				userBeans.add(userBean);
			}
		} catch (Exception e) 
		{
			e.printStackTrace();
		}

		return userBeans;
	}
}
