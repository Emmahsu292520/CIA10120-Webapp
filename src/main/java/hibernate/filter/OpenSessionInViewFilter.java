package hibernate.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.hibernate.SessionFactory;

import hibernate.util.HibernateUtil;

@WebFilter(urlPatterns = { "/*" })
public class OpenSessionInViewFilter implements Filter{

//	@Override
//	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
//		SessionFactory factory = HibernateUtil.getSessionFactory();
//		try {
//			System.out.println("filter open transaction");
//			factory.getCurrentSession().beginTransaction();
//			chain.doFilter(req, res);
//			factory.getCurrentSession().getTransaction().commit();
//		} catch (Exception e) {
//			factory.getCurrentSession().getTransaction().rollback();
//			e.printStackTrace();
//			chain.doFilter(req, res);
//		}
//	
//	}
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
	    SessionFactory factory = HibernateUtil.getSessionFactory();
	    try {
	        System.out.println("filter open transaction"); // 新增日誌：開啟事務
	        factory.getCurrentSession().beginTransaction();
	        chain.doFilter(req, res);
	        System.out.println("filter commit transaction"); // 新增日誌：提交事務
	        factory.getCurrentSession().getTransaction().commit();
	    } catch (Exception e) {
	        System.out.println("filter rollback transaction due to exception: " + e.getMessage()); // 新增日誌：因異常回滾事務
	        factory.getCurrentSession().getTransaction().rollback();
	        e.printStackTrace();
	        throw new ServletException(e); // 將異常向上拋，確保錯誤能被正確處理
	    }
	}

	
	
	
	
}
