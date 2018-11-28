package com.vaadin.flow.demo;

import javax.servlet.http.HttpServletResponse;

import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.NotFoundException;
import com.vaadin.flow.router.RouteNotFoundError;
import com.vaadin.flow.router.SessionRouteRegistry;
import com.vaadin.flow.server.VaadinSession;
import com.vaadin.flow.theme.NoTheme;

@Tag(Tag.DIV)
@NoTheme
public class NoRouteError extends RouteNotFoundError {

    @Override
    public int setErrorParameter(BeforeEnterEvent event,
            ErrorParameter<NotFoundException> parameter) {
        if (!SessionRouteRegistry
                .sessionRegistryExists(VaadinSession.getCurrent())) {
            event.rerouteTo(Login.class);
            return HttpServletResponse.SC_NO_CONTENT;
        }
        getElement().appendChild(
                new Span("No content found for given URL").getElement());
        return HttpServletResponse.SC_NOT_FOUND;
    }
}