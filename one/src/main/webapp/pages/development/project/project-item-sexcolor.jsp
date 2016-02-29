<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<script id="sexcolor-template" type="text/x-handlebars-template">
    {{#each sexcolor}}
    <div class="form-group" id="{{sexColorDivId}}">
        <label class="col-xs-2  control-label" for="{{sexName}}">性别属性</label>

        <div class="col-xs-3">
            <input type="hidden" id="{{sexIdChild}}" name="sexIdChild"/>
            <input type="text" id="{{sexName}}" name="sexName" readonly="readonly"    class="col-xs-10 col-sm-12"/>
        </div>

        <label class="col-xs-2  control-label" for="{{mainColorNames}}"> 色组 </label>
        <div class="col-xs-3">
            <!-- #section:plugins/input.tag-input -->
            <input type="text" class="tags col-xs-12 col-sm-12" id="{{mainColorNames}}" name="mainColorNames"
                   placeholder="输入色组 ..."/>
            <!-- /section:plugins/input.tag-input -->
        </div>
    </div>

    {{/each}}
</script>