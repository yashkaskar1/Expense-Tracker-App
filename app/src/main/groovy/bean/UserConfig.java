package bean;

public class UserConfig {
    private String name;
    private String className;

    public void setName(final String name){
        this.name= name;
    }
    public String getName(){
        return name;
    }

    public void setClassName(final String className){
        this.className= className;
    }
    public String getClassName(){
        return className;
    }
}
