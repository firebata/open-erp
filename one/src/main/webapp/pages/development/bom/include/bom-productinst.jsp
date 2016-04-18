<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<div id="productionInstDesc">
    <div id="productionInstDescTitle">
        <h5 class="header smaller lighter blue">
            生产指示单
            <small></small>
            <input type="hidden" name="productionInstructionId" id="productionInstructionId"/>
        </h5>
    </div>
    <div id="productionInstDescDetail">
        <div class="form-group">
            <label class="control-label  col-xs-2" for="cropRequirements"> 裁剪要求 </label>
            <div class="col-xs-3">
                        <textarea id="cropRequirements" name="cropRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="裁剪要求"></textarea>
            </div>

            <label class="col-xs-2  control-label" for="qualityRequirements"> 工艺及质量要求</label>
            <div class="col-xs-3">
                        <textarea id="qualityRequirements" name="qualityRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="工艺及质量要求"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label  col-xs-2" for="finishPressingRequirements"> 整烫要求 </label>
            <div class="col-xs-3">
                        <textarea id="finishPressingRequirements" name="finishPressingRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="整烫要求"></textarea>
            </div>

            <label class="col-xs-2  control-label" for="spcialTech"> 特殊工艺</label>
            <div class="col-xs-3">
                        <textarea id="spcialTech" name="spcialTech"  class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="特殊工艺"></textarea>
            </div>
        </div>
        <div class="form-group">
            <label class="control-label  col-xs-2" for="packingRequirements"> 包装要求 </label>
            <div class="col-xs-3">
                        <textarea id="packingRequirements" name="packingRequirements" class="autosize-transition form-control col-xs-10 col-sm-12" placeholder="包装要求"></textarea>
            </div>

        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="overstitch">面线</label>
            <div class="col-xs-3">
                <input type="text" id="overstitch" name="overstitch" placeholder="面线" class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="overstitchSpace">面线针距</label>
            <div class="col-xs-3">
                <input type="text" id="overstitchSpace" name="overstitchSpace" placeholder="面线针距" class="col-xs-10 col-sm-12"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="blindstitch">暗线</label>
            <div class="col-xs-3">
                <input type="text" id="blindstitch" name="blindstitch" placeholder="暗线"  class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="blindstitchSpace">暗线针距</label>
            <div class="col-xs-3">
                <input type="text" id="blindstitchSpace" name="blindstitchSpace" placeholder="暗线针距" class="col-xs-10 col-sm-12"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="overlock">拷边</label>
            <div class="col-xs-3">
                <input type="text" id="overlock" name="overlock" placeholder="拷边" class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="overlockSpace">拷边针距</label>
            <div class="col-xs-3">
                <input type="text" id="overlockSpace" name="overlockSpace" placeholder="拷边针距" class="col-xs-10 col-sm-12"/>
            </div>

        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="trademarkCode">商标编码</label>
            <div class="col-xs-3">
                <input type="text" id="trademarkCode" name="trademarkCode" placeholder="商标编码" class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="trademarkRemark">商标描述</label>
            <div class="col-xs-3">
                <input type="text" id="trademarkRemark" name="trademarkRemark" placeholder="商标描述" class="col-xs-10 col-sm-12"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="scaleCode">尺标编码</label>
            <div class="col-xs-3">
                <input type="text" id="scaleCode" name="scaleCode" placeholder="尺标编码" class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="scaleRemark">尺标描述</label>
            <div class="col-xs-3">
                <input type="text" id="scaleRemark" name="scaleRemark" placeholder="尺标描述" class="col-xs-10 col-sm-12"/>
            </div>

        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="rinsingMarksCode">水洗标编码</label>
            <div class="col-xs-3">
                <input type="text" id="rinsingMarksCode" name="rinsingMarksCode" placeholder="水洗标编码" class="col-xs-10 col-sm-12"/>
            </div>

            <label class="control-label  col-xs-2" for="rinsingMarksRemark">水洗标描述</label>
            <div class="col-xs-3">
                <input type="text" id="rinsingMarksRemark" name="rinsingMarksRemark" placeholder="水洗标描述" class="col-xs-10 col-sm-12"/>
            </div>
        </div>

        <div class="form-group">
            <label class="control-label  col-xs-2" for="sketchUrlUid"> 款式图 </label>
            <div class="col-xs-3">
                <input id="sketchUrlUid" type="file" multiple class="file-loading col-xs-12" name="fileLocation">
            </div>
            <label class="col-xs-2  control-label" for="specificationUrlUid"> 规格表</label>
            <div class="col-xs-3">
                <input id="specificationUrlUid" type="file" multiple class="file-loading col-xs-12" name="fileLocation">
            </div>
        </div>
    </div>
</div>
