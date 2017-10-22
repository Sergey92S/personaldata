<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><tiles:insertAttribute name="title" ignore="true"></tiles:insertAttribute></title>
  <!-- Bootstrap -->
  <link href="<c:url value="/assest/css/bootstrap.min.css" />" rel="stylesheet">
  <link href="<c:url value="/assest/css/bootstrap-theme.min.css" />" rel="stylesheet">
</head>
<body style="background-color: #FFF">
<div class="page">
  <tiles:insertAttribute name="header"/>
  <div class="content">
    <div id="body">
      <tiles:insertAttribute name="body"/>
    </div>
  </div>
  <tiles:insertAttribute name="footer"/>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<c:url value="/assest/js/jquery-1.11.3.min.js" />"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="<c:url value="/assest/js/bootstrap.min.js" />"></script>
</body>
</html>