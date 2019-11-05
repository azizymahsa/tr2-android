package ir.traap.tractor.android.models.otherModels.download;

import lombok.Getter;
import lombok.Setter;

public class Download
{
    @Getter @Setter
    private int progress;

    @Getter @Setter
    private int currentFileSize;

    @Getter @Setter
    private int totalFileSize;
}
