<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 29.03.2017
  Time: 8:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<jsp:useBean id="mlkaList" type="java.util.List<com.photochecker.model.mlka.Employee>" scope="request"></jsp:useBean>

<option disabled selected data-value="nothing"> -- выберете сотрудника -- </option>
<c:forEach var="mlka" items="${mlkaList}">
    <option data-value="${mlka.id}">${mlka.name}</option>
</c:forEach>