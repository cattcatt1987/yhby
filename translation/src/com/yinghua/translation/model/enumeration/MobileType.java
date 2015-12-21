package com.yinghua.translation.model.enumeration;

/**
 * Created by allan on 1/23/15.
 */
public enum MobileType {


    CMCC("中国移动", 1),
    UNICOM("中国联通", 2),
    CHINATELE("中国电信", 3);


    private String name;
    private int index;

    // 构造方法
    private MobileType(String name, int index) {
        this.name = name;
        this.index = index;
    }

    /**
     * 设置状态
     *
     * @param index
     * @return
     */
    public static MobileType getType(int index) {
        MobileType es = null;
        switch (index) {
            case 1:
                es = MobileType.CMCC;
                break;
            case 2:
                es = MobileType.UNICOM;
                break;
            case 3:
                es = MobileType.CHINATELE;
                break;
            default:
                es = MobileType.CMCC;
        }
        return es;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

}
