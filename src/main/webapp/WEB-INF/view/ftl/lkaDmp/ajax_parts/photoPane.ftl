<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#list photoList as photo>
    <div class="photoBlock <#if photo.checked>photoChecked</#if>">
        <img data-num="${photo?counter}" src="${photo.url}"><br>
        <span class="photoDate">Дата: ${photo.date}</span><br>
        <span class="addDate">Дата добавления: ${photo.dateAdd}</span><br>
        <textarea>${photo.comment}</textarea>
    </div>
</#list>