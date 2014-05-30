package dr.dk.drtvcast.app.mu;

import android.net.Uri;
import android.os.Debug;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import com.google.gson.*;

/**
 * Created by Bo on 29-05-2014.
 */
public class MuOnline {

    public static FrontPage FRONT_PAGE;
    public static ItemList SELECTED_LIST;
    static {

    }
    public static void LoadSelectedList(){
        if (SELECTED_LIST != null)
            return;
        try {
            URL api = new URL("http://www.dr.dk/mu-online/api/1.0/list/view/selectedlist?limit=25");
            BufferedReader in = new BufferedReader( new InputStreamReader(api.openStream()));
            Gson gson = new GsonBuilder().create();
            SELECTED_LIST= gson.fromJson(in, ItemList.class);
        } catch (MalformedURLException e) {

        }
        catch (IOException e) {

        }
    }
    public static void LoadFrontPage(){
        if (FRONT_PAGE != null)
            return;
        try {
            URL api = new URL("http://www.dr.dk/mu-online/api/1.0/page/tv/front/");
            BufferedReader in = new BufferedReader( new InputStreamReader(api.openStream()));
            Gson gson = new GsonBuilder().create();
            FRONT_PAGE= gson.fromJson(in, FrontPage.class);
        } catch (MalformedURLException e) {

        }
        catch (IOException e) {

        }
    }

    public static Item LoadProgramCard(String slug){
        Item res = null;
        try {
            URL api = new URL("http://www.dr.dk/mu-online/api/1.0/programcard/"+slug);
            BufferedReader in = new BufferedReader( new InputStreamReader(api.openStream()));
            Gson gson = new GsonBuilder().create();
            res = gson.fromJson(in, Item.class);
        } catch (MalformedURLException e) {

        }
        catch (IOException e) {

        }
        return res;
    }

    public static String GetHLS(String assetUri){
        try {
            URL api = new URL(assetUri);
            BufferedReader in = new BufferedReader( new InputStreamReader(api.openStream()));
            Gson gson = new GsonBuilder().create();
            Manifest manifest = gson.fromJson(in, Manifest.class);
            for(int x=0; x<manifest.Links.size(); x++){
                Link link = manifest.Links.get(x);
                if (link.Target.equalsIgnoreCase("HLS"))
                {
                    return link.Uri;
                }
            }
        } catch (MalformedURLException e) {

        }
        catch (IOException e) {

        }
        return null;
    }

    public static class FrontPage {
        public ItemList SelectedList;
    }
    public static class ItemList {
        public List<Item> Items;
    }

    public static class Item {
        public String Slug;
        public String Title;
        public String Description;
        public String PrimaryImageUri;
        public String toString() { return Title;}
        public String id() { return Slug;}
        public Asset PrimaryAsset;
    }
    public static class Asset {
        public String Uri;
    }
    public static class Manifest {
        public List<Link> Links ;
    }
    public static class Link {
        public String Uri;
        public String Target;
    }
}