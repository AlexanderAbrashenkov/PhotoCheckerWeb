<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#list photoList as photo>
    <div class="photoBlock">
        <img data-num="${photo?counter}" src="${photo.url}"><br>
        <span class="photoDate">Дата: ${photo.formattedDate}</span><br>
        <span class="addDate">Дата добавления: ${photo.formattedDateAdd}</span><br>
        <textarea>${photo.comment}</textarea>
    </div>
</#list>