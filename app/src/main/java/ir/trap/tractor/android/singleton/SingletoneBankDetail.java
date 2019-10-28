package ir.trap.tractor.android.singleton;


import java.util.ArrayList;
import java.util.List;

import ir.trap.tractor.android.R;
import ir.trap.tractor.android.apiServices.model.getBankList.response.Bank;


/**
 * Created by Javad.Abadi on 7/31/2018.
 */
public class SingletoneBankDetail
{
    private static SingletoneBankDetail mInstance;
    private ArrayList<BankDetail> list = null;
    private List<Bank> banks = new ArrayList<>();

    public static SingletoneBankDetail getInstance()
    {
        if (mInstance == null)
            mInstance = new SingletoneBankDetail();

        return mInstance;
    }

    public List<Bank> getBanks()
    {
        return banks;
    }

    public void setBanks(List<Bank> banks)
    {
        this.banks = banks;
    }

    private SingletoneBankDetail()
    {
        list = new ArrayList<BankDetail>();
        addToArray();
    }

    public void setList(ArrayList<BankDetail> list)
    {
        this.list = list;
    }

    public ArrayList<BankDetail> getArray()
    {
        return this.list;
    }

    public void addToArray()
    {
        list.add(new SingletoneBankDetail.BankDetail("603799", "بانک ملی", R.drawable.ic_banklogo_melli, "#091532"));
        list.add(new SingletoneBankDetail.BankDetail("589210", "بانک سپه", R.drawable.ic_banklogo_sepah, "#ea6200"));
        list.add(new SingletoneBankDetail.BankDetail("627648", "بانک توسعه صادرات", R.drawable.ic_banklogo_toseesaderat, "#006c03"));
        list.add(new SingletoneBankDetail.BankDetail("627961", "بانک صنعت معدن", R.drawable.ic_banklogo_sanatvamadan, "#153585"));
        list.add(new SingletoneBankDetail.BankDetail("603770", "بانک کشاورزی", R.drawable.ic_banklogo_keshavarzi, "#447913"));
        list.add(new SingletoneBankDetail.BankDetail("628023", "بانک مسکن", R.drawable.ic_banklogo_maskan, "#e8480d"));
        list.add(new SingletoneBankDetail.BankDetail("627760", "پست بانک", R.drawable.ic_banklogo_postbankiran, "#00790a"));
        list.add(new SingletoneBankDetail.BankDetail("502908", "بانک توسعه تعاون", R.drawable.ic_banklogo_toseetaavon, "#008a94"));
        list.add(new SingletoneBankDetail.BankDetail("627412", "بانک اقتصاد نوین", R.drawable.ic_banklogo_eghtesadnovin, "#99069b"));
        list.add(new SingletoneBankDetail.BankDetail("622106", "بانک پارسیان", R.drawable.ic_banklogo_parsian, "#a30a18"));
        list.add(new SingletoneBankDetail.BankDetail("502229", "بانک پاسارگاد", R.drawable.ic_banklogo_pasargad, "#efc23b"));
        list.add(new SingletoneBankDetail.BankDetail("627488", "بانک کارآفرین", R.drawable.ic_banklogo_karafarin, "#0c730a"));
        list.add(new SingletoneBankDetail.BankDetail("621986", "بانک سامان", R.drawable.ic_banklogo_saman, "#00a0e4"));
        list.add(new SingletoneBankDetail.BankDetail("639346", "بانک سینا", R.drawable.ic_banklogo_sina, "#1344a1"));
        list.add(new SingletoneBankDetail.BankDetail("639607", "بانک سرمایه", R.drawable.ic_banklogo_sarmaye, "#000064"));
        list.add(new SingletoneBankDetail.BankDetail("636214", "بانک آينده", R.drawable.ic_banklogo_tat, "#522B1C"));
        list.add(new SingletoneBankDetail.BankDetail("502806", "بانک شهر", R.drawable.ic_banklogo_shahr, "#ef1a19"));
        list.add(new SingletoneBankDetail.BankDetail("502938", "بانک دی", R.drawable.ic_banklogo_dey, "#008ba0"));
        list.add(new SingletoneBankDetail.BankDetail("603769", "بانک صادرات", R.drawable.ic_banklogo_saderat, "#2d2d94"));
        list.add(new SingletoneBankDetail.BankDetail("610433", "بانک ملت", R.drawable.ic_banklogo_mellat, "#ba0b22"));
        list.add(new SingletoneBankDetail.BankDetail("627353", "بانک تجارت", R.drawable.ic_banklogo_tejarat, "#12007f"));
        list.add(new SingletoneBankDetail.BankDetail("585983", "بانک تجارت", R.drawable.ic_banklogo_tejarat, "#12007f"));
        list.add(new SingletoneBankDetail.BankDetail("589463", "بانک رفاه", R.drawable.ic_banklogo_refah, "#01458c"));
        list.add(new SingletoneBankDetail.BankDetail("627381", "بانک انصار", R.drawable.ic_banklogo_ansar, "#aa1a1f"));
        list.add(new SingletoneBankDetail.BankDetail("639370", "بانک مهر اقتصاد", R.drawable.ic_banklogo_mehreghtesad, "#029a4c"));
        list.add(new SingletoneBankDetail.BankDetail("003825", "دیبا", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("003725", "دیبا", R.drawable.logo, "#af9852"));
        //==============================================================================================================================================
        list.add(new SingletoneBankDetail.BankDetail("639599", "بانک قوامین", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("606373", "قرض الحسنه مهر", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("606256", "موسسه اعتباري عسكريه", R.drawable.logo, "#af9852"));
        //list.add(new SingletoneBankDetail.BankDetail("639346", "بانک سینا", R.drawable.logo,"#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("636795", "بانك مركزي", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("628157", "بانك توسعه", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("581874", "بانک مشترک ايران و ونزوئلا", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("581672", "پرداخت الكترونيك", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("507677", "موسسه اعتباري نور", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("505801", "موسسه اعتباري كوثر", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("186214", "بانك آينده", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("504172", "بانك قرض الحسنه رسالت", R.drawable.ic_banklogo_resalat, "#009ab0"));
        list.add(new SingletoneBankDetail.BankDetail("505785", "بانک ايران زمين", R.drawable.ic_banklogo_iranzamin, "#e92d9d"));
        list.add(new SingletoneBankDetail.BankDetail("585947", "بانك خاورميانه", R.drawable.ic_banklogo_khavarmiane, "#f29200"));
        list.add(new SingletoneBankDetail.BankDetail("505809", "بانك خاورميانه", R.drawable.ic_banklogo_khavarmiane, "#f29200"));
        list.add(new SingletoneBankDetail.BankDetail("505416", "بانك گردشگري", R.drawable.ic_banklogo_gardeshgari, "#069c94"));
        list.add(new SingletoneBankDetail.BankDetail("636949", "بانک حکمت ایرانیان", R.drawable.logo, "#af9852"));
        list.add(new SingletoneBankDetail.BankDetail("639347", "بانک پاسارگاد", R.drawable.ic_banklogo_pasargad, "#efc23b"));
        list.add(new SingletoneBankDetail.BankDetail("639217", "بانک کشاورزی", R.drawable.ic_banklogo_keshavarzi, "#447913"));
        list.add(new SingletoneBankDetail.BankDetail("639194", "بانک پارسیان", R.drawable.ic_banklogo_parsian, "#a30a18"));
        list.add(new SingletoneBankDetail.BankDetail("622016", "بانک صادرات", R.drawable.ic_banklogo_saderat, "#2d2d94"));
        list.add(new SingletoneBankDetail.BankDetail("604932", "بانک سپه", R.drawable.ic_banklogo_sepah, "#ea6200"));
        list.add(new SingletoneBankDetail.BankDetail("504706", "بانک شهر", R.drawable.ic_banklogo_shahr, "#ef1a19"));
        list.add(new SingletoneBankDetail.BankDetail("502910", "بانک کارآفرین", R.drawable.ic_banklogo_karafarin, "#0c730a"));
        list.add(new SingletoneBankDetail.BankDetail("207177", "بانک توسعه صادرات", R.drawable.ic_banklogo_toseesaderat, "#006c03"));


    }

    public class BankDetail
    {
        private String code;
        private String name;
        private int image;
        private String color;

        public BankDetail(String code, String name, int image, String color)
        {
            this.code = code;
            this.name = name;
            this.image = image;
            this.color = color;
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

        public String getColor()
        {
            return color;
        }

        public void setColor(String color)
        {
            this.color = color;
        }
    }


}
