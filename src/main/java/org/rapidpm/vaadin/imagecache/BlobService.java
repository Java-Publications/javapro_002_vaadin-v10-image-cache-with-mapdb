package org.rapidpm.vaadin.imagecache;

import org.rapidpm.frp.model.Result;

/**
 *
 */
public interface BlobService {
  Result<byte[]> loadBlob(String blobID);
}
