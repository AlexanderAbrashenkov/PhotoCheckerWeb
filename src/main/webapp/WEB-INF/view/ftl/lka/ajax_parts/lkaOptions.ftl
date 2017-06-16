<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете сеть -- </option>
<#list lkaList as lka>
    <option data-value="${lka.id?string["0"]}">${lka.name} (${lka.id?string["0"]})</option>
</#list>