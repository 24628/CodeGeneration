package io.swagger.service;

import io.swagger.model.Entity.DayLimitEntity;
import io.swagger.repository.IDayLimitDTO;
import io.swagger.repository.IUserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DayLimitService {

    @Autowired
    private IDayLimitDTO dayLimitDTO;

    public void save(DayLimitEntity d) {
        dayLimitDTO.save(d);
    }
}
