package org.rapidpm.vaadin.imagecache;

import static java.nio.file.Files.readAllBytes;

import java.io.File;

import org.rapidpm.frp.functions.CheckedFunction;

public interface BlobImageServiceFileFunctions {

  String STORAGE_PREFIX = "_data/_0512px/";

  static CheckedFunction<String, byte[]> loadFile() {
    return (blobID) -> readAllBytes(new File(STORAGE_PREFIX + blobID).toPath());
  }


}
