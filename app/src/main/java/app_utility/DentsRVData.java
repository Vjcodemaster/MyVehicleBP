package app_utility;

public class DentsRVData {

    String length;
    String width;
    String depth;
    String timeInHours;
    String cost;

    public DentsRVData() {

    }


    public DentsRVData(String sLength, String sWidth, String sDepth, String sTimeInHours, String sCost){
        this.length = sLength;
        this.width = sWidth;
        this.depth = sDepth;
        this.timeInHours = sTimeInHours;
        this.cost = sCost;
    }

    public void setLength(String sLength){
        this.length = sLength;
    }

    public void setWidth(String sWidth){
        this.width = sWidth;
    }

    public void setDepth(String sDepth){
        this.depth = sDepth;
    }

    public void setTimeInHours(String sTimeInHours){
        this.timeInHours = sTimeInHours;
    }

    public void setCost(String sCost){
        this.cost = sCost;
    }
    public String getLength(){
        return this.length;
    }
    public String getWidth(){
        return this.width;
    }
    public String getDepth(){
        return this.depth;
    }
    public String getTimeInHours(){
        return this.timeInHours;
    }
    public String getCost(){
        return this.cost;
    }



}
