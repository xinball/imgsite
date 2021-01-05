package top.xinball.servlet;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import top.xinball.bean.*;
import top.xinball.constant.Constant;
import top.xinball.dao.*;
import top.xinball.dao.impl.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@WebServlet(name = "ReceiveServlet")
public class ReceiveServlet  extends BaseServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final MessageDao messageDao = new MessageDaoImpl();
    private final PhotoDao photoDao=new PhotoDaoImpl();
    public String uidmsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        System.out.println("uid="+uid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        List<Message> messages = messageDao.queryByUid(uid);
        String sendTime,alterTime,uploadTime;
        StringBuilder msg = new StringBuilder("[");
        for(Message message:messages){
            List<Photo> photos = photoDao.queryByUidMid(uid,message.getMid());
            if(message.getDisplay()==0||photos==null)
                continue;
            sendTime=Constant.dateFormat.format(message.getSendTime());
            if(message.getAlterTime()!=null)
                alterTime=Constant.dateFormat.format(message.getAlterTime());
            else
                alterTime="";            msg.append("{");
            msg.append("\"uid\":\"").append(message.getUid()).append("\",");
            msg.append("\"mid\":\"").append(message.getMid()).append("\",");
            msg.append("\"message\":\"").append(message.getContent()).append("\",");
            msg.append("\"sendTime\":\"").append(sendTime).append("\",");
            msg.append("\"alterTime\":\"").append(alterTime).append("\",");
            msg.append("\"photos\":[");
            int pid=0;
            for(Photo photo:photos){
                if(photo.getDisplay()==0)
                    continue;
                uploadTime=Constant.dateFormat.format(photo.getUploadTime());
                String url=photo.getUrl();
                url=url.substring(url.lastIndexOf("/"));
                msg.append("{");
                msg.append("\"pid\":\"").append(photo.getPid()).append("\",");
                msg.append("\"url\":\"").append(url).append("\",");
                msg.append("\"uploadTime\":\"").append(uploadTime).append("\"");
                msg.append("},");
                pid++;
            }
            msg.append("]},");
        }
        if(msg.length()>1)
            msg.deleteCharAt(msg.length()-1);
        msg.append("]");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String uidkeymsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String key=SendData.getString("key");
        System.out.println("uid="+uid);
        System.out.println("key="+key);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(key==null||key.equals("")){
            request.setAttribute("msg","发送数据格式有误！（key为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        List<Message> messages = messageDao.queryByUidKey(uid,key);
        String sendTime,alterTime,uploadTime;
        StringBuilder msg = new StringBuilder("[");
        for(Message message:messages){
            List<Photo> photos = photoDao.queryByUidMid(uid,message.getMid());
            if(message.getDisplay()==0||photos==null)
                continue;
            sendTime=Constant.dateFormat.format(message.getSendTime());
            if(message.getAlterTime()!=null)
                alterTime=Constant.dateFormat.format(message.getAlterTime());
            else
                alterTime="";
            msg.append("{");
            msg.append("\"uid\":\"").append(message.getUid()).append("\",");
            msg.append("\"key\":\"").append(key).append("\",");
            msg.append("\"mid\":\"").append(message.getMid()).append("\",");
            msg.append("\"message\":\"").append(message.getContent()).append("\",");
            msg.append("\"sendTime\":\"").append(sendTime).append("\",");
            msg.append("\"alterTime\":\"").append(alterTime).append("\",");
            msg.append("\"photos\":[");
            int p=0;
            for(Photo photo:photos){
                if(photo.getDisplay()==0)
                    continue;
                uploadTime=Constant.dateFormat.format(photo.getUploadTime());
                String url=photo.getUrl();
                url=url.substring(url.lastIndexOf("/"));
                msg.append("{");
                msg.append("\"pid\":\"").append(photo.getPid()).append("\",");
                msg.append("\"url\":\"").append(url).append("\",");
                msg.append("\"uploadTime\":\"").append(uploadTime).append("\"");
                msg.append("},");
                p++;
            }
            if(p>0)
                msg.deleteCharAt(msg.length()-1);
            msg.append("]},");
        }
        if(msg.length()>1)
            msg.deleteCharAt(msg.length()-1);
        msg.append("]");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String allmsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        List<Message> messages = messageDao.queryAll();
        String sendTime,alterTime,uploadTime;
        StringBuilder msg = new StringBuilder("[");
        for(Message message:messages){
            User user=userDao.queryByUid(message.getUid());
            List<Photo> photos = photoDao.queryByUidMid(message.getUid(),message.getMid());
            if(user==null||message.getDisplay()==0||photos==null)
                continue;
            sendTime=Constant.dateFormat.format(message.getSendTime());
            if(message.getAlterTime()!=null)
                alterTime=Constant.dateFormat.format(message.getAlterTime());
            else
                alterTime="";
            msg.append("{");
            msg.append("\"uid\":\"").append(message.getUid()).append("\",");
            msg.append("\"name\":\"").append(user.getName()).append("\",");
            msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
            msg.append("\"mid\":\"").append(message.getMid()).append("\",");
            msg.append("\"message\":\"").append(message.getContent()).append("\",");
            msg.append("\"sendTime\":\"").append(sendTime).append("\",");
            msg.append("\"alterTime\":\"").append(alterTime).append("\",");
            msg.append("\"photos\":[");
            int p=0;
            for(Photo photo:photos){
                if(photo.getDisplay()==0)
                    continue;
                uploadTime=Constant.dateFormat.format(photo.getUploadTime());
                String url=photo.getUrl();
                url=url.substring(url.lastIndexOf("/"));
                msg.append("{");
                msg.append("\"pid\":\"").append(photo.getPid()).append("\",");
                msg.append("\"url\":\"").append(url).append("\",");
                msg.append("\"uploadTime\":\"").append(uploadTime).append("\"");
                msg.append("},");
                p++;
            }
            if(p>0)
                msg.deleteCharAt(msg.length()-1);
            msg.append("]},");
        }
        if(msg.length()>1)
            msg.deleteCharAt(msg.length()-1);
        msg.append("]");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String allkeymsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String key=SendData.getString("key");
        System.out.println("key="+key);
        if(key==null||key.equals("")){
            request.setAttribute("msg","发送数据格式有误！（key为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        List<Message> messages = messageDao.queryAllKey(key);
        String sendTime,alterTime,uploadTime;
        StringBuilder msg = new StringBuilder("[");
        for(Message message:messages){
            User user=userDao.queryByUid(message.getUid());
            List<Photo> photos = photoDao.queryByUidMid(message.getUid(),message.getMid());
            if(user==null||message.getDisplay()==0||photos==null)
                continue;
            sendTime=Constant.dateFormat.format(message.getSendTime());
            if(message.getAlterTime()!=null)
                alterTime=Constant.dateFormat.format(message.getAlterTime());
            else
                alterTime="";
            msg.append("{");
            msg.append("\"uid\":\"").append(message.getUid()).append("\",");
            msg.append("\"name\":\"").append(user.getName()).append("\",");
            msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
            msg.append("\"key\":\"").append(key).append("\",");
            msg.append("\"mid\":\"").append(message.getMid()).append("\",");
            msg.append("\"message\":\"").append(message.getContent()).append("\",");
            msg.append("\"sendTime\":\"").append(sendTime).append("\",");
            msg.append("\"alterTime\":\"").append(alterTime).append("\",");
            msg.append("\"photos\":[");
            int p=0;
            for(Photo photo:photos){
                if(photo.getDisplay()==0)
                    continue;
                uploadTime=Constant.dateFormat.format(photo.getUploadTime());
                String url=photo.getUrl();
                url=url.substring(url.lastIndexOf("/"));
                msg.append("{");
                msg.append("\"pid\":\"").append(photo.getPid()).append("\",");
                msg.append("\"url\":\"").append(url).append("\",");
                msg.append("\"uploadTime\":\"").append(uploadTime).append("\"");
                msg.append("},");
                p++;
            }
            if(p>0)
                msg.deleteCharAt(msg.length()-1);
            msg.append("]},");
        }
        if(msg.length()>1)
            msg.deleteCharAt(msg.length()-1);
        msg.append("]");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String userinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        System.out.println("uid="+uid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user=userDao.queryByUid(uid);
        if(user==null){
            request.setAttribute("msg","发送数据格式有误！（uid有误）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        String birthday="";
        if(user.getBirthday()!=null)
            birthday=Constant.dateFormat.format(user.getBirthday());
        StringBuilder msg = new StringBuilder("{");
        msg.append("\"uid\":\"").append(user.getUid()).append("\",");
        msg.append("\"name\":\"").append(user.getName()).append("\",");
        msg.append("\"email\":\"").append(user.getEmail()).append("\",");
        msg.append("\"tel\":\"").append(user.getTel()).append("\",");
        msg.append("\"nickname\":\"").append(user.getNickname()).append("\",");
        msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
        msg.append("\"sex\":").append(user.getSex()).append(",");
        msg.append("\"realname\":\"").append(user.getRealname()).append("\",");
        msg.append("\"slogan\":\"").append(user.getSlogan()).append("\",");
        msg.append("\"birthday\":\"").append(birthday).append("\",");
        msg.append("\"livead\":\"").append(user.getLivead()).append("\"");
        msg.append("}");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String loginuid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","发送数据格式有误！（pwd为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user=userDao.queryByUid(uid);
        if(user==null){
            request.setAttribute("msg","发送数据格式有误！（uid有误）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        String birthday="";
        if(user.getBirthday()!=null)
            birthday=Constant.dateFormat.format(user.getBirthday());
        String regTime=Constant.dateFormat.format(user.getRegTime());
        StringBuilder msg = new StringBuilder("{");
        msg.append("\"uid\":\"").append(user.getUid()).append("\",");
        msg.append("\"name\":\"").append(user.getName()).append("\",");
        msg.append("\"pwd\":\"").append(user.getPwd()).append("\",");
        msg.append("\"email\":\"").append(user.getEmail()).append("\",");
        msg.append("\"tel\":\"").append(user.getTel()).append("\",");
        msg.append("\"nickname\":\"").append(user.getNickname()).append("\",");
        msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
        msg.append("\"sex\":").append(user.getSex()).append(",");
        msg.append("\"realname\":\"").append(user.getRealname()).append("\",");
        msg.append("\"slogan\":\"").append(user.getSlogan()).append("\",");
        msg.append("\"birthday\":\"").append(birthday).append("\",");
        msg.append("\"livead\":\"").append(user.getLivead()).append("\",");
        msg.append("\"regTime\":\"").append(regTime).append("\",");
        msg.append("\"regIp\":\"").append(user.getRegIp()).append("\"");
        msg.append("}");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String loginname(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String name=SendData.getString("name");
        String pwd=SendData.getString("pwd");
        System.out.println("name="+name);
        System.out.println("pwd="+pwd);
        if(name==null||name.equals("")){
            request.setAttribute("msg","发送数据格式有误！（name为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","发送数据格式有误！（pwd为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user=userDao.queryByName(name);
        if(user==null){
            request.setAttribute("msg","发送数据格式有误！（name有误）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        String birthday="";
        if(user.getBirthday()!=null)
            birthday=Constant.dateFormat.format(user.getBirthday());
        String regTime=Constant.dateFormat.format(user.getRegTime());
        StringBuilder msg = new StringBuilder("{");
        msg.append("\"uid\":\"").append(user.getUid()).append("\",");
        msg.append("\"name\":\"").append(user.getName()).append("\",");
        msg.append("\"pwd\":\"").append(user.getPwd()).append("\",");
        msg.append("\"email\":\"").append(user.getEmail()).append("\",");
        msg.append("\"tel\":\"").append(user.getTel()).append("\",");
        msg.append("\"nickname\":\"").append(user.getNickname()).append("\",");
        msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
        msg.append("\"sex\":\"").append(user.getSex()).append("\",");
        msg.append("\"realname\":\"").append(user.getRealname()).append("\",");
        msg.append("\"slogan\":\"").append(user.getSlogan()).append("\",");
        msg.append("\"birthday\":\"").append(birthday).append("\",");
        msg.append("\"livead\":\"").append(user.getLivead()).append("\",");
        msg.append("\"regTime\":\"").append(regTime).append("\",");
        msg.append("\"regIp\":\"").append(user.getRegIp()).append("\"");
        msg.append("}");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
    public String logintel(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String tel=SendData.getString("tel");
        String pwd=SendData.getString("pwd");
        System.out.println("tel="+tel);
        System.out.println("pwd="+pwd);
        if(tel==null||tel.equals("")){
            request.setAttribute("msg","发送数据格式有误！（tel为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","发送数据格式有误！（pwd为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user=userDao.queryByTel(tel);
        if(user==null){
            request.setAttribute("msg","发送数据格式有误！（tel有误）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        String birthday="";
        if(user.getBirthday()!=null)
            birthday=Constant.dateFormat.format(user.getBirthday());
        String regTime=Constant.dateFormat.format(user.getRegTime());
        StringBuilder msg = new StringBuilder("{");
        msg.append("\"uid\":\"").append(user.getUid()).append("\",");
        msg.append("\"name\":\"").append(user.getName()).append("\",");
        msg.append("\"pwd\":\"").append(user.getPwd()).append("\",");
        msg.append("\"email\":\"").append(user.getEmail()).append("\",");
        msg.append("\"tel\":\"").append(user.getTel()).append("\",");
        msg.append("\"nickname\":\"").append(user.getNickname()).append("\",");
        msg.append("\"avatar\":\"").append(user.getAvatar()).append("\",");
        msg.append("\"sex\":\"").append(user.getSex()).append("\",");
        msg.append("\"realname\":\"").append(user.getRealname()).append("\",");
        msg.append("\"slogan\":\"").append(user.getSlogan()).append("\",");
        msg.append("\"birthday\":\"").append(birthday).append("\",");
        msg.append("\"livead\":\"").append(user.getLivead()).append("\",");
        msg.append("\"regTime\":\"").append(regTime).append("\",");
        msg.append("\"regIp\":\"").append(user.getRegIp()).append("\"");
        msg.append("}");

        request.setAttribute("returnvalue","1");
        request.setAttribute("msg",msg);
        request.setAttribute("obj","1");
        return Constant.pagemessage;
    }
}
