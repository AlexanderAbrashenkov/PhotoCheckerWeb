<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<tr class="addrTitle">
    <th>Наименование</th>
    <th class="hidden">ID</th>
</tr>
<#list clientsList as client>
    <tr class="addr <#if client.checked gte 1>addressChecked</#if>">
        <td>${client.name}</td>
        <td class="hidden">${client.id?string["0"]}</td>
    </tr>
</#list>