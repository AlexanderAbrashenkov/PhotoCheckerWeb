<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<#list photoList as photo>
    <div class="photoBlock">
        <img data-num="${photo?counter}" src="${photo.url}" alt="Фото не загружается. Авторизуйтесь на сайте НСТ." title="Фото не загружается. Авторизуйтесь на сайте НСТ."><br>
        <span class="photoDate">Дата: ${photo.formattedDate}</span><br>
    </div>
</#list>