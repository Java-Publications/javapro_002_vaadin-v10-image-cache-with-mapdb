/**
 * Copyright © 2017 Sven Ruppert (sven.ruppert@gmail.com)
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.rapidpm.vaadin;

import org.apache.meecrowave.Meecrowave;
import org.rapidpm.vaadin.imagecache.map.BlobImageService;
import org.rapidpm.vaadin.imagecache.BlobService;

public class BasicTestUIRunner {
  private BasicTestUIRunner() {
  }


  public static final BlobService BLOB_SERVICE = new BlobImageService();
//  public static final BlobService BLOB_SERVICE = new BlobImageServiceCached();
//  public static final BlobService BLOB_SERVICE = new BlobImageServiceMapDB();



  public static void main(String[] args) {

    new Meecrowave(new Meecrowave.Builder() {
      {
//        randomHttpPort();
        setHttpPort(8080);
        setTomcatScanning(true);
        setTomcatAutoSetup(true);
        setHttp2(true);
      }
    })
        .bake()
        .await();
  }
}
