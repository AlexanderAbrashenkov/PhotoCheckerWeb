<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<tr class="addrTitle">
    <th>Наименование</th>
    <th>Адрес</th>
    <th class="hidden">ID</th>
    <th class="hidden">Type</th>
</tr>
<#list clientsList as client>
    <tr class="addr <#if client.checked == 1>addressChecked</#if>">
        <td>${client.clientName}</td>
        <td>${client.clientAddress}</td>
        <td class="hidden">${client.clientId?string["0"]}</td>
        <td class="hidden">${client.formatType.name}</td>
        <td class="hidden">${client.formatType.id?string["0"]}</td>
        <td class="hidden">${client.checked?string["0"]}</td>
    </tr>
</#list>