<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете сеть -- </option>
<#list nkaTypeList as nkaType>
    <option data-value="${nkaType.id?string["0"]}">${nkaType.name}</option>
</#list>