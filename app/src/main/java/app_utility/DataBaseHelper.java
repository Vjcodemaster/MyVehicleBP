package app_utility;


public class DataBaseHelper {


    //private variables
    private int _id;

    private String _main_category;
    private String _sub_category;
    private String _image_path;
    private String _individual_dent_count;
    private String _individual_time;
    private String _individual_cost;
    private String _individual_length;
    private String _individual_width;
    private String _individual_depth;
    private int _total_dent_count;
    private String _total_time;
    private String _total_cost;
    //private String _phone_number;


    public DataBaseHelper(){

    }

    public DataBaseHelper(int nCase, String sMultiInput){
        Constants constants = Constants.values()[nCase];
        switch (constants){
            case UPDATE_IMAGE_PATH:
                set_image_path(sMultiInput);
                break;
        }
    }

    public DataBaseHelper(String _main_category, String _sub_category){
        this._main_category = _main_category;
        this._sub_category = _sub_category;
    }

    public DataBaseHelper(String _main_category, String _sub_category, String _image_path){
        this._main_category = _main_category;
        this._sub_category = _sub_category;
        this._image_path = _image_path;
    }

    public DataBaseHelper(String _main_category, String _sub_category, String _image_path, String _individual_dent_count,
                          String _individual_time, String _individual_cost, String _individual_length,
                          String _individual_width, String _individual_depth, int _total_dent_count, String _total_time,
                          String _total_cost){
        this._main_category = _main_category;
        this._sub_category = _sub_category;
        this._image_path = _image_path;
        this._individual_dent_count = _individual_dent_count;
        this._individual_time = _individual_time;
        this._individual_cost = _individual_cost;
        this._individual_length = _individual_length;
        this._individual_width = _individual_width;
        this._individual_depth = _individual_depth;
        this._total_dent_count = _total_dent_count;
        this._total_time = _total_time;
        this._total_cost = _total_cost;
    }

    public int get_id(){
        return this._id;
    }

    public String get_main_category(){
        return this._main_category;
    }

    public void set_main_category(String main_category){
        this._main_category = main_category;
    }

    public String get_sub_category(){
        return this._sub_category;
    }

    public void set_sub_category(String sub_category){
        this._sub_category = sub_category;
    }

    public String get_image_path(){
        return this._image_path;
    }

    public void set_image_path(String image_path){
        this._image_path = image_path;
    }

    public String get_individual_dent_count(){
        return this._individual_dent_count;
    }

    public void set_individual_dent_count(String individual_dent_count){
        this._individual_dent_count = individual_dent_count;
    }

    public String get_individual_time(){
        return this._individual_time;
    }

    public void set_individual_time(String individual_time){
        this._individual_time = individual_time;
    }

    public String get_individual_cost(){
        return this._individual_cost;
    }

    public void set_individual_cost(String individual_cost){
        this._individual_cost = individual_cost;
    }
    public String get_individual_length(){
        return this._individual_length;
    }

    public void set_individual_length(String individual_length){
        this._individual_length = individual_length;
    }

    public String get_individual_width(){
        return this._individual_width;
    }

    public void set_individual_width(String individual_width){
        this._individual_width = individual_width;
    }

    public String get_individual_depth(){
        return this._individual_depth;
    }

    public void set_individual_depth(String individual_depth){
        this._individual_depth = individual_depth;
    }

    public int get_total_dent_count(){
        return this._total_dent_count;
    }

    public void set_total_dent_count(int total_dent_count){
        this._total_dent_count = total_dent_count;
    }

    public String get_total_time(){
        return this._total_time;
    }

    public void set_total_time(String total_time){
        this._total_time = total_time;
    }

    public String get_total_cost(){
        return this._total_cost;
    }

    public void set_total_cost(String total_cost){
        this._total_cost = total_cost;
    }
    /*public void set_total_dent_counts(int dent_counts){
        this._total_dent_counts = dent_counts;
    }
    public void set_total_dent_counts(int dent_counts){
        this._total_dent_counts = dent_counts;
    }*/
}
