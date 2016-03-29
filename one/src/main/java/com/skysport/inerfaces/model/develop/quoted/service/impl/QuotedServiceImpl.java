package com.skysport.inerfaces.model.develop.quoted.service.impl;

import com.skysport.core.cache.DictionaryInfoCachedMap;
import com.skysport.core.constant.CharConstant;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.core.utils.DateUtils;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.develop.BomInfo;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.QuotedInfoMapper;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.quoted.helper.QuotedServiceHelper;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.*;

/**
 * 说明:
 * Created by zhangjh on 2015/9/17.
 */
@Service("quotedService")
public class QuotedServiceImpl extends CommonServiceImpl<QuotedInfo> implements IQuotedService, InitializingBean {

    @Resource(name = "quotedInfoMapper")
    private QuotedInfoMapper quotedInfoMapper;

    @Resource(name = "bomManageService")
    private IBomManageService bomManageService;

    @Resource(name = "fabricsManageService")
    private IFabricsService fabricsManageService;

    @Override
    public void afterPropertiesSet() throws Exception {
        commonDao = quotedInfoMapper;
    }


    /**
     * 更新或新增报价信息
     *
     * @param quotedInfo QuotedInfo
     */
    @Override
    public void updateOrAdd(QuotedInfo quotedInfo) {

        DecimalFormat df = new DecimalFormat("0.0000");

        //计算报价:欧元价+包装费+C% .Remark:150527 2015年度新开发项目 - 价格分析.xlsx
        //%C= (欧元价+包装费) * 0.05 （0.05是佣金）
        //所以报价=(欧元价+包装费) * 1.05
        if (quotedInfo.getLpPrice() == null) {
            quotedInfo.setLpPrice(new BigDecimal(0));
        }

        Float commissionValue = Float.parseFloat(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FINANCE_CONFIG, WebConstants.COMMISSION_RATE, String.valueOf(WebConstants.COMMISSION_RATE_DEFAULTVALUE)));
        BigDecimal commission, quotedPrice;
        if (null != quotedInfo.getEuroPrice() && null != quotedInfo.getLpPrice()) {
            commission = (quotedInfo.getEuroPrice().add(quotedInfo.getLpPrice())).multiply(new BigDecimal(commissionValue));
            quotedPrice = quotedInfo.getEuroPrice().add(quotedInfo.getLpPrice()).add(commission);
            quotedInfo.setCommission(new BigDecimal(df.format(commission)));
            quotedInfo.setQuotedPrice(new BigDecimal(df.format(quotedPrice)));
        }

        //查询BOM是否有对应的报价表
        QuotedInfo quotedInfo1 = queryInfoByNatrualKey(quotedInfo.getBomId());
        if (null == quotedInfo1) {
            if (null != quotedInfo.getEuroPrice() && null != quotedInfo.getFactoryOffer()) {
                //查询项目和子项目id
                QuotedInfo quotedInfo2 = quotedInfoMapper.queryIds(quotedInfo.getBomId());
                quotedInfo.setProjectId(quotedInfo2.getProjectId());
                quotedInfo.setProjectItemId(quotedInfo2.getProjectItemId());
                quotedInfoMapper.add(quotedInfo);
            }
        } else {
            quotedInfo.setProjectId(quotedInfo1.getProjectId());
            quotedInfo.setProjectItemId(quotedInfo1.getProjectItemId());
            quotedInfoMapper.updateInfo(quotedInfo);


        }
    }

    /**
     * 下载文件
     *
     * @param request     HttpServletRequest
     * @param response    HttpServletResponse
     * @param natrualkeys String
     * @throws IOException
     */
    @Override
    public void downloadOffer(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException {

        String year = DateUtils.SINGLETONE.getYyyy();
        List<String> itemIds = Arrays.asList(natrualkeys.split(CharConstant.COMMA));
        List<QuotedInfo> quotedInfos = quotedInfoMapper.queryListByProjectItemIds(itemIds);

        StringBuilder bomQuoteName = new StringBuilder();
        bomQuoteName.append(DateUtils.SINGLETONE.getYyyyMmdd());
        bomQuoteName.append(CharConstant.HORIZONTAL_LINE).append(WebConstants.BOM_QUOTE_CN_NAME);

        Set<String> seriesNameSet = new HashSet<String>();
        Set<String> bomNameSet = new HashSet<>();


        //所有bomid
//        List<String> bomIds = bomManageService.queryBomIds(itemIds);
        List<BomInfo> bomInfos = bomManageService.queryBomInfosByProjectItemIds(itemIds);
        List<FabricsInfo> fabricsInfosAll = new ArrayList<>();
        if (!bomInfos.isEmpty()) {
            for (BomInfo bomInfo : bomInfos) {


                String bomId = bomInfo.getNatrualkey();

                String seriesName = bomInfo.getSeriesName();
                seriesNameSet.add(CharConstant.HORIZONTAL_LINE + seriesName);//去重复
                String bomName = bomInfo.getName();

                if (bomNameSet.isEmpty()) {
                    bomNameSet.add(bomName);
                } else if (bomNameSet.size() < 3) {
                    bomNameSet.add(WebConstants.AND + bomName);
                }

                //所有面料
                List<FabricsInfo> fabricsInfos = fabricsManageService.queryAllFabricByBomId(bomId);
                BomManageHelper.translateIdToNameInFabrics(fabricsInfos, seriesName, WebConstants.FABRIC_ID_EXCHANGE_QUOTED);//将id转成name
                fabricsInfosAll.addAll(fabricsInfos);
            }
        }


        //设置报价中面料的信息
        String bomId = CharConstant.EMPTY;
        for (QuotedInfo quotedInfo : quotedInfos) {
            if (fabricsInfosAll != null && !fabricsInfosAll.isEmpty()) {
                for (FabricsInfo fabricsInfo : fabricsInfosAll) {
                    if (fabricsInfo.getBomId().equals(quotedInfo.getBomId()) && !bomId.equals(fabricsInfo.getBomId())) {
                        if (fabricsInfo.getIsShow() == WebConstants.IS_SHOW_FABRIC) { //过滤出BOM中属性为“显示面料”的一个面料
                            bomId = fabricsInfo.getBomId();
                            quotedInfo.setMainFabricDescs(fabricsInfo.getDescription());
                            continue;
                        }
                    }

                }
            }
        }


        String ctxPath = new StringBuilder().append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                .append(DictionaryInfoCachedMap.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();

        bomQuoteName.append(StringUtils.join(seriesNameSet.toArray(), ""));
        bomQuoteName.append(StringUtils.join(bomNameSet.toArray(), ""));
        bomQuoteName.append(WebConstants.SUFFIX_EXCEL_XLS);

        String fileName = bomQuoteName.toString();

        //完整文件路径
        String downLoadPath = ctxPath + File.separator + fileName;
        //创建文件
        QuotedServiceHelper.createFile(fileName, ctxPath, WebConstants.BOM_QUOTED_TITILE, quotedInfos);
        //下载文件
        UpDownUtils.download(request, response, fileName, downLoadPath);

    }
}
