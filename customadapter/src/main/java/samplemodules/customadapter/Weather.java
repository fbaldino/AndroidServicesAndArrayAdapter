package samplemodules.customadapter;

/**
 * Created by fbald on 29/10/2016.
 */
// Exemple extracted from http://www.ezzylearning.com/tutorial/customizing-android-listview-items-with-custom-arrayadapter
public class Weather {
    public int icon;
    public String title;
    public Weather(){
        super();
    }

    public Weather(int icon, String title) {
        super();
        this.icon = icon;
        this.title = title;
    }
}