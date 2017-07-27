<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<tr>
    <td><input class="startDate" type="date""></td>
    <td><input class="endDate" type="date""></td>
    <td>
        <select class="selFormat">
        <#list formatTypeList as formatType>
            <option data-val="${formatType.id}">${formatType.name}</option>
        </#list>
        </select>
    </td>
    <td>
        <select class="selTg">
            <option>Майонез</option>
            <option>Кетчуп</option>
            <option>Соус</option>
        </select>
    </td>
    <td><input type="text" class="skuCount" value="1"></td>
    <td><input type="text" class="comment"></td>
    <td class="removeSection">Удалить</td>
</tr>