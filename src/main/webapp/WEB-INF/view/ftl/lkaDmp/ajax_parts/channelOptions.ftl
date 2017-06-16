<#ftl encoding="UTF-8"/>
<#import "/spring.ftl" as spring/>

<option disabled selected data-value="nothing"> -- выберете канал -- </option>
<#list channelList as channel>
    <option data-value="${channel.id?string["0"]}">${channel.name}</option>
</#list>