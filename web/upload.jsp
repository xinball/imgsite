<%--
  Created by IntelliJ IDEA.
  User: XinBall
  Date: 2020/12/26
  Time: 8:06
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Upload</title>
</head>

<body>
<form action="upload" id="uploadform" enctype="multipart/form-data" method="post">
    <c:if test="${not empty requestScope.files}">
        <script>
            const JSONStr='${requestScope.files}';
            if(JSONStr!==""){
                const files=JSON.parse(JSONStr);
                for(let file in files){
                    document.write('<input type="file" name="file'+file+'" value="'+files[file].url+'">');
                }
            }
        </script>
    </c:if>
    <input type="submit" value="提交">
</form>
</body>
<script language="javascript">
    window.onload=function(){
        //window.setTimeout("uploadform.submit()",1000);
    }
</script>
</html>
