package com.skysport.inerfaces.bean.develop;
/******************************************************************
 * * 类    名：KfProductionInstructionEntity
 * * 描    述：生产指示单
 * * 创 建 者：zhangjh
 * * 创建时间：2016-03-22 14:37:08
 ******************************************************************/

import com.skysport.core.bean.system.SelectItem;
import com.skysport.inerfaces.bean.common.UploadFileInfo;

import java.util.List;
import java.util.Map;

/**
 * 生产指示单(T_KF_PRODUCTION_INSTRUCTION)
 *
 * @author bianj
 * @version 1.0.0 2016-03-22
 */
public class KfProductionInstructionEntity extends SelectItem {
    /**
     * 版本号
     */
    private static final long serialVersionUID = -5159272192937975534L;

    /**  */
    private String id;

    /**
     * 指示单uid
     */
    private String uid;

    /**
     * 指示单uid
     */
    private String piNo;

    private String seriesName;

    private String customerName;
    /**
     * 工厂报价id
     */
    private String factoryQuoteId;

    /**
     * 指示单uid
     */
    private String productionInstructionId;

    /**  */
    private String bomId;

    /**
     * 成衣厂id
     */
    private String spId;

    private String spName;


    /**  */
    private String projectItemName;

    /**
     * bom名
     */
    private String bomName;

    /**  */
    private String colorName;

    /**
     * 订单数量
     */
    private Integer offerAmout;
    /**
     * 成衣收到时间
     */
    private String clothReceivedDate;
    /**
     * 裁剪要求
     */
    private String cropRequirements;

    /**
     * 工艺及质量要求
     */
    private String qualityRequirements;

    /**
     * 面线
     */
    private String overstitch;

    /**
     * 面线针距
     */
    private String overstitchSpace;

    /**
     * 暗线
     */
    private String blindstitch;

    /**
     * 暗线针距
     */
    private String blindstitchSpace;

    /**
     * 拷边
     */
    private String overlock;

    /**
     * 拷边针距
     */
    private String overlockSpace;

    /**
     * 整烫要求
     */
    private String finishPressingRequirements;

    /**
     * 特殊工艺
     */
    private String spcialTech;

    /**
     * 包装要求
     */
    private String packingRequirements;

    /**
     * 商标编码
     */
    private String trademarkCode;

    /**
     * 商标描述
     */
    private String trademarkRemark;

    /**
     * 尺标编码
     */
    private String scaleCode;

    /**
     * 尺标描述
     */
    private String scaleRemark;

    /**
     * 水洗标编码
     */
    private String rinsingMarksCode;

    /**
     * 水洗标描述
     */
    private String rinsingMarksRemark;

    /**
     * 款式图
     */
    private String sketchUrlUid;

    /**
     * 规格表
     */
    private String specificationUrlUid;

    /**
     * 删除标志
     */
    private int delFlag;

    /**
     * 备注
     */
    private String remark;

    /**  */
    private String updateTime;
    /**
     * 下单日期（导出时间）
     */
    private String exportDate;

    //面料集合
    private List<FabricsInfo> fabrics;

    //辅料集合
    private List<AccessoriesInfo> accessories;

    //包材
    private List<PackagingInfo> packagings;

    private FactoryQuoteInfo factoryQuoteInfo;

    private List<UploadFileInfo> sketchUrlUidUploadFileInfos;

    private List<UploadFileInfo> specificationUrlUidUploadFileInfos;

    /**
     * 存放初始化文件上传控件中的信息
     */
    private Map<String, Object> sketchUrlUidFileinfosMap;

    /**
     * 存放初始化文件上传控件中的信息
     */
    private Map<String, Object> specificationUrlUidFileinfosMap;
    private String approveStatus;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getClothReceivedDate() {
        return clothReceivedDate;
    }

    public void setClothReceivedDate(String clothReceivedDate) {
        this.clothReceivedDate = clothReceivedDate;
    }


    /**
     * 获取
     *
     * @return
     */
    public String getId() {
        return this.id;
    }

    /**
     * 设置
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取指示单uid
     *
     * @return 指示单uid
     */
    public String getUid() {
        return this.uid;
    }

