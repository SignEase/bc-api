package com.ichaoj.sxq.client.enums;

/**
 * 签约方分组
 * @author fan
 * @date 2018-11-30 11:05
 */
public enum GroupsEnum {
    /***/
    PARTY_A("甲方","a"),
    PARTY_B("乙方","b"),
    PARTY_C("丙方","c"),
    PARTY_D("丁方","d"),
    PARTY_E("戊方","e"),
    PARTY_F("己方","f"),
    PARTY_G("庚方","g"),
    PARTY_H("辛方","h"),
    PARTY_I("壬方","i"),
    PARTY_J("癸方","j"),
    PARTY_K("子方","k");

    GroupsEnum(String groupName, String groupChar) {
        this.groupName = groupName;
        this.groupChar = groupChar;
    }

    private String groupName;

    private String groupChar;

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getGroupChar() {
        return groupChar;
    }

    public void setGroupChar(String groupChar) {
        this.groupChar = groupChar;
    }
}
