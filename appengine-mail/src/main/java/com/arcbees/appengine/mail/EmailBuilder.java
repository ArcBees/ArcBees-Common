/*
 * Copyright 2013 ArcBees Inc.
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

package com.arcbees.appengine.mail;

public class EmailBuilder {
    public static class MailBuilderTo {
        private final String to;

        private MailBuilderTo(String to) {
            this.to = to;
        }

        public MailBuilderFromAddress fromAddress(String from) {
            return new MailBuilderFromAddress(from, this);
        }
    }

    public static final String DEFAULT_PERSONAL = "";

    public static class MailBuilderFromAddress {
        private final String fromAddress;
        private final MailBuilderTo mailBuilderTo;

        private String replyToAddress;
        private String subject;
        private String body;
        private String fromPersonal;

        private MailBuilderFromAddress(String fromAddress,
                                       MailBuilderTo mailBuilderTo) {
            this.body = "";
            this.subject = "";
            this.fromAddress = fromAddress;
            this.fromPersonal = DEFAULT_PERSONAL;
            this.replyToAddress = "";
            this.mailBuilderTo = mailBuilderTo;
        }

        public MailBuilderFromAddress subject(String subject) {
            this.subject = subject;

            return this;
        }

        public MailBuilderFromAddress body(String body) {
            this.body = body;

            return this;
        }

        public MailBuilderFromAddress fromPersonal(String fromPersonal) {
            this.fromPersonal = fromPersonal;

            return this;
        }

        public MailBuilderFromAddress replyToAddress(String replyToAddress) {
            this.replyToAddress = replyToAddress;

            return this;
        }

        public Email build() {
            return new EmailImpl(this);
        }
    }

    private static class EmailImpl implements Email {
        private final String subject;
        private final String to;
        private final String fromAddress;
        private final String fromPersonal;
        private final String body;
        private final String replyToAddress;

        private EmailImpl(MailBuilderFromAddress builder) {
            this.to = builder.mailBuilderTo.to;
            this.fromAddress = builder.fromAddress;
            this.subject = builder.subject;
            this.fromPersonal = builder.fromPersonal;
            this.body = builder.body;
            this.replyToAddress = builder.replyToAddress;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public String getFromAddress() {
            return fromAddress;
        }

        @Override
        public String getFromPersonal() {
            return fromPersonal;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public String getBody() {
            return body;
        }

        @Override
        public String getReplyToAddress() {
            return replyToAddress;
        }
    }

    public static MailBuilderTo to(String to) {
        return new MailBuilderTo(to);
    }
}
