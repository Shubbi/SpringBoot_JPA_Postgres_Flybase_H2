package com.vishal.springbootjpapostgresql.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.vishal.springbootjpapostgresql.dto.contract.TutorialContract;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

public enum TutorialDto {
    ;
    public enum RequestResponse {
        ;
        @Data
        @Builder
        @NoArgsConstructor
        @AllArgsConstructor
        @JsonInclude(JsonInclude.Include.NON_NULL)
        public static class Tutorial implements TutorialContract
        {
            private Long id;

            private String title;

            private String description;

            private Boolean published;

            private String phone;

            public Tutorial(String title, String description, boolean published, String phone) {
                this.title = title;
                this.description = description;
                this.published = published;
                this.phone = phone;
            }
        }
    }

    public static <T extends TutorialContract> RequestResponse.Tutorial from(T tutorialContract) {
        return RequestResponse.Tutorial.builder()
                .id(tutorialContract.getId())
                .title(tutorialContract.getTitle())
                .description(tutorialContract.getDescription())
                .published(tutorialContract.getPublished())
                .phone(tutorialContract.getPhone())
                .build();
    }

    public static <T extends TutorialContract> List<RequestResponse.Tutorial> fromList(List<T> tutorialContractList ) {
        List<RequestResponse.Tutorial> tutorialList = new ArrayList<>();
        tutorialContractList.forEach(tutorialContract -> tutorialList.add(from(tutorialContract)));
        return tutorialList;
    }
}