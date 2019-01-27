package org.rapidpm.vaadin.imagecache.weakref;

import static org.rapidpm.vaadin.imagecache.BlobImageServiceFileFunctions.loadFile;

import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.BlobService;

/**
 *
 */
public class BlobImageServiceWeakRef implements HasLogger, BlobService {

  //TODO fill up the memory
  private static final Map<String, WeakReference<Result<byte[]>>> CACHE = new ConcurrentHashMap<>();

  @Override
  public Result<byte[]> loadBlob(String blobID) {
    //hard coded right now
    final boolean containsKey = CACHE.containsKey(blobID);
    logger().info("containsKey = " + containsKey);
    if (containsKey) {
      final WeakReference<Result<byte[]>> weakReference = CACHE.get(blobID);
      final Result<byte[]> result = weakReference.get();

      logger().info((result == null)
                    ? "blobId " + blobID + " was eaten by GC "
                    : "blobId " + blobID + " was cached so far"
      );
      if (result == null) {
        CACHE.remove(blobID);
        return CACHE.computeIfAbsent(blobID , s -> new WeakReference<>(loadFile().apply(blobID)))
                    .get();
      } else {
        return result;
      }
    } else {
      return CACHE
          .computeIfAbsent(blobID , s -> new WeakReference<>(loadFile().apply(blobID)))
          .get();
    }
  }


}
