package com.vaadin.hummingbird.bootstrap4;

import java.util.List;

import com.vaadin.annotations.JavaScript;
import com.vaadin.annotations.StyleSheet;
import com.vaadin.ui.Template;

@StyleSheet("context://bower_components/bootstrap/dist/css/bootstrap.min.css")
@JavaScript({ "context://bower_components/jquery/dist/jquery.min.js",
        "context://bower_components/bootstrap/dist/js/bootstrap.min.js" })
public class Carousel extends Template {

    @Override
    protected void init() {
        super.init();
    }

    public interface Image {
        public String getUrl();

        public void setUrl(String url);

        public String getCaption();

        public void setCaption(String caption);

        public String getText();

        public void setText(String text);
    }

    public interface CarouselModel extends Model {
        public List<Image> getImages();

        public void setImages(List<Image> images);
    }

    @Override
    protected CarouselModel getModel() {
        return (CarouselModel) super.getModel();
    }

    public void addImage(String url) {
        addImage(url, null);
    }

    public void addImage(String url, String caption) {
        addImage(url, caption, null);
    }

    public void addImage(String url, String caption, String text) {
        getNode().getMultiValued("images"); // DAfq

        Image image = Model.create(Image.class);
        image.setUrl(url);
        image.setCaption(caption);
        image.setText(text);
        List<Image> images = getModel().getImages();

        if (images.isEmpty()) {
            getElement().getNode().enqueueRpc(
                    "$0.querySelector('.carousel-item').classList.add('active')",
                    getElement());
        }
        images.add(image);
    }
}
