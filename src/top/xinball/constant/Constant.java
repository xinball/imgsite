package top.xinball.constant;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Constant {
    public static String page404="404.jsp";
    public static String page500="500.jsp";
    public static String pageindex="index.jsp";
    public static String pagesend="send";
    public static String pagereceive="receive";
    public static String message="";
    public static String pagemessage="message.jsp";
    public static String pageupload="upload";
    public static String filedir="files/";
    public static String imgdir=filedir+"img/";
    public static String SavePath="";
    public static String[] sexStr={"男","女","未知"};

    public static String allowedFilesList="jpg,JPG,jpeg,JPEG,bmp,BMP,png,PNG";
    public static String deniedFilesList="exe,EXE,bat,BAT,jsp,JSP,html,HTML,htm,HTM";

    public static DateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    public static String ulogin="ulogin.jsp";
    public static String uregister="uregister.jsp";
    public static String umanager="umanager.jsp";
    public static String uprofile="uprofile.jsp";

    public static String blogin="blogin.jsp";
    public static String bregister="bregister.jsp";
    public static String bmanager="bmanager.jsp";
    public static String bprofile="bprofile.jsp";

    public static String registersuccess="register_success.jsp";
    public static String goods="goods.jsp";
    public static String bhome="bhome.jsp";
    public static String order="order.jsp";


    public static String uid="随机编号";
    public static String uname="用户名";
    public static String uemail="电子邮箱";
    public static String utel="用户手机号码";
    public static String upwd="用户密码";
    public static String ubalance="用户余额";
    public static String unick="昵称";
    public static String usex="性别";
    public static String urealname="真实姓名";
    public static String uslogan="个性签名";
    public static String ubirthday="出生日期";
    public static String ufavor="收藏的商家";
    public static String ucard="银行卡号";
    public static String uaddress="配送地址";
    public static String ulivead="住址";
    public static String uregtime="注册时间";
    public static String uregip="注册IP";

    public static int ostatus_unable=0;//0订单失效
    public static int ostatus_cart=1;//1购物车
    public static int ostatus_buy=2;//2待发货
    public static int ostatus_send=3;//3待收货
    public static int ostatus_get=4;//4待评价
    public static int ostatus_comment=5;//5已评价（只有在2和4状态可以退货

    public static int oreturn_isnot=0;//0未到退货状态
    public static int oreturn_notreturn=1;//1用户不退
    public static int oreturn_toreturn=2;////2用户选择退
    public static int oreturn_fail=3;//3商户不退，退货失败
    public static int oreturn_success=4;//4商户退，成功退货（只有在0,3状态可以退货

    public static String saveuser="yes";
    public static String savebusinese="yes";

    public static String[] uactive={"未激活","已激活"};
    public static String[] exists={"不存在","已存在"};

    public static String category1=null;
    public static String[] category2=null;
    public static String matchemail="^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
    public static String matchemail1="^(([^<>()\\\\[\\\\]\\\\\\\\.,;:\\\\s@\\\"]+(\\\\.[^<>()\\\\[\\\\]\\\\\\\\.,;:\\\\s@\\\"]+)*)|(\\\".+\\\"))@((\\\\[[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}\\\\.[0-9]{1,3}])|(([a-zA-Z\\\\-0-9]+\\\\.)+[a-zA-Z]{2,}))$";
    public static String matchtel="^((13[0-9])|(14[5,7])|(15[0-3,5-9])|(17[0,3,5-8])|(18[0-9])|166|198|199|(147))\\d{8}$";
    public static String matchname="^$/";
    public static String matchpwd="^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[\\x21-\\x2F\\x3A-\\x40\\x5B-\\x60\\x7B-\\x7E])[\\x21-\\x7E]{6,25}$";
    public static String matchbirthday="^\\d{4}-\\d{2}-\\d{1,2}$";
    public static String matchregex3="^\\[\\\".*\\\",\\\".*\\\",\\\".*\\\"\\]$";
    public static String matchregex3p="^\\[\\\".+\\\",\\\".+\\\",\\\".+\\\"\\]$";
    public static String matchtips="^.{1-200}$";
    public static String matchcomment="^.{20-200}$";
    public static Integer PAGE_SIZE=10;
}