package org.rapidpm.vaadin.imagecache.filesystem;

import static org.rapidpm.vaadin.imagecache.BlobImageServiceFileFunctions.loadFile;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.BlobService;

public class BlobImageServiceFileSystem implements BlobService {

  @Override
  public Result<byte[]> loadBlob(String blobID) {
    return loadFile().apply(blobID);
  }
}
