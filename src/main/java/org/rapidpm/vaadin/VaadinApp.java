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

import static org.rapidpm.frp.model.Result.ofNullable;
import static org.rapidpm.vaadin.BlobImagePushService.register;
import static org.rapidpm.vaadin.imagecache.ImageFunctions.nextImageName;
import static org.rapidpm.vaadin.imagecache.ImageFunctions.streamResource;

import java.util.function.Function;

import org.rapidpm.dependencies.core.logger.HasLogger;
import org.rapidpm.frp.model.Result;
import org.rapidpm.vaadin.imagecache.BlobService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.page.Push;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.AbstractStreamResource;
import com.vaadin.flow.shared.Registration;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;

@Route("")
@Theme(value = Lumo.class, variant = Lumo.DARK)
@Push
public class VaadinApp extends Composite<Div> implements HasLogger {

  private Function<AbstractStreamResource, Image> createImage() {
    return (r) -> new Image(r , r.getName());
  }

  private Function<String, AbstractStreamResource> createImageResource() {
    return (imageID) -> streamResource()
        .apply(BlobService.INSTANCE)
        .apply(imageID)
        .setCacheTime(0);
  }

  private Image image;
  private Result<Registration> registration;

  public VaadinApp() {
    image = createImageResource()
        .andThen(createImage()).apply(nextImageName().get());

    registration = ofNullable(register(imgID -> image.getUI()
                                      .ifPresent(ui -> ui.access(() -> {
                                        logger().info("VaadinApp - imgID = " + imgID);
                                        image.setSrc(createImageResource().apply(imgID));
                                      }))));
    getContent().add(image);
  }

  @Override
  protected void onDetach(DetachEvent detachEvent) {
    super.onDetach(detachEvent);
    registration.ifPresent(Registration::remove);
  }
}
