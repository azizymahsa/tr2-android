package ir.traap.tractor.android.ui.activities.card.add;

import java.util.ArrayList;

/**
 * Created by Javad.Abadi on 7/31/2018.
 */
public class BankType
{
    public final ArrayList<BankDetail> bank = new ArrayList<>();

    public BankType()
    {
    }

    public ArrayList<BankDetail> bankDetail()
    {

        return bank;
    }


    protected class BankDetail
    {

        private String code;
        private String name;
        private int image;

        public BankDetail(String code, String name, int image)
        {
            this.code = code;
            this.name = name;
            this.image = image;
        }

        public String getCode()
        {
            return code;
        }

        public void setCode(String code)
        {
            this.code = code;
        }

        public String getName()
        {
            return name;
        }

        public void setName(String name)
        {
            this.name = name;
        }

        public int getImage()
        {
            return image;
        }

        public void setImage(int image)
        {
            this.image = image;
        }
    }
}
