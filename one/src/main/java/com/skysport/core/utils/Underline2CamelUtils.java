package com.skysport.core.utils;

/**
 * 下划线和驼峰命名方法之间的转换
 *
 * @author zhangjh
 */
public class Underline2CamelUtils {

    /**
     * 字段名
     *
     * @param column
     * @return
     */
    public static String underline2Camel(String column) {
        if (column == null || column.equals("")) {
            return "";
        }
        StringBuilder sb = new StringBuilder(column.length());
        // 当前的下标
        int idx = 0;
        int length = column.length();
        for (int index = 0; index < length; index++) {
            if (column.charAt(index) == underlineChar) {
                // 判断后面是否还有_
                while (column.charAt(++index) == underlineChar) {
                }
                idx = index;// i所对应的字符需要转换为大写字符
                char c = column.charAt(idx);
                if (sb.length() == 0) {

                } else {

                    for (int index2 = 0; index2 < a2z.length; index2++) {

                        if (a2z[index2] == c) {
                            c = A2Z[index2];
                            break;
                        }
                    }
                }

                sb.append(c);
            } else {
                sb.append(column.charAt(index));
            }
        }

        return sb.toString();
    }

    /**
     * @param column
     * @return
     */
    public static String underline2Camel2(String column) {
        StringBuilder result = new StringBuilder();
        // 快速检查
        if (column == null || column.isEmpty()) {
            // 没必要转换
            return "";
        } else if (!column.contains(underlineStr)) {
            // 不含下划线，仅将首字母小写
            return column.substring(0, 1).toLowerCase() + column.substring(1);
        } else {
            // 用下划线将原始字符串分割
            String[] columns = column.split(underlineStr);
            for (String columnSplit : columns) {
                // 跳过原始字符串中开头、结尾的下换线或双重下划线
                if (columnSplit.isEmpty()) {
                    continue;
                }
                // 处理真正的驼峰片段
                if (result.length() == 0) {
                    // 第一个驼峰片段，全部字母都小写
                    result.append(columnSplit.toLowerCase());
                } else {
                    // 其他的驼峰片段，首字母大写
                    result.append(columnSplit.substring(0, 1).toUpperCase())
                            .append(columnSplit.substring(1).toLowerCase());
                }
            }
            return result.toString();
        }

    }

    /**
     * 驼峰转换下划线
     *
     * @param property
     * @return
     */
    public static String camel2Underline(String property) {
        if (property == null || property.isEmpty()) {
            return "";
        }
        StringBuilder column = new StringBuilder();
        column.append(property.substring(0, 1).toLowerCase());
        for (int index = 1; index < property.length(); index++) {
            String s = property.substring(index, index + 1);
            // 在小写字母前添加下划线
            if (!Character.isDigit(s.charAt(0)) && s.equals(s.toUpperCase())) {
                if (!property.contains(underlineStr)) {
                    column.append(underlineStr);
                }

            }
            // 其他字符直接转成小写
            column.append(s.toLowerCase());
        }

        return column.toString();
    }

    static char underlineChar = '_';
    static String underlineStr = "_";
    private static char[] a2z = "abcdefghijklmnopqrstwvuxyz".toCharArray();
    private static char[] A2Z = "abcdefghijklmnopqrstwvuxyz".toUpperCase().toCharArray();
}
