<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете сотрудника -- </option>
<#list mlkaList as mlka>
    <option data-value="${mlka.id?string["0"]}">${mlka.name}</option>
</#list>