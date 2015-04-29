package com.demis.smart.contact.service.converter;


import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class SortConverter {

    public static final Sort convert(List<com.demis.smart.contact.Sort> sorts) {
        List<Sort.Order> dataSorts = new ArrayList<Sort.Order>();
        for (com.demis.smart.contact.Sort sort: sorts) {
            dataSorts.add(new Sort.Order(sort.isAscending()? Sort.Direction.ASC: Sort.Direction.DESC, sort.getProperty()));
        }
        if (dataSorts.isEmpty()) {
            return null;
        }
        else {
            return new Sort(dataSorts);
        }
    }
}
