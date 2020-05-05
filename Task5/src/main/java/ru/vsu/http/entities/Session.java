package ru.vsu.http.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@AllArgsConstructor
public class Session {

    private UUID uuid;
    private Date firstVisitDate;

    public Session(UUID uuid) {
        this.uuid = uuid;
        this.firstVisitDate = new Date();
    }
}
