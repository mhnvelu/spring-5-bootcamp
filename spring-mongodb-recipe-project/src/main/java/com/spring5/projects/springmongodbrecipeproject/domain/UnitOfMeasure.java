package com.spring5.projects.springmongodbrecipeproject.domain;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class UnitOfMeasure {

    private String id;

    private String uom;

}
