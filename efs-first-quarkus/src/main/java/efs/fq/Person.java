package efs.fq;

import java.math.BigDecimal;
import java.util.Date;


import io.quarkus.mongodb.panache.PanacheMongoEntity;

public class Person extends PanacheMongoEntity {

    public String name;

    public BigDecimal age;

    public Date createAt;

    public Date modifiedAt;
    
}
