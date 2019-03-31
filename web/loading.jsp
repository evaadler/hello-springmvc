<%--
  Created by IntelliJ IDEA.
  User: fifi
  Date: 2018/10/22
  Time: 2:42 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>进度条</title>
    <script
            src="http://code.jquery.com/jquery-3.3.1.min.js"
            integrity="sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8="
            crossorigin="anonymous"></script>
</head>
<style>
    .loading{
        width: 100%; height: 100%; position:fixed; z-index: 100; top:0; left: 0; background: lightgreen; overflow: auto;
    }
</style>
<body style="overflow: auto">

<div class="loading">
    <label></label>
</div>

</body>
<script>
    $(function () {
        $("label").text(window.innerHeight);

        $(".loading").animate({"top": 50}, function () {
            document.body.offsetHeight += 50;
            $(this).css("height", 2000);
        });
    })

    // 页面加载状态改变时的事件
    document.onreadystatechange = function (ev) {
        if(document.readyState == 'complete'){
            //$(".loading").fadeOut();
        }
    }
</script>
</html>
