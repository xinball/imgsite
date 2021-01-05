package top.xinball.servlet;

import top.xinball.constant.Constant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.*;

@WebServlet(name = "BaseServlet")
public abstract class BaseServlet extends HttpServlet {
    public void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/htm");
            resp.setCharacterEncoding("utf-8");
            req.setCharacterEncoding("utf-8");
            // 获取 action 业务鉴别字符串，获取相应的业务 方法反射对象
            String action = req.getParameter("action");
            // 判断 参数是否为空  若为空,执行默认的方法
            if(action == null || action.trim().length()==0){
                action=(String)req.getAttribute("action");
                if(action == null || action.trim().length()==0) {
                    System.out.println("action为空");
                    action = "index";
                }
            }
            System.out.println(action);
            // 调用目标业务 方法
            Method method = this.getClass().getMethod(action, HttpServletRequest.class, HttpServletResponse.class);
            req.removeAttribute("action");
            // 让方法执行,接受返回值
            String path=(String)method.invoke(this, req, resp);
            // 判断返回值是否为空 若不为空统一处理请求转发
            if(path != null){
                req.getRequestDispatcher(path).forward(req, resp);
            }
        } catch (Exception e) {
            req.getRequestDispatcher(index(req,resp)).forward(req, resp);
            e.printStackTrace();
            //throw new RuntimeException();
        }
    }
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }
    public String index(HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException {
        req.setAttribute("msg","发送数据有误！（消息方法不存在）");
        req.setAttribute("returnvalue","格式0");
        return Constant.pagemessage;
        //System.out.println("无效地址");
        //return Constant.page404;
    }
}
