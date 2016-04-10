<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="bomBtnInfo">

    <h5 class="header smaller lighter blue">
        选择你的操作
    </h5>

    <div id="handleItemInfo">
        <div class="bom-info">
            <div class="bom-info form-group" style="margin: 0 auto;">
                <div class="col-xs-offset-2 col-xs-2">
                    <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomAutoBackup()"
                            id="autoBackupId">暂存
                    </button>
                </div>
                <div class="col-xs-2">
                    <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomSave()"
                            id="saveBtnId">保存
                    </button>
                </div>
                <div class="col-xs-2">
                    <button type="button" class="btn btn-info btn-md" onclick="javascript:$.bomSubmit()"
                            id="commitBtnId">提交
                    </button>
                </div>
            </div>
        </div>


    </div>


</div>