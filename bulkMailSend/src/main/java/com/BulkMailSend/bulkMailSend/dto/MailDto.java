package com.BulkMailSend.bulkMailSend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MailDto {

    private String subject;
    private String body;
    private List<String> mailIds;

    public static interface SubjectStep {
        BodyStep withSubject(String subject);
    }

    public static interface BodyStep {
        MailIdsStep withBody(String body);
    }

    public static interface MailIdsStep {
        BuildStep withMailIds(List<String> mailIds);
    }

    public static interface BuildStep {
        MailDto build();
    }

    public static class Builder implements SubjectStep, BodyStep, MailIdsStep, BuildStep {
        private String subject;

        private String body;

        private List<String> mailIds;

        private Builder() {
        }

        public static SubjectStep mailDto() {
            return new Builder();
        }

        @Override
        public BodyStep withSubject(String subject) {
            this.subject = subject;
            return this;
        }

        @Override
        public MailIdsStep withBody(String body) {
            this.body = body;
            return this;
        }

        @Override
        public BuildStep withMailIds(List<String> mailIds) {
            this.mailIds = mailIds;
            return this;
        }

        @Override
        public MailDto build() {
            return new MailDto(
                this.subject,
                this.body,
                this.mailIds
            );
        }
    }
}
