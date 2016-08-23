package com.code.space.androidlib.net.toolbox.upload;

import java.io.File;
import java.util.Locale;

import okhttp3.MediaType;

import static com.code.space.androidlib.utilities.utils.Image.*;

/**
 * Created by shangxuebin on 16-6-16.
 */
public class ImageUpload extends UploadImpl {

    public ImageUpload() {
    }

    public ImageUpload(String fileName) {
        super(fileName);
    }

    public ImageUpload(File file) {
        super(file);
    }

    @Override
    public MediaType getMediaType() {
        if(super.getMediaType()==null&&getFile()!=null){
            if(getFile().getName().toLowerCase(Locale.US).endsWith("."+PNG)) setMediaType("image/"+PNG);
            else if(getFile().getName().toLowerCase(Locale.US).endsWith("."+JPG)) setMediaType("image/"+JPG);
            else if(getFile().getName().toLowerCase(Locale.US).endsWith("."+GIF)) setMediaType("image/"+GIF);
            else if(getFile().getName().toLowerCase(Locale.US).endsWith("."+BMP)) setMediaType("image/"+BMP);
        }
        return super.getMediaType();
    }
}
