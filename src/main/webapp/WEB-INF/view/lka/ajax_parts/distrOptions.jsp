<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 29.03.2017
  Time: 7:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="distrList" type="java.util.List<com.photochecker.model.common.Distr>" scope="request"></jsp:useBean>

<option disabled selected data-value="nothing"> -- выберете дистрибьютора -- </option>
<c:forEach var="distr" items="${distrList}">
    <option data-value="${distr.id}">${distr.name}</option>
</c:forEach>