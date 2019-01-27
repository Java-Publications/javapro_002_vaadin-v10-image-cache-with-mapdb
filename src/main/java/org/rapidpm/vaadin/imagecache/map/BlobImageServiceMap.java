package org.rapidpm.vaadin.imagecache.map;

import static org.rapidpm.vaadin.imagecache.BlobImageServiceFileFunctions.loadFile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.BlobService;

public class BlobImageServiceMap implements BlobService {

  private static final Map<String, Result<byte[]>> CACHE = new ConcurrentHashMap<>(); //TODO fill up the memory

  @Override
  public Result<byte[]> loadBlob(String blobID) {
    //hard coded right now
    return CACHE.containsKey(blobID)
           ? CACHE.get(blobID)
           : CACHE.computeIfAbsent(blobID , loadFile());
  }
}
