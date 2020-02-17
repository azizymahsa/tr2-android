package com.traap.traapapp.utilities;

import android.net.Uri;

import androidx.annotation.NonNull;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class PrepareImageFilePart
{
    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri)
    {
//        File file = FileUtils.toFile(fileUri);
        File file = new File(String.valueOf(fileUri));
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    @NonNull
    public static MultipartBody.Part prepareFilePart(String partName, File file)
    {
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse("image/*"),
                        file
                );

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
//        return MultipartBody.Part.createFormData(partName,"photo", requestFile);
    }
}
