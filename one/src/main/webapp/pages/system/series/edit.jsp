   <%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<div class="modal fade" id="myModal" role="dialog" aria-labelledby="myModalLabel"
     aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                        aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">新增/修改</h4>
            </div>
            <form id="defaultForm" method="post" class="form-horizontal" action="edit">
                <div class="modal-body">
                    <div class="form-group">
                        <label class="col-xs-3 control-label">系列名称</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="name" name="name"   placeholder="系列名称">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">所属区域</label>
                        <div class="col-xs-7">
                            <select class="js-data-example-ajax form-group col-xs-12" name="areaId" id="areaId"></select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-xs-3 control-label">备注</label>
                        <div class="col-xs-7">
                            <input type="text" class="form-control" id="remark" name="remark"  placeholder="备注"/>
                        </div>
                    </div>

                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-info"   id="save">保存</button>
                    <button type="button" class="btn btn-info" id="resetBtn">重置</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script type="text/javascript" >
    var formatRepo =function (repo){
        if (repo.loading) return repo.text;
        var markup =  repo.name +'-'+repo.natrualkey;
        return markup;
    }

    var formatRepoSelection =function (repo){
        return     repo.text||repo.id;
    }

    $(function () {
        $('#myModal').on('shown.bs.modal', function (e) {
            $('.js-data-example-ajax').select2({
                placeholder: '未选择区域',
                searchInputPlaceholder: '请选择区域',
                maximumSelectionLength: 1,
                minimumInputLength: 1,
                allowClear: true,
                ajax: {
                    url: '<%=path%>/system/area/select',
                    method: 'GET',
                    dataType: 'json',
                    quietMillis: 500,
                    data: function(params) {
                        return {
                            name: params.term,
                            limit: 10
                        }
                    },
                    processResults: function(_data) {
                        var data = _data.items;
                        $.each(data, function(index, value) {
                            value.id = value.natrualkey;
                            value.text = value.name;
                        });
                        return {
                            results: data
                        };
                    },
                    cache: true
                },
                formatRepo: formatRepo,
                templateSelection: formatRepoSelection,
                escapeMarkup: function(m) {return m;}
            });
        });
    })
</script>