package org.rapidpm.vaadin.imagecache;

import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.filesystem.BlobImageServiceFileSystem;
import org.rapidpm.vaadin.imagecache.map.BlobImageServiceMap;
import org.rapidpm.vaadin.imagecache.mapdb.BlobImageServiceMapDB;
import org.rapidpm.vaadin.imagecache.weakref.BlobImageServiceWeakRef;

/**
 *
 */
public interface BlobService {

  //do not do this in production !!!
  BlobService INSTANCE = new BlobImageServiceFileSystem();
//  BlobService INSTANCE = new BlobImageServiceMap();
//  BlobService INSTANCE = new BlobImageServiceWeakRef();
//  BlobService INSTANCE = new BlobImageServiceMapDB();


  Result<byte[]> loadBlob(String blobID);
}
