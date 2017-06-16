<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#include "header.ftl"/>

<div class="report_title">Загрузка данных</div>
<div id="upload_container">
    <form id="upload_file_form" action="upload" method="post" enctype="multipart/form-data">
        <input type="file" id="file" name="file"><br><br>
        <span id="upload-error" class="error">${uploadError!""}</span>
        <input type="submit" id="upload-button" value="Отправить">
    </form>
    <div id="upload_info_block">
        ${resultOfUpload!""}
    </div>
    <input type="button" id="clear_upload_info_block" onclick="clearUploadInfoBlock()" value="Очистить результаты">
</div>

<#include "footer.ftl">