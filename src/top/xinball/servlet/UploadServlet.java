package top.xinball.servlet;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sun.corba.se.spi.orb.StringPair;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import top.xinball.bean.Photo;
import top.xinball.bean.User;
import top.xinball.constant.Constant;
import top.xinball.constant.Intpassing;
import top.xinball.dao.PhotoDao;
import top.xinball.dao.UserDao;
import top.xinball.dao.impl.PhotoDaoImpl;
import top.xinball.dao.impl.UserDaoImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@WebServlet(name = "UploadServlet")
public class UploadServlet  extends BaseServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final PhotoDao photoDao = new PhotoDaoImpl();
    public String index(HttpServletRequest request, HttpServletResponse response, List<?> list, String uid, String mid, int pid) throws ServletException, IOException {
        StringBuilder msg= new StringBuilder();
        try{//使用Apache文件上传组件处理文件上传步骤：
            System.out.println("uid="+uid+",mid="+mid+",pid="+pid);
            if(uid==null||uid.equals("")||mid==null||(pid>-1&&mid.equals(""))||pid<-1){
                request.setAttribute("msg","发送数据有误！（uid/mid/pid为空）");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
            msg = new StringBuilder("参数传递成功！\n");
            for (Object o : list) {
                FileItem item = (FileItem) o;
                if(!item.isFormField()){//如果fileitem中封装的是上传文件
                    String filename = item.getName();//得到上传的文件名称，
                    if (filename == null || filename.trim().equals(""))
                        continue;
                    String subfix=filename.substring(filename.lastIndexOf("."));
                    if(subfix.equals("")){
                        request.setAttribute("msg","发送数据有误！（文件格式有误！）");
                        request.setAttribute("returnvalue","0");
                        return Constant.pagemessage;
                    }else{
                        subfix=subfix.toLowerCase();
                        if(!subfix.equals(".png")&&!filename.endsWith(".jpg")&&!subfix.equals(".jpeg")&&!subfix.equals(".jpe")&&!subfix.equals(".gif")&&!subfix.equals(".ico")) {
                            request.setAttribute("msg","发送数据有误！（文件格式有误！）");
                            request.setAttribute("returnvalue","0");
                            return Constant.pagemessage;
                        }
                    }
                    String url,pidStr;
                    if(pid>-1)
                        pidStr=pid<10?"0"+pid:""+pid;
                    else
                        pidStr="";
                    url=Constant.SavePath+uid+mid+pidStr+subfix;
                    System.out.println(url);
                    if(pid>-1){
                        Date uploadTime=new Date();
                        Photo photo=new Photo(pidStr,uid,mid,url,uploadTime,1);
                        photoDao.save(photo);
                    }else{
                        User user=userDao.queryByUid(uid);
                        if(user==null){
                            request.setAttribute("msg","发送数据有误！（无法获取用户信息）");
                            request.setAttribute("returnvalue","0");
                            return Constant.pagemessage;
                        }
                        user.setAvatar(subfix);
                        if(userDao.update(user)==-1){
                            request.setAttribute("msg","上传头像失败！");
                            request.setAttribute("returnvalue","0");
                            return Constant.pagemessage;
                        }
                    }

                    InputStream in = item.getInputStream();//获取item中的上传文件的输入流
                    FileOutputStream out = new FileOutputStream(url);//创建一个文件输出流
                    byte[] buffer = new byte[1024];//创建一个缓冲区
                    int len = 0;// 判断输入流中的数据是否已经读完的标识
                    while ((len = in.read(buffer)) > 0) {//循环将输入流读入到缓冲区当中，(len=in.read(buffer))>0就表示in里面还有数据
                        out.write(buffer, 0, len);//使用FileOutputStream输出流将缓冲区的数据写入到指定的目录(savePath + "\\" + filename)当中
                    }
                    in.close();//关闭输入流
                    out.close();//关闭输出流
                    item.delete();//删除处理文件上传时生成的临时文件
                    msg.append("文件：").append(filename).append(" 上传成功！\n");
                    if(pid==-1){
                        break;
                    }
                    pid++;
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
            msg.append("文件上传失败！");
            request.setAttribute("msg", msg.toString());
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        request.setAttribute("msg", msg.toString());
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
}
