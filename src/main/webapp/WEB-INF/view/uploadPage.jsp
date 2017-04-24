<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ page isELIgnored="false" %>
<%@ include file="header.jsp"%>

<div class="report_title">Загрузка данных</div>
<div id="upload_container">
    <form id="upload_file_form" action="upload" method="post" enctype="multipart/form-data">
        <input type="file" id="file" name="file"><br><br>
        <span id="upload-error" class="error">${uploadError}</span>
        <input type="submit" id="upload-button" value="Отправить">
    </form>
    <div id="upload_info_block">
        ${resultOfUpload}
    </div>
</div>

<%@include file="footer.jsp"%>