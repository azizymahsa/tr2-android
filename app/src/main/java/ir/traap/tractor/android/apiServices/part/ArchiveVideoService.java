package ir.traap.tractor.android.apiServices.part;

import ir.traap.tractor.android.apiServices.generator.ServiceGenerator;
import ir.traap.tractor.android.apiServices.listener.OnServiceStatus;
import ir.traap.tractor.android.apiServices.model.WebServiceClass;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoRequest;
import ir.traap.tractor.android.apiServices.model.archiveVideo.ArchiveVideoResponse;

/**
 * Created by MahtabAzizi on 11/27/2019.
 */
public class ArchiveVideoService extends BasePart
{
    public ArchiveVideoService(ServiceGenerator serviceGenerator)
    {
        super(serviceGenerator);
    }

    @Override
    protected BasePart getPart()
    {
        return this;
    }

    public void getArchiveVideo(OnServiceStatus<WebServiceClass<ArchiveVideoResponse>> listener, ArchiveVideoRequest request,int categoryId)
    {
        start(getServiceGenerator().createService().getArchiveVideos(categoryId), listener);
    }
}

