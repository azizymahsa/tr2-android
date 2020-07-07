package com.traap.traapapp.ui.fragments.simcardPack.imp;

import android.os.Build;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.traap.traapapp.apiServices.model.getSimPackageList.response.SimContentItem;
import com.traap.traapapp.conf.TrapConfig;

import java.util.List;
import java.util.stream.Collectors;

public class GetSimContentListByTypeImp
{
    public static List<SimContentItem> getSimContentListByType(List<SimContentItem> contentList, int simType)
    {
        List<SimContentItem> filteredContentList;
        CharSequence container = "";
        if (simType == TrapConfig.SIM_TYPE_CREDIT)
        {
            container = "اعتبار";
        }
        else if (simType == TrapConfig.SIM_TYPE_FULL)
        {
            container = "دائم";
        }
        else if (simType == TrapConfig.SIM_TYPE_DATA)
        {
            container = "دیتا";
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
        {
            CharSequence finalContainer = container;
            filteredContentList = contentList.stream().filter(simContentItem ->
                    simContentItem.getTitlePackageType().contains(finalContainer)).collect(Collectors.toList());
        }
        else
        {
            CharSequence finalContainer1 = container;
            filteredContentList = Lists.newArrayList(Iterables.filter(contentList, simContentItem ->
                    simContentItem.getTitlePackageType().contains(finalContainer1)));

//            Iterable<SimContentItem> iterable = Iterables.filter(contentList, new Predicate<SimContentItem>()
//            {
//                @Override
//                public boolean apply(@NullableDecl SimContentItem simContentItem)
//                {
//                    return simContentItem.getTitlePackageType().contains(finalContainer1);
//                }
//            });
//
//            filteredContentList = Lists.newArrayList(iterable);
        }
        return filteredContentList;
    }
}
