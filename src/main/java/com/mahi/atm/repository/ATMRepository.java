package com.mahi.atm.repository;

import com.mahi.atm.model.Denomination;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
@Repository
public class ATMRepository {
    private Map<Denomination, Integer> cashCount = new HashMap<>();

    public void setNotes(Denomination denomination, Integer amount) {
        cashCount.put(denomination, amount);
    }

    public void insertNotes(Denomination denomination, Integer amount) {
        Integer count = getCount(denomination);
        cashCount.put(denomination, count + amount);
    }

    public void withdrawNotes(Denomination denomination, Integer amount) {
        Integer count = getCount(denomination);
        cashCount.put(denomination, count - amount);
    }

    public Integer getCount(Denomination denomination) {
        return cashCount.get(denomination);
    }
    public boolean isInitialised() {
        return !cashCount.isEmpty();
    }
}
