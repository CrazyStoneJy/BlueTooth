package com.code.space.androidlib.net.toolbox;

import com.code.space.androidlib.net.IUpload;
import com.code.space.androidlib.utilities.chain.AbstractChainBuilder;

import java.util.HashMap;
import java.util.Map;

public class FilesBuilder extends AbstractChainBuilder<Map<String, IUpload>>{

    public FilesBuilder() {
        setTarget(new HashMap<String, IUpload>());
    }

    public FilesBuilder put(String key, IUpload e) {
        getTarget().put(key, e);
        return this;
    }
}