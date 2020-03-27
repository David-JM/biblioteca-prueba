package com.ceiba.biblioteca.dominio.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

public final class DateUtility {

    private DateUtility() {
    }

    public static Date convertLocalDateToJavaUtilDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
    }
}
