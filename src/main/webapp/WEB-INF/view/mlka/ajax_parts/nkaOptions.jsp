<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 29.03.2017
  Time: 7:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="nkaTypeList" type="java.util.List<com.photochecker.model.mlka.NkaType>" scope="request"></jsp:useBean>

<option disabled selected data-value="nothing"> -- выберете сеть -- </option>
<c:forEach var="nkaType" items="${nkaTypeList}">
    <option data-value="${nkaType.id}">${nkaType.name}</option>
</c:forEach>