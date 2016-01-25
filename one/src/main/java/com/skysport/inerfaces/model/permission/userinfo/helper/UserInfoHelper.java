package com.skysport.inerfaces.model.permission.userinfo.helper;

import com.skysport.core.bean.permission.UserInfo;
import com.skysport.core.constant.DictionaryKeyConstant;
import com.skysport.core.instance.DictionaryInfo;
import com.skysport.core.utils.SecurityUtil;

import java.util.List;
import java.util.Map;

/**
 * 类说明:
 * Created by zhangjh on 2015/8/21.
 */
public enum UserInfoHelper {

    SINGLETONE;

    /**
     * 将数据集中的id转换成name
     *
     * @param infos
     */
    public void turnSomeIdToNameInList(List<UserInfo> infos) {
        if (null != infos && !infos.isEmpty()) {
            Map<String, String> userTypeMap = DictionaryInfo.SINGLETONE.getValueMapByTypeKey(DictionaryKeyConstant.USER_TYPE);
            Map<String, String> lockflagMap = DictionaryInfo.SINGLETONE.getValueMapByTypeKey(DictionaryKeyConstant.LOCK_FLAG);
            Map<String, String> isonlinePMap = DictionaryInfo.SINGLETONE.getValueMapByTypeKey(DictionaryKeyConstant.IS_ONLINE);
            Map<String, String> islimitMap = DictionaryInfo.SINGLETONE.getValueMapByTypeKey(DictionaryKeyConstant.IS_LIMIT);

            for (UserInfo userInfo : infos) {

                String lockFlag = userInfo.getLockFlag();
                String isOnline = userInfo.getIsOnline();
                String isLimit = userInfo.getIsLimit();
                String usertype = userInfo.getUserType();

                lockFlag = lockflagMap.get(lockFlag);
                isOnline = isonlinePMap.get(isOnline);
                isLimit = islimitMap.get(isLimit);
                usertype = userTypeMap.get(usertype);

                userInfo.setLockFlag(lockFlag);
                userInfo.setIsLimit(isLimit);
                userInfo.setIsOnline(isOnline);
                userInfo.setUserType(usertype);
            }


        }
    }

    /**
     * 加密用户密码
     *
     * @param userinfoInfo
     */
    public void encrptPwd(UserInfo userinfoInfo) throws Exception {
        String pwd = userinfoInfo.getPassword();
        userinfoInfo.setPassword(SecurityUtil.encrypt(pwd));
    }

}
