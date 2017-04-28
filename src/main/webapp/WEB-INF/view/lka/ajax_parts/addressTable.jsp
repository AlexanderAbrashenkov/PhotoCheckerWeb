<%--
  Created by IntelliJ IDEA.
  User: market6
  Date: 30.03.2017
  Time: 8:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<jsp:useBean id="clientsList" type="java.util.List<com.photochecker.model.ClientCard>" scope="request" />

<tr class="addrTitle">
    <th>Наименование</th>
    <th>Адрес</th>
    <th class="hidden">ID</th>
    <th class="hidden">Type</th>
</tr>
<c:forEach items="${clientsList}" var="client">
    <tr class="addr <c:if test="${client.checked}">addressChecked</c:if>">
        <td>${client.clientName}</td>
        <td>${client.clientAddress}</td>
        <td class="hidden">${client.clientId}</td>
        <td class="hidden">${client.clientType}</td>
    </tr>
</c:forEach>