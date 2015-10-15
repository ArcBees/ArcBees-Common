package com.arcbees.ui;

import com.google.gwt.dom.client.Element;
import com.google.gwt.user.client.ui.HTMLPanel;
import com.google.gwt.user.client.ui.HasOneWidget;
import com.google.gwt.user.client.ui.HasVisibility;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.gwt.user.client.ui.Widget;

/*
 * Panel that wraps a GWT simple panel to be replaced by the widgetToAttach. This will make sure that the DIV element
 * is being replaced rather than having the widget inserted in it.
 *
 * The result of this will be to have a better, cleaner, dom.
 */
public class ReplacePanel implements IsWidget, HasOneWidget, HasVisibility {
    static class WrongParentTypeException extends RuntimeException {
        WrongParentTypeException(String message) {
            super(message);
        }
    }

    private IsWidget widget;

    private String style;

    public ReplacePanel() {
        widget = new SimplePanel();
    }

    @Override
    public Widget asWidget() {
        return widget.asWidget();
    }

    @Override
    public Widget getWidget() {
        return widget.asWidget();
    }

    @Override
    public void setWidget(Widget widgetToAttach) {
        Widget parentAsWidget = widget.asWidget().getParent();
        if (!(parentAsWidget instanceof HTMLPanel)) {
            throw new WrongParentTypeException("The parent of ReplacePanel must be of type HTMLPanel");
        }

        HTMLPanel parent = (HTMLPanel) parentAsWidget;

        parent.addAndReplaceElement(widgetToAttach, (Element) widget.asWidget().getElement());

        widget = widgetToAttach;

        if (style != null && !style.isEmpty()) {
            widget.asWidget().addStyleName(style);
        }
    }

    @Override
    public void setWidget(IsWidget widget) {
        setWidget(widget.asWidget());
    }

    @Override
    public boolean isVisible() {
        return asWidget().isVisible();
    }

    @Override
    public void setVisible(boolean visible) {
        asWidget().setVisible(visible);
    }

    public void setAddStyleName(String style) {
        this.style = style;
    }
}
