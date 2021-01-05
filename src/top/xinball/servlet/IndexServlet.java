package top.xinball.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import top.xinball.constant.Constant;
import top.xinball.constant.Intpassing;
import top.xinball.constant.Stringpassing;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "IndexServlet")
public class IndexServlet extends BaseServlet {
    private final SendServlet sendServlet = new SendServlet();
    private final UploadServlet uploadServlet = new UploadServlet();
    public String index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData=null;
        String SendDataStr=null;
        int flag=0;//上传表单？
        if("post".equalsIgnoreCase(request.getMethod())){
            try {
                DiskFileItemFactory factory = new DiskFileItemFactory();// 1、创建一个DiskFileItemFactory工厂
                ServletFileUpload upload = new ServletFileUpload(factory);//2、创建一个文件上传解析器
                upload.setHeaderEncoding("UTF-8");//解决上传文件名的中文乱码
                if(!ServletFileUpload.isMultipartContent(request)){//3、判断提交上来的数据是否是上传表单的数据
                    flag=0;
                    System.out.println("不是上传表单的数据");
                }else {
                    flag=1;
                    //4、使用ServletFileUpload解析器解析上传数据，解析结果返回的是一个List<FileItem>集合，每一个FileItem对应一个Form表单的输入项
                    List<?> list = upload.parseRequest(request);
                    for (Object o : list) {
                        FileItem item = (FileItem) o;
                        if (item.isFormField()) {//如果fileitem中封装的是普通输入项的数据
                            if (item.getFieldName().equals("SendData")) {
                                SendDataStr = item.getString("UTF-8");//解决普通输入项的数据的中文乱码问题// value = new String(value.getBytes("iso8859-1"),"UTF-8");
                                System.out.println("SendData=" + SendDataStr);
                            }
                        }
                    }
                    SendData = JSON.parseObject(SendDataStr);
                    if (SendDataStr == null || SendData == null) {
                        request.setAttribute("msg", "发送数据格式有误！（SendData数据为空）");
                        request.setAttribute("returnvalue", "0");
                        return Constant.pagemessage;
                    } else {
                        String page = SendData.getString("action");
                        String action = SendData.getString("method");
                        String uid = SendData.getString("uid");
                        System.out.println("page=" + page);
                        System.out.println("action=" + action);
                        if (page == null) {
                            request.setAttribute("msg", "发送数据格式有误！（action为空）");
                            request.setAttribute("returnvalue", "0");
                            return Constant.pagemessage;
                        }
                        if (action == null) {
                            request.setAttribute("msg", "发送数据格式有误！（method为空）");
                            request.setAttribute("returnvalue", "0");
                            return Constant.pagemessage;
                        }
                        //request.setAttribute("action",action);
                        if (page.equals("send")) {
                            Constant.SavePath = this.getServletContext().getRealPath("/" + Constant.imgdir);
                            //得到上传文件的保存目录，将上传的文件存放于files目录下，不允许外界直接访问，保证上传文件的安全
                            File file = new File(Constant.SavePath);
                            if(!file.exists()&&!file.isDirectory()) {//判断上传文件的保存目录是否存在
                                System.out.println("目录 " + Constant.SavePath + " 不存在，需要创建");//创建目录
                                file.mkdirs();
                            }
                            switch (action) {
                                case "uploadmsg": {
                                    System.out.println("send.uploadmsg");
                                    Stringpassing mid = new Stringpassing();
                                    Intpassing pid = new Intpassing(-2);
                                    String returnpage = sendServlet.uploadmsg(request, response, SendDataStr, mid, pid);
                                    System.out.println("returnpage=" + returnpage);
                                    if (returnpage != null)
                                        return returnpage;
                                    return uploadServlet.index(request, response, list, uid, mid.str, pid.val);
                                }
                                case "uploadphoto": {
                                    System.out.println("send.uploadphoto");
                                    Stringpassing mid = new Stringpassing();
                                    Intpassing pid = new Intpassing(-2);
                                    String returnpage = sendServlet.uploadphoto(request, response, SendDataStr, mid, pid);
                                    System.out.println("returnpage=" + returnpage);
                                    if (returnpage != null)
                                        return returnpage;
                                    return uploadServlet.index(request, response, list, uid, mid.str, pid.val);
                                }
                                case "uploadavatar": {
                                    System.out.println("send.uploadavatar");
                                    String returnpage = sendServlet.uploadavatar(request, response, SendDataStr);
                                    System.out.println("returnpage=" + returnpage);
                                    if (returnpage != null)
                                        return returnpage;
                                    return uploadServlet.index(request, response, list, uid, "", -1);
                                }
                            }
                        }
                    /*else if(page.equals("receive")){
                           Constant.SavePath=this.getServletContext().getRealPath("/"+Constant.imgdir);
                           return Constant.pagereceive;
                       }*/
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
                request.setAttribute("msg", "获取multipart文件流失败！");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
        }
        if(flag==0){
            SendDataStr = request.getParameter("SendData");
            SendData= JSON.parseObject(SendDataStr);
        }
        if(SendDataStr == null||SendData==null){
            request.setAttribute("msg","发送数据格式有误！（数据为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }else{
            String page=SendData.getString("action");
            String action=SendData.getString("method");
            System.out.println(page);
            System.out.println(action);
            if(page==null){
                request.setAttribute("msg","发送数据格式有误！（action为空）");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
            if(action==null){
                request.setAttribute("msg","发送数据格式有误！（method为空）");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
            request.setAttribute("SendData", SendDataStr);
            request.setAttribute("action",action);
            if(page.equals("send")){
                Constant.SavePath=this.getServletContext().getRealPath("/"+Constant.imgdir);
                return Constant.pagesend;
            }else if(page.equals("receive")){
                Constant.SavePath=this.getServletContext().getRealPath("/"+Constant.imgdir);
                return Constant.pagereceive;
            }
        }

        return Constant.pageindex;
    }

}