/**
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

        public MailBuilderFrom from(String from) {
            return new MailBuilderFrom(from, this);
        }
    }

    public static class MailBuilderFrom {
        private final String from;
        private final MailBuilderTo mailBuilderTo;
        private String subject;
        private String body;

        private MailBuilderFrom(String from,
                                MailBuilderTo mailBuilderTo) {
            this.body = "";
            this.subject = "";
            this.from = from;
            this.mailBuilderTo = mailBuilderTo;
        }

        public MailBuilderFrom subject(String subject) {
            this.subject = subject;

            return this;
        }

        public MailBuilderFrom body(String body) {
            this.body = body;

            return this;
        }

        public Email build() {
            return new EmailImpl(mailBuilderTo.to, from, subject, body);
        }
    }

    private static class EmailImpl implements Email {
        private final String subject;
        private final String to;
        private final String from;
        private final String body;

        private EmailImpl(String to, String from, String subject, String body) {
            this.to = to;
            this.from = from;
            this.subject = subject;
            this.body = body;
        }

        @Override
        public String getTo() {
            return to;
        }

        @Override
        public String getFrom() {
            return from;
        }

        @Override
        public String getSubject() {
            return subject;
        }

        @Override
        public String getBody() {
            return body;
        }
    }

    public static MailBuilderTo to(String to) {
        return new MailBuilderTo(to);
    }
}
