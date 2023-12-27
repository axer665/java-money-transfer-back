package ru.netology.transferer.enums;

import java.util.EnumMap;
import java.util.Map;

public class transactionErrorsMap {
    EnumMap<ransacrionErrors, String> errorsList = new EnumMap(ransacrionErrors.class);
    public Map<ransacrionErrors, String> getTest() {
        errorsList.put(ransacrionErrors.CARD_FROM_NOT_FOUND, "text");
        return null;
    }
}
