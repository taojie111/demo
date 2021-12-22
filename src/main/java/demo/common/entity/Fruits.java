package demo.common.entity;

import lombok.Data;

@Data
public class Fruits {

    public int weight;

    public Fruits(int weight) {
        this.weight = weight;
    }
}
