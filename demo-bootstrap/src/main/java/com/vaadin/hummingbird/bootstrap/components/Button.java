package com.vaadin.hummingbird.bootstrap.components;

import com.vaadin.annotations.Tag;
import com.vaadin.ui.AbstractComponent;

@Tag("button")
public class Button extends AbstractComponent {
    public enum Type {
        DEFAULT, PRIMARY, SUCCESS, INFO, WARNING, DANGER, LINK;

        private String getClassName() {
            return "btn-" + name().toLowerCase();
        }
    }

    public enum Size {
        DEFAULT(null), LARGE("lg"), SMALL("sm"), EXTRA_SMALL("xs");

        private final String className;

        private Size(String styleName) {
            if (styleName == null) {
                className = null;
            } else {
                className = "btn-" + styleName;
            }
        }

        public String getClassName() {
            return className;
        }
    }

    private Size size = Size.DEFAULT;
    private Type type = Type.DEFAULT;

    public Button() {
        this("");
    }

    public Button(String caption) {
        this(caption, Type.DEFAULT);
    }

    public Button(String caption, Type type) {
        getElement().setTextContent(caption);
        getElement().addClass("btn");

        setType(type);
    }

    public Button setType(Type type) {
        if (type != this.type) {
            getElement().removeClass(this.type.getClassName());
            this.type = type;
            getElement().addClass(type.getClassName());
        }
        return this;
    }

    public Button setSize(Size size) {
        if (size != this.size) {

            String oldClass = this.size.getClassName();
            if (oldClass != null) {
                getElement().removeClass(oldClass);
            }

            this.size = size;
            String newClass = size.getClassName();
            if (newClass != null) {
                getElement().addClass(newClass);
            }
        }
        return this;
    }
}
