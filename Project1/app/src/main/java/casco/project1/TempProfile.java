package casco.project1;

/**
 * Created by Stefan on 4/30/2016.
 */
public class TempProfile {
    int id;
    String poll;
    String creator;
    int imageIndex;
    TempProfile(int id, String poll,String creator, int imageIndex){
        this.id = id;
        this.poll = poll;
        this.creator = creator;
        this.imageIndex = imageIndex;
    }
    public int getId(){
        return this.id;
    }
    public String getPoll(){
        return this.poll;
    }
    public String getCreator(){
        return this.creator;
    }
    public int getImageIndex(){
        return this.imageIndex;
    }
}
