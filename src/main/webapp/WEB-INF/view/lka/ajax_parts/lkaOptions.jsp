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

<option disabled selected data-value="nothing"> -- выберете сеть -- </option>
<c:forEach var="lka" items="${lkaMap}">
    <option data-value="${lka.key}">${lka.value} (${lka.key})</option>
</c:forEach>