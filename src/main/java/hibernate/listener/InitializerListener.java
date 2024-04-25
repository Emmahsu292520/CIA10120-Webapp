package hibernate.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import hibernate.util.HibernateUtil;


@WebListener
public class InitializerListener implements ServletContextListener{

	
	//啟動webapp時做這些事情
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("context started");
		HibernateUtil.getSessionFactory();
	}

	
	//關閉webapp時做這些事情
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("context ended");
		HibernateUtil.shutdown();
	}
	
	
	
}
