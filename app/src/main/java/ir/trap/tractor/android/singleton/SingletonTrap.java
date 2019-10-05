package ir.trap.tractor.android.singleton;

/**
 * Created by RezaNejati on 1/26/2019.
 */
public class SingletonTrap
{
    private static final SingletonTrap ourInstance = new SingletonTrap();

    private String PASS_KEY="dibaLocalpass";
    private String DIBA_KEY="5532710def49b0aeaedf2ff1938729be888cf079";
    private String DIBA_NAME="DibaKey";
    private String DIBA_USER="UserKey";
    private String KEY_NAME="appKey";

    public String getPASS_KEY() {
        return PASS_KEY;
    }

    public void setPASS_KEY(String PASS_KEY) {
        this.PASS_KEY = PASS_KEY;
    }

    public String getDIBA_KEY() {
        return DIBA_KEY;
    }

    public void setDIBA_KEY(String DIBA_KEY) {
        this.DIBA_KEY = DIBA_KEY;
    }

    public String getDIBA_NAME() {
        return DIBA_NAME;
    }

    public void setDIBA_NAME(String DIBA_NAME) {
        this.DIBA_NAME = DIBA_NAME;
    }

    public String getDIBA_USER() {
        return DIBA_USER;
    }

    public void setDIBA_USER(String DIBA_USER) {
        this.DIBA_USER = DIBA_USER;
    }



    public String getKEY_NAME() {
        return KEY_NAME;
    }

    public void setKEY_NAME(String KEY_NAME) {
        this.KEY_NAME = KEY_NAME;
    }

    public static SingletonTrap getInstance() {
        return ourInstance;
    }


}

