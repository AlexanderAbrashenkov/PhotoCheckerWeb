<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#list photoList as photo>
    <div class="photoBlock <#if photo.checked>photoChecked</#if>">
        <img data-num="${photo?counter}" src="${photo.url}"><br>
        <span class="photoDate">Дата: ${photo.formattedDate}</span><br>
        <span class="addDate">Дата добавления: ${photo.formattedDateAdd}</span><br>
        <textarea>
            <#if photo.tagId gt 0>
                &lt;${photo.tagName}&gt;&nbsp;
            </#if>
            ${photo.comment}
        </textarea>
    </div>
</#list>