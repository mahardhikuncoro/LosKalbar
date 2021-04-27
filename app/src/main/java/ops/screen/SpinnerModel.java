package ops.screen;

public class SpinnerModel {

    private String title;
    private int icon;

    public SpinnerModel(String title, int icon){
        this.title = title;
        this.icon = icon;
    }

    public String getTitle(){
        return this.title;
    }

    public int getIcon(){
        return this.icon;
    }
}