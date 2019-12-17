package app_utility;

public class SubMenuRVData {

    String imagePath;
    int totalCount;
    String totalTime;
    String totalCost;

    public SubMenuRVData() {

    }


    public SubMenuRVData(String imagePath, int totalCount, String totalTime, String totalCost){
        this.imagePath = imagePath;
        this.totalCount = totalCount;
        this.totalTime = totalTime;
        this.totalCost = totalCost;
    }

    public String getImagePath(){
        return this.imagePath;
    }
    public int getTotalCount(){
        return this.totalCount;
    }
    public String getTotalTime(){
        return this.totalTime;
    }
    public String getTotalCost(){
        return this.totalCost;
    }



}
