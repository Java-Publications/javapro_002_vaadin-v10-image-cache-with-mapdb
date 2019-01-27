package org.rapidpm.vaadin.imagecache.mapdb;

import static org.rapidpm.frp.model.Result.ofNullable;
import static org.rapidpm.vaadin.imagecache.mapdb.PersistenceFunctions.cachingDB;
import static org.rapidpm.vaadin.imagecache.mapdb.PersistenceFunctions.mapInMemoryPersistentOnDisc;
import static org.rapidpm.vaadin.imagecache.BlobImageServiceFileFunctions.loadFile;

import org.mapdb.HTreeMap;
import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.BlobService;

public class BlobImageServiceMapDB implements HasLogger, BlobService {

  private static final String IMAGE_CACHE = "imageCache";
  private static final String IMAGES = "images";

  //  private static final PersistenceFunctions.DatabasePair CACHE = memoize(cachingDB()).apply(IMAGE_CACHE);
  private static final PersistenceFunctions.DatabasePair CACHE = cachingDB().apply(IMAGE_CACHE);
  private static final HTreeMap<String, byte[]> IMAGE_MAP_IN_MEMORY = mapInMemoryPersistentOnDisc().apply(CACHE , IMAGES);

  @Override
  public Result<byte[]> loadBlob(String blobID) {
    //hard coded right now
    final byte[] imageByteArray = IMAGE_MAP_IN_MEMORY.get(blobID);
    final boolean containsKey = imageByteArray != null;
    logger().info("containsKey = " + containsKey);

    if (! containsKey) {
//      load data into system -> some filesystem remote system
      loadFile()
          .apply(blobID)
          .ifPresentOrElse(
              imageRAW -> IMAGE_MAP_IN_MEMORY.put(blobID , imageRAW) ,
              failed -> logger().warning("Image with ID " + blobID + " could not be loaded from external system")
          );
    }
    return ofNullable(IMAGE_MAP_IN_MEMORY.get(blobID));
  }

}
