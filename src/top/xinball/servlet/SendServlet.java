package top.xinball.servlet;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import sun.security.util.math.intpoly.IntegerPolynomial;
import top.xinball.bean.*;
import top.xinball.constant.Constant;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import top.xinball.constant.Intpassing;
import top.xinball.constant.Stringpassing;
import top.xinball.dao.*;
import top.xinball.dao.impl.*;
import top.xinball.utils.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet(name = "SendServlet")
public class SendServlet extends BaseServlet {
    private final UserDao userDao = new UserDaoImpl();
    private final MessageDao messageDao = new MessageDaoImpl();
    private final PhotoDao photoDao=new PhotoDaoImpl();
    public String uploadmsg(HttpServletRequest request, HttpServletResponse response, String SendDataStr, Stringpassing mid, Intpassing pid) throws ServletException, IOException {
        if(SendDataStr==null||SendDataStr.equals("")){
            request.setAttribute("msg","发送数据格式有误！（SendData为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        System.out.println(SendDataStr);
        JSONObject SendData= JSON.parseObject(SendDataStr);
        String uid=SendData.getString("uid");
        String pwd=SendData.getString("pwd");
        String content=SendData.getString("message");
        //String photos = SendData.getString("photos");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("content="+content);
        //System.out.println("photos="+photos);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(content==null||content.equals("")){
            request.setAttribute("msg","发送数据格式有误！（message为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        /*if(photos==null){
            request.setAttribute("msg","发送数据格式有误！（photos为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }*/
        if(userDao.queryByUid(uid)!=null){
            Date sendTime=new Date();
            String sendTimeStr=new SimpleDateFormat("yyMMddHHmmss").format(sendTime);
            do{
                mid.str= sendTimeStr+IdUtils.genID(1);
            }while(messageDao.queryByUidMid(uid,mid.str)!=null);
            Message message=new Message(mid.str,uid,content,sendTime,null,1);
            messageDao.save(message);
        }else{
            request.setAttribute("msg","发送数据内容有误！（获取不到用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        pid.val=0;
        //request.setAttribute("uid",uid);
        //request.setAttribute("mid",mid);
        //request.setAttribute("pid",0);
        //request.setAttribute("files",photos);
        //return Constant.pageupload;
        return null;
    }
    public String altermsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String mid=SendData.getString("mid");
        String pwd=SendData.getString("pwd");
        String content=SendData.getString("message");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid);
        System.out.println("content="+content);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid==null||mid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(content==null||content.equals("")) {
            request.setAttribute("msg", "发送数据格式有误！（message为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        Message message=messageDao.queryByUidMid(uid,mid);
        if(message==null){
            request.setAttribute("msg","发送数据格式有误！（message为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(message.getContent().equals(content)){
            request.setAttribute("msg","消息内容相同，无需修改！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        message.setContent(content);
        message.setAlterTime(new Date());
        if(messageDao.update(message)==-1){
            request.setAttribute("msg","消息内容修改失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        request.setAttribute("msg", "消息内容修改成功！");
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String deletemsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String mid=SendData.getString("mid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid==null||mid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        Message message=messageDao.queryByUidMid(uid,mid);
        if(message==null){
            request.setAttribute("msg","发送数据格式有误！（message为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(message.getDisplay()==0){
            request.setAttribute("msg","该消息已经被删除了！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        message.setDisplay(0);
        message.setAlterTime(new Date());
        if(messageDao.update(message)==-1){
            request.setAttribute("msg","消息删除失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        List<Photo> photos = photoDao.queryByUidMid(uid,mid);
        for(Photo photo:photos){
            photo.setDisplay(0);
            photoDao.update(photo);
        }
        request.setAttribute("msg", "消息删除成功！");
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String recovermsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String mid=SendData.getString("mid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid==null||mid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        Message message=messageDao.queryByUidMid(uid,mid);
        if(message==null){
            request.setAttribute("msg","发送数据格式有误！（message为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(message.getDisplay()==1){
            request.setAttribute("msg","该消息无需恢复！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        message.setDisplay(1);
        message.setAlterTime(new Date());
        if(messageDao.update(message)==-1){
            request.setAttribute("msg","消息恢复失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        List<Photo> photos = photoDao.queryByUidMid(uid,mid);
        for(Photo photo:photos){
            photo.setDisplay(1);
            photoDao.update(photo);
        }
        request.setAttribute("msg", "消息恢复成功！");
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String deletephoto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String mid=SendData.getString("mid");
        String pid=SendData.getString("pid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid);
        System.out.println("pid="+pid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid==null||mid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pid==null||pid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（pid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        Photo photo=photoDao.queryByUidMidPid(uid,mid,pid);
        if(photo==null){
            request.setAttribute("msg","发送数据格式有误！（photo为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(photo.getDisplay()==0){
            request.setAttribute("msg","该图片已经被删除了！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        photo.setDisplay(0);
        if(photoDao.update(photo)==-1){
            request.setAttribute("msg","图片删除失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        request.setAttribute("msg", "图片删除成功！");
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String recoverphoto(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid=SendData.getString("uid");
        String mid=SendData.getString("mid");
        String pid=SendData.getString("pid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid);
        System.out.println("pid="+pid);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid==null||mid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pid==null||pid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（pid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        Photo photo=photoDao.queryByUidMidPid(uid,mid,pid);
        if(photo==null){
            request.setAttribute("msg","发送数据格式有误！（photo为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(photo.getDisplay()==1){
            request.setAttribute("msg","该图片无需恢复！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        photo.setDisplay(1);
        if(photoDao.update(photo)==-1){
            request.setAttribute("msg","图片恢复失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        request.setAttribute("msg", "图片恢复成功！");
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String uploadphoto(HttpServletRequest request, HttpServletResponse response,String SendDataStr,Stringpassing mid,Intpassing pid) throws ServletException, IOException {
        if(SendDataStr==null||SendDataStr.equals("")){
            request.setAttribute("msg","发送数据格式有误！（SendData为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        JSONObject SendData= JSON.parseObject(SendDataStr);
        String uid=SendData.getString("uid");
        mid.str=SendData.getString("mid");
        String pwd=SendData.getString("pwd");
        System.out.println("uid="+uid);
        System.out.println("pwd="+pwd);
        System.out.println("mid="+mid.str);
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","发送数据格式有误！（uid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(mid.str==null||mid.str.equals("")){
            request.setAttribute("msg","发送数据格式有误！（mid为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        pid.val = photoDao.NumByUidMid(uid,mid.str);
        //request.setAttribute("uid",uid);
        //request.setAttribute("mid",mid);
        //request.setAttribute("pid",pid);
        return null;
    }
    public String uploadavatar(HttpServletRequest request, HttpServletResponse response,String SendDataStr) throws ServletException, IOException {
        if(SendDataStr==null||SendDataStr.equals("")){
            request.setAttribute("msg","发送数据格式有误！（SendData为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        JSONObject SendData= JSON.parseObject(SendDataStr);
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
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(pwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        //request.setAttribute("uid",uid);
        //request.setAttribute("mid",mid);
        //request.setAttribute("pid",pid);
        return null;
    }
    public String reguser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String name= SendData.getString("name");
        String pwd= SendData.getString("pwd");
        String email= SendData.getString("email");
        String tel= SendData.getString("tel");
        String regIp= IpUtils.getIpAddress(request);
        System.out.println("name="+name+",pwd="+pwd+",email="+email+",tel="+tel+",regIp="+regIp);
        if(name==null||name.equals("")){
            request.setAttribute("msg","用户名不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(pwd==null||pwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(email==null||email.equals("")){
            request.setAttribute("msg","邮箱账号不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(tel==null||tel.equals("")){
            request.setAttribute("msg","手机号码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(userDao.queryByTel(tel)!=null||userDao.queryByName(name)!=null){
            request.setAttribute("msg","用户名或手机号码已被注册！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        String uid;
        do{
            uid= IdUtils.genID(6);
        }while(userDao.queryByUid(uid)!=null);
        if(userDao.save(new User(uid,name,pwd,email,tel,"","","","",2,new Date(),"",new Date(),regIp,0))==-1){
            request.setAttribute("msg","用户注册失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        request.setAttribute("msg","用户注册成功！用户名为： "+name+" 邮箱账号为： "+email+" 手机号码为： "+tel);
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
    public String alteruser(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ParseException {
        JSONObject SendData= JSON.parseObject((String)request.getAttribute("SendData"));
        String uid= SendData.getString("uid");
        String name= SendData.getString("name");
        String oldpwd= SendData.getString("oldpwd");
        String newpwd= SendData.getString("newpwd");
        String email= SendData.getString("email");
        String tel= SendData.getString("tel");
        String nickname= SendData.getString("nickname");
        String sexStr= SendData.getString("sex");
        String realname= SendData.getString("realname");
        String slogan= SendData.getString("slogan");
        String birthday= SendData.getString("birthday");
        String livead= SendData.getString("livead");
        Date birthdayDate=new Date();
        int sex=2;
        if(uid==null||uid.equals("")){
            request.setAttribute("msg","uid不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(name==null||name.equals("")){
            request.setAttribute("msg","用户名不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(oldpwd==null||oldpwd.equals("")){
            request.setAttribute("msg","密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(newpwd==null){
            request.setAttribute("msg","新密码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(email==null||email.equals("")){
            request.setAttribute("msg","邮箱账号不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(tel==null||tel.equals("")){
            request.setAttribute("msg","手机号码不能为空！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(nickname==null){
            request.setAttribute("msg","数据格式有误（nickname为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(sexStr==null){
            request.setAttribute("msg","数据格式有误（sex为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }else if(sexStr.matches("[0-9]")){
            sex=Integer.parseInt(sexStr);
        }else{
            request.setAttribute("msg","数据格式有误（sex为无效值："+sex+"）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(realname==null){
            request.setAttribute("msg","数据格式有误（realname为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(slogan==null){
            request.setAttribute("msg","数据格式有误（slogan为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(birthday==null){
            request.setAttribute("msg","数据格式有误（birthday为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }else if(birthday.matches("\\d{4}-\\d{1,2}-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}")){
            birthdayDate = Constant.simpleDateFormat.parse(birthday);
        }else{
            request.setAttribute("msg","数据格式有误（birthday为无效值:"+birthday+"）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(livead==null){
            request.setAttribute("msg","数据格式有误（livead为空）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }


        User user;
        if((user=userDao.queryByUid(uid))==null){
            request.setAttribute("msg","发送数据格式有误！（无法获取用户信息）");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        if(!user.getPwd().equals(oldpwd)){
            request.setAttribute("msg","密码错误！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        StringBuilder msg=new StringBuilder("用户信息修改成功！\n");
        if(!user.getName().equals(name)){
            if(userDao.queryByName(name)!=null){
                request.setAttribute("msg","用户名已被注册，不能修改！");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
            msg.append("用户名修改为： ").append(name).append(" \n");
            user.setName(name);
        }
        if(!newpwd.equals("")&&!oldpwd.equals(newpwd)){
            msg.append("密码修改为： ").append(newpwd).append(" \n");
            user.setPwd(newpwd);
        }
        if(!user.getEmail().equals(email)){
            msg.append("邮箱修改为： ").append(email).append(" \n");
            user.setEmail(email);
        }
        if(!user.getTel().equals(tel)){
            if(userDao.queryByTel(tel)!=null){
                request.setAttribute("msg","手机号码已被注册，不能修改！");
                request.setAttribute("returnvalue","0");
                return Constant.pagemessage;
            }
            msg.append("手机号码修改为： ").append(tel).append(" \n");
            user.setTel(tel);
        }
        if(!user.getNickname().equals(nickname)){
            msg.append("昵称修改为： ").append(nickname).append(" \n");
            user.setNickname(nickname);
        }
        if(sex<9&&user.getSex()!=sex){//性别小于9则表示修改
            msg.append("性别修改为： ").append(Constant.sexStr[sex]).append(" \n");
            user.setSex(sex);
        }
        if(!user.getRealname().equals(realname)){
            msg.append("真实姓名修改为： ").append(realname).append(" \n");
            user.setRealname(realname);
        }
        if(!user.getSlogan().equals(slogan)){
            msg.append("个性签名修改为： ").append(slogan).append(" \n");
            user.setSlogan(slogan);
        }
        if(user.getBirthday().getTime()!=birthdayDate.getTime()){
            msg.append("出生日期修改为： ").append(birthday).append(" \n");
            user.setBirthday(birthdayDate);
        }
        if(!user.getLivead().equals(livead)){
            msg.append("住址修改为： ").append(livead).append(" \n");
            user.setLivead(livead);
        }
        if(userDao.update(user)==-1){
            request.setAttribute("msg","修改用户信息失败！");
            request.setAttribute("returnvalue","0");
            return Constant.pagemessage;
        }
        msg.deleteCharAt(msg.length()-1);
        request.setAttribute("msg",msg.toString());
        request.setAttribute("returnvalue","1");
        return Constant.pagemessage;
    }
}
