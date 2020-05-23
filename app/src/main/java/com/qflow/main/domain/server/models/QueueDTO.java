package com.qflow.main.domain.server.models;

public class QueueDTO {
    String name;
    String description;
    Integer capacity;
    String business;

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public String getBusiness() {
        return business;
    }


    public static final class QueueDTOBuilder {
        String name;
        String description;
        Integer capacity;
        String business;

        private QueueDTOBuilder() {
        }

        public static QueueDTOBuilder aQueueDTO() {
            return new QueueDTOBuilder();
        }

        public QueueDTOBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public QueueDTOBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        public QueueDTOBuilder withCapacity(Integer capacity) {
            this.capacity = capacity;
            return this;
        }

        public QueueDTOBuilder withBusiness(String business) {
            this.business = business;
            return this;
        }

        public QueueDTO build() {
            QueueDTO queueDTO = new QueueDTO();
            queueDTO.business = this.business;
            queueDTO.capacity = this.capacity;
            queueDTO.description = this.description;
            queueDTO.name = this.name;
            return queueDTO;
        }
    }
}
