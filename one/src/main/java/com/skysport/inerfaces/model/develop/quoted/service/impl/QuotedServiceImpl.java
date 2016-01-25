package com.skysport.inerfaces.model.develop.quoted.service.impl;

import com.skysport.core.constant.CharConstant;
import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.utils.UpDownUtils;
import com.skysport.inerfaces.bean.develop.FabricsInfo;
import com.skysport.inerfaces.bean.develop.QuotedInfo;
import com.skysport.inerfaces.constant.WebConstants;
import com.skysport.inerfaces.mapper.develop.QuotedInfoMapper;
import com.skysport.core.model.common.impl.CommonServiceImpl;
import com.skysport.inerfaces.model.develop.bom.IBomManageService;
import com.skysport.inerfaces.model.develop.bom.helper.BomManageHelper;
import com.skysport.inerfaces.model.develop.fabric.IFabricsService;
import com.skysport.inerfaces.model.develop.quoted.helper.QuotedServiceHelper;
import com.skysport.inerfaces.model.develop.quoted.service.IQuotedService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
            //计算报价:欧元价+包装费+C%
            //%C= (欧元价+包装费)*0.05
            //所以报价=(欧元价+包装费)*1.05
            if (quotedInfo.getLpPrice() == null) {
                quotedInfo.setLpPrice(new BigDecimal(0));
            }

            BigDecimal commission = (quotedInfo.getEuroPrice().add(quotedInfo.getLpPrice())).multiply(new BigDecimal(0.05));
            BigDecimal quotedPrice = quotedInfo.getEuroPrice().add(quotedInfo.getLpPrice()).add(commission);

            quotedInfo.setCommission(new BigDecimal(df.format(commission)));
            quotedInfo.setQuotedPrice(new BigDecimal(df.format(quotedPrice)));

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
    public void download(HttpServletRequest request, HttpServletResponse response, String natrualkeys) throws IOException {

        LocalDate today = LocalDate.now();
        int year = today.getYear();
        List<String> itemIds = Arrays.asList(natrualkeys.split(","));
        List<QuotedInfo> quotedInfos = quotedInfoMapper.queryListByProjectItemIds(itemIds);

        //所有bomid
        List<String> bomIds = bomManageService.queryBomIds(itemIds);
        List<FabricsInfo> fabricsInfosAll = new ArrayList<>();
        if (!bomIds.isEmpty()) {
            for (String bomId : bomIds) {
                //所有面料
                List<FabricsInfo> fabricsInfos = fabricsManageService.queryAllFabricByBomId(bomId);
                BomManageHelper.translateIdToNameInFabrics(fabricsInfos, WebConstants.FABRIC_ID_EXCHANGE_QUOTED);//将id转成name
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


        String ctxPath = new StringBuilder().append(DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.BASE_PATH)).append(WebConstants.FILE_SEPRITER).append(year).append(WebConstants.FILE_SEPRITER)
                .append(DictionaryInfo.SINGLETONE.getDictionaryValue(WebConstants.FILE_PATH, WebConstants.DEVELOP_PATH)).toString();


        String fileName = itemIds.get(0) + "-报价表.xls";

        //完整文件路径
        String downLoadPath = ctxPath + File.separator + fileName;
        QuotedServiceHelper.createFile(fileName, ctxPath, WebConstants.BOM_QUOTED_TITILE, quotedInfos);
        UpDownUtils.download(request, response, fileName, downLoadPath);

    }
}
