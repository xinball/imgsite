<%--
  Created by IntelliJ IDEA.
  User: XinBall
  Date: 2020/12/26
  Time: 0:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Home Page</title>
    <script type="text/javascript">
        /*function set(){
            //const data = '{"action":"send","method":"uploadmsg","uid":"211811124","message":"哈哈哈","photos":[{"url":"123"},{"url":"456"}]}';
            const data='{"action":"send","method":"reguser","name":"xinball","pwd":"123456","email":"","tel":""}';
            const sendData = document.getElementById("SendData");
            sendData.value=data;
        }*/
    </script>
</head>
<body>
<form action="index" method="post" enctype="multipart/form-data">
    <input id="SendData" name="SendData" value='{"action":"send","method":"reguser","name":"xinball","pwd":"123456","email":"","tel":""}'>
    上传用户：
    <label><input type="text" name="username"></label><br/>
    上传文件1：<input type="file" name="file1"><br/>
    上传文件2：<input type="file" name="file2"><br/>
    <input type="submit" value="提交">
</form>
</body>
</html>
