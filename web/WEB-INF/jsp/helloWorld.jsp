<%--
  Created by IntelliJ IDEA.
  User: fifi
  Date: 2018/7/20
  Time: 下午11:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script
            src="http://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>

    <script src="https://cdn.bootcss.com/require.js/2.3.6/require.js"></script>

</head>
<body>
<h1>Hello springMVC!</h1>
<form action="/login" method="post">
    <label>用户名111：<input type="text" name="username"/></label>
    <label>密码：<input type="text" name="password" /></label>
    <input type="submit" value="提交"/>

    <br>
    <hr>
    <div>
        <label>动态添加爱好</label>
        <ul id="hobby_ul"></ul>
    </div>
    <input type="text" name="hobby" id="hobby"/>
    <input type="button" value="Add" onclick="addMember()"/>
</form>
</body>

<script>
    function addMember() {

        $('#hobby_ul').append($('<li></li>')
            .append($("<input type='hidden' name='hobbies' value='"+$('#hobby').val()+"'/>"))
            .append($('#hobby').val()));
    }


</script>
</html>
