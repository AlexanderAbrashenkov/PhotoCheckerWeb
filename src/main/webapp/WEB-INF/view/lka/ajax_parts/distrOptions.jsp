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

<option disabled selected data-value="nothing"> -- выберете дистрибьютора -- </option>
<c:forEach var="distr" items="${distrMap}">
    <option data-value="${distr.key}">${distr.value}</option>
</c:forEach>