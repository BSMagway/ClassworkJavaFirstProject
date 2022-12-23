package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Office {
    private int id;
    private String location;
    private String phone1;
    private String phone2;

    public Office(int id) {
        this.id = id;
    }
}
