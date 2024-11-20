create table IF NOT EXISTS rider
(
    id   int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name varchar(30)
);

create table IF NOT EXISTS registration_payment
(
    id        int GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rider_id  int unique ,
    amount    int,
    timestamp timestamp,
    foreign key (rider_id) references rider (id)
);

CREATE TABLE IF NOT EXISTS EVENT_PUBLICATION
(
  ID               UUID NOT NULL,
  COMPLETION_DATE  TIMESTAMP(9) WITH TIME ZONE,
  EVENT_TYPE       VARCHAR(512) NOT NULL,
  LISTENER_ID      VARCHAR(512) NOT NULL,
  PUBLICATION_DATE TIMESTAMP(9) WITH TIME ZONE NOT NULL,
  SERIALIZED_EVENT VARCHAR(4000) NOT NULL,
  PRIMARY KEY (ID)
)