    /**
     * 设置指示单uid
     *
     * @param uid 指示单uid
     */
    public void setUid(String uid) {
        this.uid = uid;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getBomId() {
        return this.bomId;
    }

    /**
     * 设置
     *
     * @param bomId
     */
    public void setBomId(String bomId) {
        this.bomId = bomId;
    }

    /**
     * 获取成衣厂id
     *
     * @return 成衣厂id
     */
    public String getSpId() {
        return this.spId;
    }

    /**
     * 设置成衣厂id
     *
     * @param spId 成衣厂id
     */
    public void setSpId(String spId) {
        this.spId = spId;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getProjectItemName() {
        return this.projectItemName;
    }

    /**
     * 设置
     *
     * @param projectItemName
     */
    public void setProjectItemName(String projectItemName) {
        this.projectItemName = projectItemName;
    }

    /**
     * 获取bom名
     *
     * @return bom名
     */
    public String getBomName() {
        return this.bomName;
    }

    /**
     * 设置bom名
     *
     * @param bomName bom名
     */
    public void setBomName(String bomName) {
        this.bomName = bomName;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getColorName() {
        return this.colorName;
    }

    /**
     * 设置
     *
     * @param colorName
     */
    public void setColorName(String colorName) {
        this.colorName = colorName;
    }

    /**
     * 获取订单数量
     *
     * @return 订单数量
     */
    public Integer getOfferAmout() {
        return this.offerAmout;
    }

    /**
     * 设置订单数量
     *
     * @param offerAmout 订单数量
     */
    public void setOfferAmout(Integer offerAmout) {
        this.offerAmout = offerAmout;
    }

    /**
     * 获取裁剪要求
     *
     * @return 裁剪要求
     */
    public String getCropRequirements() {
        return this.cropRequirements;
    }

    /**
     * 设置裁剪要求
     *
     * @param cropRequirements 裁剪要求
     */
    public void setCropRequirements(String cropRequirements) {
        this.cropRequirements = cropRequirements;
    }

    /**
     * 获取工艺及质量要求
     *
     * @return 工艺及质量要求
     */
    public String getQualityRequirements() {
        return this.qualityRequirements;
    }

    /**
     * 设置工艺及质量要求
     *
     * @param qualityRequirements 工艺及质量要求
     */
    public void setQualityRequirements(String qualityRequirements) {
        this.qualityRequirements = qualityRequirements;
    }

    /**
     * 获取面线
     *
     * @return 面线
     */
    public String getOverstitch() {
        return this.overstitch;
    }

    /**
     * 设置面线
     *
     * @param overstitch 面线
     */
    public void setOverstitch(String overstitch) {
        this.overstitch = overstitch;
    }

    /**
     * 获取面线针距
     *
     * @return 面线针距
     */
    public String getOverstitchSpace() {
        return this.overstitchSpace;
    }

    /**
     * 设置面线针距
     *
     * @param overstitchSpace 面线针距
     */
    public void setOverstitchSpace(String overstitchSpace) {
        this.overstitchSpace = overstitchSpace;
    }

    /**
     * 获取暗线
     *
     * @return 暗线
     */
    public String getBlindstitch() {
        return this.blindstitch;
    }

    /**
     * 设置暗线
     *
     * @param blindstitch 暗线
     */
    public void setBlindstitch(String blindstitch) {
        this.blindstitch = blindstitch;
    }

    /**
     * 获取暗线针距
     *
     * @return 暗线针距
     */
    public String getBlindstitchSpace() {
        return this.blindstitchSpace;
    }

    /**
     * 设置暗线针距
     *
     * @param blindstitchSpace 暗线针距
     */
    public void setBlindstitchSpace(String blindstitchSpace) {
        this.blindstitchSpace = blindstitchSpace;
    }

    /**
     * 获取拷边
     *
     * @return 拷边
     */
    public String getOverlock() {
        return this.overlock;
    }

    /**
     * 设置拷边
     *
     * @param overlock 拷边
     */
    public void setOverlock(String overlock) {
        this.overlock = overlock;
    }

    /**
     * 获取拷边针距
     *
     * @return 拷边针距
     */
    public String getOverlockSpace() {
        return this.overlockSpace;
    }

    /**
     * 设置拷边针距
     *
     * @param overlockSpace 拷边针距
     */
    public void setOverlockSpace(String overlockSpace) {
        this.overlockSpace = overlockSpace;
    }

    /**
     * 获取整烫要求
     *
     * @return 整烫要求
     */
    public String getFinishPressingRequirements() {
        return this.finishPressingRequirements;
    }

    /**
     * 设置整烫要求
     *
     * @param finishPressingRequirements 整烫要求
     */
    public void setFinishPressingRequirements(String finishPressingRequirements) {
        this.finishPressingRequirements = finishPressingRequirements;
    }

    /**
     * 获取特殊工艺
     *
     * @return 特殊工艺
     */
    public String getSpcialTech() {
        return this.spcialTech;
    }

    /**
     * 设置特殊工艺
     *
     * @param spcialTech 特殊工艺
     */
    public void setSpcialTech(String spcialTech) {
        this.spcialTech = spcialTech;
    }

    /**
     * 获取包装要求
     *
     * @return 包装要求
     */
    public String getPackingRequirements() {
        return this.packingRequirements;
    }

    /**
     * 设置包装要求
     *
     * @param packingRequirements 包装要求
     */
    public void setPackingRequirements(String packingRequirements) {
        this.packingRequirements = packingRequirements;
    }

    /**
     * 获取商标编码
     *
     * @return 商标编码
     */
    public String getTrademarkCode() {
        return this.trademarkCode;
    }

    /**
     * 设置商标编码
     *
     * @param trademarkCode 商标编码
     */
    public void setTrademarkCode(String trademarkCode) {
        this.trademarkCode = trademarkCode;
    }

    /**
     * 获取商标描述
     *
     * @return 商标描述
     */
    public String getTrademarkRemark() {
        return this.trademarkRemark;
    }

    /**
     * 设置商标描述
     *
     * @param trademarkRemark 商标描述
     */
    public void setTrademarkRemark(String trademarkRemark) {
        this.trademarkRemark = trademarkRemark;
    }

    /**
     * 获取尺标编码
     *
     * @return 尺标编码
     */
    public String getScaleCode() {
        return this.scaleCode;
    }

    /**
     * 设置尺标编码
     *
     * @param scaleCode 尺标编码
     */
    public void setScaleCode(String scaleCode) {
        this.scaleCode = scaleCode;
    }

    /**
     * 获取尺标描述
     *
     * @return 尺标描述
     */
    public String getScaleRemark() {
        return this.scaleRemark;
    }

    /**
     * 设置尺标描述
     *
     * @param scaleRemark 尺标描述
     */
    public void setScaleRemark(String scaleRemark) {
        this.scaleRemark = scaleRemark;
    }

    /**
     * 获取水洗标编码
     *
     * @return 水洗标编码
     */
    public String getRinsingMarksCode() {
        return this.rinsingMarksCode;
    }

    /**
     * 设置水洗标编码
     *
     * @param rinsingMarksCode 水洗标编码
     */
    public void setRinsingMarksCode(String rinsingMarksCode) {
        this.rinsingMarksCode = rinsingMarksCode;
    }

    /**
     * 获取水洗标描述
     *
     * @return 水洗标描述
     */
    public String getRinsingMarksRemark() {
        return this.rinsingMarksRemark;
    }

    /**
     * 设置水洗标描述
     *
     * @param rinsingMarksRemark 水洗标描述
     */
    public void setRinsingMarksRemark(String rinsingMarksRemark) {
        this.rinsingMarksRemark = rinsingMarksRemark;
    }

    /**
     * 获取款式图
     *
     * @return 款式图
     */
    public String getSketchUrlUid() {
        return this.sketchUrlUid;
    }

    /**
     * 设置款式图
     *
     * @param sketchUrlUid 款式图
     */
    public void setSketchUrlUid(String sketchUrlUid) {
        this.sketchUrlUid = sketchUrlUid;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getSpecificationUrlUid() {
        return this.specificationUrlUid;
    }

    /**
     * 设置
     *
     * @param specificationUrlUid
     */
    public void setSpecificationUrlUid(String specificationUrlUid) {
        this.specificationUrlUid = specificationUrlUid;
    }

    /**
     * 获取删除标志
     *
     * @return 删除标志
     */
    public int getDelFlag() {
        return this.delFlag;
    }

    /**
     * 设置删除标志
     *
     * @param delFlag 删除标志
     */
    public void setDelFlag(int delFlag) {
        this.delFlag = delFlag;
    }

    /**
     * 获取备注
     *
     * @return 备注
     */
    public String getRemark() {
        return this.remark;
    }

    /**
     * 设置备注
     *
     * @param remark 备注
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     * 获取
     *
     * @return
     */
    public String getUpdateTime() {
        return this.updateTime;
    }

    /**
     * 设置
     *
     * @param updateTime
     */
    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public List<UploadFileInfo> getSketchUrlUidUploadFileInfos() {
        return sketchUrlUidUploadFileInfos;
    }

    public void setSketchUrlUidUploadFileInfos(List<UploadFileInfo> sketchUrlUidUploadFileInfos) {
        this.sketchUrlUidUploadFileInfos = sketchUrlUidUploadFileInfos;
    }

    public List<UploadFileInfo> getSpecificationUrlUidUploadFileInfos() {
        return specificationUrlUidUploadFileInfos;
    }

    public void setSpecificationUrlUidUploadFileInfos(List<UploadFileInfo> specificationUrlUidUploadFileInfos) {
        this.specificationUrlUidUploadFileInfos = specificationUrlUidUploadFileInfos;
    }

    public String getProductionInstructionId() {
        return productionInstructionId;
    }

    public void setProductionInstructionId(String productionInstructionId) {
        this.productionInstructionId = productionInstructionId;
    }

    public String getFactoryQuoteId() {
        return factoryQuoteId;
    }

    public void setFactoryQuoteId(String factoryQuoteId) {
        this.factoryQuoteId = factoryQuoteId;
    }


    public String getSpName() {
        return spName;
    }

    public void setSpName(String spName) {
        this.spName = spName;
    }

    public Map<String, Object> getSketchUrlUidFileinfosMap() {
        return sketchUrlUidFileinfosMap;
    }

    public void setSketchUrlUidFileinfosMap(Map<String, Object> sketchUrlUidFileinfosMap) {
        this.sketchUrlUidFileinfosMap = sketchUrlUidFileinfosMap;
    }

    public Map<String, Object> getSpecificationUrlUidFileinfosMap() {
        return specificationUrlUidFileinfosMap;
    }

    public void setSpecificationUrlUidFileinfosMap(Map<String, Object> specificationUrlUidFileinfosMap) {
        this.specificationUrlUidFileinfosMap = specificationUrlUidFileinfosMap;
    }


    public String getExportDate() {
        return exportDate;
    }

    public void setExportDate(String exportDate) {
        this.exportDate = exportDate;
    }

    public String getPiNo() {
        return piNo;
    }

    public void setPiNo(String piNo) {
        this.piNo = piNo;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<FabricsInfo> getFabrics() {
        return fabrics;
    }

    public void setFabrics(List<FabricsInfo> fabrics) {
        this.fabrics = fabrics;
    }

    public List<AccessoriesInfo> getAccessories() {
        return accessories;
    }

    public void setAccessories(List<AccessoriesInfo> accessories) {
        this.accessories = accessories;
    }

    public List<PackagingInfo> getPackagings() {
        return packagings;
    }

    public void setPackagings(List<PackagingInfo> packagings) {
        this.packagings = packagings;
    }

    public FactoryQuoteInfo getFactoryQuoteInfo() {
        return factoryQuoteInfo;
    }

    public void setFactoryQuoteInfo(FactoryQuoteInfo factoryQuoteInfo) {
        this.factoryQuoteInfo = factoryQuoteInfo;
    }

    public String getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(String approveStatus) {
        this.approveStatus = approveStatus;
    }

    @Override
    public String toString() {
        return "KfProductionInstructionEntity{" +
                "id=" + id +
                ", uid='" + uid + '\'' +
                ", piNo='" + piNo + '\'' +
                ", seriesName='" + seriesName + '\'' +
                ", customerName='" + customerName + '\'' +
                ", factoryQuoteId='" + factoryQuoteId + '\'' +
                ", productionInstructionId='" + productionInstructionId + '\'' +
                ", bomId='" + bomId + '\'' +
                ", spId='" + spId + '\'' +
                ", spName='" + spName + '\'' +
                ", projectItemName='" + projectItemName + '\'' +
                ", bomName='" + bomName + '\'' +
                ", colorName='" + colorName + '\'' +
                ", offerAmout=" + offerAmout +
                ", clothReceivedDate='" + clothReceivedDate + '\'' +
                ", cropRequirements='" + cropRequirements + '\'' +
                ", qualityRequirements='" + qualityRequirements + '\'' +
                ", overstitch='" + overstitch + '\'' +
                ", overstitchSpace='" + overstitchSpace + '\'' +
                ", blindstitch='" + blindstitch + '\'' +
                ", blindstitchSpace='" + blindstitchSpace + '\'' +
                ", overlock='" + overlock + '\'' +
                ", overlockSpace='" + overlockSpace + '\'' +
                ", finishPressingRequirements='" + finishPressingRequirements + '\'' +
                ", spcialTech='" + spcialTech + '\'' +
                ", packingRequirements='" + packingRequirements + '\'' +
                ", trademarkCode='" + trademarkCode + '\'' +
                ", trademarkRemark='" + trademarkRemark + '\'' +
                ", scaleCode='" + scaleCode + '\'' +
                ", scaleRemark='" + scaleRemark + '\'' +
                ", rinsingMarksCode='" + rinsingMarksCode + '\'' +
                ", rinsingMarksRemark='" + rinsingMarksRemark + '\'' +
                ", sketchUrlUid='" + sketchUrlUid + '\'' +
                ", specificationUrlUid='" + specificationUrlUid + '\'' +
                ", delFlag=" + delFlag +
                ", remark='" + remark + '\'' +
                ", updateTime=" + updateTime +
                ", exportDate='" + exportDate + '\'' +
                ", fabrics=" + fabrics +
                ", accessories=" + accessories +
                ", packagings=" + packagings +
                ", factoryQuoteInfo=" + factoryQuoteInfo +
                ", sketchUrlUidUploadFileInfos=" + sketchUrlUidUploadFileInfos +
                ", specificationUrlUidUploadFileInfos=" + specificationUrlUidUploadFileInfos +
                ", sketchUrlUidFileinfosMap=" + sketchUrlUidFileinfosMap +
                ", specificationUrlUidFileinfosMap=" + specificationUrlUidFileinfosMap +
                ", approveStatus='" + approveStatus + '\'' +
                '}';
    }
}