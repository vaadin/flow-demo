package com.vaadin.flow.demo.website;

import com.vaadin.external.jsoup.nodes.Document;
import com.vaadin.external.jsoup.nodes.Element;
import com.vaadin.server.BootstrapListener;
import com.vaadin.server.BootstrapPageResponse;
import com.vaadin.server.ServiceInitEvent;
import com.vaadin.server.VaadinServiceInitListener;

/**
 * Custom service initialization listener which adds bootstrap listener to
 * modify the bootstrap application page.
 * 
 * @since
 * @author Vaadin Ltd
 */
public class SiteServiceInitListener implements VaadinServiceInitListener {

    private static class SiteBootstrapListener implements BootstrapListener {

        @Override
        public void modifyBootstrapPage(BootstrapPageResponse response) {
            Document document = response.getDocument();

            Element head = document.head();

            head.appendChild(createMeta(document, "og:title", "The Rock"));
            head.appendChild(createMeta(document, "og:type", "video.movie"));
            head.appendChild(createMeta(document, "og:url", "http://www.imdb.com/title/tt0117500/"));
            head.appendChild(createMeta(document, "og:image", "http://ia.media-imdb.com/images/rock.jpg"));
        }

        private Element createMeta(Document document, String property, String content) {
            Element meta = document.createElement("meta");
            meta.attr("property", property);
            meta.attr("content", content);
            return meta;
        }

    }

    @Override
    public void serviceInit(ServiceInitEvent event) {
        event.addBootstrapListener(new SiteBootstrapListener());
    }

}
