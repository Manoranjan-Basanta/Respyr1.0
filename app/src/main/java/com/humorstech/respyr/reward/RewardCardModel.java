package com.humorstech.respyr.reward;

public class RewardCardModel {

     int logoId;
     String rewardTitle;
     String rewardValidityDate;

     String rewardType;


    public RewardCardModel(String rewardTitle,String rewardValidityDate , int logoId, String rewardType) {
        this.rewardTitle = rewardTitle;
        this.rewardValidityDate = rewardValidityDate;
        this.logoId = logoId;
        this.rewardType = rewardType;
    }

    public int getLogoId() {
        return logoId;
    }

    public void setLogoId(int logoId) {
        this.logoId = logoId;
    }

    public String getRewardTitle() {
        return rewardTitle;
    }

    public void setRewardTitle(String rewardTitle) {
        this.rewardTitle = rewardTitle;
    }

    public String getRewardValidityDate() {
        return rewardValidityDate;
    }

    public void setRewardValidityDate(String rewardValidityDate) {
        this.rewardValidityDate = rewardValidityDate;
    }

    public String getRewardType() {
        return rewardType;
    }

    public void setRewardType(String rewardType) {
        this.rewardType = rewardType;
    }
}
