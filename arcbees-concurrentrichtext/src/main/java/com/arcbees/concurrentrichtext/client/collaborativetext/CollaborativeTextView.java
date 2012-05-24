/*
 * Copyright 2011 ArcBees Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.arcbees.concurrentrichtext.client.collaborativetext;

import com.arcbees.concurrentrichtext.client.collaborativetext.CollaborativeTextPresenter.MyView;
import com.arcbees.core.client.mvp.ViewWithUiHandlers;
import com.arcbees.core.client.mvp.uihandlers.UiHandlersStrategy;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

public class CollaborativeTextView extends ViewWithUiHandlers<CollaborativeTextUiHandlers> implements MyView {

    public interface Binder extends UiBinder<Widget, CollaborativeTextView> {}

    @UiField
    TextArea textArea;
    @UiField
    TextBox textTitle;
    @UiField
    Button joinButton;

    private final Widget widget;

    @Inject
    public CollaborativeTextView(final Binder binder,
            final UiHandlersStrategy<CollaborativeTextUiHandlers> uiHandlersStrategy) {
        super(uiHandlersStrategy);
        
        widget = binder.createAndBindUi(this);
        textArea.setReadOnly(true);
    }

    @UiHandler("joinButton")
    void onJoinButtonClicked(ClickEvent event) {
        joinButton.setEnabled(false);
        getUiHandlers().onJoinChannel();
    }

    @Override
    public void leaveEditMode() {
        joinButton.setEnabled(true);
    }

    @Override
    public void setEditable(boolean editable) {
        textArea.setReadOnly(!editable);
    }

    @Override
    public Widget asWidget() {
        return widget;
    }

    @Override
    public String getText() {
        return textArea.getText();
    }

    @Override
    public void setText(String text) {
        textArea.setText(text);
    }

    @Override
    public String getTitle() {
        return textTitle.getText();
    }

    @Override
    public void setTitle(String title) {
        textTitle.setText(title);
    }

    @Override
    public CursorOffset getCursorOffset() {
        int pos = textArea.getCursorPos();
        int start = pos;
        int end = pos + textArea.getSelectionLength();

        return new CursorOffset(pos, start, end);
    }

    @Override
    public void setCursorOffset(CursorOffset cursor) {
        if (cursor.getCursorPosition() > textArea.getText().length()) {
            textArea.setCursorPos(textArea.getText().length());
            textArea.setSelectionRange(textArea.getText().length(), 0);
        } else {
            textArea.setCursorPos(cursor.getCursorPosition());
            textArea.setSelectionRange(cursor.getSelectionStart(),
                    cursor.getSelectionEnd() - cursor.getSelectionStart());
        }
    }
}